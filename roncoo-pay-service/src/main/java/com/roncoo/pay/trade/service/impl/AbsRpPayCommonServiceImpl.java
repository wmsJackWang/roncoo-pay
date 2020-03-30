package com.roncoo.pay.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.entity.RpTradePaymentOrder;
import com.roncoo.pay.trade.entity.RpTradePaymentRecord;
import com.roncoo.pay.trade.enums.TradeStatusEnum;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.trade.service.RpPayCommonService;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.utils.WeixinConfigUtil;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.entity.RpUserInfo;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.entity.RpUserPayInfo;
import com.roncoo.pay.user.enums.FundInfoTypeEnum;
import com.roncoo.pay.user.exception.UserBizException;
import com.roncoo.pay.user.service.RpPayWayService;
import com.roncoo.pay.user.service.RpUserInfoService;
import com.roncoo.pay.user.service.RpUserPayConfigService;
import com.roncoo.pay.user.service.RpUserPayInfoService;

public abstract class AbsRpPayCommonServiceImpl implements RpPayCommonService {

	@Autowired
	private RpUserPayConfigService rpUserPayConfigService;

	@Autowired
	private RpPayWayService rpPayWayService;

	@Autowired
	private RpUserInfoService rpUserInfoService;

	@Autowired
	private RpTradePaymentOrderDao rpTradePaymentOrderDao;

	@Autowired
	private RpTradePaymentRecordDao rpTradePaymentRecordDao;

	@Autowired
	private RpTradePaymentManagerService rpTradePaymentManagerService;

	@Autowired
	private RpUserPayInfoService rpUserPayInfoService;

	public RpUserPayConfig rpUserPayConfig = null;

	// 根据支付产品及支付方式获取费率
	public RpPayWay payWay = null;

	// 商户信息
	public RpUserInfo rpUserInfo = null;

	public RpTradePaymentOrder rpTradePaymentOrder = null;

	public RpTradePaymentRecord rpTradePaymentRecord = null;

	// 支付用户
	public RpUserPayInfo rpUserPayInfo = null;

	public String appid = "";
	public String mch_id = "";
	public String partnerKey = "";

	public void payOrderBussiness(JSONObject payOrder) {
		rpUserPayConfig = rpUserPayConfigService.getByPayKey(payOrder.getString("payKey"));
		if (rpUserPayConfig == null) {
			throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
		}

		PayTypeEnum payType = null;

		payWay = rpPayWayService.getByPayWayId(payOrder.getString("payWayId"));
		if (payWay == null) {
			throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
		}

		payType = PayTypeEnum.getEnum(payWay.getPayTypeCode());
		String payWayCode = payWay.getPayWayCode();// 支付方式

		String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
		rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
		if (rpUserInfo == null) {
			throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
		}

		rpTradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo,
				payOrder.getString("orderNo"));
		if (rpTradePaymentOrder == null) {
			rpTradePaymentOrder = rpTradePaymentManagerService.sealRpTradePaymentOrder(payOrder, merchantNo,
					rpUserInfo.getUserName(), rpUserPayConfig.getFundIntoType(), payType);
			rpTradePaymentOrderDao.insert(rpTradePaymentOrder);
		} else {
			if (TradeStatusEnum.SUCCESS.name().equals(rpTradePaymentOrder.getStatus())) {
				throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单已支付成功,无需重复支付");
			}
			if (rpTradePaymentOrder.getOrderAmount().compareTo(payOrder.getBigDecimal("orderPrice")) != 0) {
				rpTradePaymentOrder.setOrderAmount(payOrder.getBigDecimal("orderPrice"));// 如果金额不一致,修改金额为最新的金额
			}
		}

		rpTradePaymentOrder.setPayTypeCode(payType.name());
		rpTradePaymentOrder.setPayTypeName(payType.getDesc());
		rpTradePaymentOrder.setPayWayCode(payWay.getPayWayCode());
		rpTradePaymentOrder.setPayWayName(payWay.getPayWayName());
		rpTradePaymentOrderDao.update(rpTradePaymentOrder);

		rpTradePaymentRecord = rpTradePaymentManagerService.sealRpTradePaymentRecord(rpTradePaymentOrder, payWay);
		rpTradePaymentRecordDao.insert(rpTradePaymentRecord);

		if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())) {// 商户收款
			// 根据资金流向获取配置信息
			rpUserPayInfo = rpUserPayInfoService.getByUserNo(rpTradePaymentOrder.getMerchantNo(), payWayCode);
			appid = rpUserPayInfo.getAppId();
			mch_id = rpUserPayInfo.getMerchantId();
			partnerKey = rpUserPayInfo.getPartnerKey();
		} else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(rpTradePaymentOrder.getFundIntoType())) {// 平台收款
			appid = WeixinConfigUtil.readConfig("appId");
			mch_id = WeixinConfigUtil.readConfig("mch_id");
			partnerKey = WeixinConfigUtil.readConfig("partnerKey");
		}

	}

}

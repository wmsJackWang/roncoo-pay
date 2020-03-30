package com.roncoo.pay.trade.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.enums.PayTypeEnum;
import com.roncoo.pay.common.core.utils.SignUtils;
import com.roncoo.pay.common.xxpay.common.constant.PayConstant;
import com.roncoo.pay.common.xxpay.common.util.RpcUtil;
import com.roncoo.pay.common.xxpay.common.util.XXPayUtil;
import com.roncoo.pay.notify.service.RpNotifyService;
import com.roncoo.pay.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.entity.RpTradePaymentOrder;
import com.roncoo.pay.trade.entity.RpTradePaymentRecord;
import com.roncoo.pay.trade.entity.weixinpay.WeiXinPrePay;
import com.roncoo.pay.trade.enums.TradeStatusEnum;
import com.roncoo.pay.trade.enums.weixinpay.WeiXinTradeTypeEnum;
import com.roncoo.pay.trade.enums.weixinpay.WeixinTradeStateEnum;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.trade.service.RpPayChannelWechatService;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.utils.WeiXinPayUtils;
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

@Service("rpPayChannelWechatServiceImpl")
public class RpPayChannelWechatServiceImpl implements RpPayChannelWechatService {

	private static final Logger log = LoggerFactory.getLogger(RpPayChannelWechatServiceImpl.class);

	private static String logPrefix = "【微信支付统一下单】";

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

	@Autowired
	private RpNotifyService rpNotifyService;

	public String doWechatRequest(String payTypeCode, JSONObject payOrder, String resKey) {
		// TODO Auto-generated method stub

		Map<String, Object> result = null;

		switch (payTypeCode) {
		case PayConstant.PAY_CHANNEL_WX_APP:
			result = doWechatReq(WeiXinTradeTypeEnum.APP.name(), payOrder);
			break;
		case PayConstant.PAY_CHANNEL_WX_JSAPI:
			result = doWechatReq(WeiXinTradeTypeEnum.JSAPI.name(), payOrder);
			break;
		case PayConstant.PAY_CHANNEL_WX_NATIVE:
			result = doWechatReq(WeiXinTradeTypeEnum.NATIVE.name(), payOrder);
			break;
		case PayConstant.PAY_CHANNEL_WX_MWEB:
			result = doWechatReq(WeiXinTradeTypeEnum.MWEB.name(), payOrder);
			break;
		default:
			result = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL,
					"【微信】不支持的支付渠道类型[payTypeCode=" + payTypeCode + "]", null, null);
		}

		// 在这里判断 业务是否成功，根据result来判断， 根据业务成功与否，决定返回的数据不同
		String s = RpcUtil.mkRet(result);
		if (s == null) {
			return XXPayUtil.makeRetData(XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS,
					(String) result.get(PayConstant.RETURN_PARAM_RETMSG), PayConstant.RETURN_VALUE_FAIL, "0111",
					"调用微信支付失败"), resKey);
		}
		Map<String, Object> map = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "",
				PayConstant.RETURN_VALUE_SUCCESS, null);
		map.putAll((Map) result.get("bizResult"));
		return XXPayUtil.makeRetData(map, resKey);

	}

	@Transactional(rollbackFor = Exception.class)
	private Map<String, Object> doWechatReq(String tradeType, JSONObject payOrder) {
		// TODO Auto-generated method stub
		
		log.info("tradeType:{}",tradeType);

		RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payOrder.getString("payKey"));
		if (rpUserPayConfig == null) {
			throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
		}

		// 根据支付产品及支付方式获取费率
		RpPayWay payWay = null;
		PayTypeEnum payType = null;

		payWay = rpPayWayService.getByPayWayId(payOrder.getString("payWayId"));
		if (payWay == null) {
			throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
		}

		payType = PayTypeEnum.getEnum(payWay.getPayTypeCode());
		String payWayCode = payWay.getPayWayCode();// 支付方式

		String merchantNo = rpUserPayConfig.getUserNo();// 商户编号
		RpUserInfo rpUserInfo = rpUserInfoService.getDataByMerchentNo(merchantNo);
		if (rpUserInfo == null) {
			throw new UserBizException(UserBizException.USER_IS_NULL, "用户不存在");
		}

		RpTradePaymentOrder rpTradePaymentOrder = rpTradePaymentOrderDao
				.selectByMerchantNoAndMerchantOrderNo(merchantNo, payOrder.getString("orderNo"));
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

		RpTradePaymentRecord rpTradePaymentRecord = rpTradePaymentManagerService
				.sealRpTradePaymentRecord(rpTradePaymentOrder, payWay);
		rpTradePaymentRecordDao.insert(rpTradePaymentRecord);
		RpUserPayInfo rpUserPayInfo = null;

		// 微信支付
		String appid = "";
		String mch_id = "";
		String partnerKey = "";
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

		String openId = null;
		if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_JSPAI))
			openId = payOrder.getString("openId");
		log.info("tradetype:{}   ,  openId：{}",tradeType,openId);
		
		WeiXinPrePay weiXinPrePay = rpTradePaymentManagerService.sealWeixinPerPay(appid, mch_id,
				rpTradePaymentOrder.getProductName(), rpTradePaymentOrder.getRemark(),
				rpTradePaymentRecord.getBankOrderNo(), rpTradePaymentOrder.getOrderAmount(),
				rpTradePaymentOrder.getOrderTime(), rpTradePaymentOrder.getOrderPeriod(),
				WeiXinTradeTypeEnum.getEnum(tradeType), rpTradePaymentRecord.getBankOrderNo(), openId,
				rpTradePaymentOrder.getOrderIp());
		String prePayXml = WeiXinPayUtils.getPrePayXml(weiXinPrePay, partnerKey);
		log.info("扫码支付，微信请求报文:{}", prePayXml);
		// 调用微信支付的功能,获取微信支付code_url
		Map<String, Object> prePayRequest = WeiXinPayUtils.httpXmlRequest(WeixinConfigUtil.readConfig("prepay_url"),
				"POST", prePayXml);
		log.info("扫码支付，微信返回报文:{}", prePayRequest.toString());

		Map<String, Object> map = new HashMap<>();

		if (WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("return_code"))
				&& WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("result_code"))) {
			String weiXinPrePaySign = WeiXinPayUtils.geWeiXintPrePaySign(appid, mch_id, weiXinPrePay.getDeviceInfo(),
					tradeType, prePayRequest, partnerKey);
			String codeUrl = String.valueOf(prePayRequest.get("code_url"));
			String prepayid = String.valueOf(prePayRequest.get("prepay_id"));
			log.info("{} >>> 下单成功", logPrefix);
			map.put("payOrderId", rpTradePaymentOrder.getMerchantOrderNo());
			map.put("prepayId", prepayid);

			if (prePayRequest.get("sign").equals(weiXinPrePaySign)) {
				rpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
				rpTradePaymentRecordDao.update(rpTradePaymentRecord);

				// 微信的不同交易 类型 ， 返回的数据是不太一样的。
				switch (tradeType) {
				case PayConstant.WxConstant.TRADE_TYPE_NATIVE: {
					map.put("codeUrl", String.valueOf(prePayRequest.get("code_url"))); // 二维码支付链接
					break;
				}
				case PayConstant.WxConstant.TRADE_TYPE_APP: {
					Map<String, String> payInfo = new HashMap<>();
					String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
					String nonceStr = String.valueOf(System.currentTimeMillis());
					// APP支付绑定的是微信开放平台上的账号，APPID为开放平台上绑定APP后发放的参数
					String appId = appid;
					Map<String, String> configMap = new HashMap<>();
					// 此map用于参与调起sdk支付的二次签名,格式全小写，timestamp只能是10位,格式固定，切勿修改
					String partnerId = mch_id;

					configMap.put("prepayid", prepayid);
					configMap.put("partnerid", partnerId);
					String packageValue = "Sign=WXPay";
					configMap.put("package", packageValue);
					configMap.put("timestamp", timestamp);
					configMap.put("noncestr", nonceStr);
					configMap.put("appid", appId);
					// 此map用于客户端与微信服务器交互
					payInfo.put("sign", SignUtils.createSign(configMap, partnerKey, null));
					payInfo.put("prepayid", prepayid);
					payInfo.put("partnerid", partnerId);
					payInfo.put("appid", appId);
					payInfo.put("package", packageValue);
					payInfo.put("timestamp", timestamp);
					payInfo.put("noncestr", nonceStr);
					map.put("payParams", payInfo);
					break;
				}
				case PayConstant.WxConstant.TRADE_TYPE_JSPAI: {
					Map<String, String> payInfo = new HashMap<>();
					String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
					String nonceStr = String.valueOf(System.currentTimeMillis());
					payInfo.put("appId", appid);
					// 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
					payInfo.put("timeStamp", timestamp);
					payInfo.put("nonceStr", nonceStr);
					payInfo.put("package", "prepay_id=" + prepayid);
					payInfo.put("signType", "MD5");
					payInfo.put("paySign", SignUtils.createSign(payInfo, partnerKey, null));
					map.put("payParams", payInfo);
					break;
				}
				case PayConstant.WxConstant.TRADE_TYPE_MWEB: {
					map.put("payUrl", String.valueOf(prePayRequest.get("mweb_url"))); // h5支付链接地址
					break;
				}
				}
			} else {
				throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "微信返回结果签名异常");
			}
		} else {
			throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "请求微信异常");
		}

		rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
		return RpcUtil.createBizResult(null, map);
	}

}

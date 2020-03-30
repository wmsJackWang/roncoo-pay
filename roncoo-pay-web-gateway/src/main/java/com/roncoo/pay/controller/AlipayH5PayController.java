package com.roncoo.pay.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.utils.DateUtils;
import com.roncoo.pay.common.xxpay.common.constant.PayConstant;
import com.roncoo.pay.common.xxpay.common.util.XXPayUtil;
import com.roncoo.pay.controller.common.BaseController;
import com.roncoo.pay.notify.service.RpNotifyService;
import com.roncoo.pay.service.CnpPayService;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.trade.service.RpPayChannelAlipayService;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.service.RpTradePaymentQueryService;
import com.roncoo.pay.trade.utils.MerchantApiUtil;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.entity.RpUserPayConfig;
import com.roncoo.pay.user.exception.UserBizException;
import com.roncoo.pay.user.service.RpPayWayService;
import com.roncoo.pay.user.service.RpUserPayConfigService;

@Controller
@RequestMapping(value = "/AlipayH5Pay")
public class AlipayH5PayController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(AlipayH5PayController.class);

	@Autowired
	private RpTradePaymentManagerService rpTradePaymentManagerService;

	@Autowired
	private RpTradePaymentQueryService rpTradePaymentQueryService;

	@Autowired
	private RpUserPayConfigService rpUserPayConfigService;

	@Autowired
	private CnpPayService cnpPayService;

	@Autowired
	private RpNotifyService rpNotifyService;

	@Autowired
	private RpPayWayService rpPayWayService;
	
	@Autowired
	private RpPayChannelAlipayService rpPayChannelAlipayService;

	@RequestMapping("/initPay")
	@Override
	public String pay(Model model, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		logger.info("======>进入支付宝wap支付");
		// Map<String, Object> paramMap = new HashMap<String, Object>();
		// 验证参数通过,返回JSONObject对象
		JSONObject payOrder = new JSONObject();

		// ===================================================获取参数

		// 获取商户传入参数
		String payKey = getString_UrlDecode_UTF8("payKey"); // 企业支付KEY
		payOrder.put("payKey", payKey);
		String productName = getString_UrlDecode_UTF8("productName"); // 商品名称
		payOrder.put("productName", productName);
		String orderNo = getString_UrlDecode_UTF8("orderNo"); // 订单编号
		payOrder.put("orderNo", orderNo);
		String orderPriceStr = getString_UrlDecode_UTF8("orderPrice"); // 订单金额 , 单位:元
		payOrder.put("orderPrice", orderPriceStr);
		String payWayCode = getString_UrlDecode_UTF8("payWayCode"); // 支付方式编码 支付宝: ALIPAY 微信:WEIXIN
		payOrder.put("payWayCode", payWayCode);
		String payWayId = getString_UrlDecode_UTF8("payWayId"); // 支付通道id，是对支付渠道下所有支付方式抽象的最小单位
		payOrder.put("payWayId", payWayId);
		String orderIp = getString_UrlDecode_UTF8("orderIp"); // 下单IP
		payOrder.put("orderIp", orderIp);
		String orderDateStr = getString_UrlDecode_UTF8("orderDate"); // 订单日期
		payOrder.put("orderDate", orderDateStr);
		String orderTimeStr = getString_UrlDecode_UTF8("orderTime"); // 订单日期
		payOrder.put("orderTime", orderTimeStr);
		String orderPeriodStr = getString_UrlDecode_UTF8("orderPeriod"); // 订单有效期
		payOrder.put("orderPeriod", orderPeriodStr);
		String returnUrl = getString_UrlDecode_UTF8("returnUrl"); // 页面通知返回url
		payOrder.put("returnUrl", returnUrl);
		String notifyUrl = getString_UrlDecode_UTF8("notifyUrl"); // 后台消息通知Url
		payOrder.put("notifyUrl", notifyUrl);
		String remark = getString_UrlDecode_UTF8("remark"); // 支付备注
		payOrder.put("remark", remark);
		String quit_url = getString_UrlDecode_UTF8("quit_url"); // 支付备注
		payOrder.put("quit_url", quit_url);
		String sign = getString_UrlDecode_UTF8("sign"); // 签名

		String field1 = getString_UrlDecode_UTF8("field1"); // 扩展字段1
		payOrder.put("field1", field1);
		String field2 = getString_UrlDecode_UTF8("field2"); // 扩展字段2
		payOrder.put("field2", field2);
		String field3 = getString_UrlDecode_UTF8("field3"); // 扩展字段3
		payOrder.put("field3", field3);
		String field4 = getString_UrlDecode_UTF8("field4"); // 扩展字段4
		payOrder.put("field4", field4);
		String field5 = getString_UrlDecode_UTF8("field5"); // 扩展字段5
		payOrder.put("field5", field5);

		logger.info("支付宝wap支付,接收参数:{}", payOrder.toJSONString());
		Date orderDate = DateUtils.parseDate(orderDateStr, "yyyyMMdd");
		Date orderTime = DateUtils.parseDate(orderTimeStr, "yyyyMMddHHmmss");
		Integer orderPeriod = Integer.valueOf(orderPeriodStr);

		RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByPayKey(payKey);

		RpPayWay rpPayWay = rpPayWayService.getByPayWayId(payWayId);
		payWayCode = rpPayWay.getPayWayCode();

		if (rpUserPayConfig == null) {
			throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR, "用户支付配置有误");
		}
		logger.info("支付配置信息 rpUserPayConfig：{}", rpUserPayConfig.toString());

		cnpPayService.checkIp(rpUserPayConfig, httpServletRequest);// ip校验

		// 验证签名数据
//		boolean verifyFlag = XXPayUtil.verifyPaySign(payOrder, rpUserPayConfig.getPaySecret());
//		if (!verifyFlag) {
//			return XXPayUtil.makeRetFail(XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "订单签名异常", null, null));
//		}

		 if (!MerchantApiUtil.isRightSign(payOrder, rpUserPayConfig.getPaySecret(),sign)) {
			 throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR, "订单签名异常");
		 }
		
		payOrder.put("payWayCode", payWayCode);
		String result = rpPayChannelAlipayService.doAlipayRequest(rpPayWay.getPayTypeCode(), payOrder,
				rpUserPayConfig.getPaySecret());
		System.out.println(result);
		JSONObject jres = JSON.parseObject(result);

        model.addAttribute("payMessage", jres.getString("payUrl"));
		return "toPay";
	}

}

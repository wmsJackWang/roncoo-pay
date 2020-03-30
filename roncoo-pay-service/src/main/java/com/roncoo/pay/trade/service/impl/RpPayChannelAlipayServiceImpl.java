package com.roncoo.pay.trade.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.roncoo.pay.common.xxpay.common.constant.PayConstant;
import com.roncoo.pay.common.xxpay.common.util.RpcUtil;
import com.roncoo.pay.common.xxpay.common.util.XXPayUtil;
import com.roncoo.pay.notify.service.RpNotifyService;
import com.roncoo.pay.trade.dao.RpTradePaymentOrderDao;
import com.roncoo.pay.trade.dao.RpTradePaymentRecordDao;
import com.roncoo.pay.trade.exception.TradeBizException;
import com.roncoo.pay.trade.service.RpPayChannelAlipayService;
import com.roncoo.pay.trade.service.RpTradePaymentManagerService;
import com.roncoo.pay.trade.utils.alipay.config.AlipayConfigUtil;
import com.roncoo.pay.user.service.RpPayWayService;
import com.roncoo.pay.user.service.RpUserInfoService;
import com.roncoo.pay.user.service.RpUserPayConfigService;
import com.roncoo.pay.user.service.RpUserPayInfoService;

@Service("rpPayChannelAlipayServiceImpl")
public class RpPayChannelAlipayServiceImpl extends AbsRpPayCommonServiceImpl implements RpPayChannelAlipayService {

	private static final Logger log = LoggerFactory.getLogger(RpPayChannelAlipayServiceImpl.class);

	private static String logPrefix = "【支付宝支付统一下单】";

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

	@Override
	public String doAlipayRequest(String payTypeCode, JSONObject payOrder, String resKey) {
		// TODO Auto-generated method stub

		Map<String, Object> result = null;
		//支付过程的公共的订单逻辑封装在抽象 服务类的公共方法内
		this.payOrderBussiness(payOrder);
		switch (payTypeCode) {
		case PayConstant.PAY_CHANNEL_ALIPAY_MOBILE:
			result = doAliPayMobileReq(payOrder);
			break;
		case PayConstant.PAY_CHANNEL_ALIPAY_PC:
			result = doAliPayPcReq(payOrder);
			break;
		case PayConstant.PAY_CHANNEL_ALIPAY_WAP:
			result = doAliPayWapReq(payOrder);
			break;
		case PayConstant.PAY_CHANNEL_ALIPAY_QR:
			result = doAliPayQrReq(payOrder);
			break;
		default:
			result = result = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL,
					"【支付宝】不支持的支付渠道类型[payTypeCode=" + payTypeCode + "]", null, null);
			;
			break;
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

	private Map<String, Object> doAliPayQrReq(JSONObject payOrder) {
		// TODO Auto-generated method stub、
		String logPrefix = "【支付宝当面付之扫码支付下单】";
		log.info("支付宝扫码, 参数{}", rpTradePaymentOrder.toString());
		log.info("支付宝扫码, 支付宝配置参数{}", rpUserPayInfo.toString());
		AlipayClient client = new DefaultAlipayClient(AlipayConfigUtil.trade_pay_url, rpUserPayInfo.getAppId(),
				rpUserPayInfo.getRsaPrivateKey(), AlipayConfigUtil.FORMAT, AlipayConfigUtil.input_charset,
				rpUserPayInfo.getRsaPublicKey(), AlipayConfigUtil.sign_type);
		AlipayTradePrecreateRequest alipay_request = new AlipayTradePrecreateRequest();
		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(rpTradePaymentRecord.getBankOrderNo());
		model.setSubject(rpTradePaymentOrder.getProductName());

		BigDecimal orderPrice = rpTradePaymentOrder.getOrderAmount();
		model.setTotalAmount(orderPrice.toString());// 订单金额
		model.setBody(rpTradePaymentOrder.getRemark());
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(AlipayConfigUtil.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(AlipayConfigUtil.return_url);
		String payUrl = null;
		AlipayTradePrecreateResponse response;

		try {
			response = client.execute(alipay_request);
			if (response.isSuccess()) {
				payUrl = response.getQrCode();
			} else {
				log.info("支付宝调用失败,订单信息:{}", rpTradePaymentOrder.toString());
				throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "支付宝返回失败异常");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		log.info("{}生成跳转路径：payUrl={}", logPrefix, payUrl);
		log.info("{}生成请求支付宝数据,req={}", logPrefix, alipay_request.getBizModel());
		log.info("###### 商户统一下单处理完成 ######");

		// rpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
		rpTradePaymentRecordDao.update(rpTradePaymentRecord);

		log.info("###### 商户统一下单处理完成 ######");
		Map<String, Object> map = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "",
				PayConstant.RETURN_VALUE_SUCCESS, null);
		map.put("payOrderId", rpTradePaymentOrder.getMerchantOrderNo());
		map.put("payUrl", payUrl);

		rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
		return RpcUtil.createBizResult(null, map);

	}

	private Map<String, Object> doAliPayWapReq(JSONObject payOrder) {
		// TODO Auto-generated method stub
        String logPrefix = "【支付宝WAP支付下单】";
		log.info("支付宝WAP支付, 参数{}", rpTradePaymentOrder.toString());
		log.info("支付宝WAP支付, 支付宝配置参数{}", rpUserPayInfo.toString());
		AlipayClient client = new DefaultAlipayClient(AlipayConfigUtil.trade_pay_url, rpUserPayInfo.getAppId(),
				rpUserPayInfo.getRsaPrivateKey(), AlipayConfigUtil.FORMAT, AlipayConfigUtil.input_charset,
				rpUserPayInfo.getRsaPublicKey(), AlipayConfigUtil.sign_type);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(rpTradePaymentRecord.getBankOrderNo());
		model.setSubject(rpTradePaymentOrder.getProductName());

		BigDecimal orderPrice = rpTradePaymentOrder.getOrderAmount();
		model.setTotalAmount(orderPrice.toString());// 订单金额
		model.setBody(rpTradePaymentOrder.getRemark());
        model.setProductCode("QUICK_WAP_PAY");
        // 获取objParams参数
        String objParams = payOrder.getString("quit_url");
        if (StringUtils.isNotEmpty(objParams)) {
            try {
                if(StringUtils.isNotBlank(payOrder.getString("quit_url"))) {
                    model.setQuitUrl(payOrder.getString("quit_url"));
                }
            } catch (Exception e) {
                log.error("{}objParams参数格式错误！", logPrefix);
            }
        }
        
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(AlipayConfigUtil.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(AlipayConfigUtil.return_url);
		String trade_no = null;
        String payUrl = null;
		AlipayTradeWapPayResponse response;

		try {
			response = client.pageExecute(alipay_request);
			if (response.isSuccess()) {
				trade_no = response.getTradeNo();
				payUrl = response.getBody();
			} else {
				log.info("支付宝调用失败,订单信息:{}", rpTradePaymentOrder.toString());
				throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "支付宝返回失败异常");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		log.info("{}生成支付宝订单号：trade_no={}", logPrefix, trade_no);
		log.info("{}生成请求支付宝数据,req={}", logPrefix, alipay_request.getBizModel());
		log.info("###### 商户统一下单处理完成 ######");

		// rpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
		rpTradePaymentRecordDao.update(rpTradePaymentRecord);

		log.info("###### 商户统一下单处理完成 ######");
		Map<String, Object> map = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "",
				PayConstant.RETURN_VALUE_SUCCESS, null);
		map.put("payOrderId", rpTradePaymentOrder.getMerchantOrderNo());
		map.put("trade_no", trade_no);
		map.put("payUrl", payUrl);

		rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
		return RpcUtil.createBizResult(null, map);
	}

	private Map<String, Object> doAliPayPcReq(JSONObject payOrder) {
		// TODO Auto-generated method stub
		String logPrefix = "【支付宝PC支付下单】";
		log.info("支付宝PC支付, 参数{}", rpTradePaymentOrder.toString());
		log.info("支付宝PC支付, 支付宝配置参数{}", rpUserPayInfo.toString());
		AlipayClient client = new DefaultAlipayClient(AlipayConfigUtil.trade_pay_url, rpUserPayInfo.getAppId(),
				rpUserPayInfo.getRsaPrivateKey(), AlipayConfigUtil.FORMAT, AlipayConfigUtil.input_charset,
				rpUserPayInfo.getRsaPublicKey(), AlipayConfigUtil.sign_type);
		AlipayTradePagePayRequest alipay_request = new AlipayTradePagePayRequest();
		// 封装请求支付信息
		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setOutTradeNo(rpTradePaymentRecord.getBankOrderNo());
		model.setSubject(rpTradePaymentOrder.getProductName());

		BigDecimal orderPrice = rpTradePaymentOrder.getOrderAmount();
		model.setTotalAmount(orderPrice.toString());// 订单金额
		model.setBody(rpTradePaymentOrder.getRemark());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        // 获取objParams参数
        String objParams = payOrder.getString("extraParams");
        String qr_pay_mode = "2";
        String qrcode_width = "200";
        if (StringUtils.isNotEmpty(objParams)) {
            try {
                JSONObject objParamsJson = JSON.parseObject(objParams);
                qr_pay_mode = ObjectUtils.toString(objParamsJson.getString("qr_pay_mode"), "2");
                qrcode_width = ObjectUtils.toString(objParamsJson.getString("qrcode_width"), "200");
            } catch (Exception e) {
                log.error("{}objParams参数格式错误！", logPrefix);
            }
        }
        model.setQrPayMode(qr_pay_mode);
        model.setQrcodeWidth(Long.parseLong(qrcode_width));
        
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(AlipayConfigUtil.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(AlipayConfigUtil.return_url);
		String trade_no = null;
        String payUrl = null;
		AlipayTradePagePayResponse response;

		try {
			response = client.pageExecute(alipay_request);
			if (response.isSuccess()) {
				trade_no = response.getTradeNo();
				
				payUrl = response.getBody();
						
			} else {
				log.info("支付宝调用失败,订单信息:{}", rpTradePaymentOrder.toString());
				throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "支付宝返回失败异常");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		log.info("{}生成支付宝订单号：trade_no={}", logPrefix, trade_no);
		log.info("{}生成请求支付宝数据,req={}", logPrefix, alipay_request.getBizModel());
		log.info("###### 商户统一下单处理完成 ######");

		// rpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
		rpTradePaymentRecordDao.update(rpTradePaymentRecord);

		log.info("###### 商户统一下单处理完成 ######");
		Map<String, Object> map = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "",
				PayConstant.RETURN_VALUE_SUCCESS, null);
		map.put("payOrderId", rpTradePaymentOrder.getMerchantOrderNo());
		map.put("trade_no", trade_no);

		rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
		return RpcUtil.createBizResult(null, map);
	}

	private Map<String, Object> doAliPayMobileReq(JSONObject payOrder) {
		// TODO Auto-generated method stub
		String logPrefix = "【支付宝APP支付下单】";
		log.info("支付宝APP支付, 参数{}", rpTradePaymentOrder.toString());
		log.info("支付宝APP支付, 支付宝配置参数{}", rpUserPayInfo.toString());
		AlipayClient client = new DefaultAlipayClient(AlipayConfigUtil.trade_pay_url, rpUserPayInfo.getAppId(),
				rpUserPayInfo.getRsaPrivateKey(), AlipayConfigUtil.FORMAT, AlipayConfigUtil.input_charset,
				rpUserPayInfo.getRsaPublicKey(), AlipayConfigUtil.sign_type);
		AlipayTradeAppPayRequest alipay_request = new AlipayTradeAppPayRequest();
		// 封装请求支付信息
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setOutTradeNo(rpTradePaymentRecord.getBankOrderNo());
		model.setSubject(rpTradePaymentOrder.getProductName());

		BigDecimal orderPrice = rpTradePaymentOrder.getOrderAmount();
		model.setTotalAmount(orderPrice.toString());// 订单金额
		model.setBody(rpTradePaymentOrder.getRemark());
        model.setProductCode("QUICK_MSECURITY_PAY");
        
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(AlipayConfigUtil.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(AlipayConfigUtil.return_url);
		String trade_no = null;
        String payParams = null;
		AlipayTradeAppPayResponse response;

		try {
			response = client.sdkExecute(alipay_request);
			if (response.isSuccess()) {
				trade_no = response.getTradeNo();
				
				payParams = response.getBody();
						
			} else {
				log.info("支付宝调用失败,订单信息:{}", rpTradePaymentOrder.toString());
				throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR, "支付宝返回失败异常");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		log.info("{}生成支付宝订单号：trade_no={}", logPrefix, trade_no);
		log.info("{}生成请求支付宝数据,req={}", logPrefix, alipay_request.getBizModel());
		log.info("###### 商户统一下单处理完成 ######");

		// rpTradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
		rpTradePaymentRecordDao.update(rpTradePaymentRecord);

		log.info("###### 商户统一下单处理完成 ######");
		Map<String, Object> map = XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_SUCCESS, "",
				PayConstant.RETURN_VALUE_SUCCESS, null);
		map.put("payOrderId", rpTradePaymentOrder.getMerchantOrderNo());
		map.put("trade_no", trade_no);
		map.put("payParams", payParams);

		rpNotifyService.orderSend(rpTradePaymentRecord.getBankOrderNo());
		return RpcUtil.createBizResult(null, map);
	}

}

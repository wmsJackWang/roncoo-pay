package org.roncoo.pay.jackdking.middleplatformservices.modules.aggregatepay.controller;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.trade.service.RpPayChannelAlipayService;
import com.roncoo.pay.trade.service.RpPayChannelWechatService;
import com.roncoo.pay.user.entity.RpPayWay;
import com.roncoo.pay.user.entity.RpUserPayConfig;

/*
 * 聚合支付,统一所有的渠道的支付方式，无论你使用那种app,都支持你使用扫码功能进行支付。
 */
@Controller
@RequestMapping("/aggregatePay")
public class AggregatePay {
	
	private static final Logger log = LoggerFactory.getLogger(AggregatePay.class);
	
	@Autowired
	private RpPayChannelAlipayService rpPayChannelAlipayService;
	

	@Autowired
	private RpPayChannelWechatService rpPayChannelWechatService;

	@RequestMapping("")
	public String aggregatePay() {
		
		return "openQRPay";
	}
	
	
	/**
	 * @author mingshengwang
	 * @date 2020年3月26日
	 * @return String
	 * @parameter null
	 * @description 聚合扫码 ， 手机扫玩聚合码后进入的接口方法
	 * @param model
	 * @param vo
	 * @param request
	 * @param response
	 * @return
	 * @throws SocketTimeoutException
	 * @throws ChannelConnectException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/qrPay.pay")
	public String qrPay(ModelMap model, HttpServletRequest request,HttpServletResponse response)
			throws SocketTimeoutException {

		String logPrefix = "【聚合二维码扫码支付】";
		
		
		String view = "qrPay";
		
		
		
		log.info("====== 开始接收二维码扫码支付请求 ======");
		String ua = request.getHeader("User-Agent");
//		log.info("{} ,{},{}接收参数:amount={},ua={} , checkvo:{}","22222222222222222222222", "扫码步骤1" , logPrefix, vo.getAmt(), ua,vo.toString());
		String client = "wx";
		String channelId = "ALIPAY_WAP";
		if (StringUtils.isBlank(ua)) {
			String errorMessage = "请用微信或支付宝扫码";
			log.info("{}信息：{}", logPrefix, errorMessage);
			model.put("result", "failed");
			model.put("resMsg", errorMessage);
			return view;
		} else {
			if (ua.contains("Alipay")) {
				client = "zfb";
				channelId = "ALIPAY_WAP";
			} else if (ua.contains("MicroMessenger")) {
				client = "wx";
				channelId = "WX_JSAPI";
			}
		}
		if (client == null) {
			String errorMessage = "请用微信或支付宝扫码";
			log.info("{}信息：{}", logPrefix, errorMessage);
			model.put("result", "failed");
			model.put("resMsg", errorMessage);
			return view;
		}

		
		Map<String, Object> orderMap = null;
		if ("zfb".equals(client)) {
			log.info("{}{}扫码下单", logPrefix, "支付宝");
			
			//根据传进来的参数 ， 获取payorder数据,payorder在业务线访问收银台的时候就已经缓存在支付系统侧。
			JSONObject payOrder = new JSONObject();
			
			RpUserPayConfig rpUserPayConfig = null;//根据获取的参数得到这个数据
			
			RpPayWay rpPayWay = null;//根据获取的参数来得到这个数据。
			
			
			String result = rpPayChannelAlipayService.doAlipayRequest(rpPayWay.getPayTypeCode(), payOrder,
					rpUserPayConfig.getPaySecret());
			orderMap = JSON.parseObject(result);
			
			
		} else if ("wx".equals(client)) {
			// 判断是否拿到openid，如果没有则去获取
			String openId = request.getParameter("openid");
			log.info("{} {}{}扫码  ,openId:{} , {}", logPrefix,"扫码步骤2", "微信"  , openId , "22222222222222222222222");

			if (StringUtils.isNotBlank(openId)) {
//				log.info("{}openId：{} , {} ,checkvo:{}", logPrefix, openId  ,"22222222222222222222222" , vo.toString());
				
				//根据传进来的参数 ， 获取payorder数据,payorder在业务线访问收银台的时候就已经缓存在支付系统侧。
				JSONObject payOrder = new JSONObject();
				
				RpUserPayConfig rpUserPayConfig = null;//根据获取的参数得到这个数据
				
				RpPayWay rpPayWay = null;//根据获取的参数来得到这个数据。
				
				String result = rpPayChannelWechatService.doWechatRequest(rpPayWay.getPayTypeCode(), payOrder,
						rpUserPayConfig.getPaySecret());
				
				orderMap = JSON.parseObject(result);

			} else {
				
				//获取到参数后跳回来
				String redirectUrl = "http://bittechblog.com/roncopay/aggregatePay/qrPay.pay";
				String openIdUrl = "http://bittechblog.com/roncopay/wx/openIdDomain/getOpenId";
				//没openid数据则跳转
				if(StringUtils.isEmpty(openId)) {
					
					String url = openIdUrl + "?redirectUrl=" + redirectUrl;
					
					try {
						//跳转
						response.sendRedirect(url);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		
		if (orderMap != null) {
			JSONObject jsonOrderMap = (JSONObject) JSON.toJSON(orderMap);
			model.put("orderMap", jsonOrderMap);
			model.put("orderStr", jsonOrderMap.getString("result"));
			log.info("{} 跳转URL={} {} ,orderMap:{},result:{}", "" , "" ,"22222222222222222222222",JSON.toJSONString(orderMap),jsonOrderMap.getString("result"));
		}
		//前端根据client判断支付宝还是微信
		model.put("client", client);
		model.put("result", "success");
		return view;
	}   
	

}

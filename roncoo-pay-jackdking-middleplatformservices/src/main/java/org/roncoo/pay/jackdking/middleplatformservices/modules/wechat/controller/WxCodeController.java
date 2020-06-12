package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wx")
public class WxCodeController {
	

	private static final Logger log = LoggerFactory.getLogger(WxCodeController.class);

	@Value("${qrPay.getCodeUserNotice}")
	private String getCodeUserNotice;

	@Value("${qrPay.getCodeUserNotNotice}")
	private String getCodeUserNotNotice;
	
	//用户无感知方式获取code
	@RequestMapping("/openIdDomain/getCodeUserNotNotice")
	@ResponseBody
	public String getWXCodeUserNotNotice(HttpServletRequest request, HttpServletResponse response) {
		
		String redirectUrl = request.getParameter("redirectUrl");
		
		log.info("{} 进入获取用户openID页面,{},redirectUrl:{}","扫码步骤4","22222222222222222222222",redirectUrl);
		String code = request.getParameter("code");
		String openId = "";
		
		String AppID = "wx4389c6c165b0fe57";
		String AppSecret = "bc48185eb1e7d3dfaec28cf40a877de0";

		try {
			// 如果request中包括code，则是微信回调
			if (StringUtils.isEmpty(code)) {
	
				// oauth获取code
				// http://www.abc.com/xxx/get-weixin-code.html?appid=XXXX&scope=snsapi_base&state=hello-world&redirect_uri=http%3A%2F%2Fwww.xyz.com%2Fhello-world.html
				// 调用微信授权跳转获取openid
	
				log.info("{} 调用微信 获取code , {}","扫码步骤6", openId,"22222222222222222222222");
	
				String redirectUrl4Vx = getCodeUserNotNotice + "?redirectUrl=" + redirectUrl;
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				request.setCharacterEncoding("UTF-8");
	
				// 这里要将你的授权回调地址处理一下，否则微信识别不了
				String redirect_uri = URLEncoder.encode(redirectUrl4Vx, "UTF-8");
				// 简单获取openid的话参数response_type与scope与state参数固定写死即可
				StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirectUrl4Vx + "&appid="
								+ AppID + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
				// 这里千万不要使用get请求，单纯的将页面跳转到该url即可
				response.sendRedirect(url.toString());
	
			}else {
				//测试时候，将此段代码注掉, 微信扫码就会显示code数据
				
				String url = redirectUrl + "?code=" + code;
				log.info("{} 跳转URL={} {} ,redirectUrl:{}", "扫码步骤3" , url ,"22222222222222222222222",redirectUrl);
				 
				response.sendRedirect(url);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return code;
		
	}
	

	//用户感知方式获取code
	//用户需要在微信上点击确定
	@RequestMapping("/openIdDomain/getCodeUserNotice")
	@ResponseBody
	public String getWXCodeUserNotice(HttpServletRequest request, HttpServletResponse response) {
		
		String redirectUrl = request.getParameter("redirectUrl");
		String test = request.getParameter("test");
		
		log.info("{} go to get userinfo's code method,{},redirectUrl:{},test:{}","step4","22222222222222222222222",redirectUrl,test);
		String code = request.getParameter("code"); 
		
		String AppID = "wx4389c6c165b0fe57";
		String AppSecret = "bc48185eb1e7d3dfaec28cf40a877de0";

		try {
			// 如果request中包括code，则是微信回调
			if (StringUtils.isEmpty(code)) {
	
				// oauth获取code
				// http://www.abc.com/xxx/get-weixin-code.html?appid=XXXX&scope=snsapi_base&state=hello-world&redirect_uri=http%3A%2F%2Fwww.xyz.com%2Fhello-world.html
				// 调用微信授权跳转获取openid
	
				log.info("{} call wechat interface and get code , {} ,callback url :{}","step6", code,redirectUrl);
	
				String redirectUrl4Vx = getCodeUserNotice + "?redirectUrl=" + redirectUrl;
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				request.setCharacterEncoding("UTF-8");
	
				// 这里要将你的授权回调地址处理一下，否则微信识别不了
				String redirect_uri = URLEncoder.encode(redirectUrl4Vx, "UTF-8");
				// 简单获取openid的话参数response_type与scope与state参数固定写死即可
				StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirect_uri + "&appid="
								+ AppID + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
				// 这里千万不要使用get请求，单纯的将页面跳转到该url即可
				log.info("{} call wechat interface and get code , {} ,wechat callback url :{}","step7", code,redirectUrl4Vx);
				
				response.sendRedirect(url.toString());
	
			}else {
				//测试时候，将此段代码注掉, 微信扫码就会显示code数据
				if(redirectUrl.indexOf("?") > 0) {
	                redirectUrl += "&code=" + code;
	            }else {
	                redirectUrl += "?code=" + code;
	            }
				log.info("{} 跳转URL={} {} ,redirectUrl:{}", "扫码步骤3" , redirectUrl ,"22222222222222222222222",redirectUrl);
				
				response.sendRedirect(redirectUrl.toString());
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
	
}

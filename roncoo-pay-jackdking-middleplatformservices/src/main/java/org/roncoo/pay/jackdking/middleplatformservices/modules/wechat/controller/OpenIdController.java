package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.utils.HttpHelper;


@Controller("/wx")
public class OpenIdController {
	
	
	private static final Logger log = LoggerFactory.getLogger(OpenIdController.class);

	
	
	@Value("${qrPay.getOpenIdURL}")
	private String GetOpenIdURL;
	

	/**
	 * 获取code以及openid
	 * 
	 * @return
	 */
	@RequestMapping("/openIdDomain/getOpenId")
	public String getOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String redirectUrl = request.getParameter("redirectUrl");
		
		log.info("{} 进入获取用户openID页面,{},redirectUrl:{}","扫码步骤4","22222222222222222222222",redirectUrl);
		String code = request.getParameter("code");
		String openId = "";
		
		String AppID = "wx4389c6c165b0fe57";
		String AppSecret = "bc48185eb1e7d3dfaec28cf40a877de0";
//		String AppID = "";
//		String AppSecret = "";
		
		try {	
			/*
			 * 	这段代码解决的问题是：
			 * 	因为从定向 url中带有参数，且url又作为其他url的参数。
			 * 	这就会出现 一个问题，参数会混乱。例如：
			 * 	url1 = "url?x1=1&x2=2";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
			 * 	url2 = "url?url1="url1;
			 * 	那么直接组装到url2会导致url的参数 被当成是url2的参数。
			 * 	解决方式:聚合组装url1的参数
			 */
			//获取聚合一起的参数
			String unionParam = redirectUrl.substring(redirectUrl.indexOf("=")+1);
			//将聚合的参数都拆分,分别放到
			String [] param = unionParam.split(":");
			String orderid  = param[0];
			String productid  = param[1];
			String token = param[2];
			
	
			log.info("{} 调用微信返回code={} 判断code是否为空：{} , {}","扫码步骤5", code,"22222222222222222222222",!StringUtils.isBlank(code));
			
	
			// 如果request中包括code，则是微信回调
			if (!StringUtils.isBlank(code)) {
	
				log.info("{} 调用微信openid 接口  openId={} , {}","扫码步骤7", openId,"c");
				Map params = new HashMap();
				
				params.put("appid", AppID);
				params.put("secret", AppSecret);
				params.put("grant_type", "authorization_code");
				params.put("code", code);
				String result = HttpHelper.httpRequestToString(
			                "https://api.weixin.qq.com/sns/oauth2/access_token", params); 
				log.info("{} 调用微信openid 接口  result={} , {}","扫码步骤7", result,"22222222222222222222222");
				JSONObject jsonObject = JSONObject.parseObject(result);
				openId = jsonObject.get("openid").toString();
				log.info("{} 调用微信返回openId={} , {} , redirectUrl:{}","扫码步骤8", openId,"22222222222222222222222",redirectUrl);

				
				redirectUrl += "&orderid=" + orderid;
				redirectUrl += "&productid=" + productid;
				redirectUrl += "&token=" + token;
				redirectUrl += "&openId=" + openId;
				
				log.info("unionParam:{} , id:{} , redirectUrl:{}",unionParam,"22222222222222222222222",redirectUrl);
				
//				response.sendRedirect(redirectUrl);
			} else {
				// oauth获取code
				// http://www.abc.com/xxx/get-weixin-code.html?appid=XXXX&scope=snsapi_base&state=hello-world&redirect_uri=http%3A%2F%2Fwww.xyz.com%2Fhello-world.html
				// 调用微信授权跳转获取openid
	
				log.info("{} 调用微信 获取code , {}","扫码步骤6", openId,"22222222222222222222222");
	
				String redirectUrl4Vx = GetOpenIdURL + "?redirectUrl=" + redirectUrl;
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				request.setCharacterEncoding("UTF-8");
				// 这里要将你的授权回调地址处理一下，否则微信识别不了
				String redirect_uri = URLEncoder.encode(redirectUrl4Vx, "UTF-8");
				// 简单获取openid的话参数response_type与scope与state参数固定写死即可
				StringBuffer url = new StringBuffer(
						"https://open.weixin.qq.com/connect/oauth2/authorize?redirect_uri=" + redirectUrl4Vx + "&appid="
								+ AppID + "&response_type=code&scope=snsapi_base&state=1#wechat_redirect");
				// 这里千万不要使用get请求，单纯的将页面跳转到该url即可
				response.sendRedirect(url.toString());
			}
		
		} catch (Exception e) {
//			log.error("调用微信查询openId异常 {}", e,"22222222222222222222222");
		}
		return openId;
	}
	
	
	@ResponseBody
	@RequestMapping("/openIdDomain/{filename:[_a-zA-Z0-9\\.]+}")
	public String openIdCallBackDomain(@PathVariable("filename") String filename) {

		
		
		return "返回公众号域名权限配置文件.txt 内容";
	}

	@RequestMapping(value = "/")
	public String index(){
		
	    return "index";
	}
}

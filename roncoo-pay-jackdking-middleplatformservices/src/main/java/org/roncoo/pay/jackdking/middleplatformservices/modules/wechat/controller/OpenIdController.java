package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.WxPublicPlatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.utils.HttpHelper;


@Controller
@RequestMapping("/wx")
public class OpenIdController {
	
	
	private static final Logger log = LoggerFactory.getLogger(OpenIdController.class);

	
	
	@Value("${qrPay.getOpenIdURL}")
	private String GetOpenIdURL;

	@Value("${qrPay.getUserInfo}")
	private String getUserInfo;

	@Value("${qrPay.getOpenIdUserInfo}")
	private String getOpenIdUserInfo;
	
	@Value("${qrPay.getCodeUserNotice}")
	private String getCodeUserNotice;

	@Value("${qrPay.getCodeUserNotNotice}")
	private String getCodeUserNotNotice;
	
	@Autowired
	private WxPublicPlatService wxPublicPlatService;

	/**
	 * 获取code以及openid
	 * 该接口只能在微信app内调用，其他方式没用(服务器后台调用无效)
	 * 至获取openid数据
	 * @return
	 */
	@RequestMapping("/openIdDomain/getOpenId")
	@ResponseBody
	public String getOpenId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String redirectUrl = request.getParameter("redirectUrl");
		
		log.info("{} 进入获取用户openID页面,{},redirectUrl:{}","扫码步骤4","22222222222222222222222",redirectUrl);
		String code = request.getParameter("code");
		String openId = "",access_token="",refresh_token="";
		
		String AppID = "wx4389c6c165b0fe57";
		String AppSecret = "bc48185eb1e7d3dfaec28cf40a877de0";
		
		try {	
			
			log.info("{} 调用微信返回code={} 判断code是否为空：{} , {}","扫码步骤5", code,"22222222222222222222222",!StringUtils.isEmpty(code));
			
	
			// 如果request中包括code，则是微信回调
			if (!StringUtils.isEmpty(code)) {
	
				log.info("{} 调用微信openid 接口  openId={} ","扫码步骤7", openId);
				Map params = new HashMap();
				
				params.put("appid", AppID);
				params.put("secret", AppSecret);
				params.put("grant_type", "authorization_code");
				params.put("code", code);
				//以接口的调用方式  去获取openid
				String result = HttpHelper.httpRequestToString(
			                "https://api.weixin.qq.com/sns/oauth2/access_token", params); 
				log.info("{} 调用微信openid 接口  result={} , {}","扫码步骤7", result,"22222222222222222222222");
				JSONObject jsonObject = JSONObject.parseObject(result);
				openId = jsonObject.getString("openid") ;
				access_token = jsonObject.getString("access_token") ;
				refresh_token = jsonObject.getString("refresh_token") ;
				log.info("{} 调用微信返回openId={} , {} , redirectUrl:{}","扫码步骤8", openId,"22222222222222222222222",redirectUrl);
				
				//测试时候，将此段代码注掉, 微信扫码就会显示code数据
				
				String url = redirectUrl + "?openid=" + openId + "&access_token=" + access_token + "&refresh_token=" + refresh_token;
				log.info("{} 跳转URL={} {} ,redirectUrl:{}", "扫码步骤3" , url ,"22222222222222222222222",redirectUrl);
				response.sendRedirect(url);
				
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
		return openId;//这段代码用于页面测试
	}
	
	
	//既获取openid也获取userinfo数据
	//分散方式先获取code，再获取openid
	@RequestMapping("/openIdDomain/getOpenIdUserInfo")
	@ResponseBody
	public String getOpenIdUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String code = request.getParameter("code");
		JSONObject result = null;
		String redirectUrl = request.getParameter("redirectUrl");
		
		log.info("code:{}",code);
		
		// 如果request中包括code，则是微信回调
		if (StringUtils.isEmpty(code)) {
			
			String redirectUrl4Vx =  getOpenIdUserInfo + "?redirectUrl=" + redirectUrl;
			
			String url = getCodeUserNotice + "?redirectUrl=" + redirectUrl4Vx;
			log.info("{} jump URL={} {} ,redirectUrl:{}", "step: when code is null ,go to code Url" , url ,"22222222222222222222222",getCodeUserNotice);
			 
			response.sendRedirect(url);
		}else {
			
			log.info("{}   {} ,code:{}", "step: get code and code is not null  "  ,"22222222222222222222222",code);
			String res=wxPublicPlatService.getOpenId(code);
			result = JSON.parseObject(res);
			String ress = wxPublicPlatService.getUserInfo(result.getString("openId"), result.getString("access_token"));
			result.put("userinfo", JSON.parseObject(ress));
			
			String url = redirectUrl + "?result="+ress;
			log.info("userinfo:{}",ress);
			response.sendRedirect(url);
		}
		return null;
	}
	

}

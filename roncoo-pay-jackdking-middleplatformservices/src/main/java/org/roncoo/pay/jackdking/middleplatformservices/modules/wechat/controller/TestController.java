package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/wx")
public class TestController {

	
	

	//如何使用接口openIdDomain/getOpenId,看这个方法的使用方式
	@RequestMapping("/openIdDomain/testGetOpenId")
	@ResponseBody
	public String testGetOpenId(HttpServletRequest request, HttpServletResponse response){
		
		//获取到参数后跳回来
		String redirectUrl = "http://bittechblog.com/roncopay/wx/openIdDomain/testGetOpenId";
		String openId = request.getParameter("openid") ;
		String access_token = request.getParameter("access_token") ;
		String refresh_token = request.getParameter("refresh_token") ;
		
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
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("openid", openId);
		jsonObject.put("access_token", access_token);
		jsonObject.put("refresh_token", refresh_token);
		
		
		return "【获取的openid,access_token,refresh_token等数据】"+jsonObject.toJSONString();
		
	}
	
	
	//如何使用接口openIdDomain/getOpenId,看这个方法的使用方式
	@RequestMapping("/openIdDomain/testGetOpenIdUserInfo")
	@ResponseBody
	public String testGetOpenIdUserInfo(HttpServletRequest request, HttpServletResponse response){
		
		//获取到参数后跳回来
		String redirectUrl = "http://bittechblog.com/roncopay/wx/openIdDomain/testGetOpenIdUserInfo";
		String result = request.getParameter("result") ;
		
		String openIdUrl = "http://bittechblog.com/roncopay/wx/openIdDomain/getOpenIdUserInfo";
		//没openid数据则跳转
		if(StringUtils.isEmpty(result)) {
			
			String url = openIdUrl + "?redirectUrl=" + redirectUrl;
			
			try {
				//跳转
				response.sendRedirect(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("result", result);
		
		
		return "【获取的用户信息数据】"+jsonObject.toJSONString();
		
	}
	
	
	@RequestMapping(value = "/")
	public String index(){
		
	    return "index";
	}
	@RequestMapping(value = "/test")
	public String test(){
		
	    return "test";
	}
	
}

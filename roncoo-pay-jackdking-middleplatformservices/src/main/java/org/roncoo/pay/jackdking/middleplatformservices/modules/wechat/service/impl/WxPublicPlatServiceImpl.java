package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service.WxPublicPlatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.roncoo.pay.common.core.utils.HttpHelper;

@Service
public class WxPublicPlatServiceImpl implements WxPublicPlatService{
	
	
	private static final Logger log = LoggerFactory.getLogger(WxPublicPlatServiceImpl.class);


	@Override
	//获取openid,access_token,refresh_token数据
	public String getOpenId(String code) {
		
		
		String AppID = "wx4389c6c165b0fe57";
		String AppSecret = "bc48185eb1e7d3dfaec28cf40a877de0";
		JSONObject resultObj = new JSONObject();

		Map params = new HashMap();
		
		params.put("appid", AppID);
		params.put("secret", AppSecret);
		params.put("grant_type", "authorization_code");
		params.put("code", code);
		String result = HttpHelper.httpRequestToString(
	                "https://api.weixin.qq.com/sns/oauth2/access_token", params); 
		log.info("{} 调用微信openid 接口  result={} , {}","扫码步骤7", result,"22222222222222222222222");
		JSONObject jsonObject = JSONObject.parseObject(result);
		String openId = jsonObject.getString("openid"); 
		String access_token = jsonObject.getString("access_token");  
		String refresh_token = jsonObject.getString("refresh_token"); 
		
		resultObj.put("openId", openId);
		resultObj.put("access_token", access_token);
		resultObj.put("refresh_token", refresh_token);
		
		return resultObj.toJSONString();
	}

	
	//getUserInfo
	//根据获取的openid和access_token来获取用户信息
	@Override
	public String getUserInfo(String openId , String access_token) {
		
		String prefix = "【获取得到的用户信息】";
		log.info("{},openid:{},access_token:{}",prefix,openId,access_token);
		
		String AppID = "wx4389c6c165b0fe57";
		String AppSecret = "bc48185eb1e7d3dfaec28cf40a877de0";
		JSONObject resultObj = new JSONObject();

		Map params = new HashMap();
		
		params.put("access_token", access_token);
		params.put("openId", openId);
		params.put("lang", "zh_CN");
		
		String result = HttpHelper.httpRequestToString(
                "https://api.weixin.qq.com/sns/userinfo", params); 
		log.info("{},获取结果result:{}",result);
		return result;
	}
	
	//getUserInfo
	//根据获取的openid和access_token来批量获取用户信息
	@Override
	public String batchGetUserInfo(List<String> openIdList , String access_token) {
		
		String prefix = "【获取得到的用户信息】";
		log.info("{},openIdList:{},access_token:{}",prefix,JSON.toJSONString(openIdList),access_token);
		
		String AppID = "wx4389c6c165b0fe57";
		String AppSecret = "bc48185eb1e7d3dfaec28cf40a877de0";
		JSONObject resultObj = new JSONObject();

		JSONObject params = new JSONObject();
		if(!ObjectUtils.isEmpty(openIdList))
		{
			JSONArray jsonArray = new JSONArray();
			for(String param:openIdList)
			{
				JSONObject q = new JSONObject();
				q.put("openid", param);
				q.put("lang", "zh_CN");
				jsonArray.add(q);
			}
			params.put("user_list",jsonArray);
			
		}
		
		String result = HttpHelper.post(params, 
                "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+access_token);
		log.info("{},获取结果result:{}",result);
		return result;
	}
	
	

	//刷新access_token
	public String refreshToken(String appid,String refresh_token) {
		
		String prefix = "【刷新access_token】";
		log.info("{},appid:{},refresh_token:{}",prefix,appid,refresh_token);
		
		String AppID = "wx4389c6c165b0fe57";
		JSONObject resultObj = new JSONObject();

		Map params = new HashMap();
		
		params.put("refresh_token", refresh_token);
		params.put("appid", appid);
		params.put("grant_type", "refresh_token");
		
		String result = HttpHelper.httpRequestToString(
                "https://api.weixin.qq.com/sns/oauth2/refresh_token", params); 
		log.info("{},获取结果result:{}",result);
		return result;
	}

	@Override
	public boolean isValid(String openId, String access_token) {
		// TODO Auto-generated method stub
		
		String prefix = "【刷新access_token】";
		log.info("{},openId:{},access_token:{}",prefix,openId,access_token);
		
		String AppID = "wx4389c6c165b0fe57";
		JSONObject resultObj = new JSONObject();
		boolean resultb = false;

		Map params = new HashMap();
		
		params.put("access_token", access_token);
		params.put("openid", openId);
		
		String result = HttpHelper.httpRequestToString(
                "https://api.weixin.qq.com/sns/auth", params); 
		log.info("{},获取结果result:{}",result);
		resultObj = JSONObject.parseObject(result);
		//token有效
		if(!ObjectUtils.isEmpty(resultObj)&&resultObj.getIntValue("errcode")==0)
			resultb = true;
		return resultb;
	}
	
//	public static void main(String[] args) {
////		List<String>list = new ArrayList<String>();
////		System.out.println(JSON.toJSONString(list));
////		
////		list.add("hello");
////		list.add("world");
////		
////
////		System.out.println(JSON.toJSONString(list));
//		String AppID = "wx4389c6c165b0fe57";
////		new WxPublicPlatServiceImpl().refreshToken(AppID, "33_SVCg45fuwYvnYTt0b2nCC8mHg0nOksF1uYL-MLbGD7cWWjvFagL8bBWF4chP0haBm8o1VxDPqnDJHZ9hc-spG5EylGJQ4F4agL_Fsd1jouk");
//		
//		new WxPublicPlatServiceImpl().isValid("ofeGOuAxyO1OpwIFQEyEKuBqeMlU", "33_dc3Wwq3vLAHCj_7Sn_Nw2WgGlQMAv42Q8sGX9qgG2SJzLaOKF4PQMyzH5UDFwk8Vzi52ew6CXBl_TfX6lxPHWIZJ9htjM-vjskkJL3jNSjY");
//		
//		
//	}

}

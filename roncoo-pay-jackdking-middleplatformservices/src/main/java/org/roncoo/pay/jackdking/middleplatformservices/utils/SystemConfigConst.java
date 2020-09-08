package org.roncoo.pay.jackdking.middleplatformservices.utils;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;	

@Component
public class SystemConfigConst {
//	
//	//微信获取ACCESS_TOKEN API
//	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
//	 
//	//微信模板消息推送 API
//	public static final String MESSAGE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
//
//	public static final String NOTIFY_APPSCRET = "64d6d5297a25ee50ec23ee51a38a3db1";// "9a57a7a0c4e6c476bf172710bab72370";
//	
//	public static final String NOTIFY_APPID ="wx21eb36134e421873";// "wx480c505821d874f5";
//
//	public static final String GET_OPENID_COMMON = "http://wallet-test.sohupay.com/testwallet/trans/openIdDomain/getOpenIdCommon";
//
//	public static final String RECIVE_NOTIRY_URL = "http://wallet-test.sohupay.com/testwallet/api/wx/auth/bind/reciveNotify";
//	
//	public static final String MOBAN_ID = "4Rub5JT3jbhx-JVL9w0fGYFVkM8WRIv8DYUgmMQvm3E";
//	
//	public static final String TOKEN = "35_OJkCWp9RFD7QogPYMAtSjDtFps9jys8CmchAglipokWFJ6oPgC6acx_NVSM2QqUyA9ow8MewSYTG0LiRXpkFgQSasXD2MQpyjhYn4P5qBgFACpLT4l6mf2HmnQxGgm6bGNWGWfqlrbQOB6SwDBShAHASQH";
//
//	public static final String WISH_ORDER_SORTEDSET = "wishorderset";
//	// 临时二维码 
//	public final static String QR_SCENE = "QR_SCENE";  
//    // 永久二维码  
//	public final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";  
//    // 永久二维码(字符串)  
//	public final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";   
//    // 创建二维码  
//	public final static String create_ticket_path = "https://api.weixin.qq.com/cgi-bin/qrcode/create";  
//    // 通过ticket换取二维码  
//	public final static String showqrcode_path = "https://mp.weixin.qq.com/cgi-bin/showqrcode";  
//    
//	
//	public static Map<String,Object> ACCESS_TOKEN ;
//	
//	
//	
//	//过期access_token的初始化
//	public SystemConfigConst() {
//		ACCESS_TOKEN = new HashMap<String, Object>();
//		
//		Map params = new HashMap();
//		params.put("appid", NOTIFY_APPID);
//		params.put("secret", NOTIFY_APPSCRET);
//		params.put("grant_type", "client_credential");
//		
//		String result = HttpHelper.httpRequestToString(ACCESS_TOKEN_URL, params);
//		System.out.println(result);
//		JSONObject reObj = JSON.parseObject(result);
//		
//		ACCESS_TOKEN = new HashMap<String, Object>();
//		ACCESS_TOKEN.put("access_token", reObj.getString("access_token"));
//		Date expire_time = new Date();
//		Calendar cal = Calendar.getInstance();   
//    	cal.setTime(expire_time);
//    	cal.add(Calendar.HOUR, 1);//access_token官方过期时间是2小时，但是我默认1小时刷新一次
//    	expire_time = cal.getTime();
//		
//		ACCESS_TOKEN.put("expire_time", expire_time);
//		
//	}
//	
//	//获取accessToken
//	public String getAccessToken(){
//		String accessToken =  String.valueOf(ACCESS_TOKEN.get("access_token"));
//		Date now = new Date();
//		Date expire_time = (Date)ACCESS_TOKEN.get("expire_time");
//		
//		/*
//		 * 判断是否过期
//		 */
//		
//		return  accessToken;
//		
//	}
//	
////	public 
//	
//	public static void main(String[] args) {
////		
////		String url = MESSAGE_TEMPLATE_URL+TOKEN;
////		
////		
////		JSONObject data = new JSONObject();
////		JSONObject first = new JSONObject();
////		first.put("value","恭喜你，好友给你助力成功！");
////		first.put("color","#173177");
////		
////		data.put("first", first);
////
////		JSONObject keyword1 = new JSONObject();
////		keyword1.put("value","心愿1");
////		keyword1.put("color","#173177");
////		
////		data.put("keyword1", keyword1);
////		
////		JSONObject keyword2 = new JSONObject();
////		keyword2.put("value","目前心愿已经有3人助力");
////		keyword2.put("color","#173177");
////		
////		data.put("keyword2", keyword2);
////		
////		JSONObject remark = new JSONObject();
////		remark.put("value","快去感谢好友吧");
////		remark.put("color","#173177");
////		
////		data.put("remark", remark);
////		
////        JSONObject json = new JSONObject();
////        json.put("touser","oZWGcjh1nxg9_uNcyW3ixluZtpt8");
////        json.put("template_id",MOBAN_ID);
////        json.put("url","");
//////        json.put("topcolor",topcolor);
////        json.put("data",data);
////        
////        System.out.println(HttpHelper.sendPost(url, json));
//		
//		SystemConfigConst sysconst = new SystemConfigConst();
//		Date expire_time = new Date();
//		Calendar cal = Calendar.getInstance();   
//    	cal.setTime(expire_time);
//    	cal.add(Calendar.HOUR, 1);//access_token官方过期时间是2小时，但是我默认1小时刷新一次
//    	expire_time = cal.getTime();
//		System.out.println(expire_time.getTime());
//	}
//	
}

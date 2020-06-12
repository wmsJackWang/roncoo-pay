package org.roncoo.pay.jackdking.middleplatformservices.modules.wechat.service;

import java.util.List;

public interface WxPublicPlatService {
	

	public String getOpenId(String code);
	public String getUserInfo(String openId , String access_token);
	public String batchGetUserInfo(List<String> openIdList , String access_token);
	public String refreshToken(String appid,String refresh_token);
	public boolean isValid(String openId , String access_token);
	
}

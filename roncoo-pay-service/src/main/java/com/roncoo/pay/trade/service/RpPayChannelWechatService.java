package com.roncoo.pay.trade.service;

import com.alibaba.fastjson.JSONObject;

public interface RpPayChannelWechatService {
	
	public String doWechatRequest(String tradeType, JSONObject payOrder, String resKey);

}

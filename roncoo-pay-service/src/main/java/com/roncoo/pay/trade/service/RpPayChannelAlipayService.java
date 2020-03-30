package com.roncoo.pay.trade.service;

import com.alibaba.fastjson.JSONObject;

public interface RpPayChannelAlipayService {

	public String doAlipayRequest(String tradeType, JSONObject payOrder, String resKey);
}

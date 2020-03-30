package com.roncoo.pay.common.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum JDPayExtEnum {

	JD_DES_SCERET_KEY("京东DES秘钥"),
	JD_MD5_SCERET_KEY("京东MD5秘钥"),
	JD_RSA_SCERET_KEY("京东RSA秘钥"),
	JD_RSA_PUBLIC_KEY("京东RSA公钥"),
	JD_DZ_MD5_SCERET_KEY("京东对账MD5秘钥"),
	JD_CLUB_NUMBER_CARD_ID("京东支付会员卡号"),
	JD_AGENT_BUSSINESS_TYPE_CODE("京东代扣，业务分类码"),
	JD_AGENT_TRADE_SOURCE("京东代扣，交易来源"),
	JD_AGENT_CERTIFICATE_SECRET_KEY("京东代扣，商户私钥证书"),
	JD_AGENT_CERTIFICATE_PUBLIC_KEY("京东代扣，渠道公钥证书"),
	JD_AGENT_CERTIFICATE_PASSWORD("京东代扣证书密码"),
	JD_AGENT_SHA_256_SIGN_KEY("京东代扣，商户SHA-256签名密钥"),
	JD_AGENT_ACCOUNT("京东代扣，商户号绑定的account字段"),
	JD_AGENT_TEMPLATE_ID("京东代扣模板id"),
	JD_AGENT_SIGN_NUMBER_ID("京东代扣 签约会员号"),
	JD_SELLER_ID("京东商户号");
	
	

	/** 描述 */
	private String desc;

	private JDPayExtEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PayWayEnum[] ary = PayWayEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = ary[num].name();
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		PayWayEnum[] ary = PayWayEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("desc", ary[i].getDesc());
			map.put("name", ary[i].name());
			list.add(map);
		}
		return list;
	}

	public static PayWayEnum getEnum(String name) {
		PayWayEnum[] arry = PayWayEnum.values();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i].name().equalsIgnoreCase(name)) {
				return arry[i];
			}
		}
		return null;
	}

	/**
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		PayWayEnum[] enums = PayWayEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (PayWayEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}

}

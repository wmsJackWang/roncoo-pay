/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.common.core.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付类型枚举类
 * @company：广州领课网络科技有限公司（龙果学院 www.roncoo.com）.
 * @author Peter
 */
public enum PayTypeEnum {

	WX_NATIVE("WEIXIN","扫码支付-Native"),
    
	WX_MWEB("WEIXIN","H5支付"),

    MICRO_PAY("WEIXIN","刷卡支付-付款码支付"),
    
    WX_JSAPI("WEIXIN","微信JSAPI支付"),

    WX_APP("WEIXIN","微信APP支付"),

    WX_PROGRAM_PAY("WEIXIN","微信小程序"),
    
    WX_FACE_PAY("WEIXIN","微信刷脸支付"),

    F2F_PAY("ALIPAY","条码支付-扫码支付[当面付]"), 
    
    ALIPAY_MOBILE("ALIPAY","支付宝APP支付"), 
    
    ALIPAY_WAP("ALIPAY","支付宝手机网站支付"), 

    ALIPAY_PC("ALIPAY","支付宝手电脑网站支付"), 
    
    ALIPAY_QR("ALIPAY","支付宝当面付之扫码支付"),

	ALI_TEST("ALIPAY","支付宝测试"),

    DIRECT_PAY("ALIPAY","即时到账"),
	
	JINGDONG_SACNPAY("JINGDONG","京东扫码"),
	
	UNION_SCANPAY("UNION","银联扫码");

	/** 所属支付方式 */
    private String way;
    public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	/** 描述 */
    private String desc;

    private PayTypeEnum(String way,String desc) {
        this.desc = desc;
        this.way = way;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        PayTypeEnum[] ary = PayTypeEnum.values();
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
        PayTypeEnum[] ary = PayTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            map.put("name", ary[i].name());
            list.add(map);
        }
        return list;
    }

    public static PayTypeEnum getEnum(String name) {
        PayTypeEnum[] arry = PayTypeEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getWayList(String way) {
    	PayTypeEnum[] ary = PayTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
        	if(ary[i].way.equals(way)){
        		Map<String, String> map = new HashMap<String, String>();
                map.put("desc", ary[i].getDesc());
                map.put("name", ary[i].name());
                list.add(map);
        	}
        }
        return list;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        PayTypeEnum[] enums = PayTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (PayTypeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }

}

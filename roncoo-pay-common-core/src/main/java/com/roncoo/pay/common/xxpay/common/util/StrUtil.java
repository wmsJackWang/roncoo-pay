package com.roncoo.pay.common.xxpay.common.util;

/**
 * @author: dingzhiwei
 * @date: 17/11/1
 * @description:
 */
public class StrUtil {

    public static String toString(Object obj) {
        return obj == null?"":obj.toString();
    }

    public static String toString(Object obj, String nullStr) {
        return obj == null?nullStr:obj.toString();
    }

}

package com.roncoo.pay.common.core.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class SignUtils {


    public static String createSign(Map<String, String> params, String signKey, String signType) {
//      if (this.getConfig().useSandbox()) {
//        //浣跨敤浠跨湡娴嬭瘯鐜
//        //TODO 鐩墠娴嬭瘯鍙戠幇锛屼互涓嬩袱琛屼唬鐮侀兘浼氬嚭闂锛屾墍浠ユ殏涓嶅缓璁娇鐢ㄤ豢鐪熸祴璇曠幆澧�
//        signKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456";
//        //return "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456";
//      }

      SortedMap<String, String> sortedMap = new TreeMap<>(params);

      StringBuilder toSign = new StringBuilder();
      for (String key : sortedMap.keySet()) {
        String value = params.get(key);
        if (StringUtils.isNotEmpty(value)
          && !StringUtils.equalsAny(key, "sign", "key", "sign_type")) {
          toSign.append(key).append("=").append(value).append("&");
        }
      }

      toSign.append("key=").append(signKey);
      if ("HMAC_SHA256".equals(signType)) {
        return createHMACSha256Sign(toSign.toString(), signKey);
      } else {
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
      }
    }

    private static String createHMACSha256Sign(String message, String key) {
      try {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        byte[] bytes = hmacSHA256.doFinal(message.getBytes());
        return Hex.encodeHexString(bytes).toUpperCase();
      } catch (NoSuchAlgorithmException | InvalidKeyException e) {
        e.printStackTrace();
      }

      return null;
    }
}

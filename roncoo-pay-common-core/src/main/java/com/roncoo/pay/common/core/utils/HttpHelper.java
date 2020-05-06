package com.roncoo.pay.common.core.utils;
/*
 * Title HttpHelper.java
 * 提供发送http请求的通用方法 
 * 用法示例:
 * HttpHelper helper = HttpHelper.getInstance("http://somedomain.com/somurl");
 * helper.set("param1", "value1").set("param2", "value2"); // 链式添加请求参数
 * String getRes = helper.get(); // 发送get请求, 默认用utf-8将请求参数值转码
 * String postRes = helper.post(); // 发送post请求, 默认用utf-8将请求参数值转码
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 发送rest请求的工具类
 * @author rainyhao 
 * @since 2015-3-3 下午3:11:21
 */
public final class HttpHelper {
	
	private Logger log = LogManager.getLogger(HttpHelper.class);



	public static String httpRequestToString(String url, Map<String, String> params) {
		String result = null;
		try {
			InputStream is = httpRequestToStream(url, params);
			BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	private static InputStream httpRequestToStream(String url, Map<String, String> params) {
		InputStream is = null;
		try {
			String parameters = "";
			boolean hasParams = false;
			for (String key : params.keySet()) {
				String value = URLEncoder.encode(params.get(key), "UTF-8");
				parameters += key + "=" + value + "&";
				hasParams = true;
			}
			if (hasParams) {
				parameters = parameters.substring(0, parameters.length() - 1);
			}

			url += "?" + parameters;

			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			conn.setDoInput(true);
			// 设置请求方式，默认为GET
			conn.setRequestMethod("GET");

			is = conn.getInputStream();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

}

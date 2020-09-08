package org.roncoo.pay.jackdking.middleplatformservices.utils;

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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

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
	

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, JSONObject param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}

package com.sohu.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年3月8日 下午12:57:02
 */
public class IPUtils {
	private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

	/**
	 * 获取IP地址
	 * 
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
    	String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
        	logger.error("IPUtils ERROR ", e);
        }
        
//        //使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}
        
        return ip;
    }
	
	/**
	 * @Title: <p>获取hostName</p>
	 * @Author: huashuaihuang
	 * @Date: 2017年10月17日下午2:40:50
	 * @return
	 * @throws Exception
	 */
	public static String getHostName() throws Exception{
		
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") >= 0) {
			return InetAddress.getLocalHost().getHostName();
		}
		String hostName = null;
		InputStream in = null; 
		BufferedReader read = null;
		Process pro;
		try {
			pro = Runtime.getRuntime().exec("whoami");
			pro.waitFor();
			in = pro.getInputStream(); 
			read = new BufferedReader(new InputStreamReader(in)); 
			hostName = read.readLine(); 
		} catch (Exception e) {
			throw e;
		}finally {
			try {
				read.close();
			} catch (IOException e) {}
			try {
				in.close();
			} catch (IOException e) {}
		}	
		return hostName;
	}
	
	/**
	 * @Title: <p>获取系统用户地址信息</p>
	 * @Author: huashuaihuang
	 * @Date: 2017年10月17日下午2:41:08
	 * @return
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public static InetAddress getAddress(){
		
		InetAddress address = null;
		
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			
			if(osName.indexOf("windows") >= 0){				
				return InetAddress.getLocalHost();				
			}
			
			for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
					continue;
				}
				Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					
					address = addresses.nextElement();
				}
			}
		} catch (UnknownHostException | SocketException e) {
			
			e.printStackTrace();
		} 
		return address;
	}
	
	
	
}

package com.sohu.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckSignUtil {
	private static Logger log = LogManager.getLogger(CheckSignUtil.class);

	public static String signOrderArr(Map<String,String[]> obj,String seed) {
		String stringMD5="";
		try {
			TreeMap<String,Object[]> map = new TreeMap<String,Object[]>();
			map.putAll(obj);
			Iterator<String> iterator = map.keySet().iterator();
			String signtext = "";
			while(iterator.hasNext()){
				String next = iterator.next();
				if("sign".equals(next)){
					continue;
				}
				Object[] array = map.get(next);
				signtext += (null == array[0] ? "" : array[0]);
			}
			signtext+=seed;
			stringMD5 = getStringMD5(signtext);
		} catch (NoSuchAlgorithmException e) {
			log.error("",e);
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
		}
		return stringMD5;
	}
	public static String signOrder(Map<String,Object> obj,String seed) {
		String stringMD5="";
		try {
			TreeMap<String,Object> map = new TreeMap<String,Object>();
			map.putAll(obj);
			Iterator<String> iterator = map.keySet().iterator();
			String signtext = "";
			while(iterator.hasNext()){
				String next = iterator.next();
				if("sign".equals(next)){
					continue;
				}
				Object array = map.get(next);
				signtext += (null == array ? "" : array);
			}
			signtext+=seed;
			stringMD5 = getStringMD5(signtext);
		} catch (NoSuchAlgorithmException e) {
			log.error("",e);
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
		}
		return stringMD5;
	}
	private static String computeDigest(MessageDigest currentAlgorithm, byte[] b) {
		currentAlgorithm.reset();
		currentAlgorithm.update(b);
		byte[] hash = currentAlgorithm.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hash.length; i += 2) { // format with 2-byte
													// words with spaces.
			sb.append(getHexString(hash[i]));
			sb.append(getHexString(hash[i + 1]));
		}
		return sb.toString().trim().toLowerCase();
	}
	public static String getStringMD5(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
			MessageDigest currentAlgorithm = MessageDigest.getInstance("MD5");
			return computeDigest(currentAlgorithm,key.getBytes("UTF-8"));
	}
	private static String getHexString(byte value) {
		int usbyte = value & 0xFF;// byte-wise AND converts signed byte to
									// unsigned.
		StringBuilder sb = new StringBuilder();
		if (usbyte < 16)
			sb.append("0");
		sb.append(Integer.toHexString(usbyte));// pad on left if single hex
												// digit.
		return sb.toString();
	}
}

package com.sohu.common.utils;

import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanMap;

public class BeanToMapUtil {
	public static Map<String, Object> beanToMap(Object obj) { 
    	Map<String, Object> map = new HashMap<>();  
	    if (obj != null) {  
	        BeanMap beanMap = BeanMap.create(obj);  
	        for (Object key : beanMap.keySet()) {  
	            map.put(String.valueOf(key), beanMap.get(key));  
	        }             
	    }  
	    return map;
    }
	@SuppressWarnings("rawtypes")
	public static Object mapToBean(Map obj,Object bean) { 
		BeanMap beanMap = BeanMap.create(bean);  
	    beanMap.putAll(obj);  
	    return bean;
    }
	
}

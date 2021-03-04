package com.sohu.common.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sohu.common.baseenum.BaseEnum.CheckParameterExceptionEnum;
import com.sohu.common.exception.CheckException;


/**
 * 
 * @ClassName: Assert 
 * @Description: 数据校验 
 * @author honghuiwang 
 * @date 2017年9月10日 下午5:36:47 
 *
 */
public abstract class Assert {

    
    
    public static void isFalse(Boolean object, int code ,String message) {
        if (object == null) {
            throw new CheckException(code,message);
        }
        if(!object){
            throw new CheckException(code,message);
        }
    }
    
    public static void isNull(List<Object> object,  int code ,String message) {
    	if(object==null||object.isEmpty()){
            throw new CheckException(code,message);
    	}
    }

	public static void isTrue(Boolean object, int code ,String message) {
		  if (object == null) {
	            throw new CheckException(code,message);
	        }
	        if(object){
	            throw new CheckException(code,message);
	        }		
	}
	
	
	public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CheckException(CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CheckException(CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),message);
        }
    }
    
    public static void isTrue(Boolean object, String message) {
        if (object == null) {
            throw new CheckException(CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),message);
        }
        if(object){
            throw new CheckException(CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),message);
        }
    }
    public static void isFalse(Boolean object,String message) {
        if (object == null) {
            throw new CheckException(CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),message);
        }
        if(!object){
            throw new CheckException(CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),message);
        }
    }
    public static void isParamTrue(Boolean object, int code, String message) {
        if (object == null) {
            throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),message);
        }
        if(object){
            throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),message);
        }
    }
    public static void isParamFalse(Boolean object,int code,String message) {
        if (object == null) {
            throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),message);
        }
        if(!object){
            throw new CheckException(CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(),message);
        }
    }
}

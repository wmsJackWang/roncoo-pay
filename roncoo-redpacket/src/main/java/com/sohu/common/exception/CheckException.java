package com.sohu.common.exception;

/**
 * 业务异常
 * @date: 2016年10月12日 上午10:40:11
 */
@SuppressWarnings("serial")
public class CheckException extends BaseException {
	

	public CheckException(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	@Override
	public Throwable fillInStackTrace() {
		return null;
	}
	
	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	@Override
	public String getMessage() {
		return msg;
	}

	@SuppressWarnings("rawtypes")
	public Class getClazz() {
		return clazz;
	}

	public String getClassName() {
		return className;
	}

}

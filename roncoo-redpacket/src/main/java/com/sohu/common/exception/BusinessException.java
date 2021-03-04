package com.sohu.common.exception;

import com.sohu.common.baseenum.BaseEnum.BusinessExceptionEnum;

/**
 * 业务异常
 * @date: 2016年10月12日 上午10:40:11
 */
@SuppressWarnings("serial")
public class BusinessException extends BaseException {
	
	protected Exception orgException;//原始异常

	public BusinessException() {
		super();
	}

	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessException(String arg0) {
		super(arg0);
	}
	
	public BusinessException(Throwable arg0) {
		super(arg0);
	}
	
	public BusinessException(BusinessExceptionEnum businessExceptionEnum){
		this.code = businessExceptionEnum.Code();
		this.msg = businessExceptionEnum.Msg();
	}
	
	public BusinessException(BusinessExceptionEnum businessExceptionEnum,Exception orgException){
		this.code = businessExceptionEnum.Code();
		this.msg = businessExceptionEnum.Msg();
		this.orgException = orgException;
	}
	
	@SuppressWarnings("rawtypes")
	public BusinessException(BusinessExceptionEnum businessExceptionEnum,Class clazz){
		this.code = businessExceptionEnum.Code();
		this.msg = businessExceptionEnum.Msg();
		this.clazz = clazz;
		this.className = clazz.getName();
	}
	
	@SuppressWarnings("rawtypes")
	public BusinessException(BusinessExceptionEnum businessExceptionEnum,Class clazz,Exception orgException){
		this.code = businessExceptionEnum.Code();
		this.msg = businessExceptionEnum.Msg();
		this.clazz = clazz;
		this.className = clazz.getName();
		this.orgException = orgException;
	}
	
	@SuppressWarnings("rawtypes")
	public BusinessException(String msg,Class clazz){
		this.msg = msg;
		this.clazz = clazz;
		this.className = clazz.getName();
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	@SuppressWarnings("rawtypes")
	public Class getClazz() {
		return clazz;
	}

	public String getClassName() {
		return className;
	}

	public Exception getOrgException() {
		return orgException;
	}

	public void setOrgException(Exception orgException) {
		this.orgException = orgException;
	}
}

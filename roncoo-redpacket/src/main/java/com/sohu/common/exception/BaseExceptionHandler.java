package com.sohu.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sohu.common.aspect.IAspectCode;
import com.sohu.common.utils.HttpContextUtils;
import com.sohu.common.utils.R;

/**
 * 
 * @ClassName: RRExceptionHandler 
 * @Description: ExceptionHandlerExceptionResolver
 * @author honghuiwang 
 * @date 2017年9月2日 下午8:15:18 
 *
 */
@RestControllerAdvice
public class BaseExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(CheckException.class)
	public R handleCheckException(CheckException e){
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		long startTime = (long) (request.getAttribute(IAspectCode.EXECUTE_TIME)==null?-1l:(request.getAttribute(IAspectCode.EXECUTE_TIME)));
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		long endTime = System.currentTimeMillis();
		logger.error("UUID->{} METHOD->{SysExceptionHandler}.{handleCheckException} RESULT->{} EXECUTE_TIME->{}",request.getAttribute(IAspectCode.GLOBAL_PARAMETER)==null?-1:request.getAttribute(IAspectCode.GLOBAL_PARAMETER),r,endTime-startTime);
		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}


	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		long startTime = (long) (request.getAttribute(IAspectCode.EXECUTE_TIME)==null?-1l:(request.getAttribute(IAspectCode.EXECUTE_TIME)));
		R error = R.error();
		long endTime = System.currentTimeMillis();
		logger.error("UUID->{} METHOD->{SysExceptionHandler}.{handleCheckException} RESULT->{} EXECUTE_TIME->{}",request.getAttribute(IAspectCode.GLOBAL_PARAMETER)==null?-1:request.getAttribute(IAspectCode.GLOBAL_PARAMETER),error,endTime-startTime,e);
		return error;
	}
}

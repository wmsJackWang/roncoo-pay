package com.sohu.common.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sohu.common.annotation.MethodLog;
import com.sohu.common.utils.HttpContextUtils;


/**
 * 
 * @ClassName: SimpleLogAspect 
 * @Description: 记录基本日志，切面处理类 <br/> 1、记录方法入口唯一标识 <br/> 2、记录参数 
 * @author honghuiwang 
 * @date 2017年9月2日 上午12:01:47 
 *
 */
@Aspect
@Component
@Order(-1)
public class SimpleLogAspect implements IAspectCode{
	
	private Logger log = LogManager.getLogger(this.getClass());

	
	@Pointcut("@annotation(com.sohu.common.annotation.SimpleLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		//记录请求前日志
		intiMethodLog(point);
		//执行方法
		Object result = point.proceed();
		//记录执行后返回日志
		resultSimpleLog(point, result);

		return result;
	}
	private void resultSimpleLog(ProceedingJoinPoint joinPoint,Object str){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.info("UUID->{} METHOD->{}.{} RESULT->{} ",request.getAttribute(GLOBAL_PARAMETER), className, methodName,str);
	}
	
	private void intiMethodLog(ProceedingJoinPoint joinPoint){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		MethodLog syslog = method.getAnnotation(MethodLog.class);
		String logInfo = "执行方法";
		if(syslog != null){
			//注解上的描述
			logInfo = syslog.value();
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		Object[] args = joinPoint.getArgs();
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		

		//设置IP地址
		log.info("UUID->{} EXECUTE->{} SIMPLE->{}.{} PARAMETER->{}",request.getAttribute(GLOBAL_PARAMETER),logInfo, className, methodName, args);
	}
	
}

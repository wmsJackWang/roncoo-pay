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
 * @ClassName: MethodLogAspect 
 * @Description: 记录方法日志，切面处理类 <br/> 1、记录方法入口唯一标识 <br/> 2、记录参数 <br/> 3、记录方法执行时间 <br/> 4、可发送graphics记录
 * @author honghuiwang 
 * @date 2017年9月2日 上午12:01:47 
 *
 */

public class MethodLogAspect implements IAspectCode{
	
	private Logger log = LogManager.getLogger(this.getClass());
	@Pointcut("@annotation(com.sohu.common.annotation.MethodLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		MethodSignature methName = (MethodSignature) point.getSignature();
		String str = point.getTarget().getClass().getName()+"."+methName.getName();
		Object result = null;
		//记录请求前日志
		intiMethodLog(point);
		try {
			result = point.proceed();
			//执行时长(毫秒)
			long time = System.currentTimeMillis() - beginTime;
			sendAgent(str,(int) time);
		}catch (Exception e) {
			long time = System.currentTimeMillis() - beginTime;
			sendAgent(str+".ERROR",(int) time);
			throw e;
		}
		finally {
			long time = System.currentTimeMillis() - beginTime;
			//记录执行后返回日志
			resultMethodLog(point, result,time);
			
		}
		
		return result;
	}
	private void resultMethodLog(ProceedingJoinPoint joinPoint,Object str,long time){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.info("UUID->{} METHOD->{}.{} RESULT->{} EXECUTE_TIME->{}",request.getAttribute(GLOBAL_PARAMETER), className, methodName,str,time);
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
		log.info("UUID->{} EXECUTE->{} METHOD->{}.{} PARAMETER->{}",request.getAttribute(GLOBAL_PARAMETER),logInfo, className, methodName, args);
	}
}

package com.sohu.common.aspect;

import java.lang.reflect.Method;
import java.util.UUID;

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

import com.sohu.common.annotation.CtrlLog;
import com.sohu.common.utils.HttpContextUtils;
import com.sohu.common.utils.IPUtils;
import com.sohu.common.utils.UtilStatsd;


/**
 * 
 * @ClassName: CtrLogAspect 
 * @Description: 系统日志，切面处理类，主要用于controller方法上<br> 1、生成入口唯一参数 <br/> 2、记录参数  <br> 3、记录方法执行时间  <br> 4、可发送graphics记录
 * @author honghuiwang 
 * @date 2017年9月2日 上午12:01:47 
 *
 */
@Aspect
@Component
@Order(-1)
public class CtrLogAspect implements IAspectCode{
	
	private Logger log = LogManager.getLogger(this.getClass());

	
	@Pointcut("@annotation(com.sohu.common.annotation.CtrlLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable  {
		long beginTime = System.currentTimeMillis();
		MethodSignature methName = (MethodSignature) point.getSignature();
		String str = point.getTarget().getClass().getName()+"."+methName.getName();
		//记录请求前日志
		intiCtrLog(point,beginTime);
		//执行方法
		Object result = null;
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
			resultCtrLog(point, result,time);
			
		}

		
		return result;
	}
	private void resultCtrLog(ProceedingJoinPoint joinPoint,Object str,long time){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.info("UUID->{} METHOD->{}.{} RESULT->{} EXECUTE_TIME->{}",request.getAttribute(GLOBAL_PARAMETER), className, methodName,str,time);
	}
	
	private void intiCtrLog(ProceedingJoinPoint joinPoint,long beginTime){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		CtrlLog syslog = method.getAnnotation(CtrlLog.class);
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
		
		String uuid = UUID.randomUUID().toString();
	    request.setAttribute(GLOBAL_PARAMETER,uuid);
	    request.setAttribute(EXECUTE_TIME,beginTime);

		//设置IP地址
		log.info("UUID->{} EXECUTE->{} METHOD->{}.{} PARAMETER->{} IP->{}",uuid,logInfo, className, methodName, args,IPUtils.getIpAddr(request));
	}
	
}

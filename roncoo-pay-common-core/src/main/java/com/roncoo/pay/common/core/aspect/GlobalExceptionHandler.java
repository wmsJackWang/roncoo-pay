package com.roncoo.pay.common.core.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roncoo.pay.common.core.exception.BizException;

@ControllerAdvice
public class GlobalExceptionHandler {
	/*
	 * 系统出现异常，隔离返回前端的信息。使系统更加安全
	 */
	private static String resultMsg = "系统繁忙，请稍后再试！";
	
   //处理自定义的异常
   @ExceptionHandler(BizException.class) 
   @ResponseBody
   public Object customHandler(BizException e){
      e.printStackTrace();
      return e.toString();
   }
   //其他未处理的异常
   @ExceptionHandler(Exception.class)
   @ResponseBody
   public Object exceptionHandler(Exception e){
      e.printStackTrace();
      return resultMsg;
   }
}
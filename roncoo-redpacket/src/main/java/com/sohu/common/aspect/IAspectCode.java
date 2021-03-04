package com.sohu.common.aspect;

import com.sohu.common.utils.UtilStatsd;

public interface IAspectCode {

	String GLOBAL_PARAMETER = "$globalParameter";
	String EXECUTE_TIME = "$executeTime";
	public default void sendAgent(String method,int time){
		UtilStatsd.recordExecutionTime(method, time);
		UtilStatsd.incrementCounter(method);
	}
	
}

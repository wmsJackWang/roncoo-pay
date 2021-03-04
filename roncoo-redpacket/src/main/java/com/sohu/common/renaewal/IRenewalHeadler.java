package com.sohu.common.renaewal;

public interface IRenewalHeadler {

	/**
	 * 
	 * @Title: headler 
	 * @Description: 注册成功后执行此业务方法(可能会被多次调用)
	 *
	 */
	void headler();
	
	/**
	 * 
	 * @Title: shutdown 
	 * @Description: 注册失败或任务移交后使用此方法停止相关任务 
	 *
	 */
	void shutdown();
	
	/**
	 * 
	 * @return 
	 * @Title: isRun 
	 * @Description: 返回注册此key的服务务运行的状态 
	 *
	 */
	boolean isRun();
	
	/**
	 * 
	 * @return 
	 * @Title: isRun 
	 * @Description: 返回注册此key的服务线程是否准备shutdown 
	 *
	 */
	boolean isShutdowning();
}

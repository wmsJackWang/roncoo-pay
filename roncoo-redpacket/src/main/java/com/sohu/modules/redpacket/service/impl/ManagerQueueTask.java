package com.sohu.modules.redpacket.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sohu.common.asynchronous.AsynchronousManagerServiceJob;
import com.sohu.common.utils.SpringUtil;
import com.sohu.modules.redpacket.enums.RedpacketEnum.RedisQueueName;

@DisallowConcurrentExecution
public class ManagerQueueTask  extends QuartzJobBean{
	
	//20
	@Value("${redpacket.batch}")
	private Integer batch;
	//5000
	@Value("${redpacket.waitTime}")
	private Integer waitTime;
	//5000
	@Value("${redpacket.testTime}")
	private Integer testTime;
	//5000
	@Value("${redpacket.testWaitTime}")
	private Integer testWaitTime;
	
	
	

	private ExecutorService redpacketManagerPool;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		//创建 只有一个核心线程的线程池
//
//				AsynchronousManagerServiceJob managerJob = (AsynchronousManagerServiceJob) SpringUtil.getBean("asynchronousManagerServiceJob");
//
//				managerJob.setRunKey("redpacket_renewal_key");
//				managerJob.setBackQueeueManageQueueName(RedisQueueName.REDPACKET_QUEUE_BAK_MANAGE_KEY.get());
//				managerJob.setPipCount(batch);
////				managerJob.setWaitTime(waitTime);
//				managerJob.setTestTime(testTime);
//				managerJob.setTestWaitTime(testWaitTime);
//				managerJob.run();
		
	}

}

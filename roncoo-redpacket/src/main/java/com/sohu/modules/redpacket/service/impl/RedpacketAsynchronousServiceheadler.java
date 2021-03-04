package com.sohu.modules.redpacket.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sohu.common.asynchronous.AsynchronousManagerServiceJob;
import com.sohu.common.renaewal.IRenewalHeadler;
import com.sohu.common.renaewal.Renewal;
import com.sohu.common.thread.BlockingQueueThreadPool;
import com.sohu.common.utils.SpringUtil;
import com.sohu.modules.redpacket.enums.RedpacketEnum.RedisQueueName;


/**
 * @Desc   : <P>红包队列异步管理<P>
 * @Author : SOHU-wanghonghui
 * @Date   : 2017年9月30日 下午6:12:56
 * @Version: V1.0
 */
@Service("redpacket_renewal_key")
public class RedpacketAsynchronousServiceheadler  implements IRenewalHeadler{
	private Logger log = LogManager.getLogger(this.getClass());

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
	
	
	@Autowired
	private Renewal renewal;
	//数组阻塞队列的 线程池
	@Autowired
	private BlockingQueueThreadPool blockingQueueThreadPool;
	
	//存放redis8个队列的key名称
	private List<String> list= new ArrayList<String>();
	
	private ExecutorCompletionService<String> service;
	
	private ExecutorService newFixedThreadPool;

	private boolean first = true;

	//初始化
	public RedpacketAsynchronousServiceheadler(){
		list.add(RedisQueueName.REDPACKET_QUEUE_1.get());
		list.add(RedisQueueName.REDPACKET_QUEUE_2.get());
		list.add(RedisQueueName.REDPACKET_QUEUE_3.get());
		list.add(RedisQueueName.REDPACKET_QUEUE_4.get());
		list.add(RedisQueueName.REDPACKET_QUEUE_5.get());
		list.add(RedisQueueName.REDPACKET_QUEUE_6.get());
		list.add(RedisQueueName.REDPACKET_QUEUE_7.get());
		list.add(RedisQueueName.REDPACKET_QUEUE_8.get());
	}
	
	//c
	public void  headler() {
		System.out.println("业务启动入口");

		if(newFixedThreadPool!=null){
			log.info("isRun :"+(!newFixedThreadPool.isShutdown()||!newFixedThreadPool.isTerminated()));
		}
		//创建8个核心线程的线程池
		newFixedThreadPool = Executors.newFixedThreadPool(8,new ThreadFactory() {
			private final AtomicInteger mCount = new AtomicInteger(1);
			@Override
			public Thread newThread(Runnable r) {
				return  new Thread(r, "redpacket_renewal_key-pool-" + mCount.getAndIncrement());
			}
		});
		

//		service = new ExecutorCompletionService<>(newFixedThreadPool);
		//遍历每个红包redis队列key，分别开展业务
		for (String queueName : list) {
			AsynchronousRedpackerQueueextends asynchronousQueue = (AsynchronousRedpackerQueueextends) SpringUtil.getBean("asynchronousRedpackerQueueextends");
			asynchronousQueue.setPipCount(batch);
			asynchronousQueue.setWaitTime(waitTime/1000);
			asynchronousQueue.setJobName("redpacketQueueJob");
			asynchronousQueue.setRunKey("redpacket_renewal_key");
			asynchronousQueue.setWorkQueueName(queueName);
			asynchronousQueue.setBackQueeueManageQueueName(RedisQueueName.REDPACKET_QUEUE_BAK_MANAGE_KEY.get());
			asynchronousQueue.setBackQueuePrefixName(RedisQueueName.REDPACKET_QUEUE_BAK_PREFIX.get());
			asynchronousQueue.setRenewal(renewal);
			asynchronousQueue.setPool(blockingQueueThreadPool);//BlockingQueueThreadPool,线程池管理对象
			newFixedThreadPool.submit(asynchronousQueue);//提交callable权限
		}
		
		
		
	}

	@Override
	public void shutdown() {
		newFixedThreadPool.shutdown();
		log.info("shutdown:"+newFixedThreadPool.isShutdown());
		log.info("shutdown:"+newFixedThreadPool.isTerminated());
//		for (int i = 0; i < 9&&!first; i++) {
//            Future<String> f = service.poll();//poll函数无参数则不等待，有完成结果则返回结果，无完成结果则返回null
//            try {
//            	if(f!=null){
//            		
//            		System.out.println(""+i+f.get());
//            	}
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Override
	public boolean isRun() {
		if(newFixedThreadPool==null){
			return false;
		}
		log.info("isRun :"+(!newFixedThreadPool.isShutdown()||!newFixedThreadPool.isTerminated()));
		return !newFixedThreadPool.isShutdown()||!newFixedThreadPool.isTerminated();
	}

	@Override
	public boolean isShutdowning() {
		if(newFixedThreadPool==null){
			return false;
		}
		return newFixedThreadPool.isShutdown();
	}
	
	
}

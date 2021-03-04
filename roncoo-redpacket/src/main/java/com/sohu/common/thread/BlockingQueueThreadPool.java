package com.sohu.common.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 
 * @Desc    异步批量提交入库<P>
 * @Author  honghuiwang
 * @Date    2016年11月22日 下午1:00:45
 * @Version V1.0
 */
@Service
@Lazy
@Scope("prototype")
public class BlockingQueueThreadPool{
	private static Logger logger = LogManager.getLogger(BlockingQueueThreadPool.class);// 日志服务

	private ThreadPoolExecutor threadPoolExecutor ;
	public BlockingQueueThreadPool(@Qualifier("blockingQueueThreadPoolConfig") BlockingQueueThreadPoolConfig synBathConfig){
		 threadPoolExecutor = new ThreadPoolExecutor(Integer.parseInt(synBathConfig.getCorepoolsize()), Integer.parseInt(synBathConfig.getMaxpoolsize()), Long.parseLong(synBathConfig.getKeepalivetime()), TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>( Integer.parseInt(synBathConfig.getQueuecapacity())),
				new ThreadFactory() {
				 private final AtomicInteger mCount = new AtomicInteger(1);
					@Override
					public Thread newThread(Runnable r) {
						return  new Thread(r, "blocking-queue-thread-pool-" + mCount.getAndIncrement());
					}
				},new ThreadPoolExecutor.CallerRunsPolicy());
		 threadPoolExecutor.allowCoreThreadTimeOut(Boolean.parseBoolean(synBathConfig.getAllow()));
		 logger.info("线程池【AsynchronousBathSubmitDataBase】启动参数  corepoolsize:" +synBathConfig.getCorepoolsize()+"|maxpoolsize:"+synBathConfig.getMaxpoolsize()+"|keepalivetime:"+synBathConfig.getKeepalivetime()+"|allow:"+synBathConfig.getAllow()+"|queuecapacity:"+synBathConfig.getQueuecapacity());
	}
	
	public void shutdown(){
		threadPoolExecutor.shutdown();
	}
	
	public Future submit(Runnable job){
//		System.out.println("线程池队列长度："+threadPoolExecutor.getQueue().size());
		return threadPoolExecutor.submit(job);
	}
	
}

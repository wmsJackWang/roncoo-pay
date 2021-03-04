package com.sohu.common.asynchronous;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.sohu.common.renaewal.Renewal;
import com.sohu.common.thread.BlockingQueueThreadPool;
import com.sohu.common.utils.SpringUtil;
import com.sohu.common.validator.Assert;




/**
 * @Desc   : <P>TODO<P>
 * @Author : SOHU-wanghonghui
 * @Date   : 2017年10月24日 上午10:45:55
 * @Version: V1.0
 * @param <T>  实体 Entity
 * @param <R>  异步处理Job
 */
public abstract class AbsAsynchronousQueue<T,R> implements Callable<String>{
	private Logger log = LogManager.getLogger(this.getClass());

	//备份队列前缀 REDPACKET_QUEUE_BAK_
	private String backQueuePrefixName ;
	//管理备份队列队列名称 
	private String backQueeueManageQueueName ;
	//工作队列名称
	private String workQueueName; //REDPACKET_QUEUE_X
	//队列批量取出值
	private int pipCount =20;
	/**
	 * 提取数据进入备份队列名称
	 */
	public static final String QUEUE_BAK_KEY_FINAL="QUEUE_BAK_KEY_FINAL";
	/**
	 * 提取初始队列内信息时间
	 */
	public static final String QUEUE_BAK_KEY_TIME="QUEUE_BAK_KEY_TIME";
	/**
	 * 初始队列名
	 */
	public static final String QUEUE_NAME="QUEUE_NAME";

	private int waitTime = 60;
	private String jobName;//redpacketQueueJob
	private Renewal renewal;
	//执行key
	private String runKey;//redpacket_renewal_key
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private BlockingQueueThreadPool pool;
	private Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer ;

	@SuppressWarnings("unchecked")
	public AbsAsynchronousQueue(){
		Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<T>(entityClass);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void excute() {
		Assert.isBlank(jobName, "jobName：任务名不能空");
		Assert.isBlank(workQueueName, "workQueueName：工作队列名不能空");
		Assert.isBlank(backQueuePrefixName, "backQueuePrefixName：备份队列前缀名不能空");
		//REDPACKET_QUEUE_BAK_MANAGE_KEY
		Assert.isBlank(backQueeueManageQueueName, "backQueeueManageQueueName:管理备份队列队列名称不能为空");

//		System.out.println("====业务启动入口");
		log.info(workQueueName+"异步队列启动完成...");
		while(true){
			try{
//				log.info("创建备份队列.....");
				String queueRandomBakName = getBackQueuePrefixName()+UUID.randomUUID().toString().replaceAll("-","");
				byte[] queueBakNameKeybyte = redisTemplate.getStringSerializer().serialize(getBackQueeueManageQueueName());
				List<T> listMap = redisTemplate.execute(new RedisCallback<List<T>>() {
					@Override
					public List<T> doInRedis(RedisConnection connection)  {
						
						//返回执行结果list
						List<T> arr = new ArrayList<>();
						/*
						 * 1.每次任务启动都会为对应的工作队列创建一个备份队列，备份队列名是随机创建的
						 * 2.备份队列key ， 创建时间 ， 以及相应的工作队列名称 放入一个map结构中并push到备份队列管理 队列中去。
						 * 3.工作队列的数据 都 阻塞方式放入到备份队列里去，并取出 该条数据
						 * 4.如果一开始没有数据就阻塞60s,阻塞完或60s内获取到数据后，开启管道方式  批量获取数据  每批最多20条数据(非阻塞获取)。
						 * 5.将获取的数据  以 List<T> 数据结构返回
						 * 6.数据结果不为空的情况下 ， 为这一批数据创建一个redpacketQueueJob工作对象，并将这批数据放入工作对象中，提交到BlockingQueueThreadPool线程池
						 * 
						 */
						
						//封装随机备份队列key值和队列处理状态
						Map<String,Object> hash = new HashMap<>();
						hash.put(QUEUE_BAK_KEY_FINAL, queueRandomBakName);
						hash.put(QUEUE_BAK_KEY_TIME, DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
						hash.put(QUEUE_NAME, workQueueName);

						//将随机生成key存入管理队列
				    	connection.lPush(queueBakNameKeybyte, jackson2JsonRedisSerializer.serialize(hash));
				    	byte[] queueNameKeybyte = redisTemplate.getStringSerializer().serialize(getWorkQueueName());
				    	byte[] queueBakNameKeybyte = redisTemplate.getStringSerializer().serialize(queueRandomBakName);
				    	byte[] bRPopLPush = connection.bRPopLPush(getWaitTime(), queueNameKeybyte, queueBakNameKeybyte);
				    	
			    		T deserialize = jackson2JsonRedisSerializer.deserialize(bRPopLPush);
			    		if(deserialize!=null){
			    			arr.add( deserialize);
			    		}
//			    		
			    		//开启管道方式获取队列多次，减小redis网络开销
			    		connection.openPipeline();
			    		for (int i = 0; i < getPipCount()-1; i++) {
			    			connection.rPopLPush(queueNameKeybyte, queueBakNameKeybyte);
			    		}
			    		//返回不为空数据
			    		List<Object> closePipeline = connection.closePipeline();
			    		for (int i = 0; i < closePipeline.size(); i++) {
			    			if(closePipeline.get(i)==null){
			    				continue;
			    			}
			    			arr.add(jackson2JsonRedisSerializer.deserialize((byte[])closePipeline.get(i)));
			    		}
						return arr;
					}
				});

//				System.out.println(workQueueName+"判断从队列中取出的数据是否为空");
				if(listMap!=null&&!listMap.isEmpty()){
					IQueueJob  job= (IQueueJob) SpringUtil.getBean(getJobName());//redpacketQueueJob
					job.setQueueName(queueRandomBakName);//备份队列key

//					System.out.println(workQueueName+"取出数据大小："+listMap.size());
					job.setValues(listMap);
//					System.out.println(workQueueName+"提交异步线程池任务");
					System.out.println("提交异步线程 ");
					getPool().submit(job);
					listMap = null;
				}
				else {
					
//					System.out.println("没有领取红包的请求 ");
//					log.info("没有领取红包的请求  .......");
//					System.out.println(workQueueName+"队列没有红包请求");
				}
			}catch (Exception e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				log.error("",e);
			}
		}

	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getBackQueuePrefixName() {
		return backQueuePrefixName;
	}
	public void setBackQueuePrefixName(String backQueuePrefixName) {
		this.backQueuePrefixName = backQueuePrefixName;
	}
	public String getBackQueeueManageQueueName() {
		return backQueeueManageQueueName;
	}
	public void setBackQueeueManageQueueName(String backQueeueManageQueueName) {
		this.backQueeueManageQueueName = backQueeueManageQueueName;
	}
	public int getPipCount() {
		return pipCount;
	}
	public void setPipCount(int pipCount) {
		this.pipCount = pipCount;
	}
	public String getWorkQueueName() {
		return workQueueName;
	}
	public void setWorkQueueName(String workQueueName) {
		this.workQueueName = workQueueName;
	}
	public Renewal getRenewal() {
		return renewal;
	}
	public void setRenewal(Renewal renewal) {
		this.renewal = renewal;
	}
	public BlockingQueueThreadPool getPool() {
		return pool;
	}
	public void setPool(BlockingQueueThreadPool pool) {
		this.pool = pool;
	}
	@Override
	public String call() throws Exception {
		excute();
		return workQueueName;
	}
	public String getRunKey() {
		return runKey;
	}
	public void setRunKey(String runKey) {
		this.runKey = runKey;
	}
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
}

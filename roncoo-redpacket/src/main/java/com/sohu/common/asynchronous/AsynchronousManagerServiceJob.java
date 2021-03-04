package com.sohu.common.asynchronous;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sohu.common.renaewal.Renewal;
import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity.RedpacketReceiveInfoEnum;

@Service
@Scope("prototype")
public	class AsynchronousManagerServiceJob implements Runnable{
		private String backQueeueManageQueueName;
		@Autowired
		private RedisTemplate<String, Object> redisTemplate;
		@Autowired
		private JdbcTemplate jdbcTemplate;

		private Logger log = LogManager.getLogger(this.getClass());
		private Renewal renewal;
		private String runKey;
		//队列批量取出值
		private int pipCount =20;
		private long testTime=5000;
		private long testWaitTime=5000;

		public AsynchronousManagerServiceJob(){
//			jdbcTemplate = (JdbcTemplate) SpringUtil.getBean("jdbcTemplate");
//			redisTemplate = (RedisTemplate<String, Object> ) SpringUtil.getBean("redisTemplate");
		}
		@Override
		public void run() {
	    	excute();
		}
		
		/*
		 *   	管理队列存放着每个批次的备份队列信息， 备份队列在这个批次执行完成后就会被删除掉
		 */
		public void excute() {
			log.info("管理队列【"+backQueeueManageQueueName+"】启动,waitTime: pipCount:"+pipCount);
			System.out.println("启动管理队列");
			while(true){//先判断这个key是否还在运行中
				try{
					List<Map<String, Object>> list = searchManagerQueue();
					//设置下次循环停顿时间
					if(list.size()>0){
						testWaitTime = 1;
					}else{
						testWaitTime = testTime-1000;
					}
					ArrayList<Map<String, Object>> filterQueue = filterQueue(list);
					if(filterQueue.isEmpty()){
						testWaitTime = testTime-1000;
					}
					List<Map<String, Object>> removeQueueValue = removeQueueValue(filterQueue);
					againQueue(removeQueueValue);
					Thread.sleep(testWaitTime);
				}catch (Exception e) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					log.error("RedpacketAsynchronousManagerServiceJob执行出错",e);
				}
			}

		}
		private void againQueue(List<Map<String, Object>> removeQueueValue) {
			if(removeQueueValue==null||removeQueueValue.size()==0){
				return;
			}
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

			redisTemplate.execute(new RedisCallback<List<Map<String, Object>>>() {
				@Override
				public List<Map<String, Object>> doInRedis(RedisConnection connection) throws DataAccessException {

					RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
			        Iterator<Map<String, Object>> iterator = removeQueueValue.iterator();  
			        //删除备份队列数据中已经入数据库的红包 存放在备份队列中的数据
			        while(iterator.hasNext()){  
			        	Map<String, Object> map = iterator.next();
			        	List<byte[]> lRange = connection.lRange(stringSerializer.serialize((String) map.get(AbsAsynchronousQueue.QUEUE_BAK_KEY_FINAL)),0,getPipCount());
						if(!lRange.isEmpty()){
							Map<String,Object> deserialize = (Map<String, Object>) jackson2JsonRedisSerializer.deserialize(lRange.get(0));
							List<Map<String, Object>> queryForMap = jdbcTemplate.queryForList("select ps_trans_id from  SW_REDPACKET_RECEIVE_INFO where fr_id = '"+deserialize.get(RedpacketReceiveInfoEnum.FR_ID.get())+"' and ps_trans_id = '"+deserialize.get(RedpacketReceiveInfoEnum.PS_TRANS_ID.get())+"'");
							if(!queryForMap.isEmpty()){
								connection.del(stringSerializer.serialize((String) map.get(AbsAsynchronousQueue.QUEUE_BAK_KEY_FINAL)));
								iterator.remove();
							}
						}
					}
					connection.openPipeline();
					//从新将抢红包的请求  从备份队列放入到工作队列
					for (Map<String, Object> map : removeQueueValue) {
						String queueName = (String) map.get(AbsAsynchronousQueue.QUEUE_NAME);
						log.info("重新入队列:{},目标队列:{}", map.get(AbsAsynchronousQueue.QUEUE_BAK_KEY_FINAL),queueName);
						connection.rPopLPush(stringSerializer.serialize(getBackQueeueManageQueueName()), stringSerializer.serialize(getBackQueeueManageQueueName()));
						for (int i = 0; i <getPipCount(); i++) {
							connection.rPopLPush(stringSerializer.serialize((String) map.get(AbsAsynchronousQueue.QUEUE_BAK_KEY_FINAL)), stringSerializer.serialize(queueName));
						}
					}
					connection.closePipeline();
					return null;
				}
			});
		}
		
		/**
		 * 
		 * @Title: removeQueueValue 
		 * @Description: 清除入库的临时key值 ,并返回未處理的key
		 * @param jackson2JsonRedisSerializer
		 * @param maybeRm
		 * @return List    返回类型 
		 *
		 */
		private List<Map<String, Object> > removeQueueValue(ArrayList<Map<String, Object>> maybeRm) {
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
			List<Map<String, Object> > untreated = redisTemplate.execute(new RedisCallback<List<Map<String, Object> >>() {
				@Override
				public List<Map<String, Object> > doInRedis(RedisConnection connection) throws DataAccessException {
					RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
					connection.openPipeline();
					for (Map<String, Object> map : maybeRm) {
						connection.lLen(stringSerializer.serialize((String) map.get(AbsAsynchronousQueue.QUEUE_BAK_KEY_FINAL)));
					}
					List<Object> isExists = connection.closePipeline();
					ArrayList<Map<String, Object> > untreated = new ArrayList<>();
					connection.openPipeline();
					for (int i = 0; i < maybeRm.size(); i++) {
						if(((Long) isExists.get(i))>0){
							//5分钟内如果备份队列还存在
							untreated.add(maybeRm.get(i));
						}else{
							//5分钟内如果备份队列不存在存在，则从管理队列删除
							connection.lRem(stringSerializer.serialize(getBackQueeueManageQueueName()),-1,jackson2JsonRedisSerializer.serialize(maybeRm.get(i)));
						}
					}
					connection.closePipeline();
					return untreated;
				}
			});
			return untreated;
		}
		/**
		 * 
		 * @Title: filterQueue 
		 * @Description: 过滤截止到目前五分钟的队列数据
		 * @param list
		 * @return ArrayList<Map<String,Object>>    返回类型 
		 *
		 */
		private ArrayList<Map<String, Object>> filterQueue(List<Map<String, Object>> list) {
			ArrayList<Map<String, Object> > maybeRm = new ArrayList<>();
			for (Map<String, Object> map : list) {
				String object = (String) map.get(AbsAsynchronousQueue.QUEUE_BAK_KEY_TIME);
				Date addMinutes;
				int truncatedCompareTo = 1;
				try {
					addMinutes = DateUtils.addSeconds(DateUtils.parseDate(object, new String[]{"yyyyMMddHHmmss"}), (int) testTime);
					truncatedCompareTo = DateUtils.truncatedCompareTo(new Date(),addMinutes, Calendar.MINUTE);
				} catch (ParseException e) {
					log.error(e);
				}
				if(truncatedCompareTo>=0){
					maybeRm.add(map);
				}else{
//					break;
				}
			}
			
			return maybeRm;
		}
		
		/*
		 *	从管理队列中获取数据列表   REDPACKET_QUEUE_BAK_MANAGE_KEY
		 */
		private List<Map<String, Object>> searchManagerQueue() {
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> list = redisTemplate.execute(new RedisCallback<List<Map<String, Object>>>() {
				@Override
				public List<Map<String, Object>> doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] queueBakManagekeyByte = redisTemplate.getStringSerializer().serialize(getBackQueeueManageQueueName());
					//一次从队列的队尾取出最多20条备份队列管理数据
					List<byte[]> lRange = connection.lRange(queueBakManagekeyByte, 0-pipCount,-1);
					ArrayList<Map<String,Object>> temp = new ArrayList<>();
					for (byte[] bs : lRange) {
						Map<String,Object> deserialize = (Map<String,Object>)jackson2JsonRedisSerializer.deserialize(bs);
						temp.add(deserialize);
					}
					return temp;
				}
			});
			return list;
		}
		public Renewal getRenewal() {
			return renewal;
		}
		public void setRenewal(Renewal renewal) {
			this.renewal = renewal;
		}
		public String getRunKey() {
			return runKey;
		}
		public void setRunKey(String runKey) {
			this.runKey = runKey;
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
		/**
		 * @Desc   : <P>TODO<P>
		 * @author : SOHU-wanghonghui
		 * @date   : 2017年10月10日 下午4:26:54
		 * @Version: V1.0
		 * @param pipCount void
		 */
		public void setPipCount(int pipCount) {
			this.pipCount = pipCount;
		}
//		public long getWaitTime() {
//			return waitTime;
//		}
//		/**
//		 * @Desc   : <P>设定消费队列时间与管理备份队列时间差<P>
//		 * @author : SOHU-wanghonghui
//		 * @date   : 2017年10月10日 下午4:25:25
//		 * @Version: V1.0
//		 * @param waitTime 单位毫秒
//		 */
//		public void setWaitTime(long waitTime) {
//			this.waitTime = waitTime;
//		}
		public long getTestTime() {
			return testTime;
		}
		public void setTestTime(long testTime) {
			this.testTime = testTime;
		}
		public long getTestWaitTime() {
			return testWaitTime;
		}
		/**
		 * @Desc   : <P>毫秒<P>
		 * @author : SOHU-wanghonghui
		 * @date   : 2017年10月11日 上午11:20:23
		 * @Version: V1.0
		 * @param testWaitTime 单位毫秒
		 */
		public void setTestWaitTime(long testWaitTime) {
			this.testWaitTime = testWaitTime;
		}
}
package com.sohu.modules.redpacket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sohu.common.renaewal.Renewal;

@Component
public class StartRenewal implements CommandLineRunner{

	public static final String QUEUE_BAK_KEY_FINAL="QUEUE_BAK_KEY_FINAL";
	public static final String QUEUE_BAK_KEY_TIME="QUEUE_BAK_KEY_TIME";
	@Autowired
	private Renewal renewal;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		init ();
	}

	
	public void init (){
		renewal.init();

//		redpacketLoadDataBase.save(null, null);
//		Thread th = new Thread(){m
//			public void run() {
//		Jackson2JsonRedisSerializer<Map<String, Object>> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//		long currentTimeMillis = System.currentTimeMillis();
//		System.out.println("============================================");
//
//		for (int i = 0; i < 10000; i++) {
//			long currentTimeMillis3 = System.currntTimeMillis();
//
//			redisTemplate.executePipelined(new RedisCallback() {
//				
//				@Override
//				public Object doInRedis(RedisConnection connection) throws DataAccessException {
//					RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
//					//将随机生成key存入管理队列
//					for (int i = 0; i <10000; i++) {
//						Map<String,Object> hash = new HashMap<>();
//						hash.put(QUEUE_BAK_KEY_FINAL, UUID.randomUUID().toString().replace("-", ""));
//						hash.put(QUEUE_BAK_KEY_TIME, System.currentTimeMillis());
//						connection.rPush(stringSerializer.serialize("abcdef"), jackson2JsonRedisSerializer.serialize(hash));
//					}
//					return null;
//				}
//			});
//			long currentTimeMillis14 = System.currentTimeMillis();
//
//			System.out.println("end:"+(currentTimeMillis14-currentTimeMillis3));
//
//		}
//		long currentTimeMillis1 = System.currentTimeMillis();
//		System.out.println("end:"+(currentTimeMillis1-currentTimeMillis));
//			};
//		};
//		th.start();
	}


}

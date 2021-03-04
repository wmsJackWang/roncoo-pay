package com.sohu.common.renaewal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sohu.common.exception.BusinessException;
import com.sohu.common.utils.SpringUtil;



@Service("renewal")
public class Renewal {
	private Logger log = LogManager.getLogger(this.getClass());
	private Map<String, String> renewal = new HashMap<>();
	private Map<String, Integer> count = new HashMap<>();
	private Map<String, IRenewalHeadler> isRun = new HashMap<>();
	
	//key+_SQLExcption
	//key+_RedisTimeout
	//key+_RedisTimeout
	//key+_OtherKey
	private Map<String, Boolean> debug = new HashMap<>();

	
	//redpacket_renewal_key
	@Value("${renewal.keys}")
	private String renewalKey;
	
	//stop
	@Value("${renewal.status}")
	private String status;
	
	//30
	@Value("${renewal.time}")
	private Long time;
	
	//5
	@Value("${renewal.step}")
	private Long step;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public void init() {
		
			
		
		log.info("==============启动续约服务==============");
		String[] split = renewalKey.split(",");
		for (String key : split) {
			renewal.put(key, "-1");
			count.put(key, 0);
			isRun.put(key, null);
		}

		// 将所有key进行续约
		for (String key : split) {
			try {
//				System.out.println(key);
				excuteRenewal(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	protected void excuteRenewal(String key) throws Exception {
		
		//启动业务，headler方法是触发入口
		IRenewalHeadler reneal = (IRenewalHeadler) SpringUtil.getBean(key);
		reneal.headler();
	}

	private void stop(String key) {
		// 设置-1防止redis取出值为""和本地""冲突
		renewal.put(key, "-1");
		count.put(key,0);
		if(isRun.get(key)==null){
			return;
		}
		if(isRun.get(key).isRun()){
			isRun.get(key).shutdown();
		}
	}
	
	public boolean isRuning(String key){
		if(isRun.get(key)==null){
			return false;
		}
		return !isRun.get(key).isShutdowning();
	}
	public static void main(String[] args) {
		Renewal r = new Renewal();
		r.renewalKey = "redis";
		r.time = 30L;
		r.step = 5L;
		r.init();
	}
	
	public void setDebug(String key,boolean value){
		debug.put(key, value);
		log.info("debug"+key+":"+value);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

package com.sohu.modules.redpacket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sohu.common.annotation.CtrlLog;
import com.sohu.common.renaewal.Renewal;
import com.sohu.common.utils.R;
import com.sohu.common.validator.ValidatorUtils;
import com.sohu.common.validator.group.AddGroup;
import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;
import com.sohu.modules.redpacket.service.IRedpacketReceiveInfoService;




/**
 * 红包领取记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
@RestController
@RequestMapping("/")
public class RedpacketReceiveInfoController {
	@Autowired
	private IRedpacketReceiveInfoService redpacketReceiveInfoService;
	
	/////////////////////////////////////////////////////////////////////
	public static final String QUEUE_BAK_KEY_FINAL="QUEUE_BAK_KEY_FINAL";
	public static final String QUEUE_BAK_KEY_TIME="QUEUE_BAK_KEY_TIME";
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private Renewal renewal;
	/////////////////////////////////////////////////////////////////////
	@RequestMapping("/receive")
	@CtrlLog("领取红包")
	public R receive(RedpacketReceiveInfoEntity redpacketReceiveInfo, HttpServletRequest request){
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		return redpacketReceiveInfoService.receive(redpacketReceiveInfo,parameterMap);
	}
	@RequestMapping("/excute/{id}")
	@CtrlLog("消耗红包")
	public R receive(@PathVariable String id){
//		asynchronousServiceImpl.asynchronous(id);
		return R.ok();
	}
	
	
	/**
	 * 
	 * @Title: LREM 
	 * @Description: 测试LREM 方法执行时间，上线后注释
	 * @param key 队列名,
	 * @param time
	 * @return R    返回类型 
	 *
	 */
	@RequestMapping("/LREM/{key}/{time}")
	@CtrlLog("LREM")
	public R LREM(@PathVariable String key,@PathVariable Long time){
		Jackson2JsonRedisSerializer<Map<String, Object>> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

		long currentTimeMillis = System.currentTimeMillis();
		redisTemplate.execute(new RedisCallback<List>() {

			@Override
			public List doInRedis(RedisConnection connection) throws DataAccessException {
				Map<String,Object> hash = new HashMap<>();
				hash.put(QUEUE_BAK_KEY_FINAL, key);
				hash.put(QUEUE_BAK_KEY_TIME, time);
				RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
				Long lRem = connection.lRem(stringSerializer.serialize("abcdef"), 1, jackson2JsonRedisSerializer.serialize(hash));
//				System.out.println("-----"+lRem);
				Long lRem1 = connection.lRem(stringSerializer.serialize("abcdef"), 1, jackson2JsonRedisSerializer.serialize(hash));
//				System.out.println("-----"+lRem1);

				return null;
			}
		});
		long currentTimeMillis1 = System.currentTimeMillis();
		System.out.println("remove"+(currentTimeMillis1-currentTimeMillis));
		return R.ok();
	}
	/**
	 * 
	 * @Title: debug 
	 * @Description: 红包相关测试，上线后注释此代码 
	 * @param key 红包配置主续约时间key值
	 * @param value debug值类型   //key+_SQLExcption
	 *							//key+_RedisTimeout
     *							//key+_RedisTimeout
	 *							//key+_OtherKey
	 * @return R    返回类型 
	 *
	 */
	@RequestMapping("/debug/{key}/{value}")
	@CtrlLog("debug")
	public R debug(@PathVariable String key,@PathVariable Boolean value){
		renewal.setDebug(key,value);
		return R.ok();
	}
	
}

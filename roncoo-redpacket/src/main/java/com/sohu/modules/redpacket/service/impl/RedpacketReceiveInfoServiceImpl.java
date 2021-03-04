package com.sohu.modules.redpacket.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import com.sohu.common.baseenum.BaseEnum.CheckParameterExceptionEnum;
import com.sohu.common.utils.CheckSignUtil;
import com.sohu.common.utils.R;
import com.sohu.common.validator.Assert;
import com.sohu.common.validator.ValidatorUtils;
import com.sohu.common.validator.group.AddGroup;
import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;
import com.sohu.modules.redpacket.enums.RedpacketEnum.RedisQueueName;
import com.sohu.modules.redpacket.enums.RedpacketEnum.RedpacketCheckBusinessExcption;
import com.sohu.modules.redpacket.service.AbsRedpacket;
import com.sohu.modules.redpacket.service.IRedpacketReceiveInfoService;



@Service("redpacketReceiveInfoService")
public class RedpacketReceiveInfoServiceImpl extends AbsRedpacket implements IRedpacketReceiveInfoService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	

	private static DefaultRedisScript getRedisScript;
	
	
	static {
		
		getRedisScript = new DefaultRedisScript();
	    getRedisScript.setResultType(String.class);
	    getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/getRedPack.lua")));

	}
	
	/**
	 * @author mingshengwang
	 * @date 上午9:57:28
	 * @todo TODO
	 * @descript OVER_TIME 红包超时时间
	 */
	private int OVER_TIME = 60*60*24;

	@SuppressWarnings("unchecked")
	@Override
	public R  receive(RedpacketReceiveInfoEntity redpacketReceiveInfo,Map<String, String[]> parameterMap) {
		ValidatorUtils.validateEntity(redpacketReceiveInfo, AddGroup.class);
		Assert.isTrue(redpacketReceiveInfo.getAmt().compareTo(BigDecimal.ZERO)<=0, "领取金额必须大于零元");
		
		redpacketReceiveInfo.setPlanAmt(redpacketReceiveInfo.getAmt());
		List<Object> executePipelined = initCheckInfo(redpacketReceiveInfo);
		Assert.isTrue(executePipelined.get(0)==null, "业务线信息不存在");
		Map<String,Object> merchantInfo = (Map<String, Object>) executePipelined.get(0);
		String seed = (String) merchantInfo.get("seed");
		String signOrder = CheckSignUtil.signOrderArr(parameterMap,seed);
		Assert.isFalse(signOrder.equals(redpacketReceiveInfo.getSign()),CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(), "签名错误");
//		Assert.isFalse((Boolean) executePipelined.get(1),"该用户状态异常,不能领取红包");
		Assert.isFalse((Boolean)executePipelined.get(1),"该红包不存在或已过期或已被领取完");
		Assert.isTrue((Boolean) executePipelined.get(2),RedpacketCheckBusinessExcption.REDPACKET_CHECK_TRANS_REPEAT.getCode(),"领取红包订单重复");

		
		String key =getRedpacketOnlyKey(redpacketReceiveInfo);
		  /**
         * 	List设置lua的KEYS
         */
        List<String> keyList = new ArrayList();
        keyList.add(key);
        keyList.add("\"remainderkey\"");
        
        /**
         * 	用Mpa设置Lua的ARGV[1]
//         */
//        Map<String,Object> argvMap = new HashMap<String,Object>();
//        argvMap.put("amt",redpacketReceiveInfo.getAmt().doubleValue()); 
        //redis小数位自减 会出现误差，因此转换为整数。
        BigDecimal resultAmt = redpacketReceiveInfo.getAmt().multiply(new BigDecimal(100)); 
        Assert.isTrue((resultAmt.subtract(new BigDecimal(String.valueOf(resultAmt.intValue())))).doubleValue() > 0 ,CheckParameterExceptionEnum.CHECK_PARAMETER_EXCEPTION.getCode(), "金额参数小数点位数不能大于2");
        
        
        String amt = resultAmt.toString(); 
        System.out.println(amt);
        
        long start = System.currentTimeMillis();
		Object result = redisTemplate.execute(getRedisScript,redisTemplate.getStringSerializer(),redisTemplate.getStringSerializer(),keyList,resultAmt.toString());
		
		long end = System.currentTimeMillis();
		
		String resultT = String.valueOf(result);
		double re = Double.valueOf(resultT);
		
		Assert.isFalse(re == -1,CheckParameterExceptionEnum.CHECK_BUSINESS_EXCEPTION.getCode(),"红包金额不足不能领取");
		
		loadRedis(redpacketReceiveInfo);
		R ok = R.ok();
		String sign = CheckSignUtil.signOrder(ok, seed);

		ok.put("sign", sign);
		return ok;
	}



	

	/**
	 * @Desc   : <P>加载检查信息<P>
	 * @author : SOHU-wanghonghui
	 * @date   : 2017年9月25日 下午3:38:11
	 * @Version: V1.0
	 * @param userKey
	 * @param redPacketKey
	 * @return List<Object> get(0)用户是否存在：boolean 类型;get(1)红包是否存在：RedpacketReceiveInfoEntity类型 ；get(2)此红包编号是否领取过：boolean类型
	 */
	@SuppressWarnings("rawtypes")
	protected List<Object> initCheckInfo(RedpacketReceiveInfoEntity info) {
		List<Object> executePipelined = redisTemplate.executePipelined(new RedisCallback<List>() {
			@Override
			public List doInRedis(RedisConnection connection) throws DataAccessException {
//		    	byte[] userKeyByte = redisTemplate.getStringSerializer().serialize( getUserInfoOnlyKey(info));
		    	byte[] redPacketKeyByte = redisTemplate.getStringSerializer().serialize(getRedpacketOnlyKey(info));
		    	byte[] receiveKeyByte = redisTemplate.getStringSerializer().serialize(getReceiveOnlyKey(info.getProductId(), info.getFrId(), info.getPsTransId()));
		    	byte[] merchantInfoKey = redisTemplate.getStringSerializer().serialize(getMerchantInfoKey(info));

		    	connection.get(merchantInfoKey);
//				connection.exists(userKeyByte);
				connection.exists(redPacketKeyByte);
				connection.exists(receiveKeyByte);
				return null;
			}
		});
		return executePipelined;
	}
	/**
	 * @Desc   : <P>根据红包编号切分红包，并存入8个阻塞队列当中<P>
	 * @author : SOHU-wanghonghui
	 * @date   : 2017年9月25日 下午3:31:22
	 * @Version: V1.0 void
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	protected void loadRedis(RedpacketReceiveInfoEntity redpacketReceiveInfo) {
		redpacketReceiveInfo.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		List<Object> executePipelined = redisTemplate.executePipelined(new RedisCallback<List>() {
			@Override
			public List doInRedis(RedisConnection connection) throws DataAccessException {
		    	Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		    	//放入到8个redis阻塞队列中的一个
		    	byte[] splitQueueKey = redisTemplate.getStringSerializer().serialize(getQueueKey(redpacketReceiveInfo.getFrId()));
		    	//红包领取信息  存入redis中24小时
		    	byte[] repacketPsTransIdKey = redisTemplate.getStringSerializer().serialize(getReceiveOnlyKey(redpacketReceiveInfo.getProductId(),redpacketReceiveInfo.getFrId(),redpacketReceiveInfo.getPsTransId()));
				connection.lPush(splitQueueKey,jackson2JsonRedisSerializer.serialize(redpacketReceiveInfo));
				connection.setEx(repacketPsTransIdKey, OVER_TIME,jackson2JsonRedisSerializer.serialize(RedisQueueName.NULL_STRING));
				return null;
			}
		});
		
		
	}


	
}

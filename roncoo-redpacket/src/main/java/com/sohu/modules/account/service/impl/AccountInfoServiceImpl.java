package com.sohu.modules.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sohu.common.utils.BeanToMapUtil;
import com.sohu.common.validator.Assert;
import com.sohu.modules.account.dao.AccountInfoDao;
import com.sohu.modules.account.entity.AccountInfoEntity;
import com.sohu.modules.account.entity.AccountInfoEntity.AccountInfoEnum;
import com.sohu.modules.account.service.IAccountInfoService;



@Service("accountInfoService")
public class AccountInfoServiceImpl implements IAccountInfoService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private AccountInfoDao accountInfoDao;
	
	@Override
	public AccountInfoEntity queryObject(Long id){
		return accountInfoDao.queryObject(id);
	}
	
	@Override
	public List<AccountInfoEntity> queryList(Map<String, Object> map){
		return accountInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return accountInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(AccountInfoEntity accountInfo){
		accountInfoDao.insert(accountInfo);
	}
	
	@Override
	public void update(AccountInfoEntity accountInfo){
		accountInfoDao.update(accountInfo);
	}
	
	@Override
	public void delete(Long id){
		accountInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		accountInfoDao.deleteBatch(ids);
	}
	
	@Override
	public AccountInfoEntity queryObjectCacheIfAbsentUpdate(String userName, Long merChantId) {
		String key = merChantId + "_" + userName;
		Map object = (Map) redisTemplate.opsForValue().get(key);
		
		Map<String,Object> map = new HashMap<>();
		map.putIfAbsent(AccountInfoEnum.USER_NAME.get(), userName);
		map.putIfAbsent(AccountInfoEnum.MERCHANT_ID.get(), merChantId);

		AccountInfoEntity accountInfoBean; 
		if (object == null) {
			List<AccountInfoEntity> queryList = accountInfoDao.queryList(map);
			Assert.isNull(queryList, "该用户不存在");
			
			Map<String, Object> beanToMap = BeanToMapUtil.beanToMap(queryList.get(0));
			redisTemplate.opsForValue().set(key, beanToMap);
			return queryList.get(0);
		} else {
			accountInfoBean = new AccountInfoEntity();
			BeanToMapUtil.mapToBean(object,accountInfoBean);
			return accountInfoBean;
		}
	}
	
}

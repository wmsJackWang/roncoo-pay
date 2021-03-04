package com.sohu.modules.account.service;

import com.sohu.modules.account.entity.AccountInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-24 14:05:43
 */
public interface IAccountInfoService {
	
	AccountInfoEntity queryObject(Long id);
	
	List<AccountInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AccountInfoEntity accountInfo);
	
	void update(AccountInfoEntity accountInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
	
	AccountInfoEntity queryObjectCacheIfAbsentUpdate(String userName, Long merChantId);

}

package com.sohu.modules.account.dao;

import com.sohu.modules.account.entity.AccountInfoEntity;
import com.sohu.mapper.base.BaseMappper;
import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-24 14:05:43
 */
@Mapper
public interface AccountInfoDao extends BaseMappper<AccountInfoEntity,Long> {
	
}

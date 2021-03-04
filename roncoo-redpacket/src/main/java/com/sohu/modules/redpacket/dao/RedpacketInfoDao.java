package com.sohu.modules.redpacket.dao;

import com.sohu.modules.redpacket.entity.RedpacketInfoEntity;
import com.sohu.mapper.base.BaseMappper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发红包记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
@Mapper
public interface RedpacketInfoDao extends BaseMappper<RedpacketInfoEntity,Long> {
	
}

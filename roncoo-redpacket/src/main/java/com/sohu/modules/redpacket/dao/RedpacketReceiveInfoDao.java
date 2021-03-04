package com.sohu.modules.redpacket.dao;

import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;
import com.sohu.mapper.base.BaseMappper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 红包领取记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
@Mapper
public interface RedpacketReceiveInfoDao extends BaseMappper<RedpacketReceiveInfoEntity,Long> {
	
}

package com.sohu.modules.redpacket.service;

import java.util.List;
import java.util.Map;

import com.sohu.common.utils.R;
import com.sohu.modules.redpacket.entity.RedpacketInfoEntity;

/**
 * 发红包记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
public interface IRedpacketInfoService {
	
	R createRedPacket(RedpacketInfoEntity redpacketInfo,Map<String, String[]> parameterMap);
	
	RedpacketInfoEntity queryObject(Long id);
	
	List<RedpacketInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(RedpacketInfoEntity redpacketInfo);
	
	void update(RedpacketInfoEntity redpacketInfo);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}

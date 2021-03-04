package com.sohu.modules.redpacket.service;

import java.util.Map;

import com.sohu.common.utils.R;
import com.sohu.modules.redpacket.entity.RedpacketReceiveInfoEntity;

/**
 * 红包领取记录
 * 
 * @author wanghonghui
 * @email sunlightcs@gmail.com
 * @date 2017-09-23 22:34:30
 */
public interface IRedpacketReceiveInfoService {
	
	/**
	 * 
	 * @Title: receive 
	 * @Description: TODO(领取红包) 
	 * @param redpacketReceiveInfo void    返回类型 
	 * @param parameterMap 
	 *
	 */
	R receive(RedpacketReceiveInfoEntity redpacketReceiveInfo, Map<String, String[]> parameterMap);

}

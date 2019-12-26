/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roncoo.pay.common.core.page.PageBean;
import com.roncoo.pay.common.core.page.PageParam;
import com.roncoo.pay.user.dao.RpUserPayExtInfoDao;
import com.roncoo.pay.user.entity.RpUserPayExtInfo;
import com.roncoo.pay.user.service.RpUserPayExtInfoService;

/**
 * 用户第三方支付信息service实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Service("rpUserPayExtInfoService")
public class RpUserPayExtInfoServiceImpl implements RpUserPayExtInfoService {
	
	
	private static final Logger log = LoggerFactory.getLogger(RpUserPayExtInfoServiceImpl.class);


	@Autowired
	private RpUserPayExtInfoDao rpUserPayExtInfoDao;
	
	@Override
	public void saveData(RpUserPayExtInfo rpUserPayExtInfo) {
		rpUserPayExtInfoDao.insert(rpUserPayExtInfo);
	}

	@Override
	public void updateData(RpUserPayExtInfo rpUserPayExtInfo) {
		rpUserPayExtInfoDao.update(rpUserPayExtInfo);
	}

	@Override
	public RpUserPayExtInfo getDataById(String id) {
		return rpUserPayExtInfoDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpUserPayExtInfo rpUserPayExtInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return rpUserPayExtInfoDao.listPage(pageParam, paramMap);
	}

	/**
	 * 通过商户编号获取商户支付配置信息
	 *
	 * @param userNO
	 * @return
	 */
	@Override
	public List<RpUserPayExtInfo> getByUserIdAndPayWayCode(String userNo , String payWayCode) {
		
		log.info("查询扩展信息的两个条件为  --> 用户编号：{} ， 支付渠道编码：{}", userNo,payWayCode);
		return rpUserPayExtInfoDao.getByRpUserPayInfoIdAndPayWayCode(userNo,payWayCode);
	}
}
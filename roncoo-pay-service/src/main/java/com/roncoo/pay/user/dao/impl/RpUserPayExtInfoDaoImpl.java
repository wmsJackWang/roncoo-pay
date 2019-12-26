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
package com.roncoo.pay.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.roncoo.pay.common.core.dao.impl.BaseDaoImpl;
import com.roncoo.pay.user.dao.RpUserPayExtInfoDao;
import com.roncoo.pay.user.entity.RpUserPayExtInfo;
import com.roncoo.pay.user.entity.RpUserPayInfo;

/**
 * 用户第三方支付信息dao实现类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
@Repository
public class RpUserPayExtInfoDaoImpl  extends BaseDaoImpl<RpUserPayExtInfo> implements RpUserPayExtInfoDao {

	/*
	 * (non-Javadoc)
	 * @see com.roncoo.pay.user.dao.RpUserPayExtInfoDao#getByRpUserPayInfoId(java.lang.String)
	 * 根据用户的支付渠道 id
	 */
	@Override
	public List<RpUserPayExtInfo> getByRpUserPayInfoIdAndPayWayCode(String userNo , String payWayCode) {
		// TODO Auto-generated method stub
		Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("userNo",userNo);
        paramMap.put("payWayCode",payWayCode);
        return super.listBy(paramMap);
	}

	
}
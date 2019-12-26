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
package com.roncoo.pay.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.roncoo.pay.common.core.dao.BaseDao;
import com.roncoo.pay.user.entity.RpUserPayExtInfo;

/**
 * 用户第三方支付信息dao
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public interface RpUserPayExtInfoDao  extends BaseDao<RpUserPayExtInfo> {

    /**
     * 通过商户的对应渠道的支付信息的id 来查询 支付信息的扩展信息
     * @param userNo
     * @param payWayCode
     * @return
     */
    public  List<RpUserPayExtInfo> getByRpUserPayInfoIdAndPayWayCode(String userNo , String payWayCode);

}
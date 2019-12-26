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
package com.roncoo.pay.user.entity;

import com.roncoo.pay.common.core.entity.BaseEntity;

import java.io.Serializable;

/**
 * 用户第三方支付信息实体类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public class RpUserPayExtInfo extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 用户的专属支付渠道信息id
	 */
	private String rp_user_pay_info_id; 
	//支付渠道类型
	private String pay_way_code;
	//支付渠道类型描述
	private String pay_way_name;
	//支付渠道  信息类型码
	private String type_code;
	//支付渠道  信息类型描述
	private String type_desc;
	//扩展信息内容
	private String content;
	
	
	public String getRp_user_pay_info_id() {
		return rp_user_pay_info_id;
	}
	public void setRp_user_pay_info_id(String rp_user_pay_info_id) {
		this.rp_user_pay_info_id = rp_user_pay_info_id;
	}
	public String getPay_way_code() {
		return pay_way_code;
	}
	public void setPay_way_code(String pay_way_code) {
		this.pay_way_code = pay_way_code;
	}
	public String getPay_way_name() {
		return pay_way_name;
	}
	public void setPay_way_name(String pay_way_name) {
		this.pay_way_name = pay_way_name;
	}
	public String getType_code() {
		return type_code;
	}
	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String getType_desc() {
		return type_desc;
	}
	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "RpUserPayExtInfo [rp_user_pay_info_id=" + rp_user_pay_info_id + ", pay_way_code=" + pay_way_code
				+ ", pay_way_name=" + pay_way_name + ", type_code=" + type_code + ", type_desc=" + type_desc
				+ ", content=" + content + "]";
	}
}
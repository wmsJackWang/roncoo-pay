package com.sohu.entity.base;

import javax.validation.constraints.NotNull;

import com.sohu.common.validator.group.AddGroup;
import com.sohu.common.validator.group.UpdateGroup;

public class ParameterEntity {
	@NotNull(message = "sign不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String sign;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


}

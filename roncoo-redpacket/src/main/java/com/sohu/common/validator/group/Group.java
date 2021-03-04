package com.sohu.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 
 * @ClassName: Group 
 * @Description: 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * @author honghuiwang 
 * @date 2017年9月10日 下午5:39:46 
 *
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}

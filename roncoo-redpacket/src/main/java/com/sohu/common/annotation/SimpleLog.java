package com.sohu.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName: SimpleLog 
 * @Description: Simple日志注解 
 * @author honghuiwang 
 * @date 2017年9月2日 下午4:17:41 
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleLog {

	String value() default "";
}

package com.sohu.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sohu.common.baseenum.DataSourceContent;

/**
 * @Desc   : <P>TODO<P>
 * @Author : SOHU-changjiwang
 * @Date   : 2016年11月21日 下午5:56:47
 * @Version: V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Datasource {
	DataSourceContent value() default DataSourceContent.DATASOURCE_MASTER;

}

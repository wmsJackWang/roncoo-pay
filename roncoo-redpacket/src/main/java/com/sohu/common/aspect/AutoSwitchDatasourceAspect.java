package com.sohu.common.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sohu.common.annotation.Datasource;
import com.sohu.common.baseenum.DataSourceContent;
import com.sohu.datasource.DynamicDataSourceHolder;

/**
 * @Desc :
 *       <P>
 *       TODO
 *       <P>
 * @Author : SOHU-changjiwang
 * @Date : 2016年11月21日 下午6:06:31
 * @Version: V1.0
 */
@Aspect
@Component
@Order(-1)
public class AutoSwitchDatasourceAspect {


	@Pointcut("@annotation(com.sohu.common.annotation.Datasource)")
	public void logPointCut() { 
		
	}
	@Before("logPointCut()&& @annotation(ds)")
	public void before(Datasource ds) {
		DynamicDataSourceHolder.setDataSourceName(ds.value());
	}
	@After("logPointCut()&& @annotation(ds)")
	public void after(Datasource ds) {
		DynamicDataSourceHolder.setDataSourceName(DataSourceContent.DATASOURCE_MASTER);
	}

}

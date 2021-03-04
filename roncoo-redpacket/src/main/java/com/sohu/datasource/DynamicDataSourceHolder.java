package com.sohu.datasource;

import com.sohu.common.baseenum.DataSourceContent;

/**
 * @Desc   : <P>TODO<P>
 * @Author : SOHU-changjiwang
 * @Date   : 2016年11月22日 上午11:08:28
 * @Version: V1.0
 */
public class DynamicDataSourceHolder {

	private static final ThreadLocal<String> holder = new ThreadLocal<String>();

	private DynamicDataSourceHolder() {
	}

	public static void setDataSourceName(String name) {
		holder.set(name);
	}

	public static void setDataSourceName(DataSourceContent dsContent) {
		holder.set(dsContent.getValue());
	}
	
	public static String getDataSourceName() {
		return holder.get();
	}
	

	public static void removeDataSourceName() {
		holder.remove();
	}
	
}
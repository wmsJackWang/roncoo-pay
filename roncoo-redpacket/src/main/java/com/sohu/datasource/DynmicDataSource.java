package com.sohu.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynmicDataSource  extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSourceName();
	}
	
	
}
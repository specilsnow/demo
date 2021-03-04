package com.cdutcm.core.database;

import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author zhufj
 * @Description 多个数据源
 * @Date 2016-9-23
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDataSource();
	}

	@Override
	public Logger getParentLogger() {
		return null;
	}

}

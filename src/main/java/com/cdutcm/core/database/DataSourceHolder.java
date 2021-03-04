package com.cdutcm.core.database;

/**
 * @author zhufj
 * @Description 数据源的操作
 * @Date 2016-10-27
 */
public class DataSourceHolder {

	// 线程本地环境
	private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

	// 设置数据源
	public static void setDataSource(String customerType) {
		dataSources.set(customerType);
	}

	// 获取数据源
	public static String getDataSource() {
		return (String) dataSources.get();
	}

	// 清除数据源
	public static void clearDataSource() {
		dataSources.remove();
	}
}

package com.cdutcm.core.util;

import java.io.*;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhufj
 * @Created on 2014-11-17
 * @Description 处理 properties 文件
 */
public class PropertiesUtil {
	private static Properties resourceProperties = new Properties();
	private static Properties sqlProperties = new Properties();
	private static Properties dbProperties = new Properties();
	protected static Log errorLogger = LogFactory.getLog("hisError");

	public static void initProperties() throws Exception {
		// AbstractApplicationContext context = new
		// ClassPathXmlApplicationContext("locations.xml");
		// Hospital hospital = (Hospital)context.getBean("hospital");
		// InputStream is = null;
		// // 加载基础 SQL 资源文件
		// is = PropertiesUtil.class.getResourceAsStream("/sql.properties");
		// // 防止中文乱码
		// sqlProperties.load(new BufferedReader(new InputStreamReader(is)));
		// close(is);
		// 加载基础配置资源文件
		// is =
		// PropertiesUtil.class.getResourceAsStream("/resource/resource.properties");
		// resourceProperties.load(new BufferedReader(new
		// InputStreamReader(is,"UTF-8")));
		// close(is);
		// context.close();
	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 * @Description: 获取Properties文件里面的值
	 */
	public static String getValue(String key) throws Exception {
		String result = null;
		try {
			result = new String(resourceProperties.getProperty(key).getBytes(
					"ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			errorLogger.error(e.toString());
		}
		return result;
	}

	/**
	 * @param key
	 * @return gbk专用 保证Properties文件是UTF
	 * @throws Exception
	 * @Description: 获取Properties文件里面的值
	 */
	public static String getValueUTF(String key) throws Exception {
		String result = null;
		try {
			result = new String(resourceProperties.getProperty(key));
		} catch (Exception e) {
			errorLogger.error(e.toString());
		}
		return result;
	}

	public static String getDBValue(String key) throws Exception {
		String result = null;
		try {
			result = new String(dbProperties.getProperty(key).getBytes(
					"ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			errorLogger.error(e.toString());
		}
		return result;
	}

	/**
	 * @param key
	 * @return
	 * @throws Exception
	 * @Description: 获取SQL Properties文件里面的值
	 */
	public static String getSQLValue(String key) throws Exception {
		String result = null;
		try {
			String databaseType = new String(resourceProperties.getProperty(
					"system.databaseType").getBytes("ISO-8859-1"));
			result = new String(sqlProperties.getProperty(
					key + "." + databaseType).getBytes("ISO-8859-1"));
		} catch (Exception e) {
			errorLogger.error(e.toString());
		}
		return result;
	}

	/**
	 * 更新properties文件属性值
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String propertiesFileName, String key,
			String value) {
		Properties props = new Properties();
		OutputStream os = null;
		try {
			String classRootPath = PropertiesUtil.class.getResource("/")
					.toString();
			if ("Windows".indexOf(System.getProperty("os.name")) != -1) {
				classRootPath = classRootPath.replace("file:/", "");
			} else {
				classRootPath = classRootPath.replace("file:", "");
			}
			props.load(PropertiesUtil.class.getResourceAsStream("/"
					+ propertiesFileName));
			os = new FileOutputStream(classRootPath + propertiesFileName);
			props.put(key, value);
			props.store(os, "");
		} catch (Exception e) {
			errorLogger.error(e.toString());
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					errorLogger.error(e.toString());
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private static void close(InputStream is) throws Exception {
		if (is != null) {
			is.close();
		}
	}

	public static String getProperties(String filePath, String keyWord){
		Properties prop = new Properties();
		String value = null;
		try {
			// 通过输入缓冲流进行读取配置文件
			InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
			// 加载输入流
			prop.load(InputStream);
			// 根据关键字获取value值
			value = prop.getProperty(keyWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}

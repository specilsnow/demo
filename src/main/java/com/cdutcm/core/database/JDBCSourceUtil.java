package com.cdutcm.core.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title:JDBCSourceUtil Description:
 * 
 * @author zhangmin
 * @date 2016-12-1上午9:41:50
 */
public final class JDBCSourceUtil {
	private static String url = "";
	private static String username = "";
	private static String password = "";
	static Connection conn = null;

	private static final Logger errorlogger = LoggerFactory.getLogger("error");

	public static Connection getConn() {
		Properties p = new Properties();
		try {
			InputStream in = JDBCSourceUtil.class
					.getResourceAsStream("/spring/datasource/datasource_PingChang.properties");
			p.load(in);
			in.close();

			if (p.containsKey("primary.url")) {
				url = p.getProperty("primary.url");
			}
			if (p.containsKey("primary.username")) {
				username = p.getProperty("primary.username");
			}
			if (p.containsKey("primary.password")) {
				password = p.getProperty("primary.password");
			}
		} catch (IOException ex) {
			errorlogger.error(ex.toString());
		}
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException ex) {
			errorlogger.error(ex.toString());
		} catch (ClassNotFoundException ex) {
			errorlogger.error(ex.toString());
		}
		return conn;
	}

	public static Connection getConn(SqlSession sqlSession) {
		return sqlSession.getConnection();
	}

	public static void closeCon(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			errorlogger.error(e.toString());
		}
	}
}

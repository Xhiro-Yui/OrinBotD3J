package com.github.xhiroyui.orinbot.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtil {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);
	private static HikariDataSource dataSource;

	public static void initializeDatabase(String dBUrl, String username, String password) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(dBUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		dataSource = new HikariDataSource(config);
	}

	private static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}

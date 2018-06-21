package com.yan.examples.mysql;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Test {

	private static ComboPooledDataSource comboPooledDataSource;

	private static void init() {
		// 创建C3P0的连接池，注意与DBCP的区别
		comboPooledDataSource = new ComboPooledDataSource();

		InputStream inputStream = C3P0Test.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);

			comboPooledDataSource.setUser(properties.getProperty("db.username"));
			comboPooledDataSource.setPassword(properties.getProperty("db.password"));
			comboPooledDataSource.setJdbcUrl(properties.getProperty("db.url"));
			comboPooledDataSource.setDriverClass(properties.getProperty("db.driverClassName"));

			comboPooledDataSource
					.setInitialPoolSize(Integer.parseInt(properties.getProperty("dataSource.initialSize")));
			comboPooledDataSource.setMaxIdleTime(Integer.parseInt(properties.getProperty("dataSource.maxIdle")));
			comboPooledDataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty("dataSource.maxActive")));
			comboPooledDataSource
					.setMaxIdleTimeExcessConnections(Integer.parseInt(properties.getProperty("dataSource.maxWait")));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public synchronized static Connection getConnection() {
		if (comboPooledDataSource == null) {
			init();
		}
		try {
			return comboPooledDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			Connection connection = getConnection();
			if (connection != null) {
				String sql = "select * from user";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					System.out.println(resultSet.getString("Host"));
				}
			} else {
				System.out.println("获取Connection失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
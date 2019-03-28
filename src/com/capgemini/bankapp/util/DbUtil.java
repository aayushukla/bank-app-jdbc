package com.capgemini.bankapp.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DbUtil {

	private static String dburl;
	private static String username;
	private static String password;

	static Connection connection;
	static final Logger logger = Logger.getLogger(DbUtil.class);

	public static Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(connection==null) {
			connection = DriverManager.getConnection(dburl, username, password);
			connection.setAutoCommit(false);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Driver class not found");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void commit() {
		try {
			if (connection != null)
				connection.commit();
		} catch (SQLException e) {
			logger.error("SQLException", e);
			{
			}
		}
	}

	public static void rollback() {
		try {
			if (connection != null)
				connection.rollback();
		} catch (SQLException e) {
			logger.error("SQLException", e);
			{
			}
		}
	}

	static {
		try {
			File file = new File("dbCOnfig.properties");
			FileReader read = new FileReader(file);

			Properties p = new Properties();
			p.load(read);
			read.close();

			dburl = p.getProperty("dburl");
			username = p.getProperty("username");
			password = p.getProperty("password");

		} catch (IOException e) {

		}

	}
}

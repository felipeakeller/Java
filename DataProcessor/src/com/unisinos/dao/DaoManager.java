package com.unisinos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoManager {
	
	private static DaoManager daoManager;
	private Connection connection;
	
	public DaoManager() {
		try {
			Class.forName("org.sqlite.JDBC");			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	public static DaoManager getInstance() {
		if(daoManager == null) {
			daoManager = new DaoManager();
		}
		return daoManager;
	}
	
	public DaoManager openConnection(String path) {
		try {
			connection = DriverManager.getConnection(path);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public ResultSet query(String sql) {
		try {
			return connection.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

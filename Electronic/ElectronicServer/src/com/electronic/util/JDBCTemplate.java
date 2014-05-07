package com.electronic.util;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBCTemplate<T> {
	private String driverClass="com.mysql.jdbc.Driver";
	private String jdbcURL="jdbc:mysql://localhost/whiteboard?useUnicode=true&characterEncoding=UTF-8";
	private String user="root";
	private String pwd="1234";
	abstract public T execute() throws Exception;
	protected Connection getConnection()throws Exception {
			Class.forName(driverClass);
			Connection conn=DriverManager.getConnection(jdbcURL, user, pwd);
			return conn;
		
	}
	

}

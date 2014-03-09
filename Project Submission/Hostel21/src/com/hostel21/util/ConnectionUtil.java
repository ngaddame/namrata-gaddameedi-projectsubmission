package com.hostel21.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
	public static Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:/tmp/Hostel21.db");
			c.setAutoCommit(false); 
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
		return c; //make sure close this connection after use.
	}
}

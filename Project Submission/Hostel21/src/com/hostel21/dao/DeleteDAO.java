package com.hostel21.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.hostel21.util.ConnectionUtil;

public class DeleteDAO {
	public static void delete(String tableName,String keyColumn, Integer keyValue) {
		Connection con=null;
    	try {		
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("delete from "+tableName+" where "+keyColumn+"=?");
			st.setInt(1, keyValue);
			st.executeUpdate();
			con.commit();
			
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			throw new RuntimeException(e);
		}
		finally {
			try {
				con.close();
			}
			catch(Exception e) {
	    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
				
			}
		}  			
	}
	public static void delete(String tableName,String keyColumn, String keyValue) {
		Connection con=null;
    	try {		
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("delete from "+tableName+" where "+keyColumn+"=?");
			st.setString(1, keyValue);
			st.executeUpdate();
			con.commit();
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			throw new RuntimeException(e);
		}
		finally {
			try {
				con.close();
			}
			catch(Exception e) {
	    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
				
			}
		}  			
	}

}

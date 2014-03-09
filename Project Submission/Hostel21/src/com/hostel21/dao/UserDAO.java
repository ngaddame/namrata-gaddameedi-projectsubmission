package com.hostel21.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hostel21.beans.User;
import com.hostel21.util.ConnectionUtil;

public class UserDAO {
	public static void add(User user) {
		Connection con=null;
    	try {
    		con=ConnectionUtil.getConnection();
			PreparedStatement st=con.prepareStatement("insert or replace into USER (USER_ID,PASSWORD,USER_TYPE, USER_FIRST_NAME, USER_LAST_NAME,EXPIRE_DATE) "+
			" values (?,?,?,?,?,?)");
			st.setString(1, user.getUserId());
			st.setString(2, user.getPassword());
			st.setString(3, user.getUserType());
			st.setString(4, user.getFirstName());
			st.setString(5, user.getLastName());
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
	
	public static User getById(String userId) {
		Connection con=null;
		User user= null;
    	try {
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("select * from User where user_id=?");
			st.setString(1, userId);
			ResultSet rs=st.executeQuery(); 
			if(rs.next()) {
				user = new User(rs.getString("USER_ID"),rs.getString("PASSWORD"),rs.getString("USER_TYPE"),rs.getString("FIRST_NAME"),rs.getString("LAST_NAME"));
			}
			
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
    	return user;
			
	}
}

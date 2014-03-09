package com.hostel21.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hostel21.util.ConnectionUtil;

public class SequenceIdDAO {
	public static Integer createNextId(String tableName) {		
		Connection con=null;
		Integer id=100;
    	try {
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("select seq+1 from sqlite_sequence where name=?; ");
			st.setString(1, tableName);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				id=rs.getInt(1);
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
    	return id;
			
	}
}

package com.hostel21.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hostel21.beans.Hostel;
import com.hostel21.util.ConnectionUtil;

public class HostelDAO {
	public static void add(Hostel hostel) {
		Connection con=null;
    	try {
    		con=ConnectionUtil.getConnection();

			PreparedStatement st=con.prepareStatement("insert or replace into hostel(HOSTEL_NAME,STREET,CITY ,STATE,POSTAL_CODE,COUNTRY,CHECKIN_TIME,CHECKOUT_TIME,SMOKING,ALCOHOL,"+
    				"cancellation_deadline,cancellation_penalty,PHONE,EMAIL,FACEBOOK,WEB)" +
					" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			st.setString(1, hostel.getName());
			st.setString(2, hostel.getStreet());
			st.setString(3, hostel.getCity());
			st.setString(4, hostel.getState());
			st.setString(5, hostel.getPostalCode());
			st.setString(6, hostel.getCountry());
			st.setString(7, hostel.getCheckinTime());
			st.setString(8, hostel.getCheckoutTime());
			st.setString(9, hostel.getSmoking());
			st.setString(10, hostel.getAlcohol());
			st.setInt(11, hostel.getCancellationDeadline());
			st.setDouble(12, hostel.getCancellPenalty());
			st.setString(13, hostel.getPhone());
			st.setString(14, hostel.getEmail());
			st.setString(15, hostel.getFacebook());
			st.setString(16, hostel.getWeb());
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
	
	public static Hostel getByHostelName(String name) {
		Connection con=null;
		Hostel hostel= null;
    	try {
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("select * from hostel where hostel_name=? ");
			st.setString(1, name);
			ResultSet rs=st.executeQuery(); 
			if(rs.next()) {
				hostel = setData(rs);
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
    	return hostel;
			
	}

	public static Hostel setData(ResultSet rs) throws SQLException {
		Hostel hostel=new Hostel();
		hostel.setName(rs.getString("HOSTEL_NAME"));
		hostel.setStreet(rs.getString("STREET"));
		hostel.setCity(rs.getString("CITY"));
		hostel.setState(rs.getString("STATE"));
		hostel.setPostalCode(rs.getString("POSTAL_CODE"));
		hostel.setCountry(rs.getString("COUNTRY"));
		hostel.setCheckinTime(rs.getString("CHECKIN_TIME"));
		hostel.setCheckoutTime(rs.getString("CHECKOUT_TIME"));
		hostel.setSmoking(rs.getString("SMOKING"));
		hostel.setAlcohol(rs.getString("ALCOHOL"));
		hostel.setCancellationDeadline(rs.getInt("cancellation_deadline"));
		hostel.setCancellPenalty(rs.getDouble("cancellation_penalty"));
		hostel.setPhone(rs.getString("PHONE"));
		hostel.setEmail(rs.getString("EMAIL"));
		hostel.setFacebook(rs.getString("FACEBOOK"));
		hostel.setWeb(rs.getString("WEB"));
		return hostel;
	}
}

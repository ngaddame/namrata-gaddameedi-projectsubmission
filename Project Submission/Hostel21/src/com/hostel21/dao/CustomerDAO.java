package com.hostel21.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hostel21.beans.Customer;
import com.hostel21.util.ConnectionUtil;

public class CustomerDAO {
	public static Customer add(Customer customer) {
		Connection con=null;
    	try {
    		Integer customerId=SequenceIdDAO.createNextId("CUSTOMER");
    		
    		con=ConnectionUtil.getConnection();
			PreparedStatement st=con.prepareStatement("INSERT INTO customer(EMAIL,FIRST_NAME ,LAST_NAME," +
					"PHONE,CC_NUMBER,CC_EXPIRE_DATE,CC_SECURITY_CODE,FACEBOOK,TWITTER,CUST_ID,CREATE_DATE) " +
					"values (?,?,?,?,?,?,?,?,?,?,date()) ");
			st.setString(1, customer.getEmailId());
			st.setString(2, customer.getFirstName());
			st.setString(3, customer.getLastName());
			st.setString(4, customer.getCustPhone());
    		st.setString(5, customer.getCcNumber());
    		st.setString(6, customer.getCcExpireDate());
    		st.setString(7, customer.getCcSecurityCode());		
    		st.setString(8, customer.getFacebook());
    		st.setString(9, customer.getTwitter());
    		st.setInt(10, customerId);
			st.executeUpdate();
			con.commit();
			customer.setCustomerId(customerId);
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
    	return customer;
	}
	
	public static void modify(Integer customerId,String firstName,String lastName,String email,String ccNumber,String ccExpireDate,String ccSecurityCode,String custPhone,String facebook,String twitter) {
		Connection con=null;
    	try {
    		con=ConnectionUtil.getConnection();
			PreparedStatement st=con.prepareStatement("UPDATE customer set FIRST_NAME=?,LAST_NAME=?,EMAIL=?," +
					" CC_NUMBER=?,CC_EXPIRE_DATE=?,CC_SECURITY_CODE=?, PHONE=?,FACEBOOK=?,TWITTER=? where cust_id=?");
			st.setString(1, firstName);
			st.setString(2, lastName);
			st.setString(3, email);
			st.setString(4, ccNumber);
			st.setString(5, ccExpireDate);
			st.setString(6, ccSecurityCode);
			st.setString(7, custPhone);
			st.setString(8, facebook);
			st.setString(9, twitter);
			st.setInt(10, customerId);
			
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
	
	public static Customer getByEmailId(String emailId) {
		Connection con=null;
		Customer customer= null;
    	try {
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("select * from customer " +
					"where email=?");
			st.setString(1, emailId);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				customer = setData(rs);
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
    	return customer;
	}
	
	public static Customer getByCustomerId(Integer customerId) {
		Connection con=null;
		Customer customer= null;
    	try {
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("select * from customer " +
					"where cust_id=?");
			st.setInt(1, customerId);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				customer = setData(rs);
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
    	return customer;
	}	
	
	

	public static Customer setData(ResultSet rs) throws SQLException {
		Customer customer;
		customer= new Customer(rs.getInt("CUST_ID"),rs.getString("EMAIL"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("PHONE"));
		customer.setCcExpireDate(rs.getString("CC_EXPIRE_DATE"));
		customer.setCcNumber(rs.getString("CC_NUMBER"));
		customer.setCcSecurityCode(rs.getString("CC_SECURITY_CODE"));
		customer.setFacebook(rs.getString("FACEBOOK"));
		customer.setTwitter(rs.getString("TWITTER"));
		customer.setCreateDate(rs.getString("CREATE_DATE"));
		return customer;
	}
}

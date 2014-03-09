package com.hostel21.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hostel21.beans.Availability;
import com.hostel21.beans.Hostel;
import com.hostel21.util.ConnectionUtil;

public class AvailabilityDAO {
	
	public static List<Availability> getAll(String hostelName,String fromDate,String toDate) {
		Connection con=null;
		List<Availability> list=new ArrayList<Availability>();
		try {
			con=ConnectionUtil.getConnection();
			PreparedStatement st=con.prepareStatement("SELECT date,a.hostel_name a_hostel_name,STREET,CITY,STATE,POSTAL_CODE,COUNTRY,CHECKIN_TIME,CHECKOUT_TIME,SMOKING,ALCOHOL," +
					"cancellation_deadline,cancellation_penalty,room,bed,availability_id,status, price,EMAIL,phone,web FROM AVAILABILITY a inner join hostel b on a.hostel_name=b.hostel_name and date >= ? AND date < ? and upper(a.hostel_name)=upper(?)");			
    		st.setString(1, fromDate);
    		st.setString(2, toDate);
    		st.setString(3, hostelName);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				Availability availability = setData(rs);
				list.add(availability);
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
		finally {
			try {
				con.close();
			}
			catch(Exception e) {
	    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
				
			}
		} 
		return list;
	}
	
	public static List<Availability> getAll() {
		Connection con=null;
		List<Availability> list=new ArrayList<Availability>();
		try {
			con=ConnectionUtil.getConnection();
			PreparedStatement st=con.prepareStatement("SELECT date,a.hostel_name a_hostel_name,STREET,CITY,STATE,POSTAL_CODE,COUNTRY,CHECKIN_TIME,CHECKOUT_TIME,SMOKING,ALCOHOL," +
					"cancellation_deadline,cancellation_penalty,room,bed,availability_id,status, price,EMAIL,phone,web FROM AVAILABILITY a inner join hostel b on a.hostel_name=b.hostel_name");
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				Availability availability = setData(rs);
				list.add(availability);
			}
		}
		catch(Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			
		}
		finally {
			try {
				con.close();
			}
			catch(Exception e) {
	    		System.err.println(e.getClass().getName() + ": " + e.getMessage());
				
			}
		} 
		return list;
	}		
	
	public static void add(Availability availability) {
		Connection con=null;
    	try {
    		con=ConnectionUtil.getConnection();
			PreparedStatement st=con.prepareStatement("insert into AVAILABILITY(AVAILABILITY_ID,DATE,ROOM,BED ,HOSTEL_NAME,PRICE,STATUS)" +
					" values (?,?,?,?,?,?,1) ");
			st.setInt(1, availability.getAvailabilityId());
			st.setString(2, availability.getDate());
			st.setInt(3, availability.getRoom());
			st.setInt(4, availability.getBed());
			st.setString(5, availability.getHostel().getName());
			st.setDouble(6, availability.getPrice());
			st.executeUpdate();
			con.commit();
    	}
    	catch(Exception e) {
    		System.err.println("Hostel Name: "+availability.getHostel().getName()+" Room: "+availability.getRoom()+" Bed: "+availability.getBed()+" Date: "+availability.getDate());
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
	
	public static Availability getById(Integer availabilityId) {
		Connection con=null;
		Availability availability= null;
    	try {
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("select * from availability a, hostel b where a.hostel_name=b.hostel_name and a.availability_id=? ");
			st.setInt(1, availabilityId);
			ResultSet rs=st.executeQuery(); 
			if(rs.next()) {
				availability = setData(rs);
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
    	return availability;
	}
	
	public static List<Availability> getRoomsByBookingId(Integer bookingId) {
		Connection con=null;
		List<Availability> roomBooked= new ArrayList<Availability>();
    	try {
    		con=ConnectionUtil.getConnection();		
			PreparedStatement st=con.prepareStatement("select a.HOSTEL_NAME A_HOSTEL_NAME,STREET,CITY,STATE,POSTAL_CODE,COUNTRY,CHECKIN_TIME,CHECKOUT_TIME,SMOKING,ALCOHOL,cancellation_deadline,cancellation_penalty, DATE,ROOM,BED,PRICE,STATUS from availability a, hostel b,booking_details c where a.hostel_name=b.hostel_name and a.availability_id=c.availability_id and c.booking_id=? ");
			st.setInt(1, bookingId);
			ResultSet rs=st.executeQuery(); 
			while(rs.next()) {
				Availability availability = setData(rs);
				roomBooked.add(availability);
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
    	return roomBooked;
	}	
	
	
	public static Availability setData(ResultSet rs) throws SQLException {
		Hostel hostel=new Hostel();
		hostel.setName(rs.getString("A_HOSTEL_NAME"));
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
		Availability availability=new Availability();
		availability.setDate(rs.getString("DATE"));
		availability.setRoom(rs.getInt("ROOM"));
		availability.setBed(rs.getInt("BED"));
		availability.setPrice(rs.getDouble("PRICE"));
		availability.setHostel(hostel);
		availability.setStatus(rs.getInt("STATUS"));
		return availability;
	}	
}

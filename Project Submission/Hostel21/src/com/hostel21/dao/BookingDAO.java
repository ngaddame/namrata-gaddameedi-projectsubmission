package com.hostel21.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hostel21.beans.Availability;
import com.hostel21.beans.Booking;
import com.hostel21.beans.Customer;
import com.hostel21.beans.Hostel;
import com.hostel21.util.ConnectionUtil;

public class BookingDAO {
	public static Booking add(Booking booking,String userId) {
		Connection con=null;
    	try {
    		//CustomerDAO.add(booking.getCustomer());
    		Integer bookingId=SequenceIdDAO.createNextId("BOOKING");
    		booking.setBookingId(bookingId);
    		con=ConnectionUtil.getConnection();
    		PreparedStatement st=con.prepareStatement("insert into booking(BOOKING_ID,HOSTEL_NAME,CHECKIN_DATE,CHECKOUT_DATE,STATUS," +
    				"CUST_ID,BOOKING_PRICE, CREATE_DATE,CREATE_USER,NO_OF_ROOMS) values (?,?,?,?,?,?,?,date(),?,?)");
    		st.setInt(1, bookingId); 
    		st.setString(2, booking.getHostel().getName());
    		st.setString(3, booking.getCheckinDate()); 
    		st.setString(4, booking.getCheckoutDate());
    		st.setString(5, "Booked");
    		st.setInt(6, booking.getCustomer().getCustomerId());
    		st.setDouble(7, booking.getBookingPrice());
    		st.setString(8, userId);
    		st.setInt(9, booking.getNoOfBeds());
			st.executeUpdate();
			con.commit();
			
			String availabilitIDS=booking.getAvailableBedIds();
			StringTokenizer tokens=new StringTokenizer(availabilitIDS,",");
			while(tokens.hasMoreTokens()) {
				String availabilityId=tokens.nextToken();
				st=con.prepareStatement("insert into BOOKING_DETAILS (BOOKING_ID,AVAILABILITY_ID) values (?,?) ");
				st.setInt(1, bookingId);
				st.setInt(2, Integer.parseInt(availabilityId));
				st.executeUpdate();
				con.commit();
			}
			
			updateAvailability(bookingId, 0);
			return getByBookingId(bookingId);
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
    	return getByBookingId(booking.getBookingId());
	}
	
	public static Booking cancel(Integer bookingId,Double cancelFee,String userId) {
		Connection con=null;
    	try {
    		con=ConnectionUtil.getConnection();
    		PreparedStatement st=con.prepareStatement("update BOOKING set status='Cancelled',CANCEL_FEE=?,UPDATE_USER=?,UPDATE_DATE=date() where booking_id=?");
    		st.setString(1, userId);
    		st.setDouble(2, cancelFee);
    		st.setInt(3, bookingId);
			st.executeUpdate();
			con.commit();
			
			updateAvailability(bookingId, 1);
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
    	Booking booking=getByBookingId(bookingId);
    	return booking;
	}	
	
	
	public static void updateAvailability(Integer bookingId,Integer status) {
		Connection con=null;
    	try {
    		con=ConnectionUtil.getConnection();
    		PreparedStatement st=con.prepareStatement("update availability set status=? where AVAILABILITY_ID in (select AVAILABILITY_ID from booking_details where booking_id=?)");
    		st.setInt(1, status);
    		st.setInt(2, bookingId);
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
	

	public static Booking getByBookingId(Integer bookingId) {
		Connection con=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		Booking booking= null;
    	try {
    		con=ConnectionUtil.getConnection();		
    		StringBuffer sb=new StringBuffer();
    		sb.append(" select * from booking where booking_id=?");
    		st=con.prepareStatement(sb.toString());
    		st.setInt(1, bookingId);
    		rs=st.executeQuery();
    		while(rs.next()) {
	    		booking = setData(rs);
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
    	return booking;
	}

	public static List<Booking> getByCustoEmail(String custEmail) {
		Connection con=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		List<Booking> bookings=new ArrayList<Booking>();
    	try {
    		Customer customer=CustomerDAO.getByEmailId(custEmail);
    		if(customer!=null) {
	    		con=ConnectionUtil.getConnection();		
	    		StringBuffer sb=new StringBuffer();
	    		sb.append(" select * from booking where CUST_ID=?");
	    		st=con.prepareStatement(sb.toString());
	    		st.setInt(1, customer.getCustomerId());
	    		rs=st.executeQuery();
	    		while(rs.next()) {
	    			Booking booking = setData(rs);
	    			bookings.add(booking);
	    		}
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
    	return bookings;
			
	}	
	
	private static Booking setData(ResultSet rs)
			throws SQLException {
		Booking booking;
		booking=new Booking();
		booking.setBookingId(rs.getInt("BOOKING_ID"));
		Hostel hostel=HostelDAO.getByHostelName(rs.getString("HOSTEL_NAME"));
		booking.setHostel(hostel);
		booking.setCheckinDate(rs.getString("CHECKIN_DATE"));
		booking.setCheckoutDate(rs.getString("CHECKOUT_DATE"));
		booking.setStatus(rs.getString("STATUS"));
		Customer customer=CustomerDAO.getByCustomerId(rs.getInt("CUST_ID"));
		booking.setCustomer(customer);
		booking.setBookingPrice(rs.getDouble("BOOKING_PRICE"));
		booking.setCancelFee(rs.getDouble("CANCEL_FEE"));
		booking.setCreateDate(rs.getString("CREATE_DATE"));
		booking.setCreateUser(rs.getString("CREATE_USER"));
		booking.setUpdateDate(rs.getString("UPDATE_DATE"));
		booking.setUpdateUser(rs.getString("UPDATE_USER"));
		List<Availability> roomsBooked=AvailabilityDAO.getRoomsByBookingId(rs.getInt("BOOKING_ID"));
		booking.setRoomsBooked(roomsBooked);
		booking.setNoOfBeds(rs.getInt("NO_OF_ROOMS"));
		return booking;
	}
	
	

	
	

}

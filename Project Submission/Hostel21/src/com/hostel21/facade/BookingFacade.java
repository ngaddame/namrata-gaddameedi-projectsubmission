package com.hostel21.facade;

import java.util.Date;
import java.util.List;

import com.hostel21.beans.Availability;
import com.hostel21.beans.Booking;
import com.hostel21.beans.Customer;
import com.hostel21.beans.Hostel;
import com.hostel21.beans.User;
import com.hostel21.dao.BookingDAO;
import com.hostel21.dao.HostelDAO;
import com.hostel21.util.DateUtil;
import com.hostel21.util.MailUtil;

public class BookingFacade {
	public static Booking book(String checkinDate,String checkoutDate,String hostelName,Integer noOfBeds, String availableBedIds, Double bookingPrice,
		String emailId,String firstName,String lastName,String street,String city,String state,String postalCode,String country,String custPhone,
		String billingName,String ccNumber,String ccExpireDate,String ccSecurityCode,String userId) {
		Customer customer=new Customer(null,emailId, firstName, lastName, custPhone,ccNumber, ccExpireDate, ccSecurityCode);
		Hostel hostel=HostelDAO.getByHostelName(hostelName);
		Booking booking=new Booking();
		booking.setCheckinDate(checkinDate);
		booking.setCheckoutDate(checkoutDate);
		booking.setAvailableBedIds(availableBedIds);
		booking.setNoOfBeds(noOfBeds);
		booking.setHostel(hostel);
		booking.setBookingPrice(bookingPrice);
		
		booking.setCustomer(customer);
		booking=BookingDAO.add(booking, userId); 
		try {
			StringBuffer body=new StringBuffer("Welcome to Hostel21. Here is the booking information.\n");
			body.append("Booking Id: "+booking.getBookingId()+" \n");
			body.append("Hostel: "+booking.getHostel().getName()+" \n");
			body.append("City: "+booking.getHostel().getCity()+" \n");
			body.append("Checkin Date: "+booking.getCheckinDate()+" \n");
			body.append("Checkout Date: "+booking.getCheckoutDate()+" \n");
			body.append("Number of Rooms: "+booking.getNoOfBeds()+" \n");
			body.append("Price: "+booking.getBookingPrice()+" \n");
			MailUtil.send(booking.getCustomer().getEmailId(), "Booking Confirmation Id: "+booking.getBookingId(), body.toString());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return booking;
	}
	
	
	public static Booking book(String checkinDate,String checkoutDate,String hostelName,Integer noOfBeds,String availableBedIds,Double bookingPrice,
			Customer customer,String userId) {
			Hostel hostel=HostelDAO.getByHostelName(hostelName);
			Booking booking=new Booking();
			booking.setCheckinDate(checkinDate);
			booking.setCheckoutDate(checkoutDate);
			booking.setAvailableBedIds(availableBedIds);
			booking.setNoOfBeds(noOfBeds);
			booking.setHostel(hostel);
			booking.setBookingPrice(bookingPrice);
			
			booking.setCustomer(customer);
			booking=BookingDAO.add(booking, userId); 
			try {
				StringBuffer body=new StringBuffer("Welcome to Hostel21. Here is the booking information.\n");
				body.append("Booking Id: "+booking.getBookingId()+" \n");
				body.append("Hostel: "+booking.getHostel().getName()+" \n");
				body.append("City: "+booking.getHostel().getCity()+" \n");
				body.append("Checkin Date: "+booking.getCheckinDate()+" \n");
				body.append("Checkout Date: "+booking.getCheckoutDate()+" \n");
				body.append("Number of Rooms: "+booking.getNoOfBeds()+" \n");
				body.append("Price: "+booking.getBookingPrice()+" \n");
				MailUtil.send(booking.getCustomer().getEmailId(), "Booking Confirmation Id: "+booking.getBookingId(), body.toString());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return booking;
		}
	
	public static Booking cancel(Integer bookingId,String userId) {
    	Booking booking=BookingDAO.getByBookingId(bookingId);
    	Hostel hostel=booking.getHostel();
    	Date checkinDateNew=DateUtil.convertToDate(booking.getCheckinDate(), "yyyy-MM-dd");
    	int days=DateUtil.daysBetween(checkinDateNew, new Date());
    	if(days<=hostel.getCancellationDeadline()/24) {
    		Double penalty=(booking.getHostel().getCancellPenalty()*booking.getBookingPrice())/100;
    		booking=BookingDAO.cancel(bookingId, penalty, userId);
        	try {
        		StringBuffer body=new StringBuffer("Your booking has been cancelled. Here is the Booking Information. \n");
        		body.append("Booking Id: "+booking.getBookingId()+" \n");
        		body.append("Hostel: "+booking.getHostel().getName()+" \n");
        		body.append("City: "+booking.getHostel().getCity()+" \n");
        		body.append("Checkin Date: "+booking.getCheckinDate()+" \n");
        		body.append("Checkout Date: "+booking.getCheckoutDate()+" \n");
        		body.append("Number of Beds: "+booking.getNoOfBeds()+" \n");
        		body.append("Price: "+booking.getBookingPrice()+" \n");
        		MailUtil.send(booking.getCustomer().getEmailId(), "Booking Concellation. Booking Id: "+booking.getBookingId(), body.toString());
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}	        	
    	}
		return booking;
	}
}

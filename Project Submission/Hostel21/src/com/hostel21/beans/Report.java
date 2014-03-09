package com.hostel21.beans;

public class Report {
	Integer hostelId;
	Integer bookings;
	Double revenue;
	Integer cancelled;
	Double ocupancy_rate;
	public Report(Integer hostelId, Integer bookings, Double revenue,
			Integer cancelled, Double ocupancy_rate) {
		super();
		this.hostelId = hostelId;
		this.bookings = bookings;
		this.revenue = revenue;
		this.cancelled = cancelled;
		this.ocupancy_rate = ocupancy_rate;
	}	
	
	public Integer getHostelId() {
		return hostelId;
	}
	public void setHostelId(Integer hostelId) {
		this.hostelId = hostelId;
	}
	public Integer getBookings() {
		return bookings;
	}
	public void setBookings(Integer bookings) {
		this.bookings = bookings;
	}
	public Double getRevenue() {
		return revenue;
	}
	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}
	public Integer getCancelled() {
		return cancelled;
	}
	public void setCancelled(Integer cancelled) {
		this.cancelled = cancelled;
	}
	public Double getOcupancy_rate() {
		return ocupancy_rate;
	}
	public void setOcupancy_rate(Double ocupancy_rate) {
		this.ocupancy_rate = ocupancy_rate;
	}

}

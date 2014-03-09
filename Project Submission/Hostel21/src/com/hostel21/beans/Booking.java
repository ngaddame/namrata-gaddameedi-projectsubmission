package com.hostel21.beans;

import java.util.List;


public class Booking {
	Integer bookingId;
	String checkinDate;
	String checkoutDate;
	Integer noOfBeds;
	List<Availability> roomsBooked;
	String availableBedIds;
	String status;
	Double bookingPrice;
	Double cancelFee;
	Customer customer;
	Hostel hostel;
	String createDate;
	String updateDate;
	String createUser;
	String updateUser;

	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	public String getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}
	public String getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public Integer getNoOfBeds() {
		return noOfBeds;
	}
	public List<Availability> getRoomsBooked() {
		return roomsBooked;
	}
	public void setRoomsBooked(List<Availability> roomsBooked) {
		this.roomsBooked = roomsBooked;
	}
	public void setNoOfBeds(Integer noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getAvailableBedIds() {
		return availableBedIds;
	}
	public void setAvailableBedIds(String availableBedIds) {
		this.availableBedIds = availableBedIds;
	}
	public Double getBookingPrice() {
		return bookingPrice;
	}
	public void setBookingPrice(Double bookingPrice) {
		this.bookingPrice = bookingPrice;
	}
	public Double getCancelFee() {
		return cancelFee;
	}
	public void setCancelFee(Double cancelFee) {
		this.cancelFee = cancelFee;
	}

	public Hostel getHostel() {
		return hostel;
	}

	public void setHostel(Hostel hostel) {
		this.hostel = hostel;
	}
}

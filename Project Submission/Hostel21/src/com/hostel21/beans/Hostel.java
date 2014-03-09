package com.hostel21.beans;

public class Hostel extends Address {
	String name;
	Integer cancellationDeadline;
	Double cancellPenalty;
	String checkinTime;
	String checkoutTime;
	String phone;
	String email;
	String facebook;
	String web;
	String smoking;
	String alcohol; 
	
	public Hostel() {
	}	
	public Hostel(String name,Integer cancellationDeadline,Double cancellPenalty,String checkinTime,String checkoutTime,String phone,String email,
					String facebook,String web,String smoking,String alcohol,String street,String city,String state,String postalCode,String country) {
		this.name=name;
		this.cancellationDeadline=cancellationDeadline;
		this.cancellPenalty=cancellPenalty;
		this.checkinTime=checkinTime;
		this.checkoutTime=checkoutTime;
		this.phone=phone;
		this.email=email;
		this.facebook=facebook;
		this.web=web;
		this.smoking=smoking;
		this.alcohol=alcohol;
		this.street=street;
		this.city=city;
		this.state=state;
		this.postalCode=postalCode;
		this.country=country;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getCheckoutTime() {
		return checkoutTime;
	}
	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getSmoking() {
		return smoking;
	}
	public void setSmoking(String smoking) {
		this.smoking = smoking;
	}
	public String getAlcohol() {
		return alcohol;
	}
	public void setAlcohol(String alcohol) {
		this.alcohol = alcohol;
	}

	public Integer getCancellationDeadline() {
		return cancellationDeadline;
	}
	public void setCancellationDeadline(Integer cancellationDeadline) {
		this.cancellationDeadline = cancellationDeadline;
	}
	public Double getCancellPenalty() {
		return cancellPenalty;
	}
	public void setCancellPenalty(Double cancellPenalty) {
		this.cancellPenalty = cancellPenalty;
	}
}

package com.hostel21.beans;

public class Availability  implements Comparable<Availability>{
	Integer availabilityId; //bedid
    String date;
    Integer room;
    Integer bed;
    Hostel hostel;
    Double price;
    Integer status;
	public Integer getAvailabilityId() {
		return availabilityId;
	}
	public void setAvailabilityId(Integer availabilityId) {
		this.availabilityId = availabilityId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getRoom() {
		return room;
	}
	public void setRoom(Integer room) {
		this.room = room;
	}
	public Integer getBed() {
		return bed;
	}
	public void setBed(Integer bed) {
		this.bed = bed;
	}
	public Hostel getHostel() {
		return hostel;
	}
	public void setHostel(Hostel hostel) {
		this.hostel = hostel;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int compareTo(Availability compareObject) {
		double comparePrice = ((Availability) compareObject).getPrice();
		if(this.price < comparePrice) return -1;
		else if(this.price > comparePrice) return 1;
		else return 0;
 	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
}

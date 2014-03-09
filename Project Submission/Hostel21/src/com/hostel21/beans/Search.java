package com.hostel21.beans;

import java.util.List;

public class Search {
	String fromDate;
	String toDate;
	String hostelName;
	Integer noOfBeds;
	String details;
	Double totalPrice;
	List<Availability> searchDetails;
	String availableBedIds;
	Integer searchId;
	Double minPrice;
	Double maxPrice;
	
	public Search(String fromDate, String toDate, String hostelName,Integer noOfBeds, String details,Double minPrice, Double maxPrice) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.hostelName = hostelName;
		this.noOfBeds = noOfBeds;
		this.details = details;
		this.minPrice=minPrice;
		this.maxPrice=maxPrice;
	}
	
	public Search(String fromDate, String toDate, String hostelName,Integer noOfBeds, String details) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.hostelName = hostelName;
		this.noOfBeds = noOfBeds;
		this.details = details;
	}	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public Integer getNoOfBeds() {
		return noOfBeds;
	}
	public void setNoOfBeds(Integer noOfBeds) {
		this.noOfBeds = noOfBeds;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public List<Availability> getSearchDetails() {
		return searchDetails;
	}
	public void setSearchDetails(List<Availability> searchDetails) {
		this.searchDetails = searchDetails;
	}
	public Integer getSearchId() {
		return searchId;
	}
	public void setSearchId(Integer searchId) {
		this.searchId = searchId;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	public Double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getAvailableBedIds() {
		return availableBedIds;
	}

	public void setAvailableBedIds(String availableBedIds) {
		this.availableBedIds = availableBedIds;
	}
}

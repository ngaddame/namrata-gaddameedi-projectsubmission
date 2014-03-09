package com.hostel21.beans;

import java.util.List;

public class SearchResult {
	String fromDate;
	String toDate;
	String hostelName;
	List<Search> list;
	Integer noOfBedsRequested;
	public SearchResult(String hostelName,String fromDate, String toDate, List<Search> list) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.list = list;
		this.hostelName=hostelName;
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
	public List<Search> getList() {
		return list;
	}
	public void setList(List<Search> list) {
		this.list = list;
	}
	public Integer getNoOfBedsRequested() {
		return noOfBedsRequested;
	}
	public void setNoOfBedsRequested(Integer noOfBedsRequested) {
		this.noOfBedsRequested = noOfBedsRequested;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
}

package com.hostel21.beans;

public class Customer {
	Integer customerId;
	String emailId;
	String firstName;
	String lastName;
	String facebook;
	String twitter;
	String web;
	String ccNumber;
	String ccExpireDate;
	String ccSecurityCode;	
	String custPhone;
	String createDate;
	
	public Customer(Integer customerId,String emailId,String firstName,String lastName,String custPhone) {
		this.emailId=emailId;
		this.firstName=firstName;
		this.lastName=lastName;
		this.custPhone=custPhone;
		this.customerId=customerId;
	}		
	public Customer(Integer customerId,String emailId,String firstName,String lastName,String custPhone,String ccNumber,String ccExpireDate,String ccSecurityCode) {
		this.emailId=emailId;
		this.firstName=firstName;
		this.lastName=lastName;
		this.custPhone=custPhone;
		this.ccNumber=ccNumber;
		this.ccExpireDate=ccExpireDate;
		this.ccSecurityCode=ccSecurityCode;
		this.customerId=customerId;
	}	
	public void setCreditCardInfo(String ccNumber,String ccExpireDate,String ccSecurityCode) {
		this.ccNumber=ccNumber;
		this.ccExpireDate=ccExpireDate;
		this.ccSecurityCode=ccSecurityCode;
		
	}	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpireDate() {
		return ccExpireDate;
	}

	public void setCcExpireDate(String ccExpireDate) {
		this.ccExpireDate = ccExpireDate;
	}

	public String getCcSecurityCode() {
		return ccSecurityCode;
	}

	public void setCcSecurityCode(String ccSecurityCode) {
		this.ccSecurityCode = ccSecurityCode;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}	
}

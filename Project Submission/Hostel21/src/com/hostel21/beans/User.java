package com.hostel21.beans;


public class User {
	String userId;
	String password;
	String userType;
	String firstName;
	String lastName;
	public User(String userId, String password, String userType,
			String firstName, String lastName) {
		super();
		this.userId = userId;
		this.password = password;
		this.userType = userType;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
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
	public boolean isUserValid(String password) {
		try {
			if(password.equals(this.getPassword())) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}

}

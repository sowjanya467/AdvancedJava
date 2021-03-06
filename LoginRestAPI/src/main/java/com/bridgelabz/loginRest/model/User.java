package com.bridgelabz.loginRest.model;

public class User {
	String userName;
	String password;
	String emailId;
	String firstName;
	String lastName;
	String mobNumber;


	public User() {

	}

	public User(String userName, String password, String email, String firstName, String lastName, String mobNumber) {
		super();
		this.userName = userName;
		this.password = password;
		this.emailId = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobNumber = mobNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return emailId;
	}

	public void setEmail(String email) {
		this.emailId = email;
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

	public String getMobNumber() {
		return mobNumber;
	}

	public void setMobNumber(String mobNumber) {
		this.mobNumber = mobNumber;
	}

}

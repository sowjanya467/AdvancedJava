package com.bridgelabz.LoginMongodb.model;

public class ResponseDto 
{
	String message;
	int status;
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param i the status to set
	 */
	public void setStatus(int i) {
		this.status = i;
	}
	

}

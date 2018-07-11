package com.bridgelabz.LoginMongodb.utility;

/*************************************************************************************************************
 *
 * purpose:methog that handles error if it occurs while login or registering
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
public class CustomErrorType 
{
	private String errorMessage;

	public CustomErrorType(String errorMessage) 
	{
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}

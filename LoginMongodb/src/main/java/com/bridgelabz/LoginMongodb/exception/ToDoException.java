package com.bridgelabz.LoginMongodb.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ToDoException extends Exception
{
	
	
	private static final long serialVersionUID = 1L;
	private String message;

	public String customException(String message)
	{
		return this.message=message;
	}

}

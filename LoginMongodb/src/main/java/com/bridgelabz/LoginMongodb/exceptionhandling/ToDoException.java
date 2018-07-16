package com.bridgelabz.LoginMongodb.exceptionhandling;


public class ToDoException extends Exception
{
	
	
	private static final long serialVersionUID = 1L;

	public ToDoException(String message)
	{
        super(message);
	}

}

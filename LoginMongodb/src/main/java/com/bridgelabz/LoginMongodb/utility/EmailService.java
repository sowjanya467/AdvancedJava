package com.bridgelabz.LoginMongodb.utility;

import javax.mail.MessagingException;

import com.bridgelabz.LoginMongodb.model.MailDto;


public interface EmailService 
{
	public void sendEmail(MailDto mailDTO) throws MessagingException; 

}

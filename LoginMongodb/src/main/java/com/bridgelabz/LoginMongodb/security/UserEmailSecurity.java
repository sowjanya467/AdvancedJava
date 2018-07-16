package com.bridgelabz.LoginMongodb.security;

import javax.mail.MessagingException;

import com.bridgelabz.LoginMongodb.model.MailDto;

public interface UserEmailSecurity 
{
	public void sendEmail(MailDto mailDTO) throws MessagingException; 

}

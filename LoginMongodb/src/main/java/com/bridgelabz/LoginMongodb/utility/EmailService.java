package com.bridgelabz.LoginMongodb.utility;

import javax.mail.MessagingException;

import com.bridgelabz.LoginMongodb.modell.User;

public interface EmailService 
{
	void sendActivationEmail(User user) throws MessagingException;

}

package com.bridgelabz.LoginMongodb.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bridgelabz.LoginMongodb.modell.User;
import com.bridgelabz.LoginMongodb.tokens.CreateTokens;

@Component
public class EmailServiceImpl implements EmailService{
  
	@Autowired
	private JavaMailSender emailSender;

	CreateTokens tok=new CreateTokens();
	public void sendActivationEmail(User user) throws MessagingException {
		System.out.println("into mail sender");
		String token = tok.createToken(user);
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setTo(user.getEmailId());
			helper.setSubject("Confirm registeration.");
			helper.setText("To activate your account, click the following link : http://localhost:8080/user/activateaccount?token=");

			emailSender.send(message);
		
	
			
			
			
			
			
			

}
}
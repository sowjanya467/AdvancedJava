package com.bridgelabz.LoginMongodb.security;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bridgelabz.LoginMongodb.model.MailDto;


@Component
public class UserEmailSecurityImp implements UserEmailSecurity {
	 @Autowired
	    private JavaMailSender emailSender;
	 	@Override
	    public void sendEmail(MailDto mailDTO) throws MessagingException  {
	    
	    MimeMessage mimeMessage=emailSender.createMimeMessage();
	    MimeMessageHelper message=new MimeMessageHelper(mimeMessage);
	    
	    System.out.println(mailDTO.getToMailAddress());
	        message.setTo(mailDTO.getToMailAddress());
	        message.setSubject(mailDTO.getSubject());
	        message.setText(mailDTO.getBody()+"\n"+"\n"+mailDTO.getMailSign());
	        emailSender.send(mimeMessage);      
	    }
}
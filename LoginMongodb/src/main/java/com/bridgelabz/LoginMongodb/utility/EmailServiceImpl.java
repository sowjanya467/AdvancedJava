package com.bridgelabz.LoginMongodb.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bridgelabz.LoginMongodb.model.MailDto;

/*************************************************************************************************************
 *
 * purpose:To send activation link through email
 *
 * @author sowjanya467
 * @version 1.0
 * @since -05-18
 *
 * **************************************************************************************************/
@Component
public class EmailServiceImpl implements EmailService {

	 @Autowired
	    private JavaMailSender emailSender;
	 	@Override
	    public void sendEmail(MailDto mailDTO) throws MessagingException  {
	    
	    MimeMessage mimeMessage=emailSender.createMimeMessage();
	    MimeMessageHelper message=new MimeMessageHelper(mimeMessage);
	    
	        message.setTo(mailDTO.getToMailAddress());
	        message.setSubject(mailDTO.getSubject());
	        message.setText(mailDTO.getBody()+"\n"+"\n"+mailDTO.getMailSign());
	        emailSender.send(mimeMessage);      
	    }

}
package com.bridgelabz.LoginMongodb.utility;
/*************************************************************************************************************
*
* purpose:Methode to create a JWT token and decode the token
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bridgelabz.LoginMongodb.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class CreateTokens {
	final static String KEY = "sowjanya";

	/**
	 * purpose: to create a token
	 * 
	 * @param user
	 * @return token
	 */
	public String createToken(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// Long id=user.getUserId();
		String subject = user.getEmailId();
		String issuer = user.getUserName();
		Date date = new Date();
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).setIssuer(issuer)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	/**
	 * purpose: Decoding the token(to get details of user)
	 * 
	 * @param Jwt
	 * @return 
	 */
	public Claims parseJwt(String Jwt) {
		Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(Jwt).getBody();
		System.out.println("subject-" + claims.getSubject());
		System.out.println("issuer-" + claims.getIssuer());
		System.out.println("date-" + claims.getIssuedAt());
		return claims;

	}

	/**
	 * method to send activation link
	 * 
	 * @param token
	 * @param mail
	 */
	public void activationLink(String token, String mail) {
		String from = "msowjanya2014@gmail.com";
		String pass = "palem489";

		String subject = "activate your account";
		String body = " to activate your account click the link " + "http://192.168.0.8:8080/activateaccount/?" + token;

		System.out.println("activation link");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {

			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
			message.setSubject(subject);
			message.setText(body);

			Transport transport = session.getTransport("smtp");

			transport.connect(from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		}

		catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

}

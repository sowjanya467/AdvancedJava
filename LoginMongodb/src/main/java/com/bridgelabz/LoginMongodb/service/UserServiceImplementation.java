package com.bridgelabz.LoginMongodb.service;
/*************************************************************************************************************
*
* purpose:Implementation methods for registration and login
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/

import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.LoginMongodb.exceptionhandling.LoginExceptionHandling;
import com.bridgelabz.LoginMongodb.exceptionhandling.UserExceptionHandling;
import com.bridgelabz.LoginMongodb.modell.User;
import com.bridgelabz.LoginMongodb.repositoriy.UserRepository;
import com.bridgelabz.LoginMongodb.tokens.CreateTokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/*************************************************************************************************************
 *
 * purpose:
 *
 * @author sowjanya467
 * @version 1.0
 * @since -05-18
 *
 **************************************************************************************************/
@Service
public class UserServiceImplementation {
	final static String KEY = "sowjanya";

	@Autowired
	private UserRepository repo;
	CreateTokens token=new CreateTokens();

	/**
	 * purpose : method to login in to page
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws LoginExceptionHandling 
	 */
	public void  login(String emailId, String password) throws LoginExceptionHandling {

		Optional<User> user = repo.findById(emailId);

		if (user.isPresent()) {
			if (user.get().getPassword().equals(password)) {
				System.out.println("looged in sucessfully!! continue your works");
				

			}
			else {
				throw new LoginExceptionHandling(" entered password incorrect");
			}
		}
		

		
	}

	/**
	 * purpose:method to register in to a page
	 * 
	 * @param user
	 * @return boolean
	 * @throws UserExceptionHandling 
	 */
	public void register(User user) throws UserExceptionHandling {
		Optional<User> checkUser = repo.findById(user.getEmailId());
		if(checkUser.isPresent())
		{
			System.out.println("user with this email id is already present");
			throw new UserExceptionHandling("user with this email id is already present");
		}
		repo.save(user);
		String mail = user.getEmailId();
		//logger.info("User registered with : {}", checkUser.getEmailId());
		String tokenjwt = token.createToken(user);
		System.out.println(tokenjwt);
		String message = "Successfully registired ** " + tokenjwt;
		System.out.println(message);
		token.activationLink(tokenjwt, mail);
		

	}

	/**
	 * method to activate the account
	 * 
	 * @param jwt
	 * @return
	 */
	public boolean activateAc(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();
		System.out.println(Jwts.parser().setSigningKey(KEY).parse(jwt).getBody().toString());

		System.out.println(claims.getSubject());

		if(repo==null)
		{
			System.out.println("null");
		}

		Optional<User> user = repo.findById(claims.getSubject());
		//System.out.println("dsd");
		user.get().setActivate("true");
		System.out.println("activate");
		repo.save(user.get());

		return true;

	}
	/**
	 * 
	 * @param emailId
	 * @return
	 */

	public boolean forgotPassword(String emailId) {
		String from = "msowjanya2014@gmail.com";
		String pass = "palem489";
		Optional<User> user = repo.findById(emailId);

		String password = user.get().getPassword();
		String subject = "recover your password";
		String body = " your password is " + password;

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

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailId));
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
		return false;
	}

}

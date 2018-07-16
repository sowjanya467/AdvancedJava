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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.LoginMongodb.exceptionhandling.LoginExceptionHandling;
import com.bridgelabz.LoginMongodb.exceptionhandling.ToDoException;
import com.bridgelabz.LoginMongodb.exceptionhandling.UserExceptionHandling;
import com.bridgelabz.LoginMongodb.model.ForgotPasswordmodel;
import com.bridgelabz.LoginMongodb.model.MailDto;
import com.bridgelabz.LoginMongodb.model.RegistrationModel;
import com.bridgelabz.LoginMongodb.model.User;
import com.bridgelabz.LoginMongodb.repository.UserRepository;
import com.bridgelabz.LoginMongodb.security.SecurityConfig;
import com.bridgelabz.LoginMongodb.security.UserEmailSecurityImp;
import com.bridgelabz.LoginMongodb.utility.CreateTokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/*************************************************************************************************************
 *
 * purpose:User Service Implementation methods
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
	@Autowired
	private PasswordEncoder encoder;
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

	ForgotPasswordmodel password = new ForgotPasswordmodel();
	CreateTokens token = new CreateTokens();
	UserEmailSecurityImp s = new UserEmailSecurityImp();
	SecurityConfig sc = new SecurityConfig();
	// EmailServiceImpl e = new EmailServiceImpl();

	/**
	 * purpose : method to login in to page
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws LoginExceptionHandling
	 */
	public String login(String emailId, String password) throws LoginExceptionHandling {

		Optional<User> user = repo.findById(emailId);

		if (!user.isPresent()) {
			throw new LoginExceptionHandling("entered password incorrect");

		} else {

			if (encoder.encode(user.get().getPassword()).equals(password)) {
				System.out.println("looged in sucessfully!! continue your works");

			}
		}
		String message = "HI " + user.get().getUserName() + "    you can continue your works";
		return message;

	}

	/**
	 * purpose:method to register in to a page
	 * 
	 * @param user
	 * @return boolean
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 */

	public void registerUser(RegistrationModel userReg) throws UserExceptionHandling, MessagingException {
		Optional<User> checkUser = repo.findById(userReg.getEmailId());
		if (checkUser.isPresent()) {
			System.out.println("user with this email id is already present");
			throw new UserExceptionHandling("user with this email id is already present");
		}
		User user = new User();
		user.setEmailId(userReg.getEmailId());
		user.setUserId(userReg.getUserId());
		user.setUserName(userReg.getUserName());
		user.setPassword(encoder.encode(userReg.getPassword()));
		user.setPhoneNumber(userReg.getPhoneNumber());
		user.setActivate(userReg.getActivate());
		repo.save(user);

		
		  repo.findById(userReg.getEmailId()); 
		  SecurityConfig securityConfig = new SecurityConfig(); 
		  String token = securityConfig.createToken(userReg.getEmailId()); 
		 System.out.println(token);
		  MailDto mailDTO = new MailDto(); 
		  mailDTO.setId(user.getUserId()); 
		  String  m=userReg.getEmailId(); 
		  System.out.println(m);
		  mailDTO.setToMailAddress(userReg.getEmailId());
		  mailDTO.setSubject("Hi " +userReg.getUserName()); 
		  mailDTO.setBody("Activate your accout click on this link: http://localhost:8080" +
		  "/" + token);
		  mailDTO.setMailSign("\nThank you \n Sowjanya M \n Bridge Labz \n Mumbai");
		  
		  s.sendEmail(mailDTO);
		 
		// setActivationStatus(mailDTO.getId(),token);

		/*String mail = user.getEmailId();
		System.out.println(mail);
		logger.info("User registered with : {}", user.getEmailId());
		String tokenjwt = token.createToken(user);
		System.out.println(tokenjwt);
		String message = "Successfully registired ** " + tokenjwt;
		System.out.println(message);
		token.activationLink(tokenjwt, mail);*/
		// e.sendActivationEmail(tokenjwt, mail);

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
		Optional<User> user = repo.findById(claims.getSubject());
		// System.out.println("dsd");
		user.get().setActivate("true");
		System.out.println("activate");
		repo.save(user.get());

		return true;

	}

	public void setPassword(ForgotPasswordmodel f, String tokenJwt) throws ToDoException {
		System.out.println(tokenJwt);
		if (!f.getNewPassword().equals(f.getNewPassword())) {
			throw new ToDoException("passwords mismatch");
		}
		Claims claims = token.parseJwt(tokenJwt);

		Optional<User> checkUser = repo.findById(claims.getId());
		System.out.println(claims.getSubject());
		User user = checkUser.get();
		user.setPassword(encoder.encode(f.getConfirmPassword()));
		repo.save(user);
	}

	/**
	 * 
	 * @param emailId
	 * @return
	 */

	public boolean forgotPassword(String emailId) {
		String from = "msowjanya2014@gmail.com";
		String pass = "palem489";

		String tokenjwt = sc.createToken(emailId);
		// String password = user.get().getPassword();
		String subject = "recover your password";
		String body = " To reset your password click this link" + "http://192.168.0.8:8080/resetPassword/?" + tokenjwt;
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

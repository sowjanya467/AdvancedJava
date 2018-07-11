package com.bridgelabz.LoginMongodb.controller;

/*************************************************************************************************************
*
* purpose:Controller class for register and sign up using MongoDB
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.LoginMongodb.modell.User;
import com.bridgelabz.LoginMongodb.service.UserServiceImplementation;
import com.bridgelabz.LoginMongodb.tokens.CreateTokens;

@RestController
public class LoginController 
{

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserServiceImplementation userService = new UserServiceImplementation();
	CreateTokens tok = new CreateTokens();

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	/**
	 * purpose:method to login
	 * 
	 * @param checkUser
	 * @return response
	 */
	public ResponseEntity<String> login(@RequestBody User checkUser) 
	{
		logger.info("Logging User : {}", checkUser);

		User user = userService.login(checkUser.getEmailId(), checkUser.getPassword());
		if (user == null) {
			logger.error("User with email {} not found.", checkUser.getEmailId());
			return new ResponseEntity("User with email " + checkUser.getEmailId() + " not found", HttpStatus.NOT_FOUND);
		}
		String message = "Hello, " + user.getUserName() + " Id:- " + user.getUserId() + " Email:- " + user.getEmailId()
				+ " Phone Number:- " + user.getPhoneNumber();

		String token = tok.createToken(user);
		System.out.println("token-----" + token);
		tok.parseJwt(token);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	/**
	 * purpose:method to register
	 * 
	 * @param checkUser
	 * @return response
	 */
	public ResponseEntity<String> register(@RequestBody User checkUser) 
	{
		logger.info("Register user : {}", checkUser);

		boolean registered = userService.register(checkUser);
		if (!registered) 
		{
			logger.error("User with email {} already present.", checkUser.getEmailId());
			return new ResponseEntity("User with email " + checkUser.getEmailId() + " already present",
					HttpStatus.CONFLICT);
		}
		logger.info("User registered with : {}", checkUser.getEmailId());
		String message = "Successfully registired";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}

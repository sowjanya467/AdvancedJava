package com.bridgelabz.LoginMongodb.controller;

import javax.servlet.http.HttpServletRequest;

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

/*************************************************************************************************************
 *
 * purpose:Controller class for register and sign up using MongoDB
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@RestController
public class LoginController {

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserServiceImplementation userService = new UserServiceImplementation();
	CreateTokens tok = new CreateTokens();

	/**
	 * purpose:method to login
	 * 
	 * @param checkUser
	 * @return response
	 */

	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody User checkUser) {
		logger.info("Logging User : {}", checkUser);

		User user = userService.login(checkUser.getEmailId(), checkUser.getPassword());
		if (user == null) {

			logger.error("User with email {} not found.", checkUser.getEmailId());
			return new ResponseEntity<String>("User with email " + checkUser.getEmailId() + " not found",
					HttpStatus.NOT_FOUND);
		}

		String token = tok.createToken(user);
		System.out.println("token-----" + token);
		tok.parseJwt(token);
		// tok.token();
		String message = "Hello, " + user.getUserName() + " Id:- " + user.getUserId() + " Email:- " + user.getEmailId()
				+ " Phone Number:- " + user.getPhoneNumber() + "  " + token;
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * purpose:method to register
	 * 
	 * @param checkUser
	 * @return response
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody User checkUser) {
		logger.info("Register user : {}", checkUser);

		boolean registered = userService.register(checkUser);
		if (!registered) {
			logger.error("User with email {} already present.", checkUser.getEmailId());
			return new ResponseEntity("User with email " + checkUser.getEmailId() + " already present",
					HttpStatus.CONFLICT);
		}
		String mail = checkUser.getEmailId();
		logger.info("User registered with : {}", checkUser.getEmailId());
		String token = tok.createToken(checkUser);

		String message = "Successfully registired  " + token;
		tok.activationLink(token, mail);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * purpose:method to activate the account once registered
	 * 
	 * @param hsr
	 * @return
	 */

	@RequestMapping(value = "/activateaccount/")
	public ResponseEntity<String> activateAccount(HttpServletRequest request) {
		String m = request.getQueryString();

		if (!userService.activateAc(m)) {

			return new ResponseEntity<String>("Account not activated ", HttpStatus.NOT_FOUND);
		}
		String message = "account avtivated successfully";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * method to reset the password
	 * 
	 * @param CheckUser
	 * @return
	 */
	@RequestMapping(value = "/forgotpassword/", method = RequestMethod.POST)
	public ResponseEntity<String> forgotPassword(@RequestBody User CheckUser) {
		String email = CheckUser.getEmailId();
		if (userService.forgotPassword(email)) {
			return new ResponseEntity<String>("invalid", HttpStatus.NOT_FOUND);
		}
		String message = "password is set";
		return new ResponseEntity<String>(message, HttpStatus.OK);

	}
}

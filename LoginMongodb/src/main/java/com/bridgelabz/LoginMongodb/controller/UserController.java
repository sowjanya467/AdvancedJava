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

import com.bridgelabz.LoginMongodb.dto.ResponseDto;
import com.bridgelabz.LoginMongodb.exception.ToDoException;
import com.bridgelabz.LoginMongodb.exceptionhandling.LoginExceptionHandling;
import com.bridgelabz.LoginMongodb.exceptionhandling.UserExceptionHandling;
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
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	ToDoException todo=new ToDoException();

	@Autowired
	UserServiceImplementation userService = new UserServiceImplementation();
	CreateTokens tok = new CreateTokens();

	/**
	 * purpose:method to login
	 * 
	 * @param checkUser
	 * @return response
	 * @throws LoginExceptionHandling 
	 */

	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> login(@RequestBody User checkUser) throws UserExceptionHandling, LoginExceptionHandling {
		logger.info("Logging User : {}", checkUser);

		userService.login(checkUser.getEmailId(), checkUser.getPassword());
		ResponseDto response=new ResponseDto();
				String message = "Hello, " + checkUser.getUserName() + " Id:- " + checkUser.getUserId() + " Email:- " + checkUser.getEmailId()
						+ " Phone Number:- " + checkUser.getPhoneNumber() ;
		response.setMessage(message);
		response.setStatus(200);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);

		
	}

	/**
	 * purpose:method to register
	 * 
	 * @param checkUser
	 * @return response
	 * @throws UserExceptionHandling 
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> register(@RequestBody User checkUser) throws UserExceptionHandling {
		logger.info("Register user : {}", checkUser);

		
		userService.register(checkUser);
		ResponseDto response = new ResponseDto();
		response.setMessage("Registeration Successfull!!");
		response.setStatus(1);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * purpose:method to activate the account once registered
	 * 
	 * @param hsr
	 * @return
	 */

	@RequestMapping(value = "/activateaccount")
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

package com.bridgelabz.LoginMongodb.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.LoginMongodb.exceptionhandling.LoginExceptionHandling;
import com.bridgelabz.LoginMongodb.exceptionhandling.ToDoException;
import com.bridgelabz.LoginMongodb.exceptionhandling.UserExceptionHandling;
import com.bridgelabz.LoginMongodb.model.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	        
	    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	    @ExceptionHandler(UserExceptionHandling.class)
	    public ResponseEntity<ResponseDto> handleRegistrationException(UserExceptionHandling exception) {
	        logger.info("Error occured for: "+ exception.getMessage(), exception);
	        ResponseDto response=new ResponseDto();
	        response.setMessage(exception.getMessage());
	        response.setStatus(-3);
	        System.out.println("global exception");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(LoginExceptionHandling.class)
	    public ResponseEntity<ResponseDto> handleLoginActivationException(LoginExceptionHandling exception) {
	        logger.info("Error occured: " + exception.getMessage(), exception);
	        ResponseDto response=new ResponseDto();
	        response.setMessage(exception.getMessage());
	        response.setStatus(-3);

	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }
	    @ExceptionHandler(ToDoException.class)
	    public ResponseEntity<ResponseDto> handlesetPasswordException(ToDoException exception) {
	        logger.info("Error occured: " + exception.getMessage(), exception);
	        ResponseDto response=new ResponseDto();
	        response.setMessage(exception.getMessage());
	        response.setStatus(-3);

	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    /**
	     * 
	     * @param exception
	     * @param request
	     * @param reqId
	     * @return
	     */
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ResponseDto> handleException(Exception exception, HttpServletRequest request) {
	        logger.error("Error occured for: "+ exception.getMessage(), exception);
	        ResponseDto response=new ResponseDto();
	        response.setMessage("Something went wrong");
	        response.setStatus(-1);

	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	  
	    }
	    
	}
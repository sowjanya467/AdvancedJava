package com.bridgelabz.LoginMongodb.utility;



import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bridgelabz.LoginMongodb.exceptionhandling.UserExceptionHandling;
import com.bridgelabz.LoginMongodb.model.RegistrationModel;
import com.bridgelabz.LoginMongodb.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Utility {

	public Utility() {

	}

	public static void isValidateAllFields(RegistrationModel register) throws UserExceptionHandling   {
		if (!validateEmailAddress(register.getEmailId())) {
			throw new UserExceptionHandling("emailid not valid  Exception");
		} else if (!isValidUserName(register.getUserName())) {
			throw new UserExceptionHandling("UserName Not valid  Exception");
		} else if (!validatePassword(register.getPassword())) {
			throw new UserExceptionHandling("password not valid Exception");
		} else if (!isValidMobileNumber(register.getPhoneNumber())) {
			throw new UserExceptionHandling("mobilenumber not valid  Exception");
		} 
	}

	public static boolean validateEmailAddress(String emailId) {
		Pattern emailNamePtrn = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mtch = emailNamePtrn.matcher(emailId);
		return mtch.matches();

	}

	public static boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public static boolean isValidUserName(String userName) {
		Pattern userNamePattern = Pattern.compile("^[a-z0-9_-]{6,14}$");
		Matcher matcher = userNamePattern.matcher(userName);
		return matcher.matches();

	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		Pattern mobileNumberPattern = Pattern.compile("\\d{10}");
		Matcher matcher = mobileNumberPattern.matcher(mobileNumber);
		return matcher.matches();
	}

	public static boolean isPasswordMatch(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}

	public String createToken(User loginDTO) {
		final String key = "sowjanya";
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		String subject = loginDTO.getEmailId();
		Date date = new Date();

		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).signWith(signatureAlgorithm, key);
		return builder.compact();
	}

	public Claims parseJwt(String jwt) {
		final String key = "sowjanya";
		return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
	}

}

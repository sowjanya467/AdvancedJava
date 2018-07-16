package com.bridgelabz.LoginMongodb.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*************************************************************************************************************
 *
 * purpose:password encryptor configuration
 *
 * @author sowjanya467
 * @version 1.0
 * @since 16-05-18
 *
 * **************************************************************************************************/
@Configuration
public class PasswordEncoderConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

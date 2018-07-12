package com.bridgelabz.LoginMongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/*************************************************************************************************************
 *
 * purpose: Login Application using MongoDb
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@SpringBootApplication
//@EnableMongoRepositories(basePackages="com.bridgelabz.LoginMongodb.repositoriy")
//@ComponentScan(basePackages= {"com.bridgelabz.LoginMongodb.service","com.bridgelabz.LoginMongodb.controller",})
public class LoginMongodbApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(LoginMongodbApplication.class, args);
	}
}

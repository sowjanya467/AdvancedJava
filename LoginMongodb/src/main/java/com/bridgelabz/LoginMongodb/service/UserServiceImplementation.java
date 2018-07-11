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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.LoginMongodb.modell.User;
import com.bridgelabz.LoginMongodb.repositoriy.UserRepository;

@Service
public class UserServiceImplementation 
{
	@Autowired
	private UserRepository repo;

	/**
	 * purpose : method to login in to page
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 */
	public User login(String emailId, String password) 
	{

		Optional<User> user = repo.findById(emailId);

		if (user.isPresent())
		{
			if (user.get().getPassword().equals(password)) 
			{
				return user.get();
			}
		}

		return null;
	}

	/**
	 * purpose:method to register in to a page
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean register(User user) 
	{
		Optional<User> checkUser = repo.findById(user.getEmailId());
		System.out.println("sdj");
		if (checkUser.isPresent() == false) 
		{
			System.out.println("test true");
			repo.save(user);
			return true;
		}
		else 
		{
			return false;
		}

	}

}

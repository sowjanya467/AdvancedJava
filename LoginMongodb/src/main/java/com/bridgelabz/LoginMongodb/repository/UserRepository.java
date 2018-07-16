package com.bridgelabz.LoginMongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.LoginMongodb.model.User;

//interface extends MongoRepository

@Repository
public interface UserRepository extends MongoRepository<User, String> 
{
	

}

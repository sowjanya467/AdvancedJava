package com.bridgelabz.LoginMongodb.repositoriy;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.LoginMongodb.modell.User;

//interface extends MongoRepository

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}

package com.chatapp.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.userDB;

public interface UserRepository  extends MongoRepository<userDB, String> {
  public userDB findByUsername(String username);
  public userDB findByToken(String token);
}
package com.chatapp.backend.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.userDB;

public interface UserRepository  extends MongoRepository<userDB, Object> {
  public userDB findByUsername(String username);
}
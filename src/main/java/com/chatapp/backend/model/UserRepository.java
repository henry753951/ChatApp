package com.chatapp.backend.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<user, String> {
  public user findByUsername(String username);
}
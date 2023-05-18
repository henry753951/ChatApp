package com.chatapp.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.user;

public interface UserRepository  extends MongoRepository<user, String> {
  public user findByUsername(String username);
}
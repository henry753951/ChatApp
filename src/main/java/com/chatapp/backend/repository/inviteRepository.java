package com.chatapp.backend.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.inviteDB;
public interface inviteRepository  extends MongoRepository<inviteDB, Object> {
    public List<inviteDB> findBySenderId(String senderId);
}
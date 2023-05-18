package com.chatapp.backend.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.inviteDB;
public interface inviteRepository  extends MongoRepository<inviteDB, String> {
    public List<inviteDB> findBySenderId(String senderId);
    public List<inviteDB> findByReceiveId(String receiveId);
}
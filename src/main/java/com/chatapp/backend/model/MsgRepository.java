package com.chatapp.backend.model;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MsgRepository  extends MongoRepository<msg, String> {
    
}

package com.chatapp.backend.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.chatapp.backend.model.msgDB;

public interface MsgRepository  extends MongoRepository<msgDB, String> {
    
}

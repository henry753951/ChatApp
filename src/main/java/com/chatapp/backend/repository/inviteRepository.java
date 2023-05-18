package com.chatapp.backend.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatapp.backend.model.invite;
public interface inviteRepository  extends MongoRepository<invite, String> {


}
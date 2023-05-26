package com.chatapp.backend.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.chatapp.backend.model.roomDB;

public interface RoomRepository  extends MongoRepository<roomDB, Object> {
    roomDB findById(String id);
    // List<roomDB> findByMembersContainingByUserId(String id);
}

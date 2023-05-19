package com.chatapp.backend.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.chatapp.backend.model.roomDB;

public interface RoomRepository  extends MongoRepository<roomDB, Object> {
    List<roomDB> findByMemberIdsContaining(String id);
    roomDB findById(String id);
    roomDB findByMemberIdsContainingAndMemberIdsContaining(String id1, String id2);
}

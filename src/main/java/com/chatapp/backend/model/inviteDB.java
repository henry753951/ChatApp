package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.chatapp.backend.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class inviteDB {
    @Id
    public String id;
    @Indexed(unique = false)
    public String senderId;
    
    @DBRef
    public User sender;
    
    public long time;



}

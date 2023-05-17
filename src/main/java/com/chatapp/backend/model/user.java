package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.chatapp.backend.entity.User;

public class user {

    @Id
    public String id;
    @Indexed(unique = true)
    public String username;
    @Indexed(unique = true)
    public String token;
    public User user;

    
    public boolean online;
    public long lastSeen; // unix timestamp

    public user() {
    }

}
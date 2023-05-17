package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;

import com.chatapp.backend.entity.User;

public class user {

    @Id
    public String id;
    public User user;
    public String token;

    public boolean online;
    public long lastSeen; // unix timestamp

    public user() {
    }

}
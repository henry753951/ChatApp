package com.chatapp.backend.model;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.chatapp.backend.entity.User;
import com.chatapp.backend.entity.role;

public class userDB {

    @Id
    public String id;
    @Indexed(unique = true)
    public String username;
    public User user;

    //friend list
    public Set<String> Friend_Name_List;
    
    public boolean online;
    public long lastSeen; // unix timestamp
    public Set<role> roles = Set.of(new role(1L, "ROLE_USER"));
    
    public userDB() {
    }

}
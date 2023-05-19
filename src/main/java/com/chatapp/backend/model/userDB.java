package com.chatapp.backend.model;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.chatapp.backend.entity.User;
import com.chatapp.backend.entity.role;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class userDB {
    @RequiredArgsConstructor
    public class verification {
        public String verificationCode;
        public Boolean isVerified = false;
    }
    @Id
    public String id;
    @Indexed(unique = true)
    public String username;
    public User user;

    //friend list
    public Set<userDB> friends;
    
    public boolean online;
    public verification verify = new verification();
    public long lastSeen; // unix timestamp
    public Set<role> roles = Set.of(new role(1L, "ROLE_USER"));
    

}
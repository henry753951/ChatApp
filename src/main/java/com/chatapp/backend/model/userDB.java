package com.chatapp.backend.model;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.chatapp.backend.entity.User;
import com.chatapp.backend.entity.role;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Document(collection = "userDB")
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
    @DBRef
    public Set<userDB> friends;
    @DBRef
    public Set<inviteDB> invities;

    public boolean online;
    public verification verify = new verification();
    public long lastSeen; // unix timestamp
    public Set<role> roles = Set.of(new role(1L, "ROLE_USER"));
    

}
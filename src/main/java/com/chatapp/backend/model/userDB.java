package com.chatapp.backend.model;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.chatapp.backend.entity.User;
import com.chatapp.backend.entity.role;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
    @DBRef(lazy = true)
    @JsonBackReference
    public Set<userDB> friends = Set.of();
    @DBRef(lazy = true)
    public Set<inviteDB> invities = Set.of();

    public boolean online;
    public verification verify = new verification();
    public long lastSeen; // unix timestamp
    public Set<role> roles = Set.of(new role(1L, "ROLE_USER"));
    
    public boolean checkInvited(String senderId){
        for(inviteDB invite:invities){
            if(invite.senderId.equals(senderId)){
                return false;
            }
        }
        
        return true;
    } 
    public boolean checkFriend(String senderId){
        for(userDB friend:friends){
            if(friend.id.equals(senderId)){
                return true;
            }
        }
        return false;
    }
}
package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.chatapp.backend.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class inviteDB {
    @Id
    public String id;
    @Indexed(unique = false)
    public String senderId;
    @Indexed(unique = false)
    public String receiveId;

    public long time;

}

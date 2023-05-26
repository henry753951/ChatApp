package com.chatapp.backend.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class roomDB {
    @RequiredArgsConstructor
    public class msgDB {
        @Id
        public String id;
        @Indexed(unique = true)//設定索引值
        public String message;
        public String sender;
        public long time;
    }


    @Id
    public String id;
    @Indexed(unique = true)//設定索引值
    public String createrid;
    public String roomname;
    @DBRef
    public List<userDB> members = new ArrayList<userDB>();
    public List<msgDB> messages=new ArrayList<msgDB>();
}

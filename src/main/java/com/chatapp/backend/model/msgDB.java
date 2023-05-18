package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class msgDB {
    @Id
    public String id;
    @Indexed(unique = true)//設定索引值
    public String roomId;
    public String message;
    public String sender;
}

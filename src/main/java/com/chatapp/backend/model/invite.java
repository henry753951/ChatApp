package com.chatapp.backend.model;
import org.springframework.data.annotation.Id;

import com.chatapp.backend.entity.User;
public class invite {
    @Id
    public String sendname;
    public String receivename;
    public long time;
    public invite() {
    }
}

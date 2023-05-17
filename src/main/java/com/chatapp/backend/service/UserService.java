package com.chatapp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.backend.model.UserRepository;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;
    
    
}

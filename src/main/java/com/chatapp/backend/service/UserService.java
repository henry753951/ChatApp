package com.chatapp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatapp.backend.controllers.user;
import com.chatapp.backend.model.userDB;
import com.chatapp.backend.repository.RoomRepository;
import com.chatapp.backend.repository.UserRepository;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;

    public boolean checkUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            return true;
        }
        return false;
    }

    // public userDB add_friend(UserRepository userRepository){
    //     // userDB user = userRepository.findByUsername(username);
    //     // userDB friend = userRepository.findByUsername(username);
    //     // user.friends.add(friend);
    //     // userRepository.save(user);
    //     // return user;
    // }

}

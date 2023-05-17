package com.chatapp.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chatapp.backend.entity.BaseResponse;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.model.UserRepository;
import com.chatapp.backend.model.user;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.chatapp.backend.utils;

@RestController
@RequestMapping("/auth")
public class auth {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<User> login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {
        BaseResponse<User> response = new BaseResponse<User>();
        // ...
        if (false) {
            response.msg = "帳號或密碼錯誤";
            response.setError("授權失敗!");
            return response;
        }

        response.msg = "成功登入";
        User user = new User();
        user.username = username;
        user.Name = "test";
        user.email = username + "@mail.nuk.edu.tw";
        user.department = "test department";
        user.token = utils.sha256(username);
        if (userRepository.findByUsername(username) == null) {
            user userInDb = new user();
            userInDb.username = username;
            userInDb.user = user;
            userInDb.token = user.token;

            userRepository.save(userInDb);
        }
        response.data = user;
        return response;
    }
}

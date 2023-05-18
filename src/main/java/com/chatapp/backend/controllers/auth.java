package com.chatapp.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.*;

import com.chatapp.backend.entity.BaseResponse;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.model.userDB;
import com.chatapp.backend.repository.UserRepository;
import com.chatapp.backend.utils;

class UserLoginModel {
    public String username;
    public String password;
}

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class auth {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse<User> login(HttpServletResponse response_, HttpServletRequest request,
            @RequestBody UserLoginModel userLoginModel) {
        BaseResponse<User> response = new BaseResponse<User>();
        // ...

        if (false) {
            response.msg = "帳號或密碼錯誤";
            response.setError("授權失敗!");
            return response;
        }

        response.msg = "成功登入";
        User user = new User();
        user.username = userLoginModel.username;
        user.Name = "test";
        user.email = userLoginModel.username + "@mail.nuk.edu.tw";
        user.department = "test department";
        user.token = utils.sha256(userLoginModel.username);
        if (userRepository.findByUsername(userLoginModel.username) == null) {
            userDB userInDb = new userDB();
            userInDb.username = userLoginModel.username;
            userInDb.user = user;
            userInDb.token = user.token;

            userRepository.save(userInDb);
        }
        response.data = user;
        return response;
    }
}
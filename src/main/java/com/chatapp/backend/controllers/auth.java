package com.chatapp.backend.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.chatapp.backend.service.UserDetailsImpl;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatapp.backend.entity.BaseResponse;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.entity.role;
import com.chatapp.backend.model.userDB;
import com.chatapp.backend.repository.UserRepository;
import com.chatapp.backend.security.JwtUtil;
import com.chatapp.backend.service.AuthService;
import com.chatapp.backend.utils;
import java.lang.reflect.Type;

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
    public BaseResponse<HashMap<String, Object>> login(HttpServletRequest request,
            @RequestBody UserLoginModel userLoginModel) throws IOException {
        BaseResponse<HashMap<String, Object>> response = new BaseResponse<HashMap<String, Object>>();
        // ...
        userLoginModel.username = userLoginModel.username.toLowerCase();

        // print
        HashMap<String, Object> result = AuthService.getUser(userLoginModel.username, userLoginModel.password);
        if (result == null) {
            response.msg = "帳號或密碼錯誤";
            response.setError("授權失敗!");
            response.code = 401;
            return response;
        }
        response.msg = "成功登入";
        User user = new User();
        user.username = userLoginModel.username;
        user.Name = result.get("student_name").toString();
        user.email = userLoginModel.username + "@mail.nuk.edu.tw";
        user.department = result.get("student_department_name").toString();
        userDB userInDb = userRepository.findByUsername(userLoginModel.username);
        if (userInDb == null) {
            userInDb = new userDB();
            userInDb.username = userLoginModel.username;
            userInDb.user = user;
            userInDb.verify.verificationCode = utils.getRandomNumber(6).toString();
            userRepository.save(userInDb);
        }

        response.data = new HashMap<String, Object>();
        response.data.put("token", JwtUtil.createToken(userInDb));
        response.data.put("user", user);

        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public String getuser(Authentication authentication) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        return userDetails.toString();
    }

    
    @SecurityRequirement(name = "Bearer Authentication")
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public BaseResponse<String> verify(Authentication authentication, @RequestBody String code) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        userDB userInDb = userRepository.findByUsername(userDetails.getUsername());
        BaseResponse<String> response = new BaseResponse<String>();
        if (userInDb.verify.verificationCode.equals(code)) {
            userInDb.verify.verificationCode = "";
            userInDb.verify.isVerified = true;
            userRepository.save(userInDb);
            response.msg = "驗證成功";
            response.data = JwtUtil.createToken(userInDb);
            return response;
        } else {
            return new BaseResponse<String>("驗證失敗");
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @RequestMapping(value = "/verify/resend", method = RequestMethod.POST)
    public BaseResponse<String> resend(Authentication authentication) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        userDB userInDb = userRepository.findByUsername(userDetails.getUsername());
        BaseResponse<String> response = new BaseResponse<String>();
        userInDb.verify.verificationCode = utils.getRandomNumber(6).toString();
        userRepository.save(userInDb);

        response.msg = "已重新寄送驗證碼";
        return response;
    }

}
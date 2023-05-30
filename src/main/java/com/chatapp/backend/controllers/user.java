package com.chatapp.backend.controllers;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.chatapp.backend.repository.*;
import com.chatapp.backend.service.UserDetailsImpl;
import com.chatapp.backend.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;

import com.chatapp.backend.entity.*;
public class user {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user/avater", method = RequestMethod.POST)    
    @ResponseBody
    public BaseResponse<String> addavater(Authentication authentication,
    @RequestBody(required = true) MultipartFile file) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<String> response = new BaseResponse<String>("成功!");
            String path = "avatar//" + userDetails.getId() + ".jpg";
            File dest = new File(path);
            try {
                file.transferTo(dest);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        } else {
            BaseResponse<String> response = new BaseResponse<String>();
            response.setError("帳號未啟用");
            return response;
        }
    }
}
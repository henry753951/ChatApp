package com.chatapp.backend.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.chatapp.backend.repository.*;
import com.chatapp.backend.service.UserDetailsImpl;
import com.chatapp.backend.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.chatapp.backend.entity.*;


@RestController
@RequestMapping("/friend")
@SecurityRequirement(name = "Bearer Authentication")
public class friend {
    @Autowired
    private inviteRepository inviteRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/friend", method = RequestMethod.GET)
    public BaseResponse<Set<userDB>> getInvities(Authentication authentication) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<Set<userDB>> response = new BaseResponse<Set<userDB>>();
            Set<userDB> invities = userRepository.findById(userDetails.getId()).friends;
            response.data = invities;
            return response;
        } else {
            BaseResponse<Set<userDB>> response = new BaseResponse<Set<userDB>>();
            response.setError("帳號未啟用");
            return response;
        }
    }
   

}

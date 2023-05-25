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

class inviteAddBody {
    public String receiverId;
}

@RestController
@RequestMapping("/invite")
@SecurityRequirement(name = "Bearer Authentication")
public class invite {
    @Autowired
    private inviteRepository inviteRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/invite", method = RequestMethod.DELETE)
    public BaseResponse<inviteDB> deleteInvite(Authentication authentication,
            @RequestBody(required = true) inviteAddBody inviteAddBody) {
        // token 拿的 USER
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if(userDetails.isActive()){
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");
            inviteDB inviting = new inviteDB();
            inviting.senderId = userDetails.getId();
            inviteRepository.delete(inviting);
            return response;
        }else{
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>();
            response.setError("帳號未啟用");
            return response;
        }
    }
    
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public BaseResponse<inviteDB> acceptInvities(Authentication authentication,
            @RequestBody(required = true) String Userid) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if(userDetails.isActive()){
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");
            Set<inviteDB> invities = userRepository.findById(userDetails.getId()).invities;

            for(inviteDB inviting : invities){
                if(inviting.senderId.equals(Userid)){
                    userDB friend = userRepository.findById(Userid);
                    userDB mine = userRepository.findById(userDetails.getId());
                    friend.friends.add(mine);
                    mine.friends.add(friend);
                    userRepository.save(friend);
                    userRepository.save(mine);
                    inviteRepository.delete(inviting);
                    response.data = inviting;
                    return response;
                }
            }
            return response;
        }else{
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>();
            response.setError("帳號未啟用");
            return response;
        }
    }

    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public BaseResponse<Set<inviteDB>> getInvities(Authentication authentication) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<Set<inviteDB>> response = new BaseResponse<Set<inviteDB>>();
            Set<inviteDB> invities = userRepository.findById(userDetails.getId()).invities;
            response.data = invities;
            return response;
        } else {
            BaseResponse<Set<inviteDB>> response = new BaseResponse<Set<inviteDB>>();
            response.setError("帳號未啟用");
            return response;
        }
    }

    @RequestMapping(value = "/invite", method = RequestMethod.PUT)
    public BaseResponse<inviteDB> putInvite(Authentication authentication,
            @RequestBody(required = true) inviteAddBody inviteAddBody) {
        // token 拿的 USER
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");

        inviteDB inviting = new inviteDB();
        inviting.senderId = userDetails.getId();
        Date date = new Date();
        long time = date.getTime();
        inviting.time = time;
        userDB sender = userRepository.findById(inviteAddBody.receiverId);
        if (sender.invities.contains(inviting)) {
            response.setError("已經邀請過了");
            return response;
        }
        sender.invities.add(inviting);
        userRepository.save(sender);

        response.data = inviting;
        return response;
    }
}

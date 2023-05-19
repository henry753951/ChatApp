package com.chatapp.backend.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String senderId;
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
            inviting.senderId = inviteAddBody.senderId;
            inviting.receiveId = inviteAddBody.receiverId;
            inviteRepository.delete(inviting);
            return response;
        }else{
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>();
            response.setError("帳號未啟用");
            return response;
        }
    }
    
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public BaseResponse<inviteDB> acceptInvities(Authentication authentication){
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if(userDetails.isActive()){
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");
            inviteDB inviting = new inviteDB();
            
            inviteRepository.save(inviting);
            return response;
        }else{
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>();
            response.setError("帳號未啟用");
            return response;
        }
    }

    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public BaseResponse<List<inviteDB>> getInvities(Authentication authentication) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<List<inviteDB>> response = new BaseResponse<List<inviteDB>>();
            List<inviteDB> invitingList = inviteRepository.findByReceiveId(userDetails.getId());
            response.data = invitingList;
            return response;
        } else {
            BaseResponse<List<inviteDB>> response = new BaseResponse<List<inviteDB>>();
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
        inviting.senderId = inviteAddBody.senderId;
        inviting.receiveId = inviteAddBody.receiverId;
        Date date = new Date();
        long time = date.getTime();
        inviting.time = time;

        // check if inviting is already exist
        List<inviteDB> invitingList = inviteRepository.findByReceiveId(userDetails.getId());
        for (inviteDB invite : invitingList) {
            if (invite.senderId.equals(inviting.senderId)) {
                response.setError("已經邀請過了");
                response.msg = "已經邀請過了";
                return response;
            }
        }

        inviteRepository.save(inviting);
        response.data = inviting;
        return response;
    }
}

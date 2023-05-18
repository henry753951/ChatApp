package com.chatapp.backend.controllers;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chatapp.backend.repository.*;
import com.chatapp.backend.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Date;
import java.text.SimpleDateFormat;

import com.chatapp.backend.entity.*;

class inviteAddBody{
    public String senderId;
    public String receiverId;
}

@RestController
@RequestMapping("/invite")
public class invite {
    @Autowired
    private inviteRepository inviteRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public BaseResponse<List<inviteDB>> getInvities(HttpServletRequest request) {
        BaseResponse<List<inviteDB>> response = new BaseResponse<List<inviteDB>>();
        
        String token = request.getHeader("Authorization");
        userDB user = userRepository.findByToken(token);
        if (user == null) {
            response.setError("授權失敗!");
            return response;
        }
        // find invites of user
        List<inviteDB> invitingList = inviteRepository.findByReceiveId(user.id);
        response.data = invitingList;
        return response;
    }
    
    @RequestMapping(value= "/invite",method = RequestMethod.PUT)
    public BaseResponse<inviteDB> putInvite(
            @RequestBody(required = true) inviteAddBody inviteAddBody) { 
        BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");

        inviteDB inviting = new inviteDB();
        inviting.senderId = inviteAddBody.senderId;
        inviting.receiveId = inviteAddBody.receiverId;
        Date date = new Date();
        long time = date.getTime();
        inviting.time = time;

        // check if inviting is already exist
        List<inviteDB> invitingList = inviteRepository.findAll();
        for(inviteDB invite : invitingList){
            if(invite.senderId.equals(inviting.senderId) && invite.receiveId.equals(inviting.receiveId)){
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


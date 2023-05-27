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
@RequestMapping("/invite")
@SecurityRequirement(name = "Bearer Authentication")

public class invite {
    @Autowired
    private inviteRepository inviteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @RequestMapping(value = "/invite", method = RequestMethod.DELETE)
    public BaseResponse<inviteDB> deleteInvite(Authentication authentication,
            @RequestBody(required = true) String userid) {
        // token 拿的 USER
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        userDB  user=userRepository.findById(userDetails.getId());
        if(userDetails.isActive()){
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");
            for(inviteDB inviting : user.invities){
                if(inviting.senderId.equals(userid)){
                    user.invities.remove(inviting);
                    userRepository.save(user);
                    inviteRepository.delete(inviting);
                    return response;
                }
            }
        }else{
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>();
            response.setError("帳號未啟用");
            return response;
        }
        BaseResponse<inviteDB> response = new BaseResponse<inviteDB>();
        response.setError("找不到使用者");
        return response;
    }
    
 @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public BaseResponse<inviteDB> acceptInvities(Authentication authentication,
            @RequestBody(required = true) String Userid) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");
            userDB mine = userRepository.findById(userDetails.getId());

            for(inviteDB inviting : mine.invities){
                if(inviting.senderId.equals(Userid)){
                    userDB friend = userRepository.findById(Userid);
                    friend.friends.add(mine);
                    mine.friends.add(friend);
                    userRepository.save(friend);
                    mine.invities.remove(inviting);
                    userRepository.save(mine);

                    inviteRepository.delete(inviting);

                    response.data = inviting;
                    roomDB room = new roomDB();
                    room.createrid = mine.id;
                    room.members.add(mine);
                    room.members.add(friend);
                    room.roomname = mine.username + "和" + friend.username + "的聊天室";
                    roomRepository.save(room);


                    return response;
                }
            }
            response.setError("找不到使用者");
            return response;
        } else {
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
            @RequestBody(required = true) String username) {
        // token 拿的 USER
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        BaseResponse<inviteDB> response = new BaseResponse<inviteDB>("成功!");

        inviteDB inviting = new inviteDB();
        inviting.senderId = userDetails.getId();
        inviting.sender = userRepository.findById(userDetails.getId());
        Date date = new Date();
        long time = date.getTime();
        inviting.time = time;

        userDB receiver = userRepository.findByUsername(username);
        if (receiver == null) {
            response.setError("找不到使用者");
            response.msg = "失敗!";
            return response;
        }

        if (userDetails.getId().equals(receiver.id)) {
            response.setError("不能邀請自己");
            response.msg = "失敗!";
            return response;
        }
        if(receiver.checkFriend(username)){
            response.setError("已經是好友了");
            response.msg = "失敗!";
            return response;
        }
        if (!receiver.checkInvited(userDetails.getId())) {
            response.setError("已經邀請過了");
            response.msg = "失敗!";
            return response;
        }

        inviteRepository.save(inviting);
        receiver.invities.add(inviting);
        userRepository.save(receiver);

        return response;
    }
}

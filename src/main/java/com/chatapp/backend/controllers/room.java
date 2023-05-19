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
class roomAddBody {
    public Set<String> memberIds;
    public List<roomDB.msgDB> messages;
}
public class room {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public BaseResponse<List<roomDB>> getRooms(Authentication authentication) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<List<roomDB>> response = new BaseResponse<List<roomDB>>("成功!");
            List<roomDB> roomList = roomRepository.findByMemberIdsContaining(userDetails.getId());
            response.data = roomList;
            return response;

        } else {
            BaseResponse<List<roomDB>> response = new BaseResponse<List<roomDB>>();
            response.setError("帳號未啟用");
            return response;
        }
    }
    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public BaseResponse<roomDB> createRoom(Authentication authentication) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<roomDB> response = new BaseResponse<roomDB>("成功!");
            roomDB room = new roomDB();
            room.memberIds.add(userDetails.getId());
            roomRepository.save(room);
            response.data = room;
            return response;
        } else {
            BaseResponse<roomDB> response = new BaseResponse<roomDB>();
            response.setError("帳號未啟用");
            return response;
        }
    }
    @RequestMapping(value = "/room/{id}", method = RequestMethod.GET)
    public BaseResponse<roomDB> getRoomMessages(Authentication authentication,
            @PathVariable(value = "id") String id) {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());
        if (userDetails.isActive()) {
            BaseResponse<roomDB> response = new BaseResponse<roomDB>("成功!");
            roomDB room = roomRepository.findById(id);
            response.data = room;
            return response;
        } else {
            BaseResponse<roomDB> response = new BaseResponse<roomDB>();
            response.setError("帳號未啟用");
            return response;
        }
    }
}


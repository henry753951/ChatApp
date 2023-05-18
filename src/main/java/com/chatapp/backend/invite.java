package com.chatapp.backend;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chatapp.backend.entity.BaseResponse;
import com.chatapp.backend.entity.User;
import com.chatapp.backend.repository.UserRepository;
import com.chatapp.backend.model.user;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Date;
import java.text.SimpleDateFormat;

class inviting{
    public String sendname;
    public String receivename;
    public long time;
}
@RestController
@RequestMapping("/invite")
public class invite {
    @RequestMapping(value= "/invite",method = RequestMethod.PUT)
    public BaseResponse<inviting> register(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "receivename", required =true) String receivename) {
        BaseResponse<inviting> response = new BaseResponse<inviting>("成功!");
        // ...

        inviting inviting = new inviting();
        inviting.sendname = username;
        inviting.receivename =receivename;
        Date date = new Date();
        long time = date.getTime();
        inviting.time = time;
        
        response.data = inviting;
        return response;
    }
}


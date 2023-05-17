package com.chatapp.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.chatapp.backend.entity.BaseResponse;
import com.chatapp.backend.model.UserRepository;
import com.chatapp.backend.model.user;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class auth {
    @RequestMapping(value= "/register",method = RequestMethod.PUT)
    public BaseResponse<String> register(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {
        BaseResponse<String> response = new BaseResponse<String>("成功!");
        // ...);

        response.data = username;
        return response;
    }
}

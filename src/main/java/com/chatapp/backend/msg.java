package com.chatapp.backend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.entity.BaseResponse;
import com.chatapp.backend.entity.Msg;

@RestController
@RequestMapping("/msg")
public class msg {
    @RequestMapping(value= "/{id}",method = RequestMethod.PUT)
    public BaseResponse<Msg> msg(
            @RequestParam(value = "roomId", required = false) String roomId,
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "sender", required = false) String sender
            ) {
        BaseResponse<Msg> response = new BaseResponse<Msg>("成功!");
        Msg msg = new Msg();
        msg.roomId = roomId;
        msg.message = message;
        msg.sender = sender;
        msg.time = System.currentTimeMillis()/ 1000L;
        response.data = msg;
        return response;
    }
}

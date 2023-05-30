package com.chatapp.backend.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;

import com.chatapp.backend.model.roomDB;
import com.chatapp.backend.repository.RoomRepository;
import com.google.gson.Gson;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Controller
public class SocketController {
    @Autowired
    SimpMessagingTemplate template;
    @Autowired
    RoomRepository roomRepository;

    @MessageMapping("/chat")
    public void greeting(Message<?> message,String jsonString) throws Exception {
        MessageHeaders headers = message.getHeaders();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS,MultiValueMap.class);
        // print message
        System.out.println("message: " + jsonString);
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(jsonString, Map.class);

        Optional<roomDB> room = roomRepository.findById(data.get("room_id"));
        if(room.isPresent()){
            roomDB roomData = room.get();
            Map<String,Object> messageData = (Map<String,Object>) data.get("message");
            System.out.println("messageData: " + messageData);
            roomData.messages.add((Map<String, Object>) messageData);
            roomRepository.save(roomData);
        }

        
        System.out.println("data: " + data);
        // send message
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                template.convertAndSend("/topic/greetings", data);
            }
        });
    }
}
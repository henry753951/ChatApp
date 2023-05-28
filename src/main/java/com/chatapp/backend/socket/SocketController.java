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
import com.google.gson.Gson;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Controller
public class SocketController {
    @Autowired
    SimpMessagingTemplate template;

    @MessageMapping("/chat")
    public void greeting(Message<?> message,String jsonString) throws Exception {
        MessageHeaders headers = message.getHeaders();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS,MultiValueMap.class);
        // print message
        System.out.println("message: " + jsonString);
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(jsonString, Map.class);
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
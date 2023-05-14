package com.chatapp.backend;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test{
    @RequestMapping("/")
    public String sayHello() {
        return "Hello World";
    }
    @RequestMapping("/123")
    public String ttt() {
        return "Hello World";
    }
}
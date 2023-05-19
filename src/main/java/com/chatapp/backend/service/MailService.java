package com.chatapp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.Async;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
@Service
@EnableAsync
public class MailService {

   @Autowired
    private JavaMailSender mailSender;

    public void sendMailWithCode(String mail, String code)  {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ININDER");
        message.setTo(mail);
        message.setSubject("ININDER 啟用帳號驗證碼");
        message.setText("你的帳號驗證碼為: " + code + "，請在畫面上輸入驗證碼以完成啟用帳號。");

        mailSender.send(message);
    }

    
}

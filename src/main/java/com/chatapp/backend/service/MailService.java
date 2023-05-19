package com.chatapp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailService {

   @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jj.huang@hotmail.com");
        message.setTo("jj.huang@*****.com.tw");
        message.setSubject("主旨：Hello J.J.Huang");
        message.setText("內容：這是一封測試信件，恭喜您成功發送了唷");

        mailSender.send(message);
    }
}

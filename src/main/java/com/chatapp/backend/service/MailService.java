package com.chatapp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class MailService {

   @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail()  {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a1105534@mail.nuk.edu.tw");
        message.setTo("a1105534@mail.nuk.edu.tw");
        message.setSubject("測試測試");
        message.setText("內容：這是一封測試信件，恭喜您成功發送了唷");

        mailSender.send(message);
    }
}

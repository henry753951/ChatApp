package com.chatapp.backend;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class utils {
    public static String getRandomNumber(int n){
        String randomNum = "";
        for(int i = 0; i < n; i++){
            randomNum += (int)(Math.random() * 10);
        }
        return randomNum;
    }
}

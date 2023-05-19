package com.chatapp.backend.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AuthService {
    static public HashMap<String, Object> getUser(String username, String password) {
        try{
            OkHttpClient client = new OkHttpClient();
            okhttp3.RequestBody body = new FormBody.Builder()
                    .add("account", username)
                    .add("password", password)
                    .build();
            okhttp3.Request r = new Request.Builder()
                    .url("https://nukapp.tk/login?token=777app")
                    .post(body)
                    .build();
            okhttp3.Response re = client.newCall(r).execute();
            String json = re.body().string();
            HashMap<String, Object> result = new Gson().fromJson(json, HashMap.class);
            HashMap<String, Object> user_ = new Gson().fromJson(result.get("user").toString(), HashMap.class);
            return user_;
        }catch (Exception e){
            return null;
        }
    }
}

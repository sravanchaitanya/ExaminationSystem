package com.example.MainService;

import com.example.MainService.FeignClients.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdminLoader implements CommandLineRunner {

    @Autowired
    public UserServiceClient userServiceClient;
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Map<String,String> user = new HashMap<>();
        user.put("id","1234");
        user.put("password",passwordEncoder.encode("admin"));
        user.put("branch","administration");
        user.put("role","ADMIN");
        userServiceClient.postUser(user);
    }
}

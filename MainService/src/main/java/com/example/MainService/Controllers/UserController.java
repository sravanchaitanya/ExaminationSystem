package com.example.MainService.Controllers;

import com.example.MainService.FeignClients.LogServiceClient;
import com.example.MainService.FeignClients.TestServiceClient;
import com.example.MainService.FeignClients.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    public UserServiceClient userServiceClient;

    @Autowired
    public LogServiceClient logServiceClient;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Map<String,String>> getUser(@RequestParam Map<String,String> map){
        return userServiceClient.getUser(map);
    }

    @PostMapping
    public void postUser(@RequestBody Map<String,String> map){
        if(map.containsKey("password")){
            map.put("password", passwordEncoder.encode(map.get("password")));
        }
        logServiceClient.postUserLog(map);
        userServiceClient.postUser(map);
    }

    @PutMapping
    public void putUser(@RequestBody Map<String,String> map){
        if(map.containsKey("password")){
            map.put("password", passwordEncoder.encode(map.get("password")));
        }
        userServiceClient.putUser(map);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam Map<String,String> map){
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("userId",map.get("id"));
        logServiceClient.deleteLog(queryMap);
        userServiceClient.deleteUser(map);
    }
}

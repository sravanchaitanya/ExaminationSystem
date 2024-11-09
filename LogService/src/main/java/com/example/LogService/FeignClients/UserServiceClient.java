package com.example.LogService.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("USERSERVICE")
public interface UserServiceClient {
    @GetMapping("users")
    public List<Map<String,String>> getUsers(@RequestParam Map<String,String> map);
}

package com.example.MainService.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("USERSERVICE")
public interface UserServiceClient {

    @GetMapping("users")
    public List<Map<String,String>> getUser(@RequestParam Map<String,String> map);

    @PostMapping("users")
    public void postUser(@RequestBody Map<String,String> map);

    @PutMapping("users")
    public void putUser(@RequestBody Map<String,String> map);

    @DeleteMapping("users")
    public void deleteUser(@RequestParam Map<String,String> map);
}

package com.example.MainService.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("LOGSERVICE")
public interface LogServiceClient {

    @GetMapping("logs")
    public List<Map<String,String>> getLog(@RequestParam Map<String,String> map);

    @PostMapping("logs/user")
    public void postUserLog(@RequestParam Map<String,String> map);

    @PostMapping("logs/test")
    public void postTestLog(@RequestParam Map<String,String> map);

    @PutMapping("logs")
    public void putLog(@RequestParam Map<String,String> map);

    @DeleteMapping("logs")
    public void deleteLog(@RequestParam Map<String,String> map);
}

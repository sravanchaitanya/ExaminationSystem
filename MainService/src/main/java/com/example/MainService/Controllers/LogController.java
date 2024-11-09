package com.example.MainService.Controllers;


import com.example.MainService.FeignClients.LogServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("logs")
public class LogController {
    @Autowired
    public LogServiceClient logServiceClient;

    @GetMapping
    public List<Map<String, String>> getLogs(@RequestParam Map<String,String> map){
        return logServiceClient.getLog(map);
    }

    @DeleteMapping
    public void deleteLog(@RequestParam Map<String,String> map){
        logServiceClient.deleteLog(map);
    }
}

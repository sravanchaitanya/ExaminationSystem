package com.example.LogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("logs")
public class LogController {
    @Autowired
    public LogService logService;

    @GetMapping
    public List<Log> getLog(@RequestParam Map<String,String> map){
        return logService.getLogs(map);
    }

    @PostMapping("test")
    public void postTestLog(@RequestBody Map<String,String> map){
        logService.postTestLog(map);
    }

    @PostMapping("user")
    public void postUserLog(@RequestBody Map<String,String> map){
        logService.postUserLog(map);
    }

    @PutMapping
    public void putLog(@RequestBody Map<String,String> map){
        logService.putLog(map);
    }

    @DeleteMapping
    public void deleteLog(@RequestParam Map<String,String> map){
        logService.deleteLog(map);
    }
}

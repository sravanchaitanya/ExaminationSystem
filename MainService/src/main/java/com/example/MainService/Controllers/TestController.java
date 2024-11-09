package com.example.MainService.Controllers;

import com.example.MainService.FeignClients.LogServiceClient;
import com.example.MainService.FeignClients.TestServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tests")
public class TestController {
    @Autowired
    public TestServiceClient testServiceClient;
    @Autowired
    public LogServiceClient logServiceClient;

    @GetMapping
    public List<Map<String,String>> getTest(@RequestParam Map<String,String> map){
        return testServiceClient.getTest(map);
    }

    @PostMapping
    public void postTest(@RequestBody Map<String,String> map){
        testServiceClient.postTest(map);
        Map<String,String> test = testServiceClient.getTest(map).get(0);
        logServiceClient.postTestLog(test);
    }

    @PutMapping
    public void putTest(@RequestBody Map<String,String> map){
        testServiceClient.putTest(map);
    }

    @DeleteMapping
    public void deleteTest(@RequestParam Map<String,String> map){
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("testId",map.get("id"));
        logServiceClient.deleteLog(queryMap);
        testServiceClient.deleteTest(map);
    }
}

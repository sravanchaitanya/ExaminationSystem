package com.example.MainService.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("TESTSERVICE")
public interface TestServiceClient {

    @GetMapping("tests")
    public List<Map<String,String>> getTest(@RequestParam Map<String,String> map);

    @GetMapping("tests/all")
    public List<Map<String,String>> getAllTests();

    @PostMapping("tests")
    public void postTest(@RequestBody Map<String,String> map);

    @PutMapping("tests")
    public void putTest(@RequestBody Map<String,String> map);

    @DeleteMapping("tests")
    public void deleteTest(@RequestParam Map<String,String> map);
}

package com.example.LogService.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("TESTSERVICE")
public interface TestServiceClient {
    @GetMapping("tests")
    public List<Map<String,String>> getTests(@RequestParam Map<String,String> map);
}

package com.example.TestService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tests")
public class ViewController {

    @Autowired
    public TestService testService;

    @GetMapping
    public List<Test> getTest(@RequestParam Map<String,String> map){
        return testService.getTest(map);
    }

    @PostMapping
    public void postTest(@RequestBody Map<String,String> map){
        testService.postTest(map);
    }

    @PutMapping
    public void putTest(@RequestBody Map<String,String> map){
        testService.putTest(map);
    }

    @DeleteMapping
    public void deleteTest(@RequestParam Map<String,String> map){
        testService.deleteTest(map);
    }
}

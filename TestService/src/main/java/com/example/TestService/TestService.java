package com.example.TestService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {
    @Autowired
    public TestRepository testRepository;

    public Test mapTest(Map<String,String> map,Test test){
        if(map.containsKey("id"))
        test.setId(Integer.parseInt(map.get("id")));
        test.setName(map.getOrDefault("name",test.getName()));
        test.setBranch(map.getOrDefault("branch",test.getBranch()));
        return test;
    }

    public List<Test> getTest(Map<String,String> map){
        Test test = mapTest(map,new Test());
        return testRepository.findAll(Example.of(test));
    }

    public void postTest(Map<String,String> map){
        Test test = mapTest(map,new Test());
        testRepository.save(test);
    }

    public void putTest(Map<String,String> map){
        Test test = testRepository.findById(Integer.parseInt(map.get("id"))).orElse(null);
        testRepository.save(mapTest(map,test));
    }

    @Transactional
    public void deleteTest(Map<String,String> map){
        List<Test> tests = getTest(map);
        testRepository.deleteAll(tests);
    }

    public List<Test> getAllTests(){
        return testRepository.findAll();
    }
}

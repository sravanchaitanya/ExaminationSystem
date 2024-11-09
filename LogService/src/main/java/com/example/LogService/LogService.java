package com.example.LogService;

import com.example.LogService.FeignClients.TestServiceClient;
import com.example.LogService.FeignClients.UserServiceClient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class LogService {

    @Autowired
    public LogRepository logRepository;

    @Autowired
    public UserServiceClient userServiceClient;

    @Autowired
    public TestServiceClient testServiceClient;

    public Log mapLog(Map<String,String> map,Log log){
        if(map.containsKey("id"))
            log.setId(Integer.parseInt(map.get("id")));
        log.setTestId(map.getOrDefault("testId",log.getTestId()));
        log.setUserId(map.getOrDefault("userId",log.getUserId()));
        log.setBranch(map.getOrDefault("branch",log.getBranch()));
        log.setStatus(map.getOrDefault("status", log.getStatus()));
        return log;
    }

    public List<Log> getLogs(Map<String,String> map){
        return logRepository.findAll(Example.of(mapLog(map,new Log())));
    }

    public void postTestLog(Map<String,String> map){
        Map<String,String> temp = new HashMap<>();
        temp.put("branch",map.get("branch"));
        List<Map<String,String>> users = userServiceClient.getUsers(temp);
        List<Log> logs = new LinkedList<>();
        for(Map<String,String> currMap:users){
            Log log=new Log();
            log.setTestId(map.get("id"));
            log.setUserId(currMap.get("id"));
            log.setBranch(map.get("branch"));
            log.setStatus("ACTIVE");
            logs.add(log);
        }
        logRepository.saveAll(logs);
    }

    public void postUserLog(Map<String,String> map){
        Map<String,String> temp = new HashMap<>();
        temp.put("branch",map.get("branch"));
        List<Map<String,String>> tests = testServiceClient.getTests(temp);
        List<Log> logs = new LinkedList<>();
        for(Map<String,String> currMap:tests){
            Log log=new Log();
            log.setUserId(map.get("id"));
            log.setTestId(currMap.get("id"));
            log.setBranch(map.get("branch"));
            log.setStatus("ACTIVE");
            logs.add(log);
        }
        logRepository.saveAll(logs);
    }

    public void putLog(Map<String,String> map){
        Log log = mapLog(map,new Log());
        List<Log> logs = logRepository.findAll(Example.of(log));
        for(Log thisLog:logs){
            thisLog.setStatus("INACTIVE");
            logRepository.save(thisLog);
        }
    }

    @Transactional
    public void deleteLog(Map<String,String> map){
        logRepository.deleteAll(getLogs(map));
    }

    public List<Log> getAllLogs(){
        return logRepository.findAll();
    }
}

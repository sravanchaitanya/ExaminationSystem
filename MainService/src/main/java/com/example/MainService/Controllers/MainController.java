package com.example.MainService.Controllers;

import com.example.MainService.FeignClients.LogServiceClient;
import com.example.MainService.FeignClients.TestServiceClient;
import com.example.MainService.FeignClients.UserServiceClient;
import com.example.MainService.Security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    public MyUserDetailsService myUserDetailsService;
    @Autowired
    public LogServiceClient logServiceClient;
    @Autowired
    public TestServiceClient testServiceClient;
    @Autowired
    public UserServiceClient userServiceClient;

    @GetMapping("Home")
    public String getHome(){
        return "Home";
    }

    @GetMapping("UsersCorner")
    public String getUsersCorner(){
        return "UsersCorner";
    }

    @GetMapping("TestsCorner")
    public String getTestsCorner(){
        return "TestsCorner";
    }

    @GetMapping("LogsCorner")
    public String getLogsCorner(){
        return "LogsCorner";
    }

}

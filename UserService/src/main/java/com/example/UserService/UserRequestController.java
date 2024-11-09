package com.example.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*")
public class UserRequestController {

    @Autowired
    public UserService userService;

    @GetMapping()
    public List<User> getUser(@RequestParam Map<String,String> map){
        return userService.getUser(map);
    }

    @GetMapping("all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping()
    public void postUser(@RequestBody Map<String,String> map){
        userService.postUser(map);
    }

    @PutMapping()
    public void putUser(@RequestBody Map<String,String> map){
        userService.putUser(map);
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam Map<String,String> map){
        userService.deleteUser(map);
    }
}

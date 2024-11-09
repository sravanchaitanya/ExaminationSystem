package com.example.UserService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    public User mapUser(Map<String,String> map,User user){
        if(map.containsKey("id"))
            user.setId(Integer.parseInt(map.get("id")));
        user.setPassword(map.getOrDefault("password",user.getPassword()));
        user.setBranch(map.getOrDefault("branch",user.getBranch()));
        user.setRole(map.getOrDefault("role",user.getRole()));
        return user;
    }
    
    public List<User> getUser(Map<String,String> map){
        User user = mapUser(map,new User());
        return userRepository.findAll(Example.of(user));
    }

    public void postUser(Map<String,String> map){
        userRepository.saveAndFlush(mapUser(map,new User()));
    }

    public void putUser(Map<String,String> map){
        User user = userRepository.findById(Integer.parseInt(map.get("id"))).orElse(null);
        userRepository.saveAndFlush(mapUser(map,user));
    }

    @Transactional
    public void deleteUser(Map<String,String> map){
        List<User> users = getUser(map);
        userRepository.deleteAll(users);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


}

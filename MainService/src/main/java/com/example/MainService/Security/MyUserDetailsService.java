package com.example.MainService.Security;

import com.example.MainService.FeignClients.UserServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    public UserServiceClient userServiceClient;

    private Map<String,String> user;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,String> query = new HashMap<>();
        query.put("id",username);
        List<Map<String,String>> users = userServiceClient.getUser(query);
        if(users.size()==0)
            throw new UsernameNotFoundException("User Not Found");
        Map<String,String> currUser = users.get(0);
        setUser(currUser);
        return new User(username,currUser.get("password"), Collections.singleton(new SimpleGrantedAuthority("ROLE_"+currUser.get("role"))));
    }

    public void setUser(Map<String,String> map){
        user = map;
    }

    public Map<String,String> getUser(){
        return user;
    }
}

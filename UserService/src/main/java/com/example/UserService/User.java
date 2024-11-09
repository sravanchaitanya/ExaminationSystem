package com.example.UserService;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class User {
    @Id
    private Integer id;

    private String password;

    private String branch;

    private String role;

//    @ElementCollection
//    private List<String> unattempted = new LinkedList<String>();
//
//    @ElementCollection
//    private List<String> attempted = new LinkedList<String>();

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

//    public List<String> getUnattempted() {
//        return unattempted;
//    }
//
//    public void setUnattempted(List<String> unattempted) {
//        this.unattempted = unattempted;
//    }
//
//    public List<String> getAttempted() {
//        return attempted;
//    }
//
//    public void setAttempted(List<String> attempted) {
//        this.attempted = attempted;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

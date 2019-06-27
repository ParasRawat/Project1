package com.example.parasrawat.projectinfroid.ModelClasses;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    String username="",email="";
    HashMap<String,String> contributions=new HashMap<>();

    public User(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, String> getContributions() {
        return contributions;
    }

    public void setContributions(HashMap<String, String> contributions) {
        this.contributions = contributions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

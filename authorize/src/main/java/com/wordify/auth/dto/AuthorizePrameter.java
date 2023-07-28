package com.wordify.auth.dto;

public class AuthorizePrameter {
    String username;
    String password;
    
    public AuthorizePrameter() {
    }
    public AuthorizePrameter(String username,String password){
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String userName) {
        this.username = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

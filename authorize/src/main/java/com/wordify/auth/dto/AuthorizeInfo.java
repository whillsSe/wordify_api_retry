package com.wordify.auth.dto;


public class AuthorizeInfo {
    private int id;
    private String hashedPassword;
    public AuthorizeInfo(int id,String hashedPassword){
        this.id = id;
        this.hashedPassword = hashedPassword;
    }
    public int getId() {
        return id;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    
}

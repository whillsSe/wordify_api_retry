package com.wordify.auth.service;

import java.sql.SQLException;

import javax.security.auth.login.LoginException;

public interface LoginService {
    public int login(String userName,String password) throws SQLException,LoginException;
}

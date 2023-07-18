package com.wordify.auth.service;

import java.sql.SQLException;

public interface LoginService {
    public int login(String userName,String password) throws SQLException;
}

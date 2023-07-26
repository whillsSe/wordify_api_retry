package com.wordify.auth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.security.auth.login.LoginException;
import javax.security.sasl.AuthenticationException;

import com.wordify.auth.dto.AuthorizeInfo;
import com.wordify.auth.dto.InitializeInfo;

public class LoginDao {
    public AuthorizeInfo login(String userNameString,Connection conn) throws SQLException,LoginException{
        StringBuilder builder = new StringBuilder("SELECT id,password FROM user_auth WHERE username = ?");
        String password;
        int id;
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setString(1, userNameString);
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                password = resultSet.getString("password");
                id = resultSet.getInt("id");
                return new AuthorizeInfo(id,password);
            }
            throw new LoginException("No user found on database.");//誰もヒットしなかったとき用
        }catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
}

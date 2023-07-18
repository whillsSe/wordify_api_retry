package com.wordify.auth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.auth.dto.AuthorizeInfo;

public class LoginDao {
        public AuthorizeInfo login(String userNameString,Connection conn) throws SQLException{
        StringBuilder builder = new StringBuilder("SELECT id,password FROM users WHERE username = ?");
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
            throw new SQLException("No user found on database.");//誰もヒットしなかったとき用
        }catch(SQLException e){
            throw new SQLException(e.getMessage());
        }
    }
}

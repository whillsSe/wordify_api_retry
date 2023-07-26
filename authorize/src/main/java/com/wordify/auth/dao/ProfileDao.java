package com.wordify.auth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.wordify.auth.dto.InitializeInfo;

public class ProfileDao {
    public InitializeInfo getInfo(int userId,Connection conn) throws Exception{
        StringBuilder builder = new StringBuilder("SELECT username,profile_name,icon FROM users WHERE id = ?");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                InitializeInfo info = new InitializeInfo();
                info.setId(userId);
                info.setUserName(resultSet.getString("username"));
                info.setProfileName(resultSet.getString("profile_name"));
                info.setProfileImage(resultSet.getString("icon"));
                return info;
            }
            throw new Exception("No user found on database.");//誰もヒットしなかったとき用
        }
    } 
}

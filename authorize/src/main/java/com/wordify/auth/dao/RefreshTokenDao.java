package com.wordify.auth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.auth.dto.RefreshTokenInfo;

import io.jsonwebtoken.MalformedJwtException;

public class RefreshTokenDao {
    public void registerRefreshToken(RefreshTokenInfo info,Connection conn) throws SQLException{
        StringBuilder builder = new StringBuilder("INSERT IGNORE INTO refresh_tokens(token,user_id,expiration) VALUES(?,?,?)");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setString(1,info.getRefreshTokeString());
            pstmt.setInt(2, info.getUserId());
            pstmt.setDate(3, new java.sql.Date(info.getExpiration()));
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected != 1){
                //処理失敗のエラーをここで吐くべき
                    //IGNOREなので、存在した場合も多分ここで拾う
            }
        }
    }
    public int checkRefreshToken(String refreshToken,Connection conn) throws SQLException,MalformedJwtException{
        StringBuilder builder = new StringBuilder("SELECT token,user_id FROM refresh_tokens WHERE token = ? AND is_valid = 1");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setString(1, refreshToken);
            ResultSet resultSet = pstmt.executeQuery();
            if(!resultSet.next()){
                throw new MalformedJwtException("Token is invalid.");
            }
            return resultSet.getInt("user_id");
        }
    }
    public void revokeRefreshToken(String refreshToken,Connection conn)throws SQLException{
        StringBuilder builder = new StringBuilder("UPDATE refresh_tokens SET is_valid = 0 WHERE token = ?");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setString(1, refreshToken);
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected != 1){
                 //処理失敗のエラーをここで吐くべき
            }
        }
    }
}

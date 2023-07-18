package com.wordify.auth.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenServiceImpl implements JwtTokenService{

    private static SecretKey SECRET_KEY;
    private static long EXPIRATION_TIME = 3600000;
    private static long REFRESH_EXPIRATION_TIME = 86400000;
    public void init(){
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("config.properties")) {
            prop.load(input);
            SECRET_KEY = Keys.hmacShaKeyFor(prop.getProperty("jwt.key").getBytes(StandardCharsets.UTF_8));       
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String createAccessToken(int userId){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder().setSubject(Integer.toString(userId)).setIssuedAt(now).setExpiration(expiration).signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
    }
    @Override
    public String createRefreshToken(int userId,Connection conn) throws SQLException{
        //refresh_tokensでは、有効・無効の判断を行うカラム・有効期限を登録する。
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);
        String refreshToken = Jwts.builder().setSubject(Integer.toString(userId)).setIssuedAt(now).setExpiration(expiration).signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
        StringBuilder builder = new StringBuilder("INSERT IGNORE INTO refresh_tokens(token,user_id,expiration) VALUES(?,?,?)");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setString(1,refreshToken);
            pstmt.setInt(2, userId);
            pstmt.setDate(3, (java.sql.Date) expiration);
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected != 1){
                //処理失敗のエラーをここで吐くべき
            }
        }
        
        return refreshToken;
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        String userId = parseClaimsToken(refreshToken);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder().setSubject(userId).setIssuedAt(now).setExpiration(expiration).signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
    }

    @Override
    public void revokeRefreshToken(int userId,Connection conn) {
        // TODO Auto-generated method stub
             
    }
    
    public String parseClaimsToken(String jwtToken){
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
        Jws<Claims> jwsClaims = jwtParser.parseClaimsJws(jwtToken);
        return jwsClaims.getBody().getSubject();
    }
}

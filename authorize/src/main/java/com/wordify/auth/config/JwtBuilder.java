package com.wordify.auth.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtBuilder {
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
    public static String createAccessToken(int userId){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder().setSubject(Integer.toString(userId)).setIssuedAt(now).setExpiration(expiration).signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
    }
    public static String createRefreshToken(int userId){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_EXPIRATION_TIME);
        return Jwts.builder().setSubject(Integer.toString(userId)).setIssuedAt(now).setExpiration(expiration).signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
    }
}

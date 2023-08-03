package com.wordify.auth.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.crypto.SecretKey;

import com.wordify.auth.config.ConnectionPool;
import com.wordify.auth.dao.ProfileDao;
import com.wordify.auth.dao.RefreshTokenDao;
import com.wordify.auth.dto.RefreshTokenInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

public class JwtTokenServiceImpl implements JwtTokenService{
    private ConnectionPool connectionPool;
    private RefreshTokenDao refreshTokenDao;
    private ProfileDao profileDao;
    private static SecretKey SECRET_KEY;
    private static long EXPIRATION_TIME = 3600000;
    private static long REFRESH_EXPIRATION_TIME = 86400000;
    public JwtTokenServiceImpl(){
        setUpFunc();
    }
    public JwtTokenServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        setUpFunc();
    }
    private void setUpFunc(){
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        this.refreshTokenDao = new RefreshTokenDao();
        this.profileDao = new ProfileDao();
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
    public String createExpiredAccessToken(int userId){
        Date now = new Date();
        Date expiration = new Date(now.getTime() - EXPIRATION_TIME);
        return Jwts.builder().setSubject(Integer.toString(userId)).setIssuedAt(now).setExpiration(expiration).signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
    }
    @Override
    public String createRefreshToken(int userId) throws SQLException{
        //refresh_tokensでは、有効・無効の判断を行うカラム・有効期限を登録する。
        Connection conn = connectionPool.getConnection();
        Date now = new Date();
        long expirationMilliSec = now.getTime() + REFRESH_EXPIRATION_TIME;
        String refreshToken = Jwts.builder().setSubject(Integer.toString(userId)).setIssuedAt(now).setExpiration(new Date(expirationMilliSec)).signWith(SECRET_KEY,SignatureAlgorithm.HS256).compact();
        refreshTokenDao.registerRefreshToken(new RefreshTokenInfo(refreshToken, userId, expirationMilliSec), conn);
        return refreshToken;
    }
    @Override
    public int checkRefreshToken(String refreshToken) throws SQLException,MalformedJwtException{
        Connection conn = connectionPool.getConnection();
        try{
            int userId = Integer.parseInt(parseClaimsToken(refreshToken));
            //さらに、DB上にこのtokenが存在しているかをチェック
            refreshTokenDao.checkRefreshToken(refreshToken,conn);//判定をdaoに任せてしまってるので、本当はよくない。
            return userId;
        }catch(ExpiredJwtException e){
            throw new JwtException("The refresh token has expired.",e);
        }catch(SignatureException e){
            throw new JwtException("The refresh token signature is invalid.",e);
        }catch(MalformedJwtException e){
            throw new JwtException("The refresh token is invalid.", e);
        }
    }
    @Override
    public String refreshAccessToken(String refreshToken) throws SQLException,JwtException {
        Connection conn = connectionPool.getConnection();
        //リフレッシュトークンの有効期限・改ざんをチェック
        //DBにリフレッシュトークンをキーに問い合わせ、存在すればアクセストークン発行処理
            //存在しない場合or無効化キーがついている場合は、エラーを投げる。
        try{
            int userId = Integer.parseInt(parseClaimsToken(refreshToken));
            //さらに、DB上にこのtokenが存在しているかをチェック
            refreshTokenDao.checkRefreshToken(refreshToken,conn);//判定をdaoに任せてしまってるので、本当はよくない。
                
            return createAccessToken(userId);
        }catch(ExpiredJwtException e){
            throw new JwtException("The refresh token has expired.",e);
        }catch(SignatureException e){
            throw new JwtException("The refresh token signature is invalid.",e);
        }catch(MalformedJwtException e){
            throw new JwtException("The refresh token is invalid.", e);
        }
    }

    @Override
    public void revokeRefreshToken(String refreshToken) throws SQLException {
        Connection conn = connectionPool.getConnection();
        refreshTokenDao.revokeRefreshToken(refreshToken, conn);
    }

    @Override
    public String parseClaimsToken(String jwtToken)throws ExpiredJwtException,SignatureException{
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
        Jws<Claims> jwtClaims = jwtParser.parseClaimsJws(jwtToken);
        return jwtClaims.getBody().getSubject();
    }
}

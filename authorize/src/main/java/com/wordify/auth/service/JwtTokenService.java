package com.wordify.auth.service;

import java.sql.SQLException;

import io.jsonwebtoken.MalformedJwtException;

public interface JwtTokenService {
    public String createAccessToken(int userId);
    public String createRefreshToken(int userId) throws SQLException;
    public int checkRefreshToken(String refreshToken) throws SQLException,MalformedJwtException;
    public String refreshAccessToken(String refreshToken)throws SQLException;
    public void revokeRefreshToken(String refreshToken)throws SQLException;
    public String parseClaimsToken(String jwtToken);
}

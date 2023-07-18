package com.wordify.auth.service;

import java.sql.Connection;
import java.sql.SQLException;

public interface JwtTokenService {
    public String createAccessToken(int userId);
    public String createRefreshToken(int userId,Connection conn)throws SQLException;
    public String refreshAccessToken(String refreshToken);
    public void revokeRefreshToken(int userId,Connection conn)throws SQLException;
    public String parseClaimsToken(String jwtToken);
}

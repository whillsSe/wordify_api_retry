package com.wordify.auth.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.utils.ObjectMapperSingleton;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenRefreshController extends AbstractController{
    private JwtTokenService jwtTokenService;
    public TokenRefreshController(ExecutorService executor ,JwtTokenService jwtTokenService){
        super(executor);
        this.jwtTokenService = jwtTokenService;
    }
    public void handleGetRequest(HttpServletRequest req,HttpServletResponse res)throws IOException, java.io.IOException{
        Callable task = () -> {
            Cookie[] cookies = req.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("refreshToken")){
                            String refreshToken = cookie.getValue();
                            int userId = jwtTokenService.checkRefreshToken(refreshToken);
                            String accessToken = jwtTokenService.createAccessToken(userId);
                            res.addHeader("Authorization", "Bearer " + accessToken);
                            res.setStatus(200);
                        return null;
                    }
                }
            }
            res.setStatus(401);
            throw new RuntimeException("refreshToken is not found");
        };
        super.handleAsyncRequest(task, res);
    }   
}

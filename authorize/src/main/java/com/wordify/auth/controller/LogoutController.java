package com.wordify.auth.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.wordify.auth.service.JwtTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutController extends AbstractController{
    private JwtTokenService jwtTokenService;

    public LogoutController(ExecutorService executor ,JwtTokenService jwtTokenService){
        super(executor);
        this.jwtTokenService = jwtTokenService;
    }
    public void handlePostRequest(HttpServletRequest req,HttpServletResponse res)throws IOException{
        Callable<String> task = () -> {
            Cookie[] cookies = req.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("refreshToken")){
                            String refreshToken = cookie.getValue();
                            jwtTokenService.revokeRefreshToken(refreshToken);
                            res.setStatus(200);
                        return null;
                    }
                }
            }
            res.setStatus(401);
            throw new RuntimeException("refreshToken is not found");
        };
        handleAsyncRequest(task, res);
    }
}

package com.wordify.auth.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.wordify.auth.config.JwtBuilder;
import com.wordify.auth.service.LoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginController extends AbstractController{
    private LoginService loginService;
    public LoginController(ExecutorService executor ,LoginService loginService){
        super(executor);
        this.loginService = loginService;
    }
    public void handlePostRequest(HttpServletRequest req,HttpServletResponse res)throws IOException{
        Callable<String> task = () -> {
            //String requestBody = ControllerUtils.readRequestBody(req);
            String userNameString = (String) req.getAttribute("username");
            String password = (String) req.getAttribute("password");
            int userId = loginService.login(userNameString,password);
            
            String accessToken = JwtBuilder.createAccessToken(userId);
            res.addHeader("Authorization", "Bearer " + accessToken);
            
            String refreshToken = JwtBuilder.createRefreshToken(userId);
            //ここでDBにリフレッシュトークンを保存


            Cookie authCookie = new Cookie("refreshToken", refreshToken);
            authCookie.setHttpOnly(true);
            authCookie.setSecure(true); // HTTPSを使用する場合、Secure属性を設定する
            authCookie.setPath("/"); // 必要に応じてパスを指定する
            res.addCookie(authCookie);
            return "loggined!";
        };
        super.handleAsyncRequest(task, res);
    }
}

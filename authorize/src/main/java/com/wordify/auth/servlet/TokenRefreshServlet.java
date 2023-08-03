package com.wordify.auth.servlet;

import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;

import com.wordify.auth.config.ConnectionPool;
import com.wordify.auth.controller.InitializeController;
import com.wordify.auth.controller.TokenRefreshController;
import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.JwtTokenServiceImpl;
import com.wordify.auth.service.ProfileService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/token/refresh")
public class TokenRefreshServlet extends HttpServlet{
    private TokenRefreshController tokenRefreshController;
    @Override
    public void init(){
        try{
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            JwtTokenService jwtTokenService = new JwtTokenServiceImpl(ConnectionPool.getInstance());
            this.tokenRefreshController = new TokenRefreshController(executor,jwtTokenService);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, java.io.IOException{
        tokenRefreshController.handleGetRequest(req, res);
    }
}


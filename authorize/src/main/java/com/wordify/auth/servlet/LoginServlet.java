package com.wordify.auth.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;

import com.wordify.auth.config.ConnectionPool;
import com.wordify.auth.controller.LoginController;
import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.JwtTokenServiceImpl;
import com.wordify.auth.service.LoginService;
import com.wordify.auth.service.LoginServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private LoginController loginController;
    @Override
    public void init(){
        try{
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            LoginService loginService = new LoginServiceImpl(ConnectionPool.getInstance());
            JwtTokenService jwtTokenService = new JwtTokenServiceImpl(ConnectionPool.getInstance());
            loginController = new LoginController(executor,loginService,jwtTokenService);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
        loginController.handlePostRequest(req, res);
    }
}

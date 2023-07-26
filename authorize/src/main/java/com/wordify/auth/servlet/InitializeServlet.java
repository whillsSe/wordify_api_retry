package com.wordify.auth.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;

import com.wordify.auth.config.ConnectionPool;
import com.wordify.auth.controller.InitializeController;
import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.JwtTokenServiceImpl;
import com.wordify.auth.service.ProfileService;
import com.wordify.auth.service.ProfileServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/token/initialize")
public class InitializeServlet extends HttpServlet{
    private InitializeController initializeController;
    @Override
    public void init(){
        try{
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            JwtTokenService jwtTokenService = new JwtTokenServiceImpl(ConnectionPool.getInstance());
            ProfileService profileService = new ProfileServiceImpl(ConnectionPool.getInstance());
            this.initializeController = new InitializeController(executor,jwtTokenService,profileService);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException{
        initializeController.handleGetRequest(req, res);
    }
}

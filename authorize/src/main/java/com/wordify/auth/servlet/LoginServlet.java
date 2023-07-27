package com.wordify.auth.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.wordify.auth.config.ConnectionPool;
import com.wordify.auth.controller.LoginController;
import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.JwtTokenServiceImpl;
import com.wordify.auth.service.LoginService;
import com.wordify.auth.service.LoginServiceImpl;
import com.wordify.auth.service.ProfileService;
import com.wordify.auth.service.ProfileServiceImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private LoginController loginController;
    @Override
    public void init(){
        try{
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            LoginService loginService = new LoginServiceImpl(ConnectionPool.getInstance());
            JwtTokenService jwtTokenService = new JwtTokenServiceImpl(ConnectionPool.getInstance());
            ProfileService profileService = new ProfileServiceImpl(ConnectionPool.getInstance());
            loginController = new LoginController(executor,loginService,jwtTokenService,profileService);
            logger.info("LoginServlet#init");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
        logger.info("LoginServlet#doPost");
        loginController.handlePostRequest(req, res);
    }
}

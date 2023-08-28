package com.wordify.auth.servlet;

import java.util.concurrent.ExecutorService;

import javax.naming.NamingException;

import com.wordify.auth.config.ConnectionPool;

import com.wordify.auth.controller.LogoutController;
import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.JwtTokenServiceImpl;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
    private LogoutController logoutController;
    public void init(){
        try{
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            JwtTokenService jwtTokenService = new JwtTokenServiceImpl(ConnectionPool.getInstance());
            logoutController = new LogoutController(executor,jwtTokenService);
            //logger.log(Level.INFO,"LoginServlet#init");
        } catch (NamingException e) {
            e.printStackTrace();
            //logger.log(Level.FINER,"someError is occured in LoginServlet#init", e);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException, java.io.IOException{
        logoutController.handlePostRequest(req, res);
    }
}

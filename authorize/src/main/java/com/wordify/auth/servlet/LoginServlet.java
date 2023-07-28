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
import com.wordify.auth.service.ProfileService;
import com.wordify.auth.service.ProfileServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
    //private static Logger logger = Logger.getLogger(LoginServlet.class.getName());
    //private static ConsoleHandler handler = new ConsoleHandler();
    //private static Path patern = Path.of("R:","java-work","log","log.txt");
    private LoginController loginController;
    @Override
    public void init(){
        try{
            ExecutorService executor = (ExecutorService) getServletContext().getAttribute("executor");
            LoginService loginService = new LoginServiceImpl(ConnectionPool.getInstance());
            JwtTokenService jwtTokenService = new JwtTokenServiceImpl(ConnectionPool.getInstance());
            ProfileService profileService = new ProfileServiceImpl(ConnectionPool.getInstance());
            loginController = new LoginController(executor,loginService,jwtTokenService,profileService);
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
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException{
        //logger.log(Level.INFO,"LoginServlet#doPost");
        loginController.handlePostRequest(req, res);
    }
}

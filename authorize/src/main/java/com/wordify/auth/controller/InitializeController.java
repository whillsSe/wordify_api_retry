package com.wordify.auth.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.auth.dto.InitializeInfo;
import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.ProfileService;
import com.wordify.auth.utils.ObjectMapperSingleton;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InitializeController extends AbstractController{
    private JwtTokenService jwtTokenService;
    private ProfileService profileService;
    public InitializeController(ExecutorService executor ,JwtTokenService jwtTokenService,ProfileService profileService){
        super(executor);
        this.profileService = profileService;
        this.jwtTokenService = jwtTokenService;
    }
    public void handleGetRequest(HttpServletRequest req,HttpServletResponse res)throws IOException, java.io.IOException{
        Callable<String> task = () -> {
            Logger logger = Logger.getLogger(InitializeController.class.getName());
            logger.info(jwtTokenService.createExpiredAccessToken(1));
            Cookie[] cookies = req.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("refreshToken")){
                            String refreshToken = cookie.getValue();
                            int userId = jwtTokenService.checkRefreshToken(refreshToken);
                            String accessToken = jwtTokenService.createAccessToken(userId);
                            InitializeInfo initializeInfo = profileService.getInitializeInfo(userId);
                            res.addHeader("Authorization", "Bearer " + accessToken);
                            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
                        return mapper.writeValueAsString(initializeInfo);
                    }
                }
            }
            res.setStatus(401);
            throw new RuntimeException("refreshToken is not found");
        };
        super.handleAsyncRequest(task, res);
    }   
}
package com.wordify.auth.controller;

import java.io.IOException;
import java.util.ResourceBundle.Control;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.auth.controller.controllerUtils.ControllerUtils;
import com.wordify.auth.dto.AuthorizePrameter;
import com.wordify.auth.dto.InitializeInfo;
import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.LoginService;
import com.wordify.auth.service.ProfileService;
import com.wordify.auth.utils.ObjectMapperSingleton;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginController extends AbstractController{
    private LoginService loginService;
    private JwtTokenService jwtTokenService;
    private ProfileService profileService;

    public LoginController(ExecutorService executor ,LoginService loginService,JwtTokenService jwtTokenService,ProfileService profileService){
        super(executor);
        this.loginService = loginService;
        this.jwtTokenService = jwtTokenService;
        this.profileService = profileService;
    }
    public void handlePostRequest(HttpServletRequest req,HttpServletResponse res)throws IOException{
        Callable<String> task = () -> {
            //String requestBody = ControllerUtils.readRequestBody(req);
            String requestBody = ControllerUtils.readRequestBody(req);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            AuthorizePrameter requestedValue = mapper.readValue(requestBody,AuthorizePrameter.class);
            String userNameString = requestedValue.getUsername();
            Logger logger = Logger.getLogger(LoginController.class.getName());
            logger.info(userNameString);
            String password = requestedValue.getPassword();
            int userId = loginService.login(userNameString,password);
                //ユーザー情報の取得を専用Serviceから行う
            String refreshToken = jwtTokenService.createRefreshToken(userId);
            String accessToken = jwtTokenService.createAccessToken(userId);
            res.addHeader("Authorization", "Bearer " + accessToken);

            Cookie authCookie = new Cookie("refreshToken", refreshToken);
            authCookie.setHttpOnly(true);
            //authCookie.setSecure(true); // HTTPSを使用する場合、Secure属性を設定する
            authCookie.setDomain("localhost");
            authCookie.setPath("/"); // 必要に応じてパスを指定する
            res.addCookie(authCookie);

            InitializeInfo initializeInfo = profileService.getInitializeInfo(userId);
            return mapper.writeValueAsString(initializeInfo);
        };
        super.handleAsyncRequest(task, res);
    }
}

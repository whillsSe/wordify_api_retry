package com.wordify.auth.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import com.wordify.auth.config.ConnectionPool;
import com.wordify.auth.dao.LoginDao;
import com.wordify.auth.dto.AuthorizeInfo;
import com.wordify.auth.utils.Sha256Hash;

public class LoginServiceImpl implements LoginService{
    private ConnectionPool connectionPool;
    private LoginDao loginDao;
    public LoginServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        this.loginDao = new LoginDao();
    }
    public int login(String userName,String password) throws SQLException,LoginException{
        Connection conn = connectionPool.getConnection();
        AuthorizeInfo authorizeInfo = loginDao.login(userName, conn);
        String hashedPass = authorizeInfo.getHashedPassword();
        Logger logger = Logger.getLogger(LoginServiceImpl.class.getName());
        logger.info("hashedPass:"+hashedPass);
        logger.info("password:"+Sha256Hash.getSha256Hash(password));
        if(!hashedPass.equals(Sha256Hash.getSha256Hash(password))){
            throw new LoginException("Password unmatched!");//仮設
        };
        connectionPool.releaseConnection(conn);
        return authorizeInfo.getId();
    }
}

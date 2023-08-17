package com.wordify.auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.auth.config.ConnectionPool;
import com.wordify.auth.dao.ProfileDao;
import com.wordify.auth.dto.InitializeInfo;

public class ProfileServiceImpl implements ProfileService{
    private ConnectionPool connectionPool;
    private ProfileDao profileDao;
    public ProfileServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        this.profileDao = new ProfileDao();
    }
    @Override
    public InitializeInfo getInitializeInfo(int userId) throws Exception{
        Connection conn = connectionPool.getConnection();
        InitializeInfo initializeInfo = profileDao.getInfo(userId, conn);
        connectionPool.releaseConnection(conn);
        return initializeInfo;
    }
}

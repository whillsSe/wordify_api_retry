package com.wordify.auth.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.cj.x.protobuf.MysqlxNotice.Warning.Level;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionPool {
    private static ConnectionPool instance;
    private DataSource dataSource;

    private ConnectionPool() throws NamingException {
        Context initialContext = new InitialContext();
        dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/wordify");
    }

    public static synchronized ConnectionPool getInstance() throws NamingException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void releaseConnection(Connection connection) throws SQLException {
        connection.close();
    }
}


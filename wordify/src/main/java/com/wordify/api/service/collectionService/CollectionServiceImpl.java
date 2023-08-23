package com.wordify.api.service.collectionService;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dao.collection.CollectionDao;
import com.wordify.api.dao.collection.CollectionDaoImpl;
import com.wordify.api.dto.payloads.CollectionTargetPayload;

public class CollectionServiceImpl implements CollectionService{
    private ConnectionPool connectionPool;
    private CollectionDao collectionDao;
    public CollectionServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        this.collectionDao = new CollectionDaoImpl();
    }
    public void addCollection(CollectionTargetPayload query)throws SQLException{
        Connection conn = connectionPool.getConnection();
        collectionDao.addDefinition(query,conn);//エラーはcontrollerまで直通
        conn.close();
    }
    public void removeCollection(CollectionTargetPayload query)throws SQLException{
        Connection conn = connectionPool.getConnection();
        collectionDao.removeDefinition(query,conn);
        conn.close();
    }
}

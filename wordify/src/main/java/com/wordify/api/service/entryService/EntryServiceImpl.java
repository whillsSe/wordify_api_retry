package com.wordify.api.service.entryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dao.entry.EntryDao;
import com.wordify.api.dao.entry.EntryDaoImpl;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.EntryQuery;

public class EntryServiceImpl implements EntryService{
    private ConnectionPool connectionPool;
    private EntryDao entryDao;
    public EntryServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        this.entryDao = new EntryDaoImpl();//仮設。本来は上から注入。
    }
    @Override
    public List<EntryDto> getEntries(EntryQuery query){
        List<EntryDto> list = new ArrayList<EntryDto>();
        try (Connection conn = connectionPool.getConnection()) {
            //conn.setAutoCommit(false);//今回は不要
            list = entryDao.getEntry(query, conn);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
}

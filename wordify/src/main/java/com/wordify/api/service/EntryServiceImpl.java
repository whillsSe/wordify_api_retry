package com.wordify.api.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dao.EntryDao;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.EntryQuery;

public class EntryServiceImpl implements EntryService{
    private ConnectionPool connectionPool;
    private EntryDao entryDao;
    public EntryServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    @Override
    public List<EntryDto> getEntries(EntryQuery query){
        List<EntryDto> list = new ArrayList<EntryDto>();
        try (Connection conn = connectionPool.getConnection()) {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
}

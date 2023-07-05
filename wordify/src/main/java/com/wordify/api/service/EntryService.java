package com.wordify.api.service;

import java.sql.Connection;
import java.util.List;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dto.EntryDto;

public class EntryService {
    //取得メソッドList<EntryDto>
    private ConnectionPool connectionPool;
    public EntryService(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
    }
    public List<EntryDto> getEntries(){
        
    }
}

package com.wordify.api.dao.word;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.api.dto.BaseEntityDto;

public interface WordDao {
    int retrieveOrCreate(String wordString,Connection conn) throws SQLException;
    BaseEntityDto findById(int i);
}

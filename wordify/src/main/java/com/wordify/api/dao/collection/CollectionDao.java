package com.wordify.api.dao.collection;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.api.dto.params.CollectionQuery;

public interface CollectionDao {

    void addDefinition(CollectionQuery query, Connection conn)throws SQLException;
    
}

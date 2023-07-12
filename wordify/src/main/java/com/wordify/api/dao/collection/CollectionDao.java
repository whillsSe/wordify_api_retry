package com.wordify.api.dao.collection;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.api.dto.params.CollectionQuery;

public interface CollectionDao {

    public void addDefinition(CollectionQuery query, Connection conn)throws SQLException;
    public void removeDefinition(CollectionQuery query,Connection conn) throws SQLException;
}

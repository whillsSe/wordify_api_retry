package com.wordify.api.dao.collection;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.api.dto.payloads.CollectionTargetPayload;

public interface CollectionDao {

    public void addDefinition(CollectionTargetPayload query, Connection conn)throws SQLException;
    public void removeDefinition(CollectionTargetPayload query,Connection conn) throws SQLException;
}

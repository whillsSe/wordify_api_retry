package com.wordify.api.dao.collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wordify.api.dto.payloads.CollectionTargetPayload;

public class CollectionDaoImpl implements CollectionDao{

    @Override
    public void addDefinition(CollectionTargetPayload query, Connection conn) throws SQLException {
        StringBuilder builder = new StringBuilder("INSERT INTO collections(definition_id,collector_id) VALUES(?,?)");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(1, query.getDefinitionId());
            pstmt.setInt(2, query.getUserId());
            pstmt.executeUpdate();
        }
        
    }
    public void removeDefinition(CollectionTargetPayload query,Connection conn) throws SQLException{
        StringBuilder builder = new StringBuilder("DELETE FROM collections WHERE definition_id = ? AND collector_id = ?");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(1,query.getDefinitionId());
            pstmt.setInt(2, query.getUserId());
            pstmt.executeQuery();
        }
    }
    
}

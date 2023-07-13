package com.wordify.api.dao.collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wordify.api.dto.payloads.CollectionTargetPayload;

public class CollectionDaoImpl implements CollectionDao{

    @Override
    public void addDefinition(CollectionTargetPayload query, Connection conn) throws SQLException {
        StringBuilder builder = new StringBuilder("INSERT INTO collection(definition_id,user_id) VALUES(?,?)");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(0, query.getDefinitionId());
            pstmt.setInt(1, query.getUserId());
            pstmt.executeUpdate();
        }
        
    }
    public void removeDefinition(CollectionTargetPayload query,Connection conn) throws SQLException{
        StringBuilder builder = new StringBuilder("DELETE FROM collection WHERE definition_id = ? AND user_id = ?");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(0,query.getDefinitionId());
            pstmt.setInt(1, query.getUserId());
            pstmt.executeQuery();
        }
    }
    
}

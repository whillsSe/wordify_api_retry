package com.wordify.api.dao.collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wordify.api.dto.params.CollectionQuery;

public class CollectionDaoImpl implements CollectionDao{

    @Override
    public void addDefinition(CollectionQuery query, Connection conn) throws SQLException {
        StringBuilder builder = new StringBuilder("INSERT INTO collection(definition_id,user_id) VALUES(?,?)");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(0, query.getDefinitionId());
            pstmt.setInt(1, query.getUserId());
            pstmt.executeUpdate();
        }
        
    }
    
}

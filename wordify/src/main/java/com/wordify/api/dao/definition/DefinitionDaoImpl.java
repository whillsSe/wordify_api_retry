package com.wordify.api.dao.definition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DefinitionDaoImpl implements DefinitionDao{
    public int registerDefinition(int userId,int wordId,int phoneticId,Connection conn) throws SQLException{
        StringBuilder builder = new StringBuilder("INSERT INTO definitions(author_id,word_id,phonetic_id) VALUES(?,?,?);");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString(),Statement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1, userId);
            pstmt.setInt(2, wordId);
            pstmt.setInt(3,phoneticId);
            pstmt.executeUpdate();
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    return id;
                }else{
                    throw new SQLException("Creating definition failed, no ID obtained.");
                }
        }
    }
}}

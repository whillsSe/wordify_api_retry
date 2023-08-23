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
    }
    @Override
    public void updateDefinition(int userId,int definitionId,int wordId,int phoneticId,Connection conn) throws SQLException{
        StringBuilder builder = new StringBuilder("UPDATE definitions SET word_id = ?,phonetic_id = ? WHERE id = ? AND author_id = ?;");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(1, wordId);
            pstmt.setInt(2, phoneticId);
            pstmt.setInt(3,definitionId);
            pstmt.setInt(4, userId);
            pstmt.executeUpdate();
        }
    }
    @Override 
    public void deleteDefinition(int userId,int definitionId,Connection conn) throws SQLException{
        StringBuilder builder = new StringBuilder("DELETE FROM definitions WHERE id = ? AND author_id = ?");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            pstmt.setInt(1,definitionId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        }      
    } 
}

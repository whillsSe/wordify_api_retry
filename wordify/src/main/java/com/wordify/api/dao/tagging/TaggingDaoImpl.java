package com.wordify.api.dao.tagging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaggingDaoImpl implements TaggingDao{
    public void addTagging(int definitionId,int[] tagIds,Connection conn)throws SQLException{
        StringBuilder builder = new StringBuilder("INSERT INTO tagging (definition_id,tag_id) VALUES");
        for(int i=0;i<tagIds.length;i++){
            builder.append("(?,?),");
        }
        builder.deleteCharAt(builder.length() - 1);
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            for(int i=0;i<tagIds.length;i++){
                int tagId = tagIds[i];
                pstmt.setInt(i*2+1,definitionId);
                pstmt.setInt(i*2+2, tagId);
            }
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("Creating tagging failed, no ID obtained.");
        }
    }
}

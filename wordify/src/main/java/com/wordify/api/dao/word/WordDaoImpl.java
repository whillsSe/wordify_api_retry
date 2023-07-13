package com.wordify.api.dao.word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dto.BaseEntityDto;

public class WordDaoImpl implements WordDao{

  @Override
  public BaseEntityDto findById(int i) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int retrieveOrCreate(String wordString,Connection conn) throws SQLException{
    StringBuilder builder = DaoUtils.createStringBuilder("word");
    try(PreparedStatement pstmt = conn.prepareStatement(builder.toString(),Statement.RETURN_GENERATED_KEYS)){
      pstmt.setString(1, wordString);
      int affectedRows = pstmt.executeUpdate();
      if(affectedRows == 0){
        throw new SQLException("Creating word failed, no rows affected.");
      }

      try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
        if(generatedKeys.next()){
          int id = generatedKeys.getInt(1);
          return id;
        }else{
          throw new SQLException("Creating word failed, no ID obtained.");
        }
      }
    }
  }
    
}

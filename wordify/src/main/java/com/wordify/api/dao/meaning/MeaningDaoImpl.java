package com.wordify.api.dao.meaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import com.wordify.api.dto.MeaningDto;
import com.wordify.api.utils.SQLUtils;

public class MeaningDaoImpl implements MeaningDao{
  private MeaningMapper mapper;
  
    public MeaningDaoImpl() {
      this.mapper = new MeaningMapper();
  }

    public SimpleEntry<Integer,MeaningDto> getMeaningsByDefinitionIds(List<String> list,Connection conn){
      StringBuilder builder = new StringBuilder("SELECT definition_id,id,meaning FROM meanings WHERE definition_id IN (");
      SQLUtils.prepareQueryForElements(list.size(), builder);
      builder.append(")");
      try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
        ResultSet resultSet = pstmt.executeQuery();
        return mapper.mapToDtoWithKey(resultSet);
      }catch(SQLException e){
        throw new Error("SQLException has happened!");
      }
    } 
}

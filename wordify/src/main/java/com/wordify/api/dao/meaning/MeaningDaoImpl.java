package com.wordify.api.dao.meaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dao.GenericMapper;
import com.wordify.api.dto.MeaningDto;
import com.wordify.api.dto.payloads.params.ICustomParam;
import com.wordify.api.utils.SQLUtils;

public class MeaningDaoImpl implements MeaningDao{
  private GenericMapper<MeaningDto> mapper;
  
    public MeaningDaoImpl() {
      this.mapper = new GenericMapper<MeaningDto>(new MeaningMapper());
  }

    public Map<Integer,MeaningDto> getMapByDefinitionIds(List<Integer> list,Connection conn){
      StringBuilder builder = new StringBuilder("SELECT definition_id,id,meaning FROM meaning WHERE definition_id IN (");
      SQLUtils.prepareQueryForElements(list.size(), builder);
      builder.append(")");

      List<ICustomParam> params = DaoUtils.parseIntegerToICustomParams(list);
      
      try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
        DaoUtils.setParameters(pstmt, params, 0);
        ResultSet resultSet = pstmt.executeQuery();
        return mapper.mapToMap(resultSet);
      }catch(SQLException e){
        e.printStackTrace();
        throw new Error("SQLException has happened!");
      }
    }

    @Override
    public int registerMeaning(int definitionId,String meaninString,Connection conn)throws SQLException{
      StringBuilder builder = new StringBuilder("INSERT INTO meaning(definition_id,meaning) VALUES(?,?)");
      try(PreparedStatement pstmt = conn.prepareStatement(builder.toString(),Statement.RETURN_GENERATED_KEYS)){
        pstmt.setInt(1,definitionId);
        pstmt.setString(2, meaninString);
        pstmt.executeUpdate();
        try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
          if(generatedKeys.next()){
            int id = generatedKeys.getInt(1);
            return id;
          }else{
            throw new SQLException("Creating meaning failed, no ID obtained.");
          }
        }
      }
    }
    @Override
    public void deleteMeaning(int definitionId,Connection conn) throws SQLException{
      String sql = "DELETE FROM meaning WHERE definition_id = ?";
      try(PreparedStatement pstmt = conn.prepareStatement(sql)){
        pstmt.setInt(1,definitionId);
        pstmt.executeUpdate();
      }
    }
}

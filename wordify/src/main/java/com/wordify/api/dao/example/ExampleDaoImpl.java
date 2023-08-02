package com.wordify.api.dao.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dao.GenericMapper;
import com.wordify.api.dto.ExampleDto;
import com.wordify.api.dto.payloads.params.ICustomParam;
import com.wordify.api.utils.SQLUtils;

public class ExampleDaoImpl implements ExampleDao{
    private GenericMapper<ExampleDto> mapper;
    public ExampleDaoImpl(){
        this.mapper = new GenericMapper<ExampleDto>(new ExampleMapper());
    }
    public Map<Integer,List<ExampleDto>> getMapWithListByDefinitionIds(List<Integer> list,Connection conn){
        Logger logger = Logger.getLogger(ExampleDaoImpl.class.getName());

        StringBuilder builder = new StringBuilder("SELECT definition_id,id,example FROM examples WHERE definition_id IN (");
        SQLUtils.prepareQueryForElements(list.size(), builder);
        builder.append(")");
        List<ICustomParam> params = DaoUtils.parseIntegerToICustomParams(list);
        logger.info(builder.toString());
      try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
        DaoUtils.setParameters(pstmt, params, 0);
        ResultSet resultSet = pstmt.executeQuery();
        return mapper.mapToMapWithList(resultSet);
      }catch(SQLException e){
        e.printStackTrace();
        throw new Error("SQLException has happened!");
      }
    }
    @Override
    public int[] registerExample(int definitionId,String[] exampleStrings,Connection conn)throws SQLException{
      int[] exampleIds = new int[exampleStrings.length];
      StringBuilder builder = new StringBuilder("INSERT INTO examples (definition_id,example) VALUES");
      for(int i=0;i<exampleStrings.length;i++){
          builder.append("(?,?),");
      }
      builder.deleteCharAt(builder.length() - 1);
      try(PreparedStatement pstmt = conn.prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS)){
        for (int i=0;i<exampleStrings.length;i++) {
            String exampleString = exampleStrings[i];
            pstmt.setInt(i*2+1, definitionId);
            pstmt.setString(i*2+2, exampleString);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating examples failed, no rows affected.");
            }
    
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    exampleIds[i] = id;
                } else {
                    throw new SQLException("Creating tag failed, no ID obtained.");
                }
            }
            pstmt.clearParameters();
        }
      return exampleIds;
    }
    }
}

package com.wordify.api.dao.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dao.GenericMapper;
import com.wordify.api.dto.ExampleDto;
import com.wordify.api.dto.params.ICustomParam;
import com.wordify.api.utils.SQLUtils;

public class ExampleDaoImpl implements ExampleDao{
    private GenericMapper<ExampleDto> mapper;
    public ExampleDaoImpl(){
        this.mapper = new GenericMapper<ExampleDto>(new ExampleMapper());
    }
    public Map<Integer,List<ExampleDto>> getMapWithListByDefinitionIds(List<Integer> list,Connection conn){
        StringBuilder builder = new StringBuilder("SELECT definition_id,id,example FROM examples WHERE definition_id IN (");
        SQLUtils.prepareQueryForElements(list.size(), builder);
        builder.append(")");
        List<ICustomParam> params = DaoUtils.parseIntegerToICustomParams(list);
      try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
        DaoUtils.setParameters(pstmt, params, 0);
        ResultSet resultSet = pstmt.executeQuery();
        return mapper.mapToMapWithList(resultSet);
      }catch(SQLException e){
        throw new Error("SQLException has happened!");
      }
    }
}

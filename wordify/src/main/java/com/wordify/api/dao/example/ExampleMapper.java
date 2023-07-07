package com.wordify.api.dao.example;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;

import com.wordify.api.dao.IDtoAndKeyMapper;
import com.wordify.api.dto.ExampleDto;

public class ExampleMapper implements IDtoAndKeyMapper<ExampleDto>{
  @Override
  public SimpleEntry<Integer,ExampleDto> mapToDtoWithKey(ResultSet resultSet) throws SQLException {
        int definitionId = resultSet.getInt("definition_id");
        int id = resultSet.getInt("id");
        String example = resultSet.getString("example");
        ExampleDto dto = new ExampleDto(id, example);
      return new SimpleEntry<Integer,ExampleDto>(definitionId, dto);
  }
}
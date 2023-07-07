package com.wordify.api.dao.meaning;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;

import com.wordify.api.dao.IDtoAndKeyMapper;
import com.wordify.api.dto.MeaningDto;

public class MeaningMapper implements IDtoAndKeyMapper<MeaningDto>{

  @Override
  public SimpleEntry<Integer,MeaningDto> mapToDtoWithKey(ResultSet resultSet) throws SQLException {
        int definitionId = resultSet.getInt("definition_id");
        int id = resultSet.getInt("id");
        String meaning = resultSet.getString("meaning");
        MeaningDto dto = new MeaningDto(id, meaning);
        return new SimpleEntry<Integer,MeaningDto>(definitionId, dto);
  }
}

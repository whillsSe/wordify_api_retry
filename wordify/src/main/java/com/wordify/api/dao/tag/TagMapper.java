package com.wordify.api.dao.tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;

import com.wordify.api.dao.IDtoAndKeyMapper;
import com.wordify.api.dto.TagDto;

public class TagMapper implements IDtoAndKeyMapper<TagDto>{

  @Override
  public SimpleEntry<Integer,TagDto> mapToDtoWithKey(ResultSet resultSet) throws SQLException {
        int definitionId = resultSet.getInt("definition_id");
        int id = resultSet.getInt("id");
        String meaning = resultSet.getString("tag");
        TagDto dto = new TagDto(id, meaning);
        return new SimpleEntry<Integer,TagDto>(definitionId, dto);
  }
}
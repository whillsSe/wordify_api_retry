package com.wordify.api.dao.entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordify.api.dto.BaseEntityDto;
import com.wordify.api.dto.EntryDto;

public class EntryMapper {
    public List<EntryDto> mapToList(ResultSet resultSet) throws SQLException{
        List<EntryDto> list = new ArrayList<EntryDto>();
        while(resultSet.next()){
            BaseEntityDto word = new BaseEntityDto(resultSet.getInt("wordId"),resultSet.getString("word"));
            BaseEntityDto phonetic = new BaseEntityDto(resultSet.getInt("phoneticId"),resultSet.getString("phonetic"));
            EntryDto dto = new EntryDto(word, phonetic);
            list.add(dto);
        }
        return list;
    }
}

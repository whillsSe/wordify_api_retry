package com.wordify.api.dao.context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordify.api.dto.BaseEntityDto;
import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.EntryDto;

public class ContextMapper {
    public ContextDto map(ResultSet resultSet) throws SQLException{
        List<DefinitionDto> definitions = new ArrayList<>();
        EntryDto prevEntry = null;
        EntryDto nextEntry = null;

        while (resultSet.next()) {
            int prevWordId = resultSet.getInt("prev_word_id");
            String prevWord = resultSet.getString("prev_word");
            int prevPhoneticId = resultSet.getInt("prev_phonetic_id");
            String prevPhonetic = resultSet.getString("prev_phonetic");

            int nextWordId = resultSet.getInt("next_word_id");
            String nextWord = resultSet.getString("next_word");
            int nextPhoneticId = resultSet.getInt("next_phonetic_id");
            String nextPhonetic = resultSet.getString("next_phonetic");

            if (prevEntry == null) {
                BaseEntityDto prevWordDto = new BaseEntityDto(prevWordId, prevWord);
                BaseEntityDto prevPhoneticDto = new BaseEntityDto(prevPhoneticId, prevPhonetic);
                prevEntry = new EntryDto(prevWordDto, prevPhoneticDto);
            }

            BaseEntityDto nextWordDto = new BaseEntityDto(nextWordId, nextWord);
            BaseEntityDto nextPhoneticDto = new BaseEntityDto(nextPhoneticId, nextPhonetic);
            nextEntry = new EntryDto(nextWordDto, nextPhoneticDto);
            //DefinitionDtoを作って、user_idと一緒にsetしてやらんばいけん。
            DefinitionDto definition = new DefinitionDto();
            definition.setId(resultSet.getInt("definition_id"));
            definition.setAuthorId(resultSet.getInt("user_id"));
            definitions.add(definition);
        }
        return new ContextDto(prevEntry,nextEntry,definitions);
    }
}

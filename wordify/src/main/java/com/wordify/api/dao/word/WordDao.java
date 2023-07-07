package com.wordify.api.dao.word;

import com.wordify.api.dto.BaseEntityDto;

public interface WordDao {
    void retrieveOrCreate(String word);
    BaseEntityDto findById(int i);
}

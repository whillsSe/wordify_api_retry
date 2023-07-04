package com.wordify.api.dao;

import com.wordify.api.dto.BaseEntityDto;

public interface WordDao {
    void retrieveOrCreate(String word);
    BaseEntityDto findById(int i);
}

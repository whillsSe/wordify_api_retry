package com.wordify.api.service;

import java.util.List;

import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.EntryQuery;

public interface EntryService {
    //取得メソッドList<EntryDto>
    public List<EntryDto> getEntries(EntryQuery query);
}

package com.wordify.api.service.entryService;

import java.sql.SQLException;
import java.util.List;

import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.ContextQuery;
import com.wordify.api.dto.params.EntryQuery;

public interface EntryService {
    //取得メソッドList<EntryDto>
    public List<EntryDto> getEntries(EntryQuery query) throws SQLException;
    public ContextDto getContext(ContextQuery query) throws SQLException;
}

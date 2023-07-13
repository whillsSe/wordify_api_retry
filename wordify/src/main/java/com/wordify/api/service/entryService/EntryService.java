package com.wordify.api.service.entryService;

import java.sql.SQLException;
import java.util.List;

import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.payloads.ContextRetrievalPayload;
import com.wordify.api.dto.payloads.EntrySearchPayload;

public interface EntryService {
    //取得メソッドList<EntryDto>
    public List<EntryDto> getEntries(EntrySearchPayload query) throws SQLException;
    public ContextDto getContext(ContextRetrievalPayload query) throws SQLException;
}

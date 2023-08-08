package com.wordify.api.service.definitionService;

import java.sql.SQLException;

import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.payloads.EntryRegistrationPayload;
import com.wordify.api.dto.payloads.EntryUpdatePayload;

public interface DefinitionService {
    public DefinitionDto registerDefinition(EntryRegistrationPayload requestedValue)throws SQLException;
    public DefinitionDto updateDefinition(EntryUpdatePayload requestedValue)throws SQLException;
}

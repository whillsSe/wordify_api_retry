package com.wordify.api.service.definitionService;

import java.sql.SQLException;

import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.payloads.EntryRegistrationPayload;

public interface DefinitionService {
    public DefinitionDto registerDefinition(EntryRegistrationPayload requestedValue)throws SQLException;
}

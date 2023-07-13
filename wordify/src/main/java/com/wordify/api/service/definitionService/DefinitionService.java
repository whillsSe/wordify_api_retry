package com.wordify.api.service.definitionService;

import java.sql.SQLException;

import com.wordify.api.dto.DefinitionDto;

public interface DefinitionService {
    public DefinitionDto registerDefinition(DefinitionDto requestedValue)throws SQLException;
}

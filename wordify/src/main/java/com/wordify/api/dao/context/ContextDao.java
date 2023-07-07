package com.wordify.api.dao.context;

import java.sql.Connection;

import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.params.ContextQuery;

public interface ContextDao {
    public ContextDto getContext(ContextQuery query,Connection conn);
}

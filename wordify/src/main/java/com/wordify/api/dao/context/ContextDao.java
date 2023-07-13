package com.wordify.api.dao.context;

import java.sql.Connection;

import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.payloads.ContextRetrievalPayload;

public interface ContextDao {
    public ContextDto getContext(ContextRetrievalPayload query,Connection conn);
}

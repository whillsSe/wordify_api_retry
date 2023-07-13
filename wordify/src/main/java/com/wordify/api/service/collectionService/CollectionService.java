package com.wordify.api.service.collectionService;

import java.sql.SQLException;

import com.wordify.api.dto.payloads.CollectionTargetPayload;

public interface CollectionService {
    public void addCollection(CollectionTargetPayload query)throws SQLException;
    public void removeCollection(CollectionTargetPayload query)throws SQLException;
}

package com.wordify.api.service.collectionService;

import java.sql.SQLException;

import com.wordify.api.dto.params.CollectionQuery;

public interface CollectionService {
    public void addCollection(CollectionQuery query)throws SQLException;
}

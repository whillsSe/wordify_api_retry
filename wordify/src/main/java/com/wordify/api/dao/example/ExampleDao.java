package com.wordify.api.dao.example;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.wordify.api.dto.ExampleDto;

public interface ExampleDao {
    public Map<Integer,List<ExampleDto>> getMapWithListByDefinitionIds(List<Integer> list,Connection conn);
}

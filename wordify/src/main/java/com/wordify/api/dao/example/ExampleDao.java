package com.wordify.api.dao.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wordify.api.dto.ExampleDto;

public interface ExampleDao {
    public Map<Integer,List<ExampleDto>> getMapWithListByDefinitionIds(List<Integer> list,Connection conn);
    public int[] registerExample(int definitionId,String[] exampleStrings,Connection conn)throws SQLException;
}

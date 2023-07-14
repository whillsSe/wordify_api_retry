package com.wordify.api.dao.meaning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wordify.api.dto.MeaningDto;

public interface MeaningDao {
    public Map<Integer,MeaningDto> getMapByDefinitionIds(List<Integer> list,Connection conn);
    public int registerMeaning(int definitionId,String meaninString,Connection conn)throws SQLException;
}

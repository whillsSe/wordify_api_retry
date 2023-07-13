package com.wordify.api.dao.meaning;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.wordify.api.dto.MeaningDto;
import com.wordify.api.dto.payloads.params.ICustomParam;

public interface MeaningDao {
    public Map<Integer,MeaningDto> getMapByDefinitionIds(List<Integer> list,Connection conn);
}

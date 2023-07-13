package com.wordify.api.dao.tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wordify.api.dto.BaseEntityDto;
import com.wordify.api.dto.TagDto;

public interface TagDao {
    public Map<Integer,List<TagDto>> getMapWithListByDefinitionIds(List<Integer> list,Connection conn);
    public List<BaseEntityDto> retrieveOrCreate(List<BaseEntityDto> dtos, Connection conn) throws SQLException;
}

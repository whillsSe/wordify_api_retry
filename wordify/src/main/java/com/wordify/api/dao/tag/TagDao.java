package com.wordify.api.dao.tag;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.wordify.api.dto.TagDto;

public interface TagDao {
    public Map<Integer,List<TagDto>> getMapWithListByDefinitionIds(List<Integer> list,Connection conn);
}

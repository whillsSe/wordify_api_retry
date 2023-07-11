package com.wordify.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

public class GenericMapper<T> {
    private final IDtoAndKeyMapper<T> dtoAndKeyMapper;

    public GenericMapper(IDtoAndKeyMapper<T> dtoAndKeyMapper) {
        this.dtoAndKeyMapper = dtoAndKeyMapper;
    }

    public Map<Integer, T> mapToMap(ResultSet resultSet) throws SQLException {
        Map<Integer, T> map = new HashMap<>();
        while (resultSet.next()) {
            SimpleEntry<Integer, T> entry = dtoAndKeyMapper.mapToDtoWithKey(resultSet);
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
    public Map<Integer,List<T>> mapToMapWithList(ResultSet resultSet) throws SQLException{
        Map<Integer,List<T>> map = new HashMap<>();
        while (resultSet.next()) {
            SimpleEntry<Integer, T> entry = dtoAndKeyMapper.mapToDtoWithKey(resultSet);
            List<T> existingValues = map.get(entry.getKey());
            if(existingValues == null){
                existingValues = new ArrayList<>();
                existingValues.add(entry.getValue());
                map.put(entry.getKey(),existingValues);
            }else{
                existingValues.add(entry.getValue());
            }
        }
        return map;
    }
}


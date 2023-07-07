package com.wordify.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;

public interface IDtoAndKeyMapper<T> {
 SimpleEntry<Integer,T> mapToDtoWithKey(ResultSet resultSet)throws SQLException;
}

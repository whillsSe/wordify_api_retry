package com.wordify.api.dao.definition;

import java.sql.Connection;
import java.sql.SQLException;

public interface DefinitionDao {
    public int registerDefinition(int userId,int wordId,int phoneticId,Connection conn) throws SQLException;
    public void updateDefinition(int userId,int definition_id ,int wordId,int phoneticId,Connection conn) throws SQLException;
    public void deleteDefinition(int userId,int id,Connection conn) throws SQLException;
}

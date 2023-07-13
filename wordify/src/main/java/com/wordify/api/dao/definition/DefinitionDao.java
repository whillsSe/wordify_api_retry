package com.wordify.api.dao.definition;

import java.sql.Connection;
import java.sql.SQLException;

public interface DefinitionDao {
    public int registerDefinition(int userId,int wordId,int phoneticId,Connection conn) throws SQLException;
}

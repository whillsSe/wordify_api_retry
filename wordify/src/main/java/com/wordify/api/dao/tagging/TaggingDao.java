package com.wordify.api.dao.tagging;

import java.sql.Connection;
import java.sql.SQLException;

public interface TaggingDao {
    public void addTagging(int definitionId,int[] tagIds,Connection conn)throws SQLException;
    public void deleteTagging(int definitionId,Connection conn)throws SQLException;
}

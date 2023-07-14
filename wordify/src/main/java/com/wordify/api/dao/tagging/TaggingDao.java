package com.wordify.api.dao.tagging;

import java.sql.Connection;
import java.sql.SQLException;

public interface TaggingDao {
    public void addTagging(int definitionId,int[] tagIds,Connection conn)throws SQLException;
}

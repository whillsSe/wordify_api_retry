package com.wordify.api.dao.phonetic;

import java.sql.Connection;
import java.sql.SQLException;

public interface PhoneticDao {
    public int retrieveOrCreate(String phoneticString,Connection conn) throws SQLException;
}

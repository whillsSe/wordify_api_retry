package com.wordify.api.dao.phonetic;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.api.dto.BaseEntityDto;

public interface PhoneticDao {
    public BaseEntityDto retrieveOrCreate(BaseEntityDto dto,Connection conn) throws SQLException;
}

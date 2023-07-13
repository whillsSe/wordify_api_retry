package com.wordify.api.dao.phonetic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dto.BaseEntityDto;

public class PhoneticDaoImpl implements PhoneticDao{
    @Override
    public BaseEntityDto retrieveOrCreate(BaseEntityDto dto,Connection conn) throws SQLException{
        StringBuilder builder = DaoUtils.createStringBuilder("phonetic");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString(),Statement.RETURN_GENERATED_KEYS)){
        pstmt.setString(1, dto.getValue());
        int affectedRows = pstmt.executeUpdate();
        if(affectedRows == 0){
            throw new SQLException("Creating phonetic failed, no rows affected.");
        }

        try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
            if(generatedKeys.next()){
            int id = generatedKeys.getInt(1);
            dto.setId(id);
            }else{
            throw new SQLException("Creating phonetic failed, no ID obtained.");
            }
        }
        return dto;
        }
    
  }
}

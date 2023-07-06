package com.wordify.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wordify.api.dao.mapper.EntryMapper;
import com.wordify.api.dao.utils.DaoUtils;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.EntryQuery;
import com.wordify.api.dto.utils.SubqueryResult;
import com.wordify.api.utils.SubqueryUtil;

public class EntryDaoImpl implements EntryDao{
    private EntryMapper mapper;
    
    public EntryDaoImpl() {
        this.mapper = new EntryMapper();
    }

    //トランザクション管理が必要
    public List<EntryDto> getEntry(EntryQuery query,Connection conn){
        SubqueryResult subqueryResult = SubqueryUtil.createSubquery(query);
        SubqueryResult whereClauseResult = SubqueryUtil.createEntryWhereClause(query);
        StringBuilder builder = new StringBuilder("SELECT word,w.id AS wordId,phonetic,p.id AS phoneticId FROM definitions d JOIN (");
        builder.append(subqueryResult.getQuery()).append(") AS scope ON d.id = scope.definition_id JOIN word w ON d.word_id = w.id JOIN phonetic p ON d.phonetic_id = p.id");
        builder.append(whereClauseResult.getQuery());
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            int count = 0;
            DaoUtils.setParameters(pstmt,subqueryResult.getParameter(),count);
            DaoUtils.setParameters(pstmt,whereClauseResult.getParameter(),count);
            ResultSet resultSet = pstmt.executeQuery();
            return mapper.mapToList(resultSet);
        }catch(SQLException e){
            e.getErrorCode();
            throw new Error("SQLException is happened!");
        }
    }
}

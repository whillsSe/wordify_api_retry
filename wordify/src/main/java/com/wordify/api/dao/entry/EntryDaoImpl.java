package com.wordify.api.dao.entry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.payloads.EntrySearchPayload;
import com.wordify.api.dto.utils.SubqueryResult;
import com.wordify.api.utils.SubqueryUtil;

public class EntryDaoImpl implements EntryDao{
    private EntryMapper mapper;
    
    public EntryDaoImpl() {
        this.mapper = new EntryMapper();
    }

    //トランザクション管理が必要
    public List<EntryDto> getEntry(EntrySearchPayload query,Connection conn){
        SubqueryResult subqueryResult = SubqueryUtil.createSubquery(query);
        SubqueryResult whereClauseResult = SubqueryUtil.createEntryWhereClause(query);
        StringBuilder builder = new StringBuilder("SELECT word,w.id AS wordId,phonetic,p.id AS phoneticId FROM definitions d JOIN (");
        builder.append(subqueryResult.getQuery()).append(") AS scope ON d.id = scope.definition_id JOIN words w ON d.word_id = w.id JOIN phonetics p ON d.phonetic_id = p.id");
        builder.append(whereClauseResult.getQuery());
        Logger logger = Logger.getLogger(EntryDaoImpl.class.getName());
        logger.info(builder.toString());
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            int count = 0;
            count = DaoUtils.setParameters(pstmt,subqueryResult.getParameter(),count);
            DaoUtils.setParameters(pstmt,whereClauseResult.getParameter(),count);
            ResultSet resultSet = pstmt.executeQuery();
            return mapper.mapToList(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
            throw new Error("SQLException is happened!");
        }
    }
}

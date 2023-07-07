package com.wordify.api.dao.context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.params.ContextQuery;
import com.wordify.api.dto.utils.SubqueryResult;
import com.wordify.api.utils.SubqueryUtil;

public class ContextDaoImpl implements ContextDao{
    private ContextMapper mapper;
    
    public ContextDaoImpl() {
        this.mapper = new ContextMapper();
    }

    @Override
    public ContextDto getContext(ContextQuery query, Connection conn) {
        SubqueryResult subqueryResult = SubqueryUtil.createSubquery(query);
        StringBuilder builder = new StringBuilder("WITH sorted_keys AS (SELECT w.id AS word_id,w.word AS word,p.id AS phonetic_id,p.phonetic AS phonetic,sub.id AS definition_id,sub.user_id AS user_id,ROW_NUMBER() OVER (ORDER BY p.phonetic,w.word) AS row_num FROM definitions d JOIN (");
        builder.append(subqueryResult.getQuery()).append(") AS sub JOIN words w ON sub.word_id = w.id JOIN phonetics p ON sub.phonetic_id = p.id)");
        builder.append("SELECT k_prev.word_id AS prev_word_id,k_prev.word AS prev_word,k_prev.phonetic_id AS prev_phonetic_id,k_prev.phonetic AS prev_phonetic,k_next.word_id AS next_word_id,k_next.word AS next_word,k_next.phonetic_id AS next_phonetic_id,k_next.phonetic AS next_phonetic ,k.definition_id AS definition_id,k.user_id AS user_id FROM sorted_keys k LEFT JOIN sorted_keys k_prev ON k_prev.row_num = k.row_num - 1 LEFT JOIN sorted_keys k_next ON k_next.row_num = k.row_num + 1 WHERE k.word_id = ? AND k.phonetic_id = ?;");
        
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
            int count = 0;
            DaoUtils.setParameters(pstmt,subqueryResult.getParameter(),count);
            pstmt.setInt(query.getWordId(), ++count);
            pstmt.setInt(query.getPhoneticId(), ++count);
            ResultSet resultSet = pstmt.executeQuery();
            return mapper.map(resultSet);
        }catch(SQLException e){
            
        }
        
        return null;
    }
    
}

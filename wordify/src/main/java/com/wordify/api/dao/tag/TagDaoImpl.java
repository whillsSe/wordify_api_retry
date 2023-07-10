package com.wordify.api.dao.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.wordify.api.dao.GenericMapper;
import com.wordify.api.dto.TagDto;
import com.wordify.api.utils.SQLUtils;

public class TagDaoImpl implements TagDao{
    private GenericMapper<TagDto> mapper;
    public TagDaoImpl(){
        this.mapper = new GenericMapper<TagDto>(new TagMapper());
    }
    //definitionIdsからtagを取得するならDao分けてても良いけども、逆の場合は一時的に保持するレコード。
    //一旦いつも通りの実装をする(definitionからテーブル結合して直に取得)
    public Map<Integer,TagDto> getTagsByDefinitionIds(List<String> list,Connection conn){
        StringBuilder builder = new StringBuilder("SELECT definition_id,tag_id,tag FROM definitions_tags dt JOIN tag t ON dt.tag_id = t.id WHERE definition_id IN (");
        SQLUtils.prepareQueryForElements(list.size(), builder);
        builder.append(")");
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
          ResultSet resultSet = pstmt.executeQuery();
          return mapper.mapToMap(resultSet);
        }catch(SQLException e){
          throw new Error("SQLException has happened!");
        }
      } 
    }

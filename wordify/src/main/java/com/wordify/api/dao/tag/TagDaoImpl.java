package com.wordify.api.dao.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.wordify.api.dao.DaoUtils;
import com.wordify.api.dao.GenericMapper;
import com.wordify.api.dto.TagDto;
import com.wordify.api.dto.payloads.params.ICustomParam;
import com.wordify.api.utils.SQLUtils;

public class TagDaoImpl implements TagDao{
    private GenericMapper<TagDto> mapper;
    public TagDaoImpl(){
        this.mapper = new GenericMapper<TagDto>(new TagMapper());
    }
    //definitionIdsからtagを取得するならDao分けてても良いけども、逆の場合は一時的に保持するレコード。
    //一旦いつも通りの実装をする(definitionからテーブル結合して直に取得)
    public Map<Integer,List<TagDto>> getMapWithListByDefinitionIds(List<Integer> list,Connection conn){
        StringBuilder builder = new StringBuilder("SELECT definition_id,tag_id,tag,t.id FROM tagging dt JOIN tags t ON dt.tag_id = t.id WHERE definition_id IN (");
        SQLUtils.prepareQueryForElements(list.size(), builder);
        builder.append(")");
        List<ICustomParam> params = DaoUtils.parseIntegerToICustomParams(list);
        Logger logger = Logger.getLogger(TagDaoImpl.class.getName());
        logger.info(builder.toString());
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString())){
          DaoUtils.setParameters(pstmt, params, 0);
          ResultSet resultSet = pstmt.executeQuery();
          return mapper.mapToMapWithList(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
          throw new Error("SQLException has happened!");
        }
      }
    @Override
    public int[] retrieveOrCreate(String[] tagStrings, Connection conn) throws SQLException{
        int[] tagIds = new int[tagStrings.length];
        StringBuilder builder = DaoUtils.createStringBuilder("tag");
        
        try(PreparedStatement pstmt = conn.prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS)){
            for (int i=0;i<tagStrings.length;i++) {
                String tagString = tagStrings[i];
                pstmt.setString(1, tagString);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating tag failed, no rows affected.");
                }
        
                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                    if(generatedKeys.next()){
                        int id = generatedKeys.getInt(1);
                        tagIds[i] = id;
                    } else {
                        throw new SQLException("Creating tag failed, no ID obtained.");
                    }
                }
                pstmt.clearParameters();
            }
        }
        return tagIds;
    }

    }

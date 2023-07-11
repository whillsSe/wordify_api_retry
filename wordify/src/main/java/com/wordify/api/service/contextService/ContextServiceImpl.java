package com.wordify.api.service.contextService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dao.collection.CollectionDao;
import com.wordify.api.dao.collection.CollectionDaoImpl;
import com.wordify.api.dao.context.ContextDao;
import com.wordify.api.dao.context.ContextDaoImpl;
import com.wordify.api.dao.definition.DefinitionDao;
import com.wordify.api.dao.definition.DefinitionDaoImpl;
import com.wordify.api.dao.definitionTag.DefinitionTagDao;
import com.wordify.api.dao.example.ExampleDao;
import com.wordify.api.dao.example.ExampleDaoImpl;
import com.wordify.api.dao.meaning.MeaningDao;
import com.wordify.api.dao.meaning.MeaningDaoImpl;
import com.wordify.api.dao.tag.TagDao;
import com.wordify.api.dao.tag.TagDaoImpl;
import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.ExampleDto;
import com.wordify.api.dto.MeaningDto;
import com.wordify.api.dto.TagDto;
import com.wordify.api.dto.params.ContextQuery;

public class ContextServiceImpl implements ContextService{
  private ConnectionPool connectionPool;
  private ContextDao contextDao;
  private TagDao tagDao;
  private MeaningDao meaningDao;
  private ExampleDao exampleDao;
  private DefinitionDao definitionDao;
  private CollectionDao collectionDao;
  private DefinitionTagDao definitionTagDao;
  public ContextServiceImpl(ConnectionPool connectionPool){
    this.connectionPool = connectionPool;
    this.contextDao = new ContextDaoImpl();//仮設。本来は上から注入。
    this.tagDao = new TagDaoImpl();
    this.meaningDao = new MeaningDaoImpl();
    this.exampleDao = new ExampleDaoImpl();
    this.collectionDao = new CollectionDaoImpl();
    this.definitionDao = new DefinitionDaoImpl();
  }
  public ContextDto getContext(ContextQuery query) throws SQLException{
    try (Connection conn = connectionPool.getConnection()){
      ContextDto contextDto = contextDao.getContext(query, conn);
      Map<Integer,DefinitionDto> definitionsMap = contextDto.getDefinitionsMap();
      List<Integer> definitionIds = new ArrayList<>(definitionsMap.keySet());
      Map<Integer,MeaningDto> meaningsResult = meaningDao.getMapByDefinitionIds(definitionIds,conn);
      Map<Integer,List<ExampleDto>> examplesResult = exampleDao.getMapWithListByDefinitionIds(definitionIds,conn);
      Map<Integer,List<TagDto>> tagsResult = tagDao.getMapWithListByDefinitionIds(definitionIds, conn);
      
      for(Integer definitionId: definitionIds){
        DefinitionDto definition = definitionsMap.get(definitionId);
      
        MeaningDto meaning = meaningsResult.get(definitionId);
        List<ExampleDto> examples = examplesResult.get(definitionId);
        List<TagDto> tags = tagsResult.get(definitionId);

        definition.setMeaning(meaning);
        definition.setExamples(examples);
        definition.setTags(tags);
      }
      return contextDto;
    }
  }
}

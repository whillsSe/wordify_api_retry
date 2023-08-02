package com.wordify.api.service.entryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dao.context.ContextDao;
import com.wordify.api.dao.context.ContextDaoImpl;

import com.wordify.api.dao.entry.EntryDao;
import com.wordify.api.dao.entry.EntryDaoImpl;
import com.wordify.api.dao.example.ExampleDao;
import com.wordify.api.dao.example.ExampleDaoImpl;
import com.wordify.api.dao.meaning.MeaningDao;
import com.wordify.api.dao.meaning.MeaningDaoImpl;
import com.wordify.api.dao.tag.TagDao;
import com.wordify.api.dao.tag.TagDaoImpl;
import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.ExampleDto;
import com.wordify.api.dto.MeaningDto;
import com.wordify.api.dto.TagDto;
import com.wordify.api.dto.payloads.ContextRetrievalPayload;
import com.wordify.api.dto.payloads.EntrySearchPayload;

public class EntryServiceImpl implements EntryService{
    private ConnectionPool connectionPool;
    private EntryDao entryDao;
    private ContextDao contextDao;
    private TagDao tagDao;
    private MeaningDao meaningDao;
    private ExampleDao exampleDao;

    public EntryServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        this.entryDao = new EntryDaoImpl();//仮設。本来は上から注入。
        this.contextDao = new ContextDaoImpl();//仮設。本来は上から注入。
        this.tagDao = new TagDaoImpl();
        this.meaningDao = new MeaningDaoImpl();
        this.exampleDao = new ExampleDaoImpl();
    }
    @Override
    public List<EntryDto> getEntries(EntrySearchPayload query) throws SQLException{
        List<EntryDto> list = new ArrayList<EntryDto>();
        try (Connection conn = connectionPool.getConnection()) {
            //conn.setAutoCommit(false);//今回は不要
            list = entryDao.getEntry(query, conn);
        }
        return list;
    }

    @Override
    public ContextDto getContext(ContextRetrievalPayload query) throws SQLException{
        Logger logger = Logger.getLogger(ContextDto.class.getName());
        try (Connection conn = connectionPool.getConnection()){
        ContextDto contextDto = contextDao.getContext(query, conn);
        logger.info("Is ContextDto null ?" + (contextDto == null));//false
                    //ここがnullだったときに、次が進まなくなってしまう問題。
        List<DefinitionDto> definitionsList = contextDto.getDefinitionsList();
        List<Integer> definitionIds = new ArrayList<>();
        Set<Integer> userIds = new HashSet<>();
        for(DefinitionDto definition:definitionsList){
            definitionIds.add(definition.getId());
            userIds.add(definition.getAuthorId());
            userIds.add(definition.getCollectorId());
        }
        Map<Integer,MeaningDto> meaningsResult = meaningDao.getMapByDefinitionIds(definitionIds,conn);
        Map<Integer,List<ExampleDto>> examplesResult = exampleDao.getMapWithListByDefinitionIds(definitionIds,conn);
        Map<Integer,List<TagDto>> tagsResult = tagDao.getMapWithListByDefinitionIds(definitionIds, conn);
        for(DefinitionDto definition: definitionsList){
            Integer definitionId = definition.getId();
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

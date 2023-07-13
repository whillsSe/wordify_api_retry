package com.wordify.api.service.definitionService;

import java.sql.Connection;
import java.sql.SQLException;

import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dao.collection.CollectionDao;
import com.wordify.api.dao.collection.CollectionDaoImpl;
import com.wordify.api.dao.definition.DefinitionDao;
import com.wordify.api.dao.definition.DefinitionDaoImpl;
import com.wordify.api.dao.example.ExampleDao;
import com.wordify.api.dao.example.ExampleDaoImpl;
import com.wordify.api.dao.meaning.MeaningDao;
import com.wordify.api.dao.meaning.MeaningDaoImpl;
import com.wordify.api.dao.phonetic.PhoneticDao;
import com.wordify.api.dao.phonetic.PhoneticDaoImpl;
import com.wordify.api.dao.tag.TagDao;
import com.wordify.api.dao.tag.TagDaoImpl;
import com.wordify.api.dao.tagging.TaggingDao;
import com.wordify.api.dao.tagging.TaggingDaoImpl;
import com.wordify.api.dao.word.WordDao;
import com.wordify.api.dao.word.WordDaoImpl;
import com.wordify.api.dto.DefinitionDto;

public class DefinitionServiceImpl implements DefinitionService{
    private ConnectionPool connectionPool;
    private WordDao wordDao;
    private PhoneticDao phoneticDao;
    private TagDao tagDao;
    private DefinitionDao definitionDao;
    private MeaningDao meaningDao;
    private ExampleDao exampleDao;
    private CollectionDao collectionDao;
    private TaggingDao taggingDao;
    public DefinitionServiceImpl(ConnectionPool connectionPool){
        this.connectionPool = connectionPool;
        this.wordDao = new WordDaoImpl();
        this.phoneticDao = new PhoneticDaoImpl();
        this.tagDao = new TagDaoImpl();
        this.definitionDao = new DefinitionDaoImpl();
        this.meaningDao = new MeaningDaoImpl();
        this.exampleDao = new ExampleDaoImpl();
        this.collectionDao = new CollectionDaoImpl(); 
        this.taggingDao = new TaggingDaoImpl();
    }
    public DefinitionDto registerDefinition(EntryDto)throws SQLException{
        //word,phonetic,tagを呼び出します
        //それぞれを検証・登録します。主キーidを取得します。
        //definitionを登録します。主キーidを取得します。
        //上記idと合わせ、meaning,example,definitions_tags,collectionsを登録します。
        //commitします。
        Connection conn = connectionPool.getConnection();
        conn.setAutoCommit(false);
        //BaseEntityDtoを渡して、BaseEntityDtoを返すべきでは？
        BaseEntityDto wordDto = wordDao.retrieveOrCreate(, conn)
        //BaseEntityDto phoneticDto = phoneticDao.
        //BaseEntityDto[] tagIds = tagDao.
    }
}

package com.wordify.api.service.definitionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.wordify.api.dto.BaseEntityDto;
import com.wordify.api.dto.DefinitionDtoWithEntryInfo;
import com.wordify.api.dto.ExampleDto;
import com.wordify.api.dto.MeaningDto;
import com.wordify.api.dto.TagDto;
import com.wordify.api.dto.payloads.CollectionTargetPayload;
import com.wordify.api.dto.payloads.EntryRegistrationPayload;

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
    public DefinitionDtoWithEntryInfo registerDefinition(EntryRegistrationPayload payload)throws SQLException{
        //word,phonetic,tagを呼び出します
        //それぞれを検証・登録します。主キーidを取得します。
        //definitionを登録します。主キーidを取得します。
        //上記idと合わせ、meaning,example,definitions_tags,collectionsを登録します。
        //commitします。
        Connection conn = connectionPool.getConnection();
        conn.setAutoCommit(false);
        //BaseEntityDtoを渡して、BaseEntityDtoを返すべきでは？
        String wordString = payload.getWordString();
        int wordId = wordDao.retrieveOrCreate(wordString, conn);
        BaseEntityDto wordDto = new BaseEntityDto(wordId, wordString);

        //メモ：取得との統一感としても、引数Objで渡す⇒欲しいdtoで返す方が統一感があった気がする。
            //追記：どんなdto型で返すかが不透明だし、dtoで返すとidとか毎回getで呼び出して埋め込んで…と煩雑。
        String phoneticString = payload.getPhoneticString();
        int phoneticId = phoneticDao.retrieveOrCreate(phoneticString, conn);
        BaseEntityDto phoneticDto = new BaseEntityDto(phoneticId, phoneticString);

        String[] tagStrings = payload.getTagStrings();
        int[] tagIds = tagDao.retrieveOrCreate(tagStrings, conn);
        List<TagDto> tags = new ArrayList<>();
        for(int i=0;i<tagStrings.length;i++){
            tags.add(new TagDto(tagIds[i],tagStrings[i]));
        }
        int userId = payload.getUserId();
        int definitionId = definitionDao.registerDefinition(userId,wordId,phoneticId,conn);

        String meaninString = payload.getMeaningString();
        int meaningId = meaningDao.registerMeaning(definitionId,meaninString,conn);
        MeaningDto meaningDto = new MeaningDto(meaningId, meaninString);

        String[] exampleStrings = payload.getExampleStrings();
        int[] exampleIds = exampleDao.registerExample(definitionId,exampleStrings,conn);
        List<ExampleDto> examples = new ArrayList<>();
        for(int i=0;i<exampleStrings.length;i++){
            examples.add(new ExampleDto(exampleIds[i],exampleStrings[i]));
        }

        taggingDao.addTagging(definitionId,tagIds,conn);
        CollectionTargetPayload collectionPayload = new CollectionTargetPayload(userId,definitionId);
        collectionDao.addDefinition(collectionPayload,conn);

        conn.commit();

        DefinitionDtoWithEntryInfo dto = new DefinitionDtoWithEntryInfo();
        dto.setId(definitionId);
        dto.setAuthorId(userId);
        dto.setWord(wordDto);
        dto.setPhonetic(phoneticDto);
        dto.setMeaning(meaningDto);
        dto.setExamples(examples);
        dto.setTags(tags);

        return dto;
    }

    public DefinitionDtoWithEntryInfo updateDefinition(DefinitionDtoWithEntryInfo payload){
        
    }
}

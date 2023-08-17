package com.wordify.api.service.definitionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
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
import com.wordify.api.dto.payloads.EntryUpdatePayload;

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
    public DefinitionDtoWithEntryInfo registerDefinition(EntryRegistrationPayload payload) throws SQLException{
        Connection conn = connectionPool.getConnection();
        DefinitionDtoWithEntryInfo dto = new DefinitionDtoWithEntryInfo();
        Logger logger = Logger.getLogger(DefinitionServiceImpl.class.getName());
        try{
            //word,phonetic,tagを呼び出します
            //それぞれを検証・登録します。主キーidを取得します。
            //definitionを登録します。主キーidを取得します。
            //上記idと合わせ、meaning,example,definitions_tags,collectionsを登録します。
            //commitします。
        conn.setAutoCommit(false);
            //BaseEntityDtoを渡して、BaseEntityDtoを返すべきでは？
        String wordString = payload.getWordString();
        BaseEntityDto wordDto = retrieveOrCreateWord(wordString, conn);
            //メモ：取得との統一感としても、引数Objで渡す⇒欲しいdtoで返す方が統一感があった気がする。
            
        String phoneticString = payload.getPhoneticString();
        BaseEntityDto phoneticDto = retrieveOrCreatePhonetic(phoneticString, conn);

        String[] tagStrings = payload.getTagStrings();
        int[] tagIds = tagDao.retrieveOrCreate(tagStrings, conn);
        List<TagDto> tags = new ArrayList<>();
        for(int i=0;i<tagStrings.length;i++){
            tags.add(new TagDto(tagIds[i],tagStrings[i]));
        }

        int userId = payload.getUserId();
        int definitionId = definitionDao.registerDefinition(userId,wordDto.getId(),phoneticDto.getId(),conn);

        String meaningString = payload.getMeaningString();
        MeaningDto meaningDto = registerMeaning(definitionId, meaningString, conn);

        String[] exampleStrings = payload.getExampleStrings();
        List<ExampleDto> examples = registerExample(definitionId, exampleStrings, conn);

        taggingDao.addTagging(definitionId,tagIds,conn);
        CollectionTargetPayload collectionPayload = new CollectionTargetPayload(userId,definitionId);
        collectionDao.addDefinition(collectionPayload,conn);

        conn.commit();

        dto.setId(definitionId);
        dto.setAuthorId(userId);
        dto.setWord(wordDto);
        dto.setPhonetic(phoneticDto);
        dto.setMeaning(meaningDto);
        dto.setExamples(examples);
        dto.setTags(tags);

    }catch(SQLException e){
        logger.info(e.getMessage() + e.getLocalizedMessage());
        rollbackTransaction(conn);
    }
        return dto;
    }
    private void rollbackTransaction(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.rollback();
                throw new SQLException("Transaction rolled back");
            } catch (SQLException e) {
                Logger logger = Logger.getLogger(DefinitionServiceImpl.class.getName());
                logger.info("Failed to rollback transaction: " + e.getMessage());
                throw new SQLException("Failed to rollback transaction: " + e.getMessage());
            }
        }
    }
    private BaseEntityDto retrieveOrCreateWord(String wordString, Connection conn) throws SQLException{
        int wordId = wordDao.retrieveOrCreate(wordString, conn);
        BaseEntityDto wordDto = new BaseEntityDto(wordId, wordString);
        return wordDto;
    }
    private BaseEntityDto retrieveOrCreatePhonetic(String phoneticString,Connection conn)throws SQLException{
        int phoneticId = phoneticDao.retrieveOrCreate(phoneticString, conn);
        BaseEntityDto phoneticDto = new BaseEntityDto(phoneticId, phoneticString);
        return phoneticDto;
    }
    private MeaningDto registerMeaning(int definitionId, String meaningString, Connection conn) throws SQLException{
        int meaningId = meaningDao.registerMeaning(definitionId, meaningString, conn);
        MeaningDto meaningDto = new MeaningDto(meaningId, meaningString);
        return meaningDto;
    }
    private List<ExampleDto> registerExample(int definitionId, String[] exampleStrings, Connection conn) throws SQLException{
        int[] exampleIds = exampleDao.registerExample(definitionId, exampleStrings, conn);
        List<ExampleDto> examples = new ArrayList<>();
        for(int i=0;i<exampleStrings.length;i++){
            examples.add(new ExampleDto(exampleIds[i],exampleStrings[i]));
        }
        return examples;
    }

    @Override
    public DefinitionDtoWithEntryInfo updateDefinition(EntryUpdatePayload payload) throws SQLException{
        Connection conn = connectionPool.getConnection();
        DefinitionDtoWithEntryInfo dto = new DefinitionDtoWithEntryInfo();
        try{
            conn.setAutoCommit(false);
            int definitionId = payload.getEntryId();
            int userId = payload.getUserId();
            BaseEntityDto wordDto = retrieveOrCreateWord(payload.getWordString(), conn);
            BaseEntityDto phoneticDto = retrieveOrCreatePhonetic(payload.getPhoneticString(), conn);
            String[] tagStrings = payload.getTagStrings();
            int[] tagIds = tagDao.retrieveOrCreate(tagStrings, conn);
            List<TagDto> tags = new ArrayList<>();
            for(int i=0;i<tagStrings.length;i++){
                tags.add(new TagDto(tagIds[i],tagStrings[i]));
            }

            meaningDao.deleteMeaning(definitionId, conn);
            exampleDao.deleteExample(definitionId, conn);
            taggingDao.deleteTagging(definitionId, conn);
            
            MeaningDto meaningDto = registerMeaning(definitionId, payload.getMeaningString(), conn);
            List<ExampleDto> examples = registerExample(definitionId, payload.getExampleStrings(), conn);
            taggingDao.addTagging(definitionId,tagIds,conn);
            CollectionTargetPayload collectionPayload = new CollectionTargetPayload(userId,definitionId);
            collectionDao.addDefinition(collectionPayload,conn);

            dto.setId(definitionId);
            dto.setAuthorId(userId);
            dto.setWord(wordDto);
            dto.setPhonetic(phoneticDto);
            dto.setMeaning(meaningDto);
            dto.setExamples(examples);
            dto.setTags(tags);

            conn.commit();
        }catch(SQLException e){
            rollbackTransaction(conn);
        }
        return dto;
    }
/* 
    public DefinitionDtoWithEntryInfo updateDefinition(DefinitionDtoWithEntryInfo payload){
        //Dtoの中のidを読んで、idが未設定だったら新しく参照をとる、でいいような気もする。
        //word
        //phonetic
        //tag
        //meaning,exampleに関しては、送られてきた内容をそのまま更新かけるだけで良いはず。
        //examplesとかに関してはもっと項目数減らしても良いかもしれないが。
        //meaning
        //examples
    }
*/
}

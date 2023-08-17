package com.wordify.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dao.collection.CollectionDao;
import com.wordify.api.dao.collection.CollectionDaoImpl;
import com.wordify.api.dao.definition.DefinitionDao;
import com.wordify.api.dao.definition.DefinitionDaoImpl;
import com.wordify.api.dao.example.ExampleDaoImpl;
import com.wordify.api.dao.meaning.MeaningDao;
import com.wordify.api.dao.meaning.MeaningDaoImpl;
import com.wordify.api.dao.phonetic.PhoneticDaoImpl;
import com.wordify.api.dao.tag.TagDaoImpl;
import com.wordify.api.dao.tagging.TaggingDaoImpl;
import com.wordify.api.dao.word.WordDaoImpl;
import com.wordify.api.dto.BaseEntityDto;
import com.wordify.api.dto.DefinitionDtoWithEntryInfo;
import com.wordify.api.dto.MeaningDto;
import com.wordify.api.dto.payloads.CollectionTargetPayload;
import com.wordify.api.dto.payloads.EntryRegistrationPayload;
import com.wordify.api.dto.payloads.EntryUpdatePayload;
import com.wordify.api.service.definitionService.DefinitionServiceImpl;

public class DefinitionServiceImplTest {
    private EntryRegistrationPayload getTestPayload(){
        EntryRegistrationPayload payload = new EntryRegistrationPayload();
        payload.setUserId(1);
        payload.setWordString("test");
        payload.setPhoneticString("てすと");
        payload.setTagStrings(new String[]{"testTag1","testTag2"});
        payload.setMeaningString(new String("testMeaning"));
        payload.setExampleStrings(new String[]{"testExample1","testExample2"});
        return payload;
    }

    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private WordDaoImpl wordDao;
    @Mock
    private PhoneticDaoImpl phoneticDao;
    @Mock
    private TagDaoImpl tagDao;
    @Mock
    private DefinitionDaoImpl definitionDao;
    @Mock
    private MeaningDaoImpl meaningDao;
    @Mock
    private ExampleDaoImpl exampleDao;
    @Mock
    private TaggingDaoImpl taggingDao;
    @Mock
    private CollectionDaoImpl collectionDao;

    private int[] generateRandomIntsBasedOnStrings(String[] strings) {
        int[] randomInts = new int[strings.length];
        Random random = new Random();
        for (int i = 0; i < randomInts.length; i++) {
            randomInts[i] = random.nextInt();
        }
        return randomInts;
    }
    private void setField(String fieldName,Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
        Field field = definitionService.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(definitionService, value);
    }
    public static void printObjectFields(Object obj) {
        Class<?> clazz = obj.getClass();
    
        // クラスの全フィールドを取得
        Field[] fields = clazz.getDeclaredFields();
    
        for (Field field : fields) {
            field.setAccessible(true); // privateフィールドにもアクセス可能にする
    
            String name = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            System.out.printf("Field name: %s, Value: %s%n", name, value);
        }
    }
    
    @InjectMocks
    private DefinitionServiceImpl definitionService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        definitionService = new DefinitionServiceImpl(connectionPool);
        setField("wordDao", wordDao);
        setField("phoneticDao", phoneticDao);
        setField("tagDao", tagDao);
        setField("definitionDao", definitionDao);
        setField("collectionDao", collectionDao);
        setField("meaningDao", meaningDao);
        setField("exampleDao", exampleDao);
        setField("taggingDao", taggingDao);
    }
    @Test
    public void test() {
        try {
                when(connectionPool.getConnection()).thenReturn(mock(Connection.class));
                when(wordDao.retrieveOrCreate(anyString(), any(Connection.class))).thenReturn(999);
                when(phoneticDao.retrieveOrCreate(anyString(),any(Connection.class))).thenReturn(999);
                when(tagDao.retrieveOrCreate(any(String[].class),any(Connection.class))).thenAnswer(new Answer<int[]>() {
                    @Override
                    public int[] answer(InvocationOnMock invocation) throws Throwable {
                        // 引数を取得
                        Object[] args = invocation.getArguments();
                        String[] strings = (String[]) args[0];
                        return generateRandomIntsBasedOnStrings(strings);
                    }
                });
                when(definitionDao.registerDefinition(anyInt(), anyInt(),anyInt(), any(Connection.class))).thenReturn(999);
                when(meaningDao.registerMeaning(anyInt(),anyString(),any(Connection.class))).thenReturn(999);
                when(exampleDao.registerExample(anyInt(),any(String[].class),any(Connection.class))).thenAnswer(new Answer<int[]>() {
                    @Override
                    public int[] answer(InvocationOnMock invocation) throws Throwable {
                        // 引数を取得
                        Object[] args = invocation.getArguments();
                        String[] strings = (String[]) args[1];
                        return generateRandomIntsBasedOnStrings(strings);
                    }
                });
                Mockito.doNothing().when(taggingDao).addTagging(anyInt(), any(int[].class), any(Connection.class));
                Mockito.doNothing().when(collectionDao).addDefinition(any(CollectionTargetPayload.class), any(Connection.class));
                
                DefinitionDtoWithEntryInfo result = definitionService.registerDefinition(getTestPayload());
                printObjectFields(result);
                assertEquals(999,result.getMeaning().getId());
            } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

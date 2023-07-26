package com.wordify.auth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperSingleton {
    //シングルトンパターンのObjectMapperを各箇所から呼び出すことになる。
    private static final ObjectMapper instance = new ObjectMapper();
    private ObjectMapperSingleton(){
    };
    public static ObjectMapper getInstance() {
        return instance;
    }
}
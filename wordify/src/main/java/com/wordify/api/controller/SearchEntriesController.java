package com.wordify.api.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.api.dto.BaseEntityDto;
import com.wordify.api.utils.ObjectMapperSingleton;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchEntriesController extends AbstractController{
    public SearchEntriesController(ExecutorService executor){
        super(executor);
    }
    public void handleGetRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Callable<String> task = () -> {
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            BaseEntityDto dto = new BaseEntityDto("testId","test");//あくまで仮実装
            String json = mapper.writeValueAsString(dto);
            return json;
        };
        super.handleAsyncRequest(task, resp);
    }
}

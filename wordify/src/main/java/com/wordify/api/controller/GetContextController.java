package com.wordify.api.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.api.config.ConnectionPool;
import com.wordify.api.dto.BaseEntityDto;
import com.wordify.api.utils.ObjectMapperSingleton;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetContextController extends AbstractController{
    private ContextService service;
    public GetContextController(ExecutorService executor,ContextService service){
        super(executor);
        this.service = service;
    }
    public void handleGetRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Callable<String> task = () -> {
            BaseEntityDto dto = new BaseEntityDto("testId","test");
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            String json = mapper.writeValueAsString(dto);
            return json;
        };
        super.handleAsyncRequest(task, resp);
    }
}

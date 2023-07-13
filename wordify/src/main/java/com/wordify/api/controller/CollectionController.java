package com.wordify.api.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.api.controller.utils.ResponseEntity;
import com.wordify.api.dto.params.CollectionQuery;
import com.wordify.api.service.collectionService.CollectionService;
import com.wordify.api.utils.ObjectMapperSingleton;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CollectionController extends AbstractController{
    private CollectionService service;
    public CollectionController(ExecutorService executor,CollectionService service){
        super(executor);
        this.service = service;
    }
    public void handlePostRequest(HttpServletRequest req,HttpServletResponse res) throws IOException{
        Callable<String> task = () -> {
            Integer userId = (Integer) req.getAttribute("user");
            Integer definitionId = Integer.parseInt(req.getParameter("dId"));
            CollectionQuery query = new CollectionQuery(userId);
            query.setDefinitionId(definitionId);
            service.addCollection(query);

            ResponseEntity responseEntity = new ResponseEntity("success", "Record has been successfully inserted.");
                //API通信の結果を示す、仮打ちメッセージ
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            String json = mapper.writeValueAsString(responseEntity);
            return json;
        };
        handleAsyncRequest(task, res);
    }
    public void handleDeleteRequest(HttpServletRequest req,HttpServletResponse res)throws IOException{
        Callable<String> task = () -> {
            Integer userId = (Integer) req.getAttribute("user");
            Integer definitionId = Integer.parseInt(req.getParameter("dId"));
            CollectionQuery query = new CollectionQuery(userId);
            query.setDefinitionId(definitionId);
            service.removeCollection(query);

            ResponseEntity responseEntity = new ResponseEntity("success", "Record has been successfully inserted.");
                //API通信の結果を示す、仮打ちメッセージ
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            String json = mapper.writeValueAsString(responseEntity);
            return json;
        };
        handleAsyncRequest(task, res);
    }
}

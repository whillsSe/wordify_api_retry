package com.wordify.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.api.controller.utils.ControllerUtils;
import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.EntryQuery;
import com.wordify.api.service.entryService.EntryService;
import com.wordify.api.utils.ObjectMapperSingleton;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EntriesController extends AbstractController{
    private EntryService service;
    public EntriesController(ExecutorService executor,EntryService service){
        super(executor);
        this.service = service;
    }
    public void handleGetRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Callable<String> task = () -> {
            List<EntryDto> list = new ArrayList<EntryDto>();
            EntryQuery query = ControllerUtils.getEntryQuery(req);
            query.setWordString(req.getParameter("word"));
            query.setPhoneticString(req.getParameter("phonetic"));
            String tags = req.getParameter("tag");
            if(tags != null){
                query.setTagsStrings(Arrays.asList(tags.split(",")));
            }
            list = service.getEntries(query);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            String json = mapper.writeValueAsString(list);
            return json;
        };
        super.handleAsyncRequest(task, resp);
    }
    public void handlePostRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Callable<String> rask = () -> {
            String requestBody = ControllerUtils.readRequestBody(req);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            DefinitionDto requestedValue = mapper.readValue(requestBody,DefinitionDto.class);
            requestedValue.setAuthorId((Integer)req.getAttribute("user"));
            DefinitionDto dto = service.registerDefinition(requestedValue);
            String json = mapper.writeValueAsString(dto);
            return json;
        };
        handleAsyncRequest(rask, res);
    }
}

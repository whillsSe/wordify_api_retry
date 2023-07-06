package com.wordify.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.api.controller.utils.ControllerUtils;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.EntryQuery;
import com.wordify.api.service.EntryService;
import com.wordify.api.utils.ObjectMapperSingleton;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchEntriesController extends AbstractController{
    private EntryService service;
    public SearchEntriesController(ExecutorService executor,EntryService service){
        super(executor);
        this.service = service;
    }
    public void handleGetRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Callable<String> task = () -> {
            List<EntryDto> list = new ArrayList<EntryDto>();
            EntryQuery query = new EntryQuery((int)req.getAttribute("user"));
                //ここでreqから各パラメータの取得
            String[] pathParts = ControllerUtils.getPathParts(req);
            query.setScope(pathParts[0]);
            if(pathParts[1] != "_") query.setScopeId(Integer.parseInt(pathParts[1]));
            query.setWordString(req.getParameter("word"));
            query.setPhoneticString(req.getParameter("phonetic"));
            query.setTagsStrings(Arrays.asList(req.getParameter("tag").split(",")));

            list = service.getEntries(query);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            String json = mapper.writeValueAsString(list);
            return json;
        };
        super.handleAsyncRequest(task, resp);
    }
}

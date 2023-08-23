package com.wordify.api.controller;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.api.controller.utils.ControllerUtils;
import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.payloads.EntryDeletePayload;
import com.wordify.api.dto.payloads.EntryRegistrationPayload;
import com.wordify.api.dto.payloads.EntryUpdatePayload;
import com.wordify.api.service.definitionService.DefinitionService;
import com.wordify.api.utils.ObjectMapperSingleton;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DefinitionController extends AbstractController{
    private DefinitionService service;
    public DefinitionController(ExecutorService executor,DefinitionService service){
        super(executor);
        this.service = service;
    }

    public void handlePostRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Callable<String> task = () -> {
            String requestBody = ControllerUtils.readRequestBody(req);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            EntryRegistrationPayload requestedValue = mapper.readValue(requestBody,EntryRegistrationPayload.class);
            requestedValue.setUserId((Integer)req.getAttribute("user"));
            DefinitionDto dto = service.registerDefinition(requestedValue);
            String json = mapper.writeValueAsString(dto);
            return json;
        };
        handleAsyncRequest(task, res);
    }
    public void handlePutRequest(HttpServletRequest req,HttpServletResponse res)throws IOException{
        Callable<String> task = () -> {
            String requestBody = ControllerUtils.readRequestBody(req);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            EntryUpdatePayload requestedValue = mapper.readValue(requestBody,EntryUpdatePayload.class);
            Logger logger = Logger.getLogger(DefinitionController.class.getName());
            logger.info(Integer.toString(requestedValue.getEntryId()));
            requestedValue.setUserId((Integer)req.getAttribute("user"));
            DefinitionDto dto = service.updateDefinition(requestedValue);
            String json = mapper.writeValueAsString(dto);
            return json;
        };
        handleAsyncRequest(task, res);
    };
    public void handleDeleteRequest(HttpServletRequest req,HttpServletResponse res) throws IOException{
        Callable<String> task = () ->{
            String requestBody = ControllerUtils.readRequestBody(req);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            EntryDeletePayload requestedValue = mapper.readValue(requestBody,EntryDeletePayload.class);
            requestedValue.setUserId((Integer)req.getAttribute("user"));
            service.deleteDefinition(requestedValue);
            return generateJsonString(requestedValue.getId());
        };
        handleAsyncRequest(task, res);
    }
    private String generateJsonString(int id) {
        return String.format("{\"message\": \"Deletion was successful.\", \"deletedId\": %d}", id);
    }
    

    }


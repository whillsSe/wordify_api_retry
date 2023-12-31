package com.wordify.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordify.api.controller.utils.ControllerUtils;
import com.wordify.api.controller.viewmodel.ContextDtoViewModel;
import com.wordify.api.controller.viewmodel.EntryDtoViewModel;
import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.payloads.ContextRetrievalPayload;
import com.wordify.api.dto.payloads.EntrySearchPayload;
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
    public void handleGetEntriesRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Callable<String> task = () -> {
            List<EntryDto> list = new ArrayList<EntryDto>();
            EntrySearchPayload query = ControllerUtils.getEntryQuery(req);
            query.setWordString(req.getParameter("word"));
            query.setPhoneticString(req.getParameter("phonetic"));
            String tags = req.getParameter("tags");
            if(tags != null){
                query.setTagsStrings(Arrays.asList(tags.split(",")));
            }
            list = service.getEntries(query);
            List<EntryDtoViewModel> viewModels = new ArrayList<EntryDtoViewModel>();
            for(EntryDto dto :list){
                viewModels.add(convertEntryDtoToViewModel(dto));
            }
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            String json = mapper.writeValueAsString(viewModels);
            return json;
        };
        super.handleAsyncRequest(task, resp);
    }

    public void handleGetContextRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException{
        Callable<String> task = () -> {
            ContextRetrievalPayload query = ControllerUtils.getContextQuery(req);

            ContextDto dto = service.getContext(query);
            ObjectMapper mapper = ObjectMapperSingleton.getInstance();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String json = mapper.writeValueAsString(convertContextDtoToViewModel(dto));
            return json;
        };
        super.handleAsyncRequest(task, resp);
    }



    private ContextDtoViewModel convertContextDtoToViewModel(ContextDto dto){
        ContextDtoViewModel viewModel = new ContextDtoViewModel();
        viewModel.setDefinitions(dto.getDefinitionsList());
            EntryDtoViewModel prev = convertEntryDtoToViewModel(dto.getPrevEntry());
            viewModel.setPrev(prev);
            EntryDtoViewModel next = convertEntryDtoToViewModel(dto.getNextEntry());
            viewModel.setNext(next);
        return viewModel;
    }
    private EntryDtoViewModel convertEntryDtoToViewModel(EntryDto dto){
        EntryDtoViewModel viewModel = new EntryDtoViewModel();
        viewModel.setWord(dto.getWord().getValue());
        viewModel.setWordId(dto.getWord().getId());
        viewModel.setPhonetic(dto.getPhonetic().getValue());
        viewModel.setPhoneticId(dto.getPhonetic().getId());
        return viewModel;
    }
}

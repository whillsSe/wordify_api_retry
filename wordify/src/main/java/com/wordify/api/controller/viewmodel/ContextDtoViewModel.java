package com.wordify.api.controller.viewmodel;

import java.util.List;

import com.wordify.api.dto.DefinitionDto;
import com.wordify.api.dto.EntryDto;

public class ContextDtoViewModel {
    private EntryDto prevEntry;
    private EntryDto nextEntry;
    private List<DefinitionDto> definitions;
    public EntryDto getPrevEntry() {
        return prevEntry;
    }
    public void setPrevEntry(EntryDto prevEntry) {
        this.prevEntry = prevEntry;
    }
    public EntryDto getNextEntry() {
        return nextEntry;
    }
    public void setNextEntry(EntryDto nextEntry) {
        this.nextEntry = nextEntry;
    }
    public List<DefinitionDto> getDefinitions() {
        return definitions;
    }
    public void setDefinitions(List<DefinitionDto> definitions) {
        this.definitions = definitions;
    }
    
}

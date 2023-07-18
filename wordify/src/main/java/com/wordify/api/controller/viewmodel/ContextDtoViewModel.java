package com.wordify.api.controller.viewmodel;

import java.util.List;

import com.wordify.api.dto.DefinitionDto;

public class ContextDtoViewModel {
    private List<DefinitionDto> definitions;

    private EntryDtoViewModel prev;
    private EntryDtoViewModel next;
    
    public List<DefinitionDto> getDefinitions() {
        return definitions;
    }
    public void setDefinitions(List<DefinitionDto> definitions) {
        this.definitions = definitions;
    }
    public EntryDtoViewModel getPrev() {
        return prev;
    }
    public void setPrev(EntryDtoViewModel prev) {
        this.prev = prev;
    }
    public EntryDtoViewModel getNext() {
        return next;
    }
    public void setNext(EntryDtoViewModel next) {
        this.next = next;
    }
    
}

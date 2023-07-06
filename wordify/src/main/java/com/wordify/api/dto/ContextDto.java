package com.wordify.api.dto;

import java.util.List;

public class ContextDto {
    EntryDto prevEntry;
    EntryDto nextEntry;
    List<DefinitionDto> definitions;
    
    public ContextDto(EntryDto prevEntry, EntryDto nextEntry, List<DefinitionDto> definitions) {
        this.prevEntry = prevEntry;
        this.nextEntry = nextEntry;
        this.definitions = definitions;
    }
    public EntryDto getPrevEntry() {
        return prevEntry;
    }
    public EntryDto getNextEntry() {
        return nextEntry;
    }
    public List<DefinitionDto> getDefinitions() {
        return definitions;
    }
    
}

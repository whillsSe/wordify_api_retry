package com.wordify.api.dto;

import java.util.List;

public class ContextDto {
    EntryDto prevEntry;
    EntryDto nextEntry;
    List<DefinitionDto> definitionsList;//Mapにしてるけど、definitionの表示に優先順位をつけるならちょっと考えないといけない
    public ContextDto(EntryDto prevEntry, EntryDto nextEntry, List<DefinitionDto> definitionsList) {
        if(prevEntry != null) this.prevEntry = prevEntry;
        if(nextEntry != null) this.nextEntry = nextEntry;
        this.definitionsList = definitionsList;
    }
    public EntryDto getPrevEntry() {
        return prevEntry;
    }
    public EntryDto getNextEntry() {
        return nextEntry;
    }
    public List<DefinitionDto> getDefinitionsList() {
        return definitionsList;
    }
    
}

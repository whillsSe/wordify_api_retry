package com.wordify.api.dto;

import java.util.Map;

public class ContextDto {
    EntryDto prevEntry;
    EntryDto nextEntry;
    Map<Integer,DefinitionDto> definitionsMap;//Mapにしてるけど、definitionの表示に優先順位をつけるならちょっと考えないといけない
    
    public ContextDto(EntryDto prevEntry, EntryDto nextEntry, Map<Integer,DefinitionDto> definitionsMap) {
        this.prevEntry = prevEntry;
        this.nextEntry = nextEntry;
        this.definitionsMap = definitionsMap;
    }
    public EntryDto getPrevEntry() {
        return prevEntry;
    }
    public EntryDto getNextEntry() {
        return nextEntry;
    }
    public Map<Integer,DefinitionDto> getDefinitionsMap() {
        return definitionsMap;
    }
    
}

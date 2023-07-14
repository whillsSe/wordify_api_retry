package com.wordify.api.dto;

public class DefinitionDtoWithEntryInfo extends DefinitionDto{
    private BaseEntityDto word;
    private BaseEntityDto phonetic;
    public BaseEntityDto getWord() {
        return word;
    }
    public void setWord(BaseEntityDto word) {
        this.word = word;
    }
    public BaseEntityDto getPhonetic() {
        return phonetic;
    }
    public void setPhonetic(BaseEntityDto phonetic) {
        this.phonetic = phonetic;
    }
    
}

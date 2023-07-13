package com.wordify.api.dto;

public class EntryDto {//見出しの列挙を取得する時のDto
    BaseEntityDto word;
    BaseEntityDto phonetic;
    
    public EntryDto(BaseEntityDto word, BaseEntityDto phonetic) {
        this.word = word;
        this.phonetic = phonetic;
    }
    public BaseEntityDto getWord(){
        return word;
    }
    public BaseEntityDto getPhonetic(){
        return phonetic;
    }
    public void setWord(BaseEntityDto word) {
        this.word = word;
    }
    public void setPhonetic(BaseEntityDto phonetic) {
        this.phonetic = phonetic;
    }
    
}

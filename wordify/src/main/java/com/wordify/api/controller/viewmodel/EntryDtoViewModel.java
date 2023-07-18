package com.wordify.api.controller.viewmodel;

public class EntryDtoViewModel {
    private int wordId;
    private int phoneticId;
    private String word;
    private String phonetic;
    public int getWordId() {
        return wordId;
    }
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
    public int getPhoneticId() {
        return phoneticId;
    }
    public void setPhoneticId(int phoneticId) {
        this.phoneticId = phoneticId;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getPhonetic() {
        return phonetic;
    }
    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
    
}

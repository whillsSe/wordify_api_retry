package com.wordify.api.dto.params;

import java.util.ArrayList;
import java.util.List;

public class EntryQuery implements IScopeQuery{
    private int userId;
    private String scope;
    private int scopeId;
    private String wordString = "";
    private String phoneticString = "";
    private List<String> tagsStrings = new ArrayList<>();
    public EntryQuery(int userId){
        this.userId = userId;
    }
    @Override
    public int getUserId(){
        return this.userId;
    }
    @Override
    public String getScope() {
        return this.scope;
    }
    @Override
    public int getScopeId() {
        return this.scopeId;
    }
    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }
    @Override
    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getWordString() {
        return wordString;
    }
    public void setWordString(String wordString) {
        if(wordString != null){
            this.wordString = wordString;
        }
    }
    public String getPhoneticString() {
        return phoneticString;
    }
    public void setPhoneticString(String phoneticString) {
        if(phoneticString != null){
        this.phoneticString = phoneticString;
        }
    }
    public List<String> getTagsStrings() {
        return tagsStrings;
    }
    public void setTagsStrings(List<String> tagsStrings) {
    if(tagsStrings != null){
        this.tagsStrings = tagsStrings;
    }
    }
    
}

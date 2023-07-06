package com.wordify.api.dto.params;

public class ContextQuery implements IScopeQuery{
    private int userId;
    private String scope;
    private int scopeId;
    private int wordId;
    private int phoneticId;
    public ContextQuery(int userId){
        this.userId = userId;
    }
    @Override
    public int getUserId() {
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
    public void setScopeId(int scopeId){
        this.scopeId = scopeId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
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
    
}

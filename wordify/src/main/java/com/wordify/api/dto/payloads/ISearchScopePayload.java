package com.wordify.api.dto.payloads;

public interface ISearchScopePayload extends IBasePayload{
    public String getScope();
    public void setScope(String scope);
    public int getScopeId();
    public void setScopeId(int scopeId);
}

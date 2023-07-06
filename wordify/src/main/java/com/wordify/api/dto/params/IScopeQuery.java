package com.wordify.api.dto.params;

public interface IScopeQuery extends IBaseQuery{
    public String getScope();
    public void setScope(String scope);
    public int getScopeId();
    public void setScopeId(int scopeId);
}

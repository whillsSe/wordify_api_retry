package com.wordify.api.dto.payloads;

public class CollectionTargetPayload implements IBasePayload{
    private Integer userId;
    private Integer definitionId;
    
    public CollectionTargetPayload(Integer userId,Integer definitionId){
        this.userId = userId;
        this.definitionId = definitionId;
    }
    @Override
    public int getUserId() {
        return userId;
    }
    public Integer getDefinitionId() {
        return definitionId;
    }
    public void setDefinitionId(Integer definitionId) {
        this.definitionId = definitionId;
    }
    @Override
    public void setUserId(int Id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserId'");
    }
    
}

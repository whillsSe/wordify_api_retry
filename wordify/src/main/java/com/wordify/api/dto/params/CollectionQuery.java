package com.wordify.api.dto.params;

public class CollectionQuery implements IBaseQuery{
    private Integer userId;
    private Integer definitionId;
    
    public CollectionQuery(Integer userId){
        this.userId = userId;
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

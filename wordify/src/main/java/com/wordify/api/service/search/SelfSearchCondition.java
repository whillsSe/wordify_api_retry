package com.wordify.api.service.search;

import java.util.ArrayList;
import java.util.List;

public class SelfSearchCondition implements ISearchCondition{
    private List<String> params;
    public SelfSearchCondition(String id){
        this.params = new ArrayList<>();
        this.params.add(id);
    }
    @Override
    public String getRangeSubquery(){
        return "SELECT definition_id FROM collections WHERE user_id = ?";
    }
    @Override
    public List<String> getParams(){
        return this.params;
    }
}

package com.wordify.api.service.search;

import java.util.ArrayList;
import java.util.List;

public class FollowsSearchCondition implements ISearchCondition{
    private List<String> params;
    public FollowsSearchCondition(String id){
        this.params = new ArrayList<>();
        this.params.add(id);
    }
    @Override
    public String getRangeSubquery(){
        return "SELECT definition_id FROM collections WHERE user_id IN (SELECT following_id FROM follows WHERE follower_id = ?)";
    }
    @Override
    public List<String> getParams(){
        return this.params;
    }
}

package com.wordify.api.service.search;

import java.util.List;

import com.wordify.api.utils.SQLUtils;

public class SpecificSearchCondition implements ISearchCondition{
    private List<String> params;
    public SpecificSearchCondition(List<String> ids){
        this.params = ids;
    }
    @Override
    public String getRangeSubquery() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT definition_id FROM collections WHERE user_id IN (");
        SQLUtils.prepareQueryForElements(this.params.size(),sqlBuilder);
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }
    @Override
    public List<String> getParams() {
        return this.params;
    }
}

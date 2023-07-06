package com.wordify.api.dto.utils;

import java.util.List;

import com.wordify.api.dto.params.ICustomParam;

public class SubqueryResult {
    private String query;
    private List<ICustomParam> parameter;

    public SubqueryResult(String query, List<ICustomParam> parameter) {
        this.query = query;
        this.parameter = parameter;
    }

    public String getQuery() {
        return query;
    }

    public List<ICustomParam> getParameter() {
        return parameter;
    }
}


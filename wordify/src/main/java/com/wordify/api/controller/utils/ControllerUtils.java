package com.wordify.api.controller.utils;

import com.wordify.api.dto.params.ContextQuery;
import com.wordify.api.dto.params.EntryQuery;
import com.wordify.api.dto.params.IScopeQuery;

import jakarta.servlet.http.HttpServletRequest;

public class ControllerUtils {
    private static String[] getPathParts(HttpServletRequest req){
        return req.getPathInfo().split("/");
    }
    private static void getScopeInfo(String[] pathParts,IScopeQuery query){
        String scope = pathParts[0];
        if(scope != "self" && scope != "follows"){
            Integer scopeId = Integer.parseInt(pathParts[1]);
            query.setScopeId(scopeId);
        }
        query.setScope(scope);
    }
    public static EntryQuery getEntryQuery(HttpServletRequest req){
        Integer userId = (Integer)req.getAttribute("user");
        String[] pathParts = getPathParts(req);
        EntryQuery query = new EntryQuery(userId);
        getScopeInfo(pathParts,query);
        return query;
    }
    public static ContextQuery getContextQuery(HttpServletRequest req){
        Integer userId = (Integer)req.getAttribute("user");
        String[] pathParts = getPathParts(req);
        ContextQuery query = new ContextQuery(userId);
        getScopeInfo(pathParts, query);
        return query;
    }
}

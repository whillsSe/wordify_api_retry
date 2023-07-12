package com.wordify.api.controller.utils;

import java.io.BufferedReader;
import java.io.IOException;

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
        getContextInfo(pathParts, query);
        return query;
    }
    private static void getContextInfo(String[] pathParts,ContextQuery query){
        int marker = 0;
        if(pathParts[1] == "context"){
            marker = 2;
        }else if(pathParts[2] == "context"){
            marker = 3;
        }
        query.setPhoneticId(Integer.parseInt(pathParts[marker]));
        query.setWordId(Integer.parseInt(pathParts[marker+1]));
    }

    public static String readRequestBody(HttpServletRequest req) throws IOException{
        StringBuilder builder = new StringBuilder();
         try(BufferedReader reader = req.getReader()){
                String line;
                while((line = reader.readLine())!= null){
                    builder.append(line);
                }
                
            }
        return builder.toString();
    }
}

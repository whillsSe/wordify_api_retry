package com.wordify.api.controller.utils;

import java.io.BufferedReader;
import java.io.IOException;

import com.wordify.api.dto.payloads.ContextRetrievalPayload;
import com.wordify.api.dto.payloads.EntrySearchPayload;
import com.wordify.api.dto.payloads.ISearchScopePayload;

import jakarta.servlet.http.HttpServletRequest;

public class ControllerUtils {
    private static String[] getPathParts(HttpServletRequest req){
        return req.getPathInfo().split("/");
    }
    private static void getScopeInfo(String[] pathParts,ISearchScopePayload query){
        String scope = pathParts[0];
        if(scope != "self" && scope != "follows"){
            Integer scopeId = Integer.parseInt(pathParts[1]);
            query.setScopeId(scopeId);
        }
        query.setScope(scope);
    }
    public static EntrySearchPayload getEntryQuery(HttpServletRequest req){
        Integer userId = (Integer)req.getAttribute("user");
        String[] pathParts = getPathParts(req);
        EntrySearchPayload query = new EntrySearchPayload(userId);
        getScopeInfo(pathParts,query);
        return query;
    }
    public static ContextRetrievalPayload getContextQuery(HttpServletRequest req){
        Integer userId = (Integer)req.getAttribute("user");
        String[] pathParts = getPathParts(req);
        ContextRetrievalPayload query = new ContextRetrievalPayload(userId);
        getScopeInfo(pathParts, query);
        getContextInfo(pathParts, query);
        return query;
    }
    private static void getContextInfo(String[] pathParts,ContextRetrievalPayload query){
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

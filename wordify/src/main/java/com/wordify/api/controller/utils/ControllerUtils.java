package com.wordify.api.controller.utils;

import java.io.BufferedReader;
import java.io.IOException;

import org.jboss.logging.Logger;

import com.wordify.api.dto.payloads.ContextRetrievalPayload;
import com.wordify.api.dto.payloads.EntrySearchPayload;
import com.wordify.api.dto.payloads.ISearchScopePayload;

import jakarta.servlet.http.HttpServletRequest;

public class ControllerUtils {
    private static String[] getPathParts(HttpServletRequest req){
        //String pathInfo = req.getPathInfo();
        //String[] pathArr = pathInfo.split("/");

        //Logger logger = Logger.getLogger(ControllerUtils.class.getName());
        //logger.info(req.getPathInfo());//"/self"
        //logger.info(pathArr[0]);//""
        //logger.info(pathArr[1]);//"self"
        return req.getPathInfo().split("/");
    }
    private static void getScopeInfo(String[] pathParts,ISearchScopePayload query){
        String scope = pathParts[1];
        //Logger logger = Logger.getLogger(ControllerUtils.class.getName());
        //logger.info(scope);
        if(!scope.equals("self") && !scope.equals("follows")){
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
        query.setWordId(Integer.parseInt(req.getParameter("wId")));
        query.setPhoneticId(Integer.parseInt(req.getParameter("pId")));
        return query;
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

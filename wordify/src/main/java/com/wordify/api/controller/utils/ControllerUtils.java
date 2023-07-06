package com.wordify.api.controller.utils;

import jakarta.servlet.http.HttpServletRequest;

public class ControllerUtils {
    public static String[] getPathParts(HttpServletRequest req){
        return req.getPathInfo().split("/");
    }
}

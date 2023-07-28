package com.wordify.auth.controller.controllerUtils;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

public class ControllerUtils {
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

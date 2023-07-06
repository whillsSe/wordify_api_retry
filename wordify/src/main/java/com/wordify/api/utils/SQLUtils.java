package com.wordify.api.utils;

public class SQLUtils {
    public static void prepareQueryForElements(int numElements,StringBuilder builder){
        for(int i = 0;i<numElements;i++){
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
    }
    public static void appendPercentSymbol(StringBuilder builder){
        builder.append("%");
    }
}

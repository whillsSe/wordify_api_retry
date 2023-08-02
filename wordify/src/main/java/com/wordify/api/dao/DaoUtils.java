package com.wordify.api.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.wordify.api.dto.payloads.params.ICustomParam;
import com.wordify.api.dto.payloads.params.IntParam;
import com.wordify.api.dto.payloads.params.StringParam;

public class DaoUtils {
    public static int setParameters(PreparedStatement pstmt, List<ICustomParam> params,int count) throws SQLException {
        Logger logger = Logger.getLogger(DaoUtils.class.getName());
    for (ICustomParam param : params) {
        Object value = param.getValue();
        if (value instanceof Integer) {
            pstmt.setInt(++count, (Integer) value);
        } else if (value instanceof String) {
            pstmt.setString(++count, (String) value);
        }
        logger.info("Count is" + count);
        // 他のデータ型に対する判定とセット処理を追加することもできます
    }
    return count;
    }
    public static List<ICustomParam> parseIntegerToICustomParams(List<Integer> list){
        List<ICustomParam> params = new ArrayList<>();
        for(Integer param:list){
            params.add(new IntParam(param));
        }
        return params;
    }
    public static List<ICustomParam> parseStringToICustomParams(List<String> list){
        List<ICustomParam> params = new ArrayList<>();
        for(String param:list){
            params.add(new StringParam(param));
        }
        return params;
    }
    public static StringBuilder createStringBuilder(String columnName){
        String tableName = columnName + "s";
        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(tableName).append("(").append(columnName).append(") VALUES (?) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id),").append(columnName).append("=VALUES(").append(columnName).append(")");
        return builder;
    }
}

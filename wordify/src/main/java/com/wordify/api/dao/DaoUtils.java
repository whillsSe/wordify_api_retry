package com.wordify.api.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.wordify.api.dto.params.ICustomParam;

public class DaoUtils {
    public static void setParameters(PreparedStatement pstmt, List<ICustomParam> params,int count) throws SQLException {
    for (ICustomParam param : params) {
        Object value = param.getValue();
        if (value instanceof Integer) {
            pstmt.setInt(++count, (Integer) value);
        } else if (value instanceof String) {
            pstmt.setString(++count, (String) value);
        }
        // 他のデータ型に対する判定とセット処理を追加することもできます
    }
}
}

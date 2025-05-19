package com.tzs.antique.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class SqlUtil {

    /**
     * json to sql
     *
     * @return sql
     */
    public static String jsonToInsertSql(String tableName, JSONObject jsonObject) {
        String sqlStr = "INSERT INTO " + tableName + " (%cloums%) VALUES (%value%);\r\n";
        String cloums = "create_time,";
        String values = "now(),";
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String cloum = entry.getKey();
            Object value = entry.getValue();
            if (cloum.contains("_ip")) {
                value = "INET_ATON(" + value + ")";
            }
            cloums += cloum + ",";
            values += "'" + value + "'" + ",";
        }
        cloums = cloums.substring(0, cloums.length() - 1);
        values = values.substring(0, values.length() - 1);
        sqlStr = sqlStr.replaceAll("%cloums%", cloums);
        sqlStr = sqlStr.replaceAll("%value%", values);
        return sqlStr;
    }

    /***
     *  json to update sql
     *
     */
    public static String jsonToUpdateSql(String tableName, JSONObject jsonObject) {
        String updateSql = "UPDATE " + tableName + " SET %cloums% WHERE perim_area_id=" + jsonObject.getString("perim_area_id") + ";\r\n";
        String cloums = "update_time = now(),";
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String cloum = entry.getKey();
            Object value = entry.getValue();
            if (cloum.contains("_ip")) {
                value = "INET_ATON(" + value + ")";
            }
            cloums += cloum + " = '" + value + "',";
        }
        cloums = cloums.substring(0, cloums.length() - 1);

        updateSql = updateSql.replaceAll("%cloums%", cloums);
        return updateSql;
    }
}

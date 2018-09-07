package com.xuexibao.fastgun.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.Map;


public class MapUtil {

    /**
     * json 转 map
     * @param json
     * @return
     */
    public static Map<String, Object> json2Map(String json) {
        Map<String, Object> map;
            Gson gson = new Gson();
            map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
                }.getType());

        return map;
    }


}

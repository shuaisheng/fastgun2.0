package com.github.shuaisheng.fastgun.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class JsonUtil {

    /**
     * Object转json
     * @param object
     * @return
     */
    public  static  String object2Json(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = new Gson();
        return  gson.toJson(object);
}

    /**
     * 取出 json的key值
     * @param json
     * @param key
     * @return
     */
    public static String getValue(String json,String key){
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);
        return (String) map.get(key);
    }

    /**
     * 取出 json的key值返回int类型
     * @param json
     * @param key
     * @return
     */
    public static int getIntValue(String json,String key){
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);
        return (int)map.get(key) ;
    }



}

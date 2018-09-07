package com.xuexibao.fastgun.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
}

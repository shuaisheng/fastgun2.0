package com.xuexibao.fastgun.utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;


public class BsonUtil {
    public static Map<String, Object> documentToMap(Document document) {
        Set<Entry<String, Object>> entrySet = document.entrySet();
        Iterator<Entry<String, Object>> it = entrySet.iterator();
        Map<String, Object> map = new HashMap<String, Object>();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }


}

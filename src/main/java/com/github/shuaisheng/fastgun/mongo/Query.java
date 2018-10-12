package com.github.shuaisheng.fastgun.mongo;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;

public class Query {
    
    public static Map<String, Object> eq(String fieldName, Object value) {
        Map<String, Object> query = new HashMap<>();
        query.put(fieldName, value);
        return query;
    }
    
    public static Map<String, Object> regex(String fieldName, Pattern pattern) {
        Map<String, Object> query = new HashMap<>();
        query.put(fieldName, pattern);
        return query;
    }
    
    public static Map<String, Object> ne( String fieldName, Object value) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> neMap = new HashMap<>();
        neMap.put("$ne", value);
        query.put(fieldName, neMap);
        return query;
    }
    
    public static <T>Map<String, Object> in(String fieldName, Collection<T> c) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> inMap = new HashMap<>();
        inMap.put("$in", c);
        query.put(fieldName, inMap);
        return query;
    }
    
    public static <T>Map<String, Object> idIn(Collection<String> c) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> inMap = new HashMap<>();
        List<ObjectId> idlist = new LinkedList<>();
        for (String s : c) {
            idlist.add(new ObjectId(s));
        }
        inMap.put("$in", idlist);
        query.put("_id", inMap);
        return query;
    }
    
    public static Map<String, Object> gt(String fieldName, Object value) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> gtMap = new HashMap<>();
        gtMap.put("$gt", value);
        query.put(fieldName, gtMap);
        return query;
    }
    
    public static Map<String, Object> gte(String fieldName, Object value) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> gteMap = new HashMap<>();
        gteMap.put("$gte", value);
        query.put(fieldName, gteMap);
        return query;
    }
    
    public static Map<String, Object> lt(String fieldName, Object value) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> ltMap = new HashMap<>();
        ltMap.put("$lt", value);
        query.put(fieldName, ltMap);
        return query;
    }
    
    public static Map<String, Object> lte(String fieldName, Object value) {
        Map<String, Object> query = new HashMap<>();
        Map<String, Object> lteMap = new HashMap<>();
        lteMap.put("$lte", value);
        query.put(fieldName, lteMap);
        return query;
    }
    
    public static Map<String, Object> or(@SuppressWarnings("unchecked") Map<String, Object>... maps) {
        Map<String, Object> query = new HashMap<>();
        List<Map<String, Object>> orList = new LinkedList<>();
        for (Map<String, Object> m : maps) {
            orList.add(m);
        }
        query.put("$or", orList);
        return query;
    }
    
    @SafeVarargs
    public static Map<String, Object> and(Map<String, Object>... maps) {
        Map<String, Object> query = new HashMap<>();
        List<Map<String, Object>> andList = new LinkedList<>();
        for (Map<String, Object> m : maps) {
            andList.add(m);
        }
        query.put("$and", andList);
        return query;
    }
}

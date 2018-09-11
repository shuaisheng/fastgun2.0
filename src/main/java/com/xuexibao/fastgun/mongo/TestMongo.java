package com.xuexibao.fastgun.mongo;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class TestMongo {
    public static void main(String[] args) {
        XMongoPool xuexibao2 = new XMongoPool("192.168.1.12:27017", "liveaa2", "9d599#$Aed67326be", "xuexibao2");
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("mobile", "15827292946");
        query = xuexibao2.findOne("users", query);
        System.out.println(JSON.toJSONString(query));
    }
}

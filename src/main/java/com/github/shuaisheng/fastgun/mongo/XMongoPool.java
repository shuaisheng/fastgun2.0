package com.github.shuaisheng.fastgun.mongo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.shuaisheng.fastgun.utils.BsonUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.javatuples.Pair;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;


import com.github.shuaisheng.fastgun.utils.ObjectUtil;


public class XMongoPool {
    private MongoClient mongoClient;
    private String database;

    public XMongoPool(String url, String user, String password, String database) {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(200);
        builder.maxWaitTime(3000);
        builder.connectTimeout(1000);
        String uri = String.format("mongodb://%s:%s@%s/%s", user, password, url, database);
        mongoClient = new MongoClient(new MongoClientURI(uri, builder));
        this.database = database;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoClient.getDatabase(this.database).getCollection(collectionName);
    }

    @SuppressWarnings("deprecation")
    public DBCollection getDBCollection(String collectionName) {
        return mongoClient.getDB(this.database).getCollection(collectionName);
    }

    public MongoCollection<Document> getCollection(String databaseName, String collectionName) {
        return mongoClient.getDatabase(databaseName).getCollection(collectionName);
    }

    public Map<String, Object> findOne(String collectionName, Map<String, Object> query) {
        MongoCollection<Document> collection = this.getCollection(collectionName);
        FindIterable<Document> docs = collection.find(new Document(query));
        MongoCursor<Document> it = docs.iterator();
        if (it.hasNext()) {
            Document doc = it.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            return map;
        }
        return null;
    }

    public void insertOne(String collectionName, Map<String, Object> doc) {
        MongoCollection<Document> col = this.getCollection(collectionName);
        Document document = new Document(doc);
        col.insertOne(document);
    }

    public void insertMany(String collectionName, List<Map<String, Object>> docs) {
        MongoCollection<Document> col = this.getCollection(collectionName);
        List<Document> list = new LinkedList<Document>();
        Iterator<Map<String, Object>> it = docs.iterator();
        while (it.hasNext()) {
            Document doc = new Document(it.next());
            list.add(doc);
        }
        col.insertMany(list);
    }
    
    public Long count(String collectionName, Map<String, Object> query) {
        MongoCollection<Document> collection = this.getCollection(collectionName);
        return collection.count(new Document(query));
    }
    
    @SuppressWarnings("rawtypes")
    public List distinct(String collectionName, String fieldName, Map<String, Object> query) {
        DBCollection collection = this.getDBCollection(collectionName);
        return collection.distinct(fieldName, new BasicDBObject(query));
    }
    
    public List<Map<String, Object>> find(String collectionName, Map<String, Object> query) {
        MongoCollection<Document> collection = this.getCollection(collectionName);
        FindIterable<Document> docs = collection.find(new Document(query));
        MongoCursor<Document> it = docs.iterator();
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        while (it.hasNext()) {
            Document doc = it.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            list.add(map);
        }
        return list;
    }
    
    public List<Map<String, Object>> findWithProjection(String collectionName, Map<String, Object> query, Map<String, Object> projection) {
        MongoCollection<Document> collection = this.getCollection(collectionName);
        FindIterable<Document> docs = collection.find(new Document(query)).projection(new Document(projection));
        MongoCursor<Document> it = docs.iterator();
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        while (it.hasNext()) {
            Document doc = it.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            list.add(map);
        }
        return list;
    }
    
    /**
     * 根据搜索条件和排序条件获取数据
     */
    public List<Map<String, Object>> find(String collectionName, Map<String, Object> query, 
            Map<String, Object> sortMap, Integer page, Integer pageSize) {
        MongoCollection<Document> collection = this.getCollection(collectionName);
        FindIterable<Document> docs = collection.find(new Document(query));
        if (sortMap != null) {
            docs = docs.sort(new Document(sortMap));
        }
        if (page != null && pageSize != null) {
            docs = docs.skip((page - 1) * pageSize).limit(pageSize);
        }
        MongoCursor<Document> it = docs.iterator();
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        while (it.hasNext()) {
            Document doc = it.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            list.add(map);
        }
        return list;
    }
    
    public List<String> findIds(String collectionName, Map<String, Object> query) {
        MongoCollection<Document> collection = this.getCollection(collectionName);
        FindIterable<Document> docs = collection.find(new Document(query));
        MongoCursor<Document> it = docs.iterator();
        List<String> list = new LinkedList<String>();
        while (it.hasNext()) {
            Document doc = it.next();
            list.add(doc.get("_id").toString());
        }
        return list;
    }

    public <T> List<T> find(String collectionName, Map<String, Object> query, Map<String, Object> sortMap,
            Class<T> clazz, Integer page, Integer pageSize) throws InstantiationException, IllegalAccessException {
        MongoCollection<Document> collection = this.getCollection(collectionName);
        FindIterable<Document> docs = collection.find(new Document(query));
        if (sortMap != null) {
            docs = docs.sort(new Document(sortMap));
        }
        if (page != null && pageSize != null) {
            docs = docs .skip((page - 1) * pageSize).limit(pageSize);
        }
        MongoCursor<Document> it = docs.iterator();
        List<T> list = new LinkedList<T>();
        while (it.hasNext()) {
            Document doc = it.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            T obj = ObjectUtil.getObjectByMap(clazz.newInstance(), map);

            list.add(obj);
        }
        return list;
    }

    // 通过 ObjectId $in 来查询方法
    public List<Map<String, Object>> getDocumentByObjectIdInList(String collectionName, List<String> objectIdInList) {
        List<Map<String, Object>> resultList = new LinkedList<>();
        MongoCollection<Document> collection = this.getCollection(collectionName);
        List<ObjectId> list = new LinkedList<>();
        for (String crmId : objectIdInList) {
            list.add(new ObjectId(crmId));
        }
        Document in = new Document("$in", list);
        Document query = new Document("_id", in);
        MongoCursor<Document> iterator = collection.find(query).iterator();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            resultList.add(map);
        }
        return resultList;
    }

    public Map<String, Map<String, Object>> getDocumentMapByObjectIdInList(String collectionName,
            Collection<String> objectIdInList) {
        Map<String, Map<String, Object>> resultMap = new HashMap<>();
        MongoCollection<Document> collection = this.getCollection(collectionName);
        List<ObjectId> list = new LinkedList<>();
        for (String crmId : objectIdInList) {
            list.add(new ObjectId(crmId));
        }
        Document in = new Document("$in", list);
        Document query = new Document("_id", in);
        MongoCursor<Document> iterator = collection.find(query).iterator();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            resultMap.put(doc.get("_id").toString(), map);
        }
        return resultMap;
    }

    // 通过 字段 $in 来查询方法
    public <T>List<Map<String, Object>> getDocumentByFieldInList(String collectionName, Collection<T> inList,
            String fieldName) {
        List<Map<String, Object>> resultList = new LinkedList<>();
        MongoCollection<Document> collection = this.getCollection(collectionName);
        Document in = new Document("$in", inList);
        Document query = new Document(fieldName, in);
        MongoCursor<Document> iterator = collection.find(query).iterator();
        while (iterator.hasNext()) {
            Document doc = iterator.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            resultList.add(map);
        }
        return resultList;
    }

    public Pair<Long, Long> upsetOne(String collectionName, Map<String, Object> query, Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document("$set", update);
        UpdateResult rs = col.updateOne(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }
    
    public Pair<Long, Long> unsetOne(String collectionName, Map<String, Object> query, Map<String, Object> unSetMap) {
        MongoCollection<Document> col = getCollection(collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document("$unset", unSetMap);
        UpdateResult rs = col.updateOne(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public Pair<Long, Long> upsetMany(String collectionName, Map<String, Object> query, Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document("$set", update);
        UpdateResult rs = col.updateMany(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public Pair<Long, Long> updateOne(String collectionName, Map<String, Object> query, Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document(update);
        UpdateResult rs = col.updateOne(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public Pair<Long, Long> updateMany(String collectionName, Map<String, Object> query, Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document(update);
        UpdateResult rs = col.updateMany(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }
    
    public Pair<Long, Long> unsetMany(String collectionName, Map<String, Object> query, Map<String, Object> unSetMap) {
        MongoCollection<Document> col = getCollection(collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document("$unset", unSetMap);
        UpdateResult rs = col.updateMany(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public long deleteOne(String collectionName, Map<String, Object> query) {
        MongoCollection<Document> col = getCollection(collectionName);
        Document queryDocument = new Document(query);
        DeleteResult rs = col.deleteOne(queryDocument);
        return rs.getDeletedCount();
    }

    /**
     *
     * 下面的是带数据库名的
     *
     */

    public Map<String, Object> findOne(String databaseName, String collectionName, Map<String, Object> query) {
        MongoCollection<Document> collection = this.getCollection(databaseName, collectionName);
        FindIterable<Document> docs = collection.find(new Document(query));
        MongoCursor<Document> it = docs.iterator();
        if (it.hasNext()) {
            Document doc = it.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            return map;
        }
        return null;
    }

    public List<Map<String, Object>> find(String databaseName, String collectionName, Map<String, Object> query) {
        MongoCollection<Document> collection = this.getCollection(databaseName, collectionName);
        FindIterable<Document> docs = collection.find(new Document(query));
        MongoCursor<Document> it = docs.iterator();
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        while (it.hasNext()) {
            Document doc = it.next();
            Map<String, Object> map = BsonUtil.documentToMap(doc);
            list.add(map);
        }
        return list;
    }

    public Pair<Long, Long> upsetOne(String databaseName, String collectionName, Map<String, Object> query,
            Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(databaseName, collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document("$set", update);
        UpdateResult rs = col.updateOne(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public Pair<Long, Long> upsetMany(String databaseName, String collectionName, Map<String, Object> query,
            Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(databaseName, collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document("$set", update);
        UpdateResult rs = col.updateMany(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public Pair<Long, Long> updateOne(String databaseName, String collectionName, Map<String, Object> query,
            Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(databaseName, collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document(update);
        UpdateResult rs = col.updateOne(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public Pair<Long, Long> updateMany(String databaseName, String collectionName, Map<String, Object> query,
            Map<String, Object> update) {
        MongoCollection<Document> col = getCollection(databaseName, collectionName);
        Document queryDocument = new Document(query);
        Document updateDocument = new Document(update);
        UpdateResult rs = col.updateMany(queryDocument, updateDocument);
        return new Pair<Long, Long>(rs.getMatchedCount(), rs.getModifiedCount());
    }

    public long deleteOne(String databaseName, String collectionName, Map<String, Object> query) {
        MongoCollection<Document> col = getCollection(databaseName, collectionName);
        Document queryDocument = new Document(query);
        DeleteResult rs = col.deleteOne(queryDocument);
        return rs.getDeletedCount();
    }
}

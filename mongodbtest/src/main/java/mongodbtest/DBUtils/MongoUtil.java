package mongodbtest.DBUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * @ClassName MongoUtil
 * @Description TODO
 * @Author WXY
 * @Date 2019-01-22 14:00
 **/
public class MongoUtil {

    static final MongoUtil instance = new MongoUtil();

    private static MongoClient mongoClient;

    static {
        System.out.println("=====MongoDBUtil初始化=====");
        String ip = "localhost";
        int port = 27017;
        instance.mongoClient = new MongoClient(ip, port);
        MongoClientOptions.Builder options = new MongoClientOptions.Builder();
        options.cursorFinalizerEnabled(true);
        options.connectionsPerHost(300);
        options.connectTimeout(3000);
        options.maxWaitTime(5000);
        options.socketTimeout(0);
        options.threadsAllowedToBlockForConnectionMultiplier(5000);
        options.writeConcern(WriteConcern.ACKNOWLEDGED);
        options.build();
    }

    public MongoDatabase getDB(String dbName) {
        if (null != dbName && !"".equals(dbName)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            return database;
        }
        return null;
    }

    public MongoCollection<Document> getCollection(String dbName, String collName) {
        if (null == collName || "".equals(collName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
        return collection;
    }

    public List<String> getAllCollections(String dbName) {
        MongoIterable<String> colls = getDB(dbName).listCollectionNames();
        List<String> _list = new ArrayList<>();
        for (String s : colls) {
            _list.add(s);
        }
        return _list;
    }

    public MongoIterable<String> getAllDBNames() {
        MongoIterable<String> s = mongoClient.listDatabaseNames();
        return s;
    }

    public void dropDB(String dbName) {
        getDB(dbName).drop();
    }

    public Document findById(MongoCollection<Document> coll, String id) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        Document myDoc = coll.find(eq("_id", _idobj)).first();
        return myDoc;
    }

    public Integer getCount(MongoCollection<Document> coll) {
        Integer count = Math.toIntExact(coll.countDocuments());
        return count;
    }

    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).iterator();
    }

    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }

    public Document updateById(MongoCollection<Document> coll, String id, Document newDoc) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        Bson filter = eq("_id", _idobj);
        coll.replaceOne(filter, newDoc);
        coll.updateOne(filter, new Document("$set", newDoc));
        return newDoc;
    }

    public void dropCollection(String dbName, String collName) {
        getDB(dbName).getCollection(collName).drop();
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    public static void main(String[] args) {
        String dbName = "test";
        String collName = "mongotest";
        MongoCollection<Document> coll = MongoUtil.instance.getCollection(dbName, collName);

        MongoUtil.instance.getDB(dbName).getCollection(collName).find();
        coll.createIndex(new Document("",1));




//        ListIndexesIterable<Document> list = coll.listIndexes();
//        for (Document document : list) {
//            System.out.println(document.toJson());
//        }
//        coll.find(and(eq("like", 100), Filters.lt("description", "database")));
//        coll.find(and(eq("like", 100), lt("description", "database")));
//        coll.find().sort(new Document());
        MongoCursor<Document> list = MongoUtil.instance.find(coll, Filters.eq("gender", "male"));
        while (list.hasNext()) {
            Document _doc = list.next();
            System.out.println(_doc.toString());
        }
        list.close();
    }

}

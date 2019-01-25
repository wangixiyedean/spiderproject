package mongodbtest.DBUtils;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @ClassName MongoDBJDBC
 * @Description TODO
 * @Author WXY
 * @Date 2019-01-16 13:40
 **/
public class MongoDBJDBC {
    public static void main(String args[]) {
        try {
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase mongoDatabase = client.getDatabase("test");
            System.out.println("Connect to database successfully");
//            mongoDatabase.createCollection("mongotest");
//            System.out.println("集合创建成功");
            MongoCollection<Document> collection = mongoDatabase.getCollection("mongotest");
            System.out.println("集合monogotest选择成功");

            Document document = new Document("title", "MongoDB").
                    append("description", "database").
                    append("likes", 300).
                    append("by", "Fly").
                    append("gender", "female");
            List<Document> documents = new ArrayList<Document>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插入成功");

//            collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));

//            FindIterable<Document> findIterable = collection.find();
//            List<Integer> dateList = new ArrayList<>();
//            List<String> dateListString = new ArrayList<>();
//            for (Document document : findIterable) {
//                ObjectId objectId = (ObjectId) document.get("_id");
//                dateListString.add(objectId.getDate().toString());
//                Integer date = objectId.getTimestamp();
//                dateList.add(date);
//                System.out.println(document);
//            }
//            System.out.println(dateList);
//            System.out.println(dateListString);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

//    private String timeStampToDate(Integer timeStamp) {
//        String date = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        date = sdf.format(new Date(timeStamp));
//        return date;
//    }
}

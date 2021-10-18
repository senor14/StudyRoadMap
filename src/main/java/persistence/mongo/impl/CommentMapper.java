package persistence.mongo.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import persistence.mongo.ICommentMapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component("CommentMapper")
public class CommentMapper implements ICommentMapper {

    @Autowired
    private MongoTemplate mongodb;

    @Override
    public JSONArray getComment(String studyRoadId) {
        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("Comment");

            Document query = new Document();

            query.append("roadId", studyRoadId);

            Document projection = new Document();

            projection.append("commentId", "$commentId");
            projection.append("commentContents", "$commentContents");
            projection.append("userId", "$userId");
            projection.append("created", "$created");
            projection.append("_id", 0);

            Document sort = new Document();

            sort.append("created", -1);

            collection.find(query).projection(projection).sort(sort).forEach(studyRoadMap::add);

        }catch (Exception e){
            e.printStackTrace();
        }

        return studyRoadMap;
    }

    @Override
    public JSONArray findMyComment(String roadMapId, String userUuid) {
        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("Comment");
            Document query = new Document();

            query.append("roadId", roadMapId);
            query.append("userUuid", userUuid);

            Document projection = new Document();

            projection.append("commentId", "$commentId");
            projection.append("_id", 0);

            Document sort = new Document();

            sort.append("created", -1);

            collection.find(query).projection(projection).sort(sort).forEach(studyRoadMap::add);
        }catch (Exception e){
            e.printStackTrace();
        }
        return studyRoadMap;
    }

    @Override
    public boolean insertComment(Map<String, Object> pMap) {
        log.info(this.getClass().getName());
        try {
            MongoCollection<Document> collection = mongodb.getCollection("Comment");
            collection.insertOne(new Document(pMap));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteComment(String commentId, String userUuid) {
        Map<String, Object> pMap =new HashMap<>();
        pMap.put("commentId",commentId);
        pMap.put("userUuid",userUuid);
        try {
            MongoCollection<Document> collection = mongodb.getCollection("Comment");

            FindIterable<Document> dRs = collection.find(new Document(pMap));

            Iterator<Document> cursor = dRs.iterator();

            DeleteResult deleteResult = null;
            int res = 0;

            if (cursor.hasNext()) {

                while (cursor.hasNext()) {
                    deleteResult = collection.deleteOne(cursor.next());
                }

                res = (int) deleteResult.getDeletedCount();

            }
            cursor = null;
            dRs = null;
            collection = null;
            deleteResult = null;

            if (res == 0) return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


}

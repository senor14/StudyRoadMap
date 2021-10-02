package persistence.mongo.impl;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import persistence.mongo.ICommunityMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("CommunityMapper")
public class CommunityMapper implements ICommunityMapper {

    @Autowired
    private MongoTemplate mongodb;

    @Override
    public JSONArray getStudyRoadMap() {

        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("StudyRoadMap");

            Document query = new Document();
            query.append("public", "Y");

            Document projection = new Document();

            projection.append("road_id", "$road_id");
            projection.append("road_category", "$road_category");
            projection.append("road_title", "$road_title");
            projection.append("user_uuid", "$user_uuid");
            projection.append("created", "$created");
            projection.append("_id", 0);

            Document sort = new Document();

            sort.append("created", -1);

            collection.find(query).projection(projection).forEach(studyRoadMap::add);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return studyRoadMap;
    }

    @Override
    public JSONArray getCareerRoadMap() {

        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("CareerRoadMap");

            Document query = new Document();
            query.append("public", "Y");

            Document projection = new Document();

            projection.append("career_id", "$career_id");
            projection.append("user_uuid", "$user_uuid");
            projection.append("created", "$created");
            projection.append("_id", 0);

            Document sort = new Document();

            sort.append("created", -1);

            collection.find(query).projection(projection).forEach(studyRoadMap::add);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return studyRoadMap;
    }

    @Override
    public JSONArray findStudyRoadMap(String searchType, String keyWord) {

        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();
        String[] words = keyWord.split(" ");

        try {
            MongoCollection<Document> collection = mongodb.getCollection("StudyRoadMap");

            Document query = new Document();

            // 조건 Document 리스트 생성
            List<Document> conditions= new ArrayList<>();
            conditions.add(new Document().append("public", "Y"));
            for(String word : words){
                conditions.add(new Document().append(searchType, new BsonRegularExpression("^.*"+word+".*$", "i")));
            }

            Document projection = new Document();

            projection.append("road_id", "$road_id");
            projection.append("road_category", "$road_category");
            projection.append("road_title", "$road_title");
            projection.append("user_uuid", "$user_uuid");
            projection.append("created", "$created");
            projection.append("_id", 0);

            Document sort = new Document();

            sort.append("created", -1);

            collection.find(query).projection(projection).forEach(studyRoadMap::add);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return studyRoadMap;
    }

    @Override
    public JSONArray findCareerRoadMap(String keyWord) {
        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();
        String[] words = keyWord.split(" ");

        try {
            MongoCollection<Document> collection = mongodb.getCollection("StudyRoadMap");

            Document query = new Document();

            // 조건 Document 리스트 생성
            List<Document> conditions= new ArrayList<>();

            conditions.add(new Document().append("public", "Y"));
            for(String word : words){
                conditions.add(new Document().append("career_title", new BsonRegularExpression("^.*"+word+".*$", "i")));
            }

            Document projection = new Document();

            projection.append("career_id", "$career_id");
            projection.append("career_title", "$career_title");
            projection.append("user_uuid", "$user_uuid");
            projection.append("created", "$created");
            projection.append("_id", 0);

            Document sort = new Document();

            sort.append("created", -1);

            collection.find(query).projection(projection).forEach(studyRoadMap::add);

        } catch (Exception e) {
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
    public JSONArray getComment(String studyRoad_id) {
        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("Comment");

            Document query = new Document();

            query.append("studyRoad_id", studyRoad_id);

            Document projection = new Document();

            projection.append("comment_contents", "$comment_contents");
            projection.append("user_id", "$user_id");
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


}

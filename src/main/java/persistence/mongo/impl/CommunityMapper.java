package persistence.mongo.impl;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

    @Override  //select road_id,road_node.node_category,road_title,user_uuid,created from StudyRoadMap where public ="Y" order by created desc
    public JSONArray getStudyRoadMap() {

        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("StudyRoadMap");

            Document query = new Document();
//            query.append("public", "Y");

            Document projection = new Document();

            projection.append("road_id", "$road_id");
            projection.append("road_node.node_category", "$road_node.node_category");
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

    @Override  //select career_id,career_title,user_uuid,created from CareerRoadMap where public = "Y" order by created desc
    public JSONArray getCareerRoadMap() {

        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("CareerRoadMap");

            Document query = new Document();
            query.append("public", "Y");

            Document projection = new Document();

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

    @Override  //select road_id,road_node.node_category,road_title,user_uuid,created from StudyRoadMap where public ="Y" and road_node.node_category like "%%" order by created desc
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

            query.append("$and", conditions);

            Document projection = new Document();

            projection.append("road_id", "$road_id");
            projection.append("road_nodeDataArray.node_category", "$road_nodeDataArray.node_category");
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

    @Override  //select career_id,career_title,user_uuid,created from CareerRoadMap where public = "Y" and career_title like "%나의%" order by created desc
    public JSONArray findCareerRoadMap(String keyWord) {
        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();
        String[] words = keyWord.split(" ");

        try {
            MongoCollection<Document> collection = mongodb.getCollection("CareerRoadMap");

            Document query = new Document();

            // 조건 Document 리스트 생성
            List<Document> conditions= new ArrayList<>();

            conditions.add(new Document().append("public", "Y"));
            for(String word : words){
                conditions.add(new Document().append("career_title", new BsonRegularExpression("^.*"+word+".*$", "i")));
            }
            query.append("$and", conditions);

            Document projection = new Document();

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

    @Override
    public boolean copyRoadMap(String oldRoad_id, Map<String, Object> pMap) {
        log.info(this.getClass().getName());

        JSONArray oldData = new JSONArray();
        try {
            MongoCollection<Document> collection = mongodb.getCollection("StudyRoadMap");

            // 조건 Document 리스트 생성
            Document query = new Document();

            query.append("road_id", oldRoad_id);
            query.append("public", "Y");

            Document projection = new Document();

            projection.append("road_node", "$road_node");
            projection.append("road_diagram", "$road_diagram");
            projection.append("_id", 0);

            collection.find(query).projection(projection).forEach(oldData::add);
            JSONObject jsonObject = new JSONObject((Map) oldData.get(0));

            pMap.put("road_nodeDataArray", jsonObject.get("road_nodeDataArray"));
            pMap.put("road_diagram", jsonObject.get("road_diagram"));

            collection.insertOne(new Document(pMap));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}

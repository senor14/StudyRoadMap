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

    @Override  //select road_id,road_node.node_category,road_title,userUuid,created from StudyRoadMap where public ="Y" order by created desc
    public JSONArray getStudyRoadMap() {

        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("StudyRoadMap");

            Document query = new Document();
//            query.append("public", "Y");

            Document projection = new Document();

            projection.append("_id", "$_id");
            projection.append("roadNodeDataArray.nodeCategory", "$roadNodeDataArray.nodeCategory");
            projection.append("roadTitle", "$roadTitle");
            projection.append("userUuid", "$userUuid");
            projection.append("created", "$created");

            Document sort = new Document();

            sort.append("created", -1);

            collection.find(query).projection(projection).forEach(studyRoadMap::add);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return studyRoadMap;
    }

    @Override  //select career_id,careerTitle,userUuid,created from CareerRoadMap where public = "Y" order by created desc
    public JSONArray getCareerRoadMap() {

        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("CareerRoadMap");

            Document query = new Document();
            query.append("public", "Y");

            Document projection = new Document();

            projection.append("careerTitle", "$careerTitle");
            projection.append("userUuid", "$userUuid");
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

    @Override  //select road_id,road_node.node_category,road_title,userUuid,created from StudyRoadMap where public ="Y" and road_node.node_category like "%%" order by created desc
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
            projection.append("roadNodeDataArray.nodeCategory", "$roadNodeDataArray.nodeCategory");
            projection.append("roadTitle", "$roadTitle");
            projection.append("userUuid", "$userUuid");
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

    @Override  //select career_id,careerTitle,userUuid,created from CareerRoadMap where public = "Y" and careerTitle like "%나의%" order by created desc
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
                conditions.add(new Document().append("careerTitle", new BsonRegularExpression("^.*"+word+".*$", "i")));
            }
            query.append("$and", conditions);

            Document projection = new Document();

            projection.append("careerTitle", "$careerTitle");
            projection.append("userUuid", "$userUuid");
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
    public JSONArray getComment(String studyRoadId) {
        log.info(this.getClass().getName());

        JSONArray studyRoadMap = new JSONArray();

        try {
            MongoCollection<Document> collection = mongodb.getCollection("Comment");

            Document query = new Document();

            query.append("studyRoadId", studyRoadId);

            Document projection = new Document();

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
    public boolean copyRoadMap(String oldRoadId, Map<String, Object> pMap) {
        log.info(this.getClass().getName());

        JSONArray oldData = new JSONArray();
        try {
            MongoCollection<Document> collection = mongodb.getCollection("StudyRoadMap");

            // 조건 Document 리스트 생성
            Document query = new Document();

            query.append("road_id", oldRoadId);
            query.append("public", "Y");

            Document projection = new Document();

            projection.append("roadNodeDataArray", "roadNodeDataArray");
            projection.append("roadDiagram", "roadDiagram");
            projection.append("_id", 0);

            collection.find(query).projection(projection).forEach(oldData::add);
            JSONObject jsonObject = new JSONObject((Map) oldData.get(0));

            pMap.put("roadNodeDataArray", jsonObject.get("roadNodeDataArray"));
            pMap.put("roadDiagram", jsonObject.get("roadDiagram"));

            collection.insertOne(new Document(pMap));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}

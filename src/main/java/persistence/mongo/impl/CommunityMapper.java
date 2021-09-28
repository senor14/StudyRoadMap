package persistence.mongo.impl;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import persistence.mongo.ICommunityMapper;

import java.util.function.Consumer;

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

            Consumer<Document> processBlock = studyRoadMap::add;
            collection.find(query).projection(projection).forEach(processBlock);

        } catch (Exception e) {
            // handle exception
        }
        return studyRoadMap;
    }

    @Override
    public JSONArray getStudyMindMap() {
        return null;
    }
}

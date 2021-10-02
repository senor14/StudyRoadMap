package controller;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import org.json.simple.JSONArray;

import java.util.function.Consumer;

import static util.DateUtil.getDateTime;

public class test {

   public static void main(String[] args) {
      test ts = new test();
      System.out.println(getDateTime());
//      ts.testGetStudyMap();
      ts.testfindStudyMap("프로그래밍");
   }

   public void testGetStudyMap() {

      JSONArray studyRoadMap = new JSONArray();
      //select road_id,road_category,road_title,user_uuid from StudyRoadMap where public ="Y" order by created desc
      try (MongoClient client = new MongoClient("52.79.231.216", 21316)) {

         MongoDatabase database = client.getDatabase("RoadMap");
         MongoCollection<Document> collection = database.getCollection("StudyRoadMap");

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
         System.out.println(studyRoadMap);
      }catch (Exception e) {
         // handle exception
      }
//      System.out.println(studyRoadMap);
   }

   public void testfindStudyMap(String serch) {

      JSONArray studyRoadMap = new JSONArray();
      //select road_id,road_category,road_title,user_uuid from StudyRoadMap where public ="Y" order by created desc
      try (MongoClient client = new MongoClient("52.79.231.216", 21316)) {

         MongoDatabase database = client.getDatabase("RoadMap");
         MongoCollection<Document> collection = database.getCollection("StudyRoadMap");

         Document query = new Document();

         query.append("public", "Y");
         query.append("road_title", new BsonRegularExpression("^.*"+serch+".*$", "i"));

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
         System.out.println(studyRoadMap);
      }catch (Exception e) {
         // handle exception
      }
//      System.out.println(studyRoadMap);
   }


}

package controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonRegularExpression;
import org.bson.Document;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static util.DateUtil.getDateTime;

public class test {

   public static void main(String[] args) {
      test ts = new test();
      System.out.println(getDateTime());
//      ts.testGetStudyMap();
      ts.testfindStudyMap("road_category","프로 백");
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

   public void testfindStudyMap(String searchType,String keyWord) {

      String[] words = keyWord.split(" ");
      
      JSONArray studyRoadMap = new JSONArray();
      //select road_id,road_category,road_title,user_uuid from StudyRoadMap where public ="Y" order by created desc
      try (MongoClient client = new MongoClient("52.79.231.216", 21316)) {

         MongoDatabase database = client.getDatabase("RoadMap");
         MongoCollection<Document> collection = database.getCollection("StudyRoadMap");

         Document query = new Document();

         // 조건 Document 리스트 생성
         List<Document> conditions= new ArrayList<>();
         conditions.add(new Document().append("public", "Y"));
         for(String word : words){
            conditions.add(new Document().append(searchType, new BsonRegularExpression("^.*"+word+".*$", "i")));
         }

         query.append("$and", conditions);
         
//         query.append("public", "Y");
//         System.out.println(query["$and"]);
//         query.append(searchType, new BsonRegularExpression("^.*"+keyWord+".*$", "i"));

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
         System.out.println(studyRoadMap);
      }catch (Exception e) {
         // handle exception
      }
//      System.out.println(studyRoadMap);
   }

   public void insert(){}
}

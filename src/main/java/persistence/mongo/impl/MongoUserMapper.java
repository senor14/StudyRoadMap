package persistence.mongo.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import persistence.mongo.IMongoUserMapper;

import java.util.*;
import java.util.function.Consumer;


@Component("MongoUserMapper")
public class MongoUserMapper implements IMongoUserMapper {

	@Autowired
	private MongoTemplate mongodb;

	private Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int insertUserInfo(Map<String, Object> pMap, String colNm) throws Exception {

		log.info(this.getClass().getName() + ".insertUserInfo Start!");

		int res = 0;

		if (pMap == null) {
			pMap = new HashMap();
		}
		
		
		if(mongodb.collectionExists(colNm)) {
			
		} else {
			//mongodb.createCollection(colNm).createIndex("user_id", pMap.get("user_id"));
		}
		
		
		
		
		MongoCollection<Document> collection = mongodb.getCollection(colNm);
		
		collection.insertOne(new Document(pMap));
		
		res = 1;

		log.info(this.getClass().getName() + ".insertUserInfo End!");

		return res;
	}

	@Override
	public List<Map<String, String>> getUserInfo(Map<String, Object> pMap, String colNm) throws Exception {

		log.info(this.getClass().getName() + "getUserInfo Start!");
        
        List<Map<String, String>> rList = new LinkedList<>();
        
        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        Document query = new Document(pMap);

        Consumer<Document> processBlock = document -> {
        	
        	Map<String, String> rMap = new HashMap<>();
        	
            String site_name = document.getString("site_name");
            String site_address = document.getString("site_address");
            String site_id = document.getString("site_id");
            String site_pw = document.getString("site_pw");

            rMap.put("site_name", site_name);
            rMap.put("site_address", site_address);
            rMap.put("site_id", site_id);
            rMap.put("site_pw", site_pw);
            
            rList.add(rMap);
            
            rMap = null;
        };
        
        collection.find(query).forEach(processBlock);
		
		log.info(this.getClass().getName() + "getUserInfo End!");

		return rList;
	}

	@Override
	public int deleteUserInfo(Map<String, Object> pMap, String colNm) {
		
		log.info(this.getClass().getName() + "deleteUserInfo start");
		
	      MongoCollection<Document> col = null;
	      Iterator<Document> cursor = null;
	      FindIterable<Document> dRs = null;

	      /*
	       * #############################################################################
	       * 기 등록된 데이터 사전 삭제 시작!
	       * #############################################################################
	       */
	      // 가수 이름 삭제
	      col = mongodb.getCollection(colNm);
	      
	      Document document = new Document(pMap);
	      
	      dRs = col.find(document);
	      cursor = dRs.iterator();
	      DeleteResult deleteResult = null;
	      int res = 0;
	      
	      if(cursor.hasNext()) {
	      
		      while (cursor.hasNext()) {
		    	  deleteResult = col.deleteOne(cursor.next());
		      }
		      
		      res = (int) deleteResult.getDeletedCount();
	      
	      }
	      cursor = null;
	      dRs = null;
	      col = null;
	      deleteResult = null;
		/*
	    MongoCollection<Document> collection = mongodb.getCollection(colNm);
	    
	    DeleteResult deleteResult = collection.deleteOne(new Document(pMap));
	    int res = (int) deleteResult.getDeletedCount();
		*/
		log.info(this.getClass().getName() + "deleteUserInfo end");
		
		return res;
	}

	@Override
	public int editUserInfo(Map<String, Object> before_map, Map<String, Object> after_map, String colNm) {
		
		log.info(this.getClass().getName() + "editUserInfo start");
		
		 MongoCollection<Document> collection = mongodb.getCollection(colNm);
		 
		 Document findQuery = new Document(before_map);
		 
		 Document updateQuery = new Document(after_map);
		 
		 UpdateResult updateResults = collection.updateOne(findQuery, new Document("$set", updateQuery));
		 
		 int res = 0;
		 res = (int) updateResults.getMatchedCount();
		 
		log.info(this.getClass().getName() + "editUserInfo end");
		
		return res;
	}

}

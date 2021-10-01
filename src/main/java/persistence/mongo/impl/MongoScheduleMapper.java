package persistence.mongo.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import persistence.mongo.IMongoScheduleMapper;


@Component("MongoScheduleMapper")
public class MongoScheduleMapper implements IMongoScheduleMapper {

	private MongoTemplate mongodb;

	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public int insertSchedule(Map<String, Object> pMap, String colNm) {

		log.info(this.getClass().getName() + "insertSchedule start");

		int res = 0;

		if (pMap == null) {
			pMap = new HashMap<String, Object>();
		}

		MongoCollection<Document> collection = mongodb.getCollection(colNm);

		collection.insertOne(new Document(pMap));

		res = 1;

		log.info(this.getClass().getName() + "insertSchedule end");

		return res;
	}

	@Override
	public List<Map<String, String>> getSchedule(Map<String, Object> pMap, String colNm) {

		List<Map<String, String>> rList = new LinkedList<>();

		MongoCollection<Document> collection = mongodb.getCollection(colNm);

		Document query = new Document(pMap);

		Consumer<Document> processBlock = document -> {

			Map<String, String> rMap = new HashMap<>();

			String schedule_name = document.getString("schedule_name");
			String schedule_alarm = document.getString("schedule_alarm");
			String schedule_time = document.getString("schedule_time");
			String schedule_minute = document.getString("schedule_minute");

			rMap.put("schedule_name", schedule_name);
			rMap.put("schedule_alarm", schedule_alarm);
			rMap.put("schedule_time", schedule_time);
			rMap.put("schedule_minute", schedule_minute);

			rList.add(rMap);

			rMap = null;
		};

		collection.find(query).forEach(processBlock);

		return rList;
	}

	@Override
	public int deleteSchedule(Map<String, Object> pMap, String colNm) {

		log.info(this.getClass().getName() + "deleteList start");

		MongoCollection<Document> col = null;
		Iterator<Document> cursor = null;
		FindIterable<Document> dRs = null;

		col = mongodb.getCollection(colNm);

		Document document = new Document(pMap);

		dRs = col.find(document);
		cursor = dRs.iterator();
		DeleteResult deleteResult = null;
		int res = 0;

		if (cursor.hasNext()) {

			while (cursor.hasNext()) {
				deleteResult = col.deleteOne(cursor.next());
			}

			res = (int) deleteResult.getDeletedCount();

		}
		cursor = null;
		dRs = null;
		col = null;
		deleteResult = null;

		log.info(this.getClass().getName() + "deleteList end");

		return res;
	}

	@Override
	public int updateSchedule(Map<String, Object> before_map, Map<String, Object> after_map, String colNm) {
		
		log.info(this.getClass().getName() + "updateSchedule start");
		
		 MongoCollection<Document> collection = mongodb.getCollection(colNm);
		 
		 Document findQuery = new Document(before_map);
		 
		 Document updateQuery = new Document(after_map);
		 
		 UpdateResult updateResults = collection.updateOne(findQuery, new Document("$set", updateQuery));
		 
		 int res = 0;
		 res = (int) updateResults.getMatchedCount();
		
		log.info(this.getClass().getName() + "updateSchedule end");
		
		return res;
	}

}

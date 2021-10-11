package mapper.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import mapper.IUserMapper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Component("UserMapper")
public class UserMapper implements IUserMapper {

    @Autowired
    private MongoTemplate mongodb;


    //로그인 처리
    @Override
    public List<Map<String, String>> getUserInfo(Map<String, Object> uMap, String colNm) {

        log.info(this.getClass().getName() + "getUserInfo Start");

        List<Map<String, String>> rList = new LinkedList<>();

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        Document query = new Document(uMap);

        Consumer<Document> processBlock = document -> {

            Map<String, String> rMap = new HashMap<>();

            String user_id = document.getString("userId");
            String user_email = document.getString("userEmail");
            String user_uuid = document.getString("userUuid");


            rMap.put("userId", user_id);
            rMap.put("userEmail", user_email);
            rMap.put("userUuid", user_uuid);

            rList.add(rMap);

            rMap = null;
        };

        collection.find(query).forEach(processBlock);

        log.info(this.getClass().getName() + "getUserInfo end");

        return rList;
    }

    @Override
    public int insertUserInfo(Map<String, Object> uMap, String colNm) {

        log.info(this.getClass().getName() + " insertUserInfo start");

        if(uMap == null){
            uMap = new HashMap();
        }

        log.info("collection start");
        MongoCollection<Document> collection = mongodb.getCollection(colNm);
        log.info("collection end");

        log.info("insert start");
        collection.insertOne(new Document(uMap));
        log.info("insert end");

        log.info("count start");
        long count = collection.countDocuments(new Document(uMap));
        log.info("count end");

        log.info("형변환 start");
        int res = Long.valueOf(count).intValue();
        log.info("형변환 end");

        log.info(this.getClass().getName() + " insertUserInfo end");

        return res;
    }

    @Override
    public int insertAuthNum(Map<String, Object> pMap, String auth_colNm) {

        log.info("insertAuthNum start");

        MongoCollection<Document> collection = mongodb.getCollection(auth_colNm);

        collection.insertOne(new Document(pMap));

        long count = collection.countDocuments(new Document(pMap));

        int res = Long.valueOf(count).intValue();

        log.info("insertAuthNum end");

        return res;
    }

    @Override
    public int getUserEmail(Map<String, Object> uMap, String colNm) {

        log.info("getUserEmail start");

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        long count = collection.countDocuments(new Document(uMap));

        int res = Long.valueOf(count).intValue();

        log.info("getUserEmail end");

        return res;
    }

    @Override
    public int idCheck(Map<String, Object> uMap, String colNm) {

        log.info("idCheck start");

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        long count = collection.countDocuments(new Document(uMap));

        int res = Long.valueOf(count).intValue();

        log.info("idCheck end");

        return res;
    }

    // 인증번호 확인
    @Override
    public int getAuthNum(Map<String, Object> uMap, String auth_colNm) {

        MongoCollection<Document> collection = mongodb.getCollection(auth_colNm);

        long count = collection.countDocuments(new Document(uMap));

        int res = Long.valueOf(count).intValue();

        return res;
    }

    @Override
    public int reMakePW(Map<String, Object> beforeMap, Map<String, Object> afterMap, String colNm) {

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        Document findQuery = new Document(beforeMap);

        Document updateQuery = new Document(afterMap);

        UpdateResult updateResults = collection.updateOne(findQuery, new Document("$set", updateQuery));

        int res = 0;
        res = (int) updateResults.getMatchedCount();

        return res;
    }

    // 유저의 정보 보냄과 동시에 유저 정보 삭제
    @Override
    public List<Map<String, Object>> getDeleteUserInfo(Map<String, Object> uMap, String colNm) {

        List<Map<String, Object>> rList = new LinkedList<>();

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        Document query = new Document(uMap);

        Consumer<Document> processBlock = document -> {

            Map<String, Object> rMap = new HashMap<>();

            rMap.put("userUuid", document.getString("userUuid"));

            rList.add(rMap);

            rMap = null;
        };

        collection.find(query).forEach(processBlock);

        FindIterable<Document> dRs = collection.find(new Document(uMap));
        Iterator<Document> cursor = dRs.iterator();

        if (cursor.hasNext()) {

            while (cursor.hasNext()) {
                collection.deleteOne(cursor.next());
            }

        }
        cursor = null;
        dRs = null;
        collection = null;

        return rList;
    }

    @Override
    public int deleteCareerRoadMap(Map<String, Object> pMap, String career_colNm) {

        log.info(this.getClass().getName() + "deleteCareerRoadMap start");

        MongoCollection<Document> col = mongodb.getCollection(career_colNm);

        FindIterable<Document> dRs = col.find(new Document(pMap));

        Iterator<Document> cursor = dRs.iterator();

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

        log.info(this.getClass().getName() + "deleteCareerRoadMap end");

        return res;
    }

    @Override
    public int deleteStudyMinddMap(Map<String, Object> pMap, String mind_colNm) {

        log.info(this.getClass().getName() + "deleteCareerRoadMap start");

        MongoCollection<Document> col = mongodb.getCollection(mind_colNm);

        FindIterable<Document> dRs = col.find(new Document(pMap));

        Iterator<Document> cursor = dRs.iterator();

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

        log.info(this.getClass().getName() + "deleteCareerRoadMap end");

        return res;
    }

    @Override
    public int deleteStudyRoadMap(Map<String, Object> pMap, String road_colNm) {

        log.info(this.getClass().getName() + "deleteCareerRoadMap start");

        MongoCollection<Document> col = mongodb.getCollection(road_colNm);

        FindIterable<Document> dRs = col.find(new Document(pMap));

        Iterator<Document> cursor = dRs.iterator();

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

        log.info(this.getClass().getName() + "deleteCareerRoadMap end");

        return res;
    }

    @Override
    public int passWordChange(Map<String, Object> beforeMap, Map<String, Object> afterMap, String colNm) {

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        Document findQuery = new Document(beforeMap);

        Document updateQuery = new Document(afterMap);

        UpdateResult updateResults = collection.updateOne(findQuery, new Document("$set", updateQuery));

        int res = 0;
        res = (int) updateResults.getMatchedCount();

        return res;
    }

    @Override
    public int deleteAuthNum(Map<String, Object> pMap, String auth_colNm) {

        log.info(this.getClass().getName() + "deleteAuthNum start");

        MongoCollection<Document> col = mongodb.getCollection(auth_colNm);

        FindIterable<Document> dRs = col.find(new Document(pMap));

        Iterator<Document> cursor = dRs.iterator();

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

        log.info(this.getClass().getName() + "deleteAuthNum end");

        return res;
    }

    @Override
    public List<Map<String, String>> getUserId(Map<String, Object> uMap, String user_colNm) {

        log.info(this.getClass().getName() + "getUserId Start");

        List<Map<String, String>> rList = new LinkedList<>();

        MongoCollection<Document> collection = mongodb.getCollection(user_colNm);

        Document query = new Document(uMap);

        Consumer<Document> processBlock = document -> {

            Map<String, String> rMap = new HashMap<>();

            String user_id = document.getString("userId");

            rMap.put("userId", user_id);
            rList.add(rMap);

            rMap = null;
        };

        collection.find(query).forEach(processBlock);

        log.info(this.getClass().getName() + "getUserId end");

        return rList;
    }
}

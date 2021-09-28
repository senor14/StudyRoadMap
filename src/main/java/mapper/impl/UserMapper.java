package mapper.impl;

import com.mongodb.client.MongoCollection;
import mapper.IUserMapper;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


@Component("UserMapper")
public class UserMapper implements IUserMapper {

    @Autowired
    private MongoTemplate mongodb;

    private Logger log = Logger.getLogger(this.getClass());

    //로그인 처리
    @Override
    public List<Map<String, String>> getUserInfo(Map<String, Object> uMap, String colNm) {

        log.info(this.getClass().getName() + "getUserInfo Start");

        List<Map<String, String>> rList = new LinkedList<>();

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        Document query = new Document(uMap);

        Consumer<Document> processBlock = document -> {

            Map<String, String> rMap = new HashMap<>();

            String user_id = document.getString("user_id");
            String user_email = document.getString("user_email");

            rMap.put("user_id", user_id);
            rMap.put("user_email", user_email);

            rList.add(rMap);

            rMap = null;
        };

        collection.find(query).forEach(processBlock);

        log.info(this.getClass().getName() + "getUserInfo end");

        return rList;
    }

    @Override
    public int insertUserInfo(Map<String, Object> uMap, String colNm) {

        log.info(this.getClass().getName() + "insertUserInfo start");

        if(uMap == null){
            uMap = new HashMap();
        }

        MongoCollection<Document> collection = mongodb.getCollection(colNm);

        collection.insertOne(new Document(uMap));

        long count = collection.countDocuments(new Document(uMap));

        int res = Long.valueOf(count).intValue();

        log.info(this.getClass().getName() + "insertUserInfo end");

        return res;
    }
}

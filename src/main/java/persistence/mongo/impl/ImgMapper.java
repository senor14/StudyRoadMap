package persistence.mongo.impl;

import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import persistence.mongo.IImgMapper;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("ImgMapper")
public class ImgMapper implements IImgMapper {

    @Autowired
    private MongoTemplate mongodb;


    @Override
    public int InsertImage(Map<String, Object> iMap, String img_colNm) {
        log.info(this.getClass().getName() + " insert start");

        if(iMap == null){
            iMap = new HashMap();
        }

        log.info("collection start");
        MongoCollection<Document> collection = mongodb.getCollection(img_colNm);
        log.info("collection end");

        log.info("insert start");
        collection.insertOne(new Document(iMap));
        log.info("insert end");

        log.info("count start");
        long count = collection.countDocuments(new Document(iMap));
        log.info("count end");

        log.info("형변환 start");
        int res = Long.valueOf(count).intValue();
        log.info("형변환 end");

        log.info(this.getClass().getName() + " insert end");

        return res;
    }
}

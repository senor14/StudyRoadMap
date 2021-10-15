package persistence.mongo.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import persistence.mongo.IImgMapper;

import java.util.*;
import java.util.function.Consumer;

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

    @Override
    public List<Map<String, String>> getImgList(Map<String, Object> iMap, String img_colNm) {

        log.info(this.getClass().getName() + "getImgList Start");

        List<Map<String, String>> rList = new LinkedList<>();

        MongoCollection<Document> collection = mongodb.getCollection(img_colNm);

        Document query = new Document(iMap);

        Consumer<Document> processBlock = document -> {

            Map<String, String> rMap = new HashMap<>();

            String ext = document.getString("ext");
            String saveFileName = document.getString("saveFileName");
            String userUuid = document.getString("userUuid");
            String saveFilePath = document.getString("saveFilePath");
            String orgFileName = document.getString("orgFileName");
            String createDate = document.getString("createDate");


            rMap.put("ext", ext);
            rMap.put("saveFileName", saveFileName);
            rMap.put("userUuid", userUuid);
            rMap.put("saveFilePath", saveFilePath);
            rMap.put("orgFileName", orgFileName);
            rMap.put("createDate", createDate);

            rList.add(rMap);

            rMap = null;
        };

        collection.find(query).forEach(processBlock);

        log.info(this.getClass().getName() + "getImgList end");

        return rList;
    }

    @Override
    public List<Map<String, String>> deletePastImg(Map<String, Object> dMap, String img_colNm) {

        log.info(this.getClass().getName() + "deletePastImg Start");

        List<Map<String, String>> rList = new LinkedList<>();

        MongoCollection<Document> collection = mongodb.getCollection(img_colNm);

        Document query = new Document(dMap);

        Consumer<Document> processBlock = document -> {

            Map<String, String> rMap = new HashMap<>();

            String saveFileName = document.getString("saveFileName");
            String saveFilePath = document.getString("saveFilePath");

            rMap.put("saveFileName", saveFileName);
            rMap.put("saveFilePath", saveFilePath);

            rList.add(rMap);

            rMap = null;
        };

        collection.find(query).forEach(processBlock);

        MongoCollection<Document> col = mongodb.getCollection(img_colNm);

        FindIterable<Document> dRs = col.find(new Document(dMap));

        Iterator<Document> cursor = dRs.iterator();

        DeleteResult deleteResult = null;

        if (cursor.hasNext()) {

            while (cursor.hasNext()) {
                deleteResult = col.deleteOne(cursor.next());
            }

        }

        log.info(this.getClass().getName() + "deletePastImg end");

        return rList;
    }

    @Override
    public int imgCheck(Map<String, Object> dMap, String img_colNm) {
        log.info("imgCheck start");

        MongoCollection<Document> collection = mongodb.getCollection(img_colNm);

        long count = collection.countDocuments(new Document(dMap));

        int res = Long.valueOf(count).intValue();

        log.info("imgCheck end");

        return res;
    }
}

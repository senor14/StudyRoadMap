package persistence.mongo.impl;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dto.StudyRoadInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import persistence.mongo.IStudyMindMapper;

import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Component("StudyMindMapper")
public class StudyMindMapper implements IStudyMindMapper {

    @Autowired
    private MongoTemplate mongodb;

    /**
     * 스터디 로드맵 노드 아이디 가 마인드아이디와 일치하는 마인드 document 조회
     *
     * @param colNm 조회할 컬렉션 이름
     * @param pDto 마인드맵 컬렉션을 조회할 로드맵 노드 정보
     * @return 마인드맵 정보
     * @throws Exception
     */
    @Override
    public List<Map<String, String>> getMindMap(String colNm, StudyRoadInfoDTO pDto) throws Exception {

        log.info(this.getClass().getName() + ".getMindMap Start!");

        List<Map<String, String>> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();

        query.append("studyroad_node_id", pDto.getStudyRoadNodeId());

        Consumer<Document> processBlock = document ->  {

            Map<String, String> rMap = new HashMap<>();

            String user_uuid = document.getString("user_uuid");
            String studyroad_id = document.getString("studyroad_id");
            String studyroad_node_id = document.getString("studyroad_node_id");
            String mind_id = document.getString("mind_id");
            String mind_label = document.getString("mind_label");
            String mind_contents = document.getString("mind_contents");
            String url = document.getString("url");
            String book_title = document.getString("book_title");
            String book_link = document.getString("book_link");
            String created = document.getString("created");

            rMap.put("user_uuid", user_uuid);
            rMap.put("studyroad_id", studyroad_id);
            rMap.put("studyroad_node_id", studyroad_node_id);
            rMap.put("mind_id", mind_id);
            rMap.put("mind_label", mind_label);
            rMap.put("mind_contents", mind_contents);
            rMap.put("url", url);
            rMap.put("book_title", book_title);
            rMap.put("book_link", book_link);
            rMap.put("created", created);

            rList.add(rMap);

            rMap = null;

        };

        col.find(query).forEach(processBlock);

        log.info("rList: "+ rList.toString());

        log.info(this.getClass().getName() + ".getMindMap End!");

        return rList;
    }

    /**
     * 스터디 로드맵 노드 아이디가 마인드아이디와 일치하는 마인드 노드 document 조회
     *
     * @param colNm 조회할 컬렉션 이름
     * @param pDto 마인드맵 컬렉션을 조회할 로드맵 노드 정보
     * @return 마인드맵 정보
     * @throws Exception
     */
    @Override
    public List<Map<String, String>> getMindMapNode(String colNm, StudyRoadInfoDTO pDto) throws Exception {

        log.info(this.getClass().getName() + ".getMindMapNode Start!");

        List<Map<String, String>> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();

        query.append("studyroad_node_id", pDto.getStudyRoadNodeId());

        Consumer<Document> processBlock = document ->  {

            Map<String, String> rMap = new HashMap<>();

            String user_uuid = document.getString("user_uuid");
            String studyroad_id = document.getString("studyroad_id");
            String studyroad_node_id = document.getString("studyroad_node_id");
            String group = document.getString("group");
            String id = document.getString("id");
            if (group.equals("nodes")) {
                String label = document.getString("label");
                rMap.put("label", label);
            } else if (group.equals("edges")) {
                String source = document.getString("source");
                String target = document.getString("target");
                rMap.put("source", source);
                rMap.put("target", target);
            }

            rMap.put("user_uuid", user_uuid);
            rMap.put("studyroad_id", studyroad_id);
            rMap.put("studyroad_node_id", studyroad_node_id);
            rMap.put("group", group);
            rMap.put("id", id);

            rList.add(rMap);

            rMap = null;

        };

        col.find(query).forEach(processBlock);

        log.info("rList: "+ rList.toString());

        log.info(this.getClass().getName() + ".getMindMapNode End!");

        return rList;
    }

    /**
     *
     *
     * @param pMap  저장할 정보
     * @param colNm 저장할 컬렉션 이름
     * @return 저장 결과
     * @throws Exception
     */
    @Override
    public int insertDefaultMindMap(Map<String, Object> pMap, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".insertDefaultMindMap Start!");


        log.info(this.getClass().getName() + ".insertDefaultMindMap End!");

        return 0;
    }

    /**
     * 루트 노드 및 마인드 정보 저장
     * ex) 네트워크 스터디로드맵 노드 관련 마인드맵의 루트 -> 네트워크
     *
     * @param nMap 저장할 노드 정보
     * @param mMap 저장할 마인드 정보
     * @param colNm 저장할 컬렉션 이름
     * @return 저장 결과
     * @throws Exception
     */
    @Override
    public int insertRootNode(Map<String, Object> nMap, Map<String, String> mMap, String colNm) throws Exception {

        log.info(this.getClass().getName() + ".insertRootNode Start!");


        log.info(this.getClass().getName() + ".insertRootNode End!");

        return 0;
    }
}

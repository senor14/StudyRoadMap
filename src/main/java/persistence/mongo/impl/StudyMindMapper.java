//package persistence.mongo.impl;
//
//import com.mongodb.MongoException;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import dto.StudyRoadInfoDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;
//import persistence.mongo.IStudyMindMapper;
//
//import java.util.*;
//import java.util.function.Consumer;
//
//@Slf4j
//@Component("StudyMindMapper")
//public class StudyMindMapper implements IStudyMindMapper {
//
//    @Autowired
//    private MongoTemplate mongodb;
//
//    /**
//     * 스터디 로드맵 노드 아이디 가 마인드아이디와 일치하는 마인드 document 조회
//     *
//     * @param colNm 조회할 컬렉션 이름
//     * @param pDto 마인드맵 컬렉션을 조회할 로드맵 노드 정보
//     * @return 마인드맵 정보
//     * @throws Exception
//     */
//    @Override
//    public List<Map<String, String>> getMindMapData(String colNm, StudyRoadInfoDTO pDto) throws Exception {
//
//        log.info(this.getClass().getName() + ".getMindMap Start!");
//
//        List<Map<String, String>> rList = new LinkedList<>();
//
//        MongoCollection<Document> col = mongodb.getCollection(colNm);
//
//        Document query = new Document();
//
//        query.append("studyRoadNodeId", pDto.getStudyRoadNodeId());
//
//        Consumer<Document> processBlock = document ->  {
//
//            Map<String, String> rMap = new HashMap<>();
//
//            String userUuid = document.getString("userUuid");
//            String studyRoadId = document.getString("studyRoadId");
//            String studyRoadNodeId = document.getString("studyRoadNodeId");
//            String mindId = document.getString("mindId");
//            String mindLabel = document.getString("mindLabel");
//            String mindContents = document.getString("mindContents");
//            String url = document.getString("url");
//            String bookTitle = document.getString("bookTitle");
//            String bookLink = document.getString("bookLink");
//            String created = document.getString("created");
//
//            rMap.put("userUuid", userUuid);
//            rMap.put("studyRoadId", studyRoadId);
//            rMap.put("studyRoadNodeId", studyRoadNodeId);
//            rMap.put("mindId", mindId);
//            rMap.put("mindLabel", mindLabel);
//            rMap.put("mindContents", mindContents);
//            rMap.put("url", url);
//            rMap.put("bookTitle", bookTitle);
//            rMap.put("bookLink", bookLink);
//            rMap.put("created", created);
//
//            rList.add(rMap);
//
//            rMap = null;
//
//        };
//
//        col.find(query).forEach(processBlock);
//
//        log.info("rList: "+ rList.toString());
//
//        log.info(this.getClass().getName() + ".getMindMap End!");
//
//        return rList;
//    }
//
//
//    /**
//     * 스터디 로드맵 노드 아이디가 마인드아이디와 일치하는 마인드 노드 document 조회
//     *
//     * @param colNm 조회할 컬렉션 이름
//     * @param pDto 마인드맵 컬렉션을 조회할 로드맵 노드 정보
//     * @return 마인드맵 정보
//     * @throws Exception
//     */
//    @Override
//    public List<Map<String, String>> getMindMapNode(String colNm, StudyRoadInfoDTO pDto) throws Exception {
//
//        log.info(this.getClass().getName() + ".getMindMapNode Start!");
//
//        List<Map<String, String>> rList = new LinkedList<>();
//
//        MongoCollection<Document> col = mongodb.getCollection(colNm);
//
//        Document query = new Document();
//
//        query.append("studyRoadNodeId", pDto.getStudyRoadNodeId());
//
//        Consumer<Document> processBlock = document ->  {
//
//            Map<String, String> rMap = new HashMap<>();
//
//            String userUuid = document.getString("userUuid");
//            String studyRoadId = document.getString("studyRoadId");
//            String studyRoadNodeId = document.getString("studyRoadNodeId");
//            String group = document.getString("group");
//            String mindId = document.getString("mindId");
//            if (group.equals("nodes")) {
//                String mindLabel = document.getString("mindLabel");
//                rMap.put("mindLabel", mindLabel);
//            } else if (group.equals("edges")) {
//                String source = document.getString("source");
//                String target = document.getString("target");
//                rMap.put("source", source);
//                rMap.put("target", target);
//            }
//
//            rMap.put("userUuid", userUuid);
//            rMap.put("studyRoadId", studyRoadId);
//            rMap.put("studyRoadNodeId", studyRoadNodeId);
//            rMap.put("group", group);
//            rMap.put("mindId", mindId);
//
//            rList.add(rMap);
//
//            rMap = null;
//
//        };
//
//        col.find(query).forEach(processBlock);
//
//        log.info("rList: "+ rList.toString());
//
//        log.info(this.getClass().getName() + ".getMindMapNode End!");
//
//        return rList;
//    }
//
//
//}

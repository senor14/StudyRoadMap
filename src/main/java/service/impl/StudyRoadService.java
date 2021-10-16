package service.impl;

import domain.*;
import jpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import service.IStudyRoadService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service("StudyRoadService")
public class StudyRoadService implements IStudyRoadService {

    private final MongoTemplate mongoTemplate;
    private final StudyRoadRepository studyRoadRepository;
    private final StudyRoadNodeRepository studyRoadNodeRepository;

    @Override
    public StudyRoadData getRoadMapData(String roadId) {

        log.info(this.getClass().getName()+".getRoadMapData Start!");

        StudyRoadData roadData;
        try {
            roadData = studyRoadRepository.findByRoadId(roadId);
        } catch (Exception e) {
            e.getStackTrace();
            roadData = new StudyRoadData();
        }

        log.info("roadData: "+roadData);
        log.info(this.getClass().getName()+".getRoadMapData End!");

        return roadData;
    }

    @Override
    public StudyRoadNodeData getRoadMapNodeData(String nodeId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapNodeDate Start!");

        StudyRoadNodeData nodeData;
        try {
            nodeData = studyRoadNodeRepository.getStudyRoadNodeDataByNodeId(nodeId);
        } catch (Exception e) {
            e.getStackTrace();
            nodeData = new StudyRoadNodeData();
        }

        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName()+".getRoadMapNodeDate End!");

        return nodeData;
    }

    @Override
    public List<StudyRoadData> getPublicRoadTitle(String roadTitle) throws Exception {

        log.info(this.getClass().getName()+".getPublicRoadTitle Start!");

        Query query = new Query();

        Criteria criteria = Criteria.where("roadTitle").regex(roadTitle, "i");
        Criteria criteria2 = Criteria.where("publicYn").is("Y");

        query.addCriteria(criteria.andOperator(criteria2));

        List<StudyRoadData> results;
        try {
            results = mongoTemplate.find(query, StudyRoadData.class, "StudyRoadData");
        } catch (Exception e) {
            e.getStackTrace();
            results = new ArrayList<>();
        }

        log.info("results: "+ results);

        log.info(this.getClass().getName()+".getPublicRoadTitle End!");

        return results;
    }

    @Override
    public List<String> getRoadIdsByCategory(String nodeCategory) throws Exception {

        log.info(this.getClass().getName()+".getRoadIdsByCategory Start!");

        Query query = new Query();

        Criteria criteria = Criteria.where("category").regex(nodeCategory, "i");

        query.addCriteria(criteria);

        List<StudyRoadNodeData> results;
        try {
            results = mongoTemplate.find(query, StudyRoadNodeData.class, "StudyRoadNodeData");
        } catch (Exception e) {
            e.getStackTrace();
            results = new ArrayList<>();
        }

        log.info("results: "+ results);

        Set<StudyRoadNodeData> set = new HashSet<>(results);
        List<StudyRoadNodeData> finalResults = new ArrayList<>(set);

        List<String> rList = new ArrayList<>();

        for (StudyRoadNodeData nodeData : finalResults) {
            rList.add(nodeData.getRoadId());
        }

        log.info("rList: "+ rList);
        log.info(this.getClass().getName()+".getRoadIdsByCategory End!");

        return rList;
    }

    @Override
    public List<StudyRoadData> getPublicCategory(List<String> roadIds) throws Exception {

        log.info(this.getClass().getName()+".getPublicCategory Start!");
        if (roadIds.size() == 0) {
            return new ArrayList<StudyRoadData>();
        }
        log.info("roadIds: "+roadIds);

        List<StudyRoadData> results = new ArrayList<>();
        for (String roadId : roadIds) {
            log.info("roadId: "+roadId);
            StudyRoadData roadData;
            try {
                roadData = studyRoadRepository.findByRoadId(roadId);
            } catch (Exception e) {
                e.getStackTrace();
                roadData = new StudyRoadData();
            }

            if (roadData.getPublicYn().equals("Y")) {
                results.add(roadData);
            }
            roadData = null;
        }
        log.info("results: "+ results);

        log.info(this.getClass().getName()+".getPublicCategory End!");

        return results;
    }

    @Override
    public List<StudyRoadNodeData> getRoadMapNodeByRoadId(String roadId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapNode Start!");

        List<StudyRoadNodeData> results;
        try {
            results = studyRoadNodeRepository.getAllByRoadId(roadId);
        } catch (Exception e) {
            e.getStackTrace();
            results = new ArrayList<>();
        }

        log.info("results: "+results);

        log.info(this.getClass().getName()+".getRoadMapNode End!");
        return results;
    }

    @Override
    public List<StudyRoadData> getRoadDataByUserUuid(String userUuid) throws Exception {

        log.info(this.getClass().getName()+".getRoadDataByUserUuid Start!");

        List<StudyRoadData> roadData;

        try {
            roadData = studyRoadRepository.findAllByUserUuid(userUuid);
        } catch (Exception e) {
            e.getStackTrace();
            roadData = new ArrayList<>();
        }
        log.info("roadData: "+ roadData);

        log.info(this.getClass().getName()+".getRoadDataByUserUuid End!");

        return roadData;
    }

    @Override
    public int insertRoadData(StudyRoadData roadData) throws Exception {

        log.info(this.getClass().getName() + ".insertRoadData Start!");

        int res = 0;

        StudyRoadData studyRoadData;
        try {
            studyRoadData = mongoTemplate.save(roadData, "StudyRoadData");
        } catch (Exception e) {
            studyRoadData = new StudyRoadData();
        }

        log.info("studyRoadData: " + studyRoadData);

        if (studyRoadData == null || studyRoadData.equals("")) {
            res = 1;
        }
        log.info("res: " + res);
        log.info(this.getClass().getName() + ".insertRoadData End!");

        return res;
    }

    @Override
    public int insertRoadNode(StudyRoadNodeData nodeData) throws Exception {

        log.info(this.getClass().getName()+".insertRoadNode Start!");

        int res = 0;

        StudyRoadNodeData studyRoadNodeData;
        try {
            studyRoadNodeData = mongoTemplate.save(nodeData, "StudyRoadNodeData");
        } catch (Exception e) {
            studyRoadNodeData = new StudyRoadNodeData();
        }

        log.info("studyRoadNodeData "+ studyRoadNodeData);

        if (studyRoadNodeData.equals("") || studyRoadNodeData==null) {
            res = 1;
        }
        log.info("res "+ res);

        log.info(this.getClass().getName()+".insertRoadNode End!");

        return res;
    }

    @Override
    public int updateRoadData(StudyRoadData roadData) throws Exception {

        log.info(this.getClass().getName()+".updateRoadData Start!");
        int res = 0;
        StudyRoadData result;
        try {
            result = studyRoadRepository.findByRoadId(roadData.getRoadId());
            log.info("result before: "+ result);
            result.setPublicYn(roadData.getPublicYn());
            result.setRoadTitle(roadData.getRoadTitle());

            StudyRoadData save;
            try {
                save = studyRoadRepository.save(result);
            } catch (Exception e) {
                e.getStackTrace();
                save = new StudyRoadData();
                res = 1;
            }
            log.info("save: "+ save);

        } catch (Exception e) {
            e.getStackTrace();
            result = new StudyRoadData();
            res = 1;
        }
        log.info("result after: "+ result);

        log.info(this.getClass().getName()+".updateRoadData End!");

        return res;
    }

    @Override
    public int updateRoadNodeData(StudyRoadNodeData nodeData) throws Exception {

        log.info(this.getClass().getName()+".updateRoadNodeData Start!");

        int res = 0;
        StudyRoadNodeData result;
        try {
            result = studyRoadNodeRepository.getStudyRoadNodeDataByNodeId(nodeData.getNodeId());
            log.info("result before: "+ result);

            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            mapper.map(nodeData, result);

            StudyRoadNodeData save;
            try {
                save = studyRoadNodeRepository.save(result);
            } catch (Exception e) {
                e.getStackTrace();
                save = new StudyRoadNodeData();
                res = 1;
            }
            log.info("save: "+save);
        } catch (Exception e) {
            e.getStackTrace();
            result = new StudyRoadNodeData();
            res = 1;
        }

        log.info("result after: "+ result);

        log.info(this.getClass().getName()+".updateRoadNodeData End!");

        return res;
    }

    @Override
    public int deleteRoadData(String roadId) throws Exception {

        log.info(this.getClass().getName()+".deleteRoadData Start!");
        int res=0;
        StudyRoadData roadData;
        try {
            roadData = studyRoadRepository.deleteByRoadId(roadId);
            log.info("roadData: "+roadData);
        } catch (Exception e) {
            e.getStackTrace();
            roadData = new StudyRoadData();
            res=1;
        }

        log.info("roadData: "+roadData);

        log.info(this.getClass().getName()+".deleteRoadData End!");

        return res;
    }

    @Override
    public int deleteRoadNode(String nodeId) throws Exception {

        log.info(this.getClass().getName()+".deleteRoadNode Start!");

        int res=0;
        StudyRoadNodeData nodeData;
        try {
            nodeData = studyRoadNodeRepository.deleteByNodeId(nodeId);
            log.info("nodeData: "+nodeData);
        } catch (Exception e) {
            e.getStackTrace();
            nodeData = new StudyRoadNodeData();
            res=1;
        }

        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName()+".deleteRoadNode End!");

        return res;
    }


}

package service.impl;

import domain.StudyRoadData;
import domain.StudyRoadDiagramData;
import domain.StudyRoadNodeData;
import jpa.StudyRoadDiagramRepository;
import jpa.StudyRoadNodeRepository;
import jpa.StudyRoadRepository;
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
    private final StudyRoadDiagramRepository studyRoadDiagramRepository;

    @Override
    public StudyRoadData getRoadMapData(String roadId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapData Start!");

        StudyRoadData roadData = studyRoadRepository.findByRoadId(roadId);

        log.info("roadData: "+roadData.toString());
        log.info(this.getClass().getName()+".getRoadMapData End!");

        return roadData;
    }

    @Override
    public StudyRoadDiagramData getRoadMapDiagramData(String diagramId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapDiagramData Start!");

        StudyRoadDiagramData diagramData = studyRoadDiagramRepository.getStudyRoadDiagramDataByDiagramId(diagramId);

        log.info("diagramData: "+diagramData.toString());
        log.info(this.getClass().getName()+".getRoadMapDiagramData End!");

        return diagramData;
    }

    @Override
    public StudyRoadNodeData getRoadMapNodeDate(String nodeId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapNodeDate Start!");

        StudyRoadNodeData nodeData = studyRoadNodeRepository.getStudyRoadNodeDataByNodeId(nodeId);

        log.info("nodeData: "+nodeData.toString());
        log.info(this.getClass().getName()+".getRoadMapNodeDate End!");

        return nodeData;
    }

    @Override
    public List<StudyRoadData> getPublicRoadTitle(String roadTitle) throws Exception {

        log.info(this.getClass().getName()+".getPublicRoadTitle Start!");

        Query query = new Query();
        Criteria criteria = new Criteria();
        Criteria criteria2 = new Criteria();

        criteria = Criteria.where("roadTitle").regex(roadTitle);
        criteria2 = Criteria.where("publicYn").is("Y");

        query.addCriteria(criteria.andOperator(criteria2));

        List<StudyRoadData> results = mongoTemplate.find(query, StudyRoadData.class, "StudyRoadData");

        log.info("results: "+ results.toString());
        log.info(this.getClass().getName()+".getPublicRoadTitle End!");

        return results;
    }

    @Override
    public List<String> getRoadIdsByNodeCategory(String nodeCategory) throws Exception {

        log.info(this.getClass().getName()+".getRoadIdsByNodeCategory Start!");

        Query query = new Query();
        Criteria criteria = new Criteria();

        criteria = Criteria.where("nodeCategory").regex(nodeCategory);

        query.addCriteria(criteria);
//        query.fields().include("roadId");

        List<StudyRoadNodeData> results = mongoTemplate.find(query, StudyRoadNodeData.class, "StudyRoadNodeData");

        log.info("results: "+ results.toString());

        Set<StudyRoadNodeData> set = new HashSet<>(results);
        List<StudyRoadNodeData> finalResults = new ArrayList<>(set);

        List<String> rList = new ArrayList<>();

        for (StudyRoadNodeData nodeData : finalResults) {
            rList.add(nodeData.getRoadId());
        }

        log.info("rList: "+ rList);
        log.info(this.getClass().getName()+".getRoadIdsByNodeCategory End!");

        return rList;
    }

    @Override
    public List<StudyRoadData> getPublicNodeCategory(List<String> roadIds) throws Exception {

        log.info(this.getClass().getName()+".getPublicNodeCategory Start!");
        if (roadIds.size() == 0) {
            return new ArrayList<StudyRoadData>();
        }
        log.info("roadIds: "+roadIds.toString());
//        Query query = new Query();
//        Criteria criteria = new Criteria();
//
//        Criteria[] criteria_arr = new Criteria[nodeIds.size()];
        List<StudyRoadData> results = new ArrayList<>();
        for (String roadId : roadIds) {
            log.info("roadId: "+roadId);
            results.add(studyRoadRepository.findByRoadId(roadId));
        }
        log.info("results: "+ results.toString());
//        int i = 0;
//        for (String nodeId : nodeIds) {
//            String question = nodeId;
//            criteria_arr[i++] = Criteria.where("nodeId").is(question);
//            log.info((i-1)+"");
//        }
//        query.addCriteria(criteria.orOperator(criteria_arr));


//        log.info("query: "+query.toString());
//        List<StudyRoadData> results = mongoTemplate.find(query, StudyRoadData.class, "StudyRoadData");

        log.info(this.getClass().getName()+".getPublicNodeCategory End!");

        return results;
    }

    @Override
    public List<StudyRoadDiagramData> getRoadMapDiagramByRoadId(String roadId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapDiagram Start!");

        List<StudyRoadDiagramData> results = studyRoadDiagramRepository.getAllByRoadId(roadId);

        log.info("results: "+results);
        log.info(this.getClass().getName()+".getRoadMapDiagram End!");

        return results;
    }

    @Override
    public List<StudyRoadNodeData> getRoadMapNode(String roadId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapNode Start!");

        List<StudyRoadNodeData> results = studyRoadNodeRepository.getAllByRoadId(roadId);

        log.info("results: "+results);
        log.info(this.getClass().getName()+".getRoadMapNode End!");
        return results;
    }

    @Override
    public List<StudyRoadData> getRoadDataByUserUuid(String userUuid) throws Exception {

        log.info(this.getClass().getName()+".getRoadDataByUserUuid Start!");

        List<StudyRoadData> roadData = studyRoadRepository.findAllByUserUuid(userUuid);

        log.info("roadData: "+ roadData);

        log.info(this.getClass().getName()+".getRoadDataByUserUuid End!");

        return roadData;
    }

    @Override
    public int insertRoadData(StudyRoadData roadData) throws Exception {

        log.info(this.getClass().getName() + ".insertRoadData Start!");

        int res = 0;

        StudyRoadData studyRoadData = mongoTemplate.save(roadData, "StudyRoadData");

        log.info("studyRoadData: "+ studyRoadData.toString());

        if (studyRoadData == null || studyRoadData.equals("")) {
            res = 1;
        }
        log.info("res: "+ res);
        log.info(this.getClass().getName() + ".insertRoadData End!");

        return res;
    }

    @Override
    public int insertRoadDiagram(StudyRoadDiagramData diagramData) throws Exception {

        log.info(this.getClass().getName()+".insertRoadDiagram Start!");

        int res = 0;

        StudyRoadDiagramData studyRoadDiagramData = mongoTemplate.save(diagramData, "StudyRoadDiagramData");

        log.info("studyRoadDiagramData "+ studyRoadDiagramData.toString());

        if (studyRoadDiagramData.equals("") || studyRoadDiagramData==null) {
            res = 1;
        }
        log.info("res "+ res);

        log.info(this.getClass().getName()+".insertRoadDiagram End!");

        return res;
    }

    @Override
    public int insertRoadNode(StudyRoadNodeData nodeData) throws Exception {

        log.info(this.getClass().getName()+".insertRoadNode Start!");

        int res = 0;

        StudyRoadNodeData studyRoadDiagramData = mongoTemplate.save(nodeData, "StudyRoadNodeData");

        log.info("studyRoadDiagramData "+ studyRoadDiagramData.toString());

        if (studyRoadDiagramData.equals("") || studyRoadDiagramData==null) {
            res = 1;
        }
        log.info("res "+ res);

        log.info(this.getClass().getName()+".insertRoadNode End!");

        return res;
    }

    @Override
    public int updateRoadData(StudyRoadData roadData) throws Exception {

        log.info(this.getClass().getName()+".updateRoadData Start!");

        StudyRoadData result = studyRoadRepository.findByRoadId(roadData.getRoadId());
        log.info("result before: "+ result);

        result.setPublicYn(roadData.getPublicYn());
//        result.setPublicYn("N");
        result.setRoadTitle(roadData.getRoadTitle());
//        result.setRoadTitle("웹 프로그래밍");

        StudyRoadData save = studyRoadRepository.save(result);
        int res;
        if (save!=null) {
            res = 0;
        } else {
            res = 1;
        }

        log.info("result after: "+ result.toString());

        log.info(this.getClass().getName()+".updateRoadData End!");

        return res;
    }

    @Override
    public int updateRoadDiagramData(StudyRoadDiagramData diagramData) throws Exception {

        log.info(this.getClass().getName()+".updateRoadDiagramDate Start!");

        StudyRoadDiagramData result = studyRoadDiagramRepository.getStudyRoadDiagramDataByDiagramId(diagramData.getDiagramId());
        log.info("result before: "+ result.toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.map(diagramData, result);

        StudyRoadDiagramData save = studyRoadDiagramRepository.save(result);

        int res;
        if (save!=null) {
            res = 0;
        } else {
            res = 1;
        }

        log.info("result after: "+ result.toString());

        log.info(this.getClass().getName()+".updateRoadDiagramDate End!");

        return res;
    }

    @Override
    public int updateRoadNodeData(StudyRoadNodeData nodeData) throws Exception {

        log.info(this.getClass().getName()+".updateRoadNodeData Start!");
        StudyRoadNodeData result = studyRoadNodeRepository.getStudyRoadNodeDataByNodeId(nodeData.getNodeId());
        log.info("result before: "+ result.toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.map(nodeData, result);

        StudyRoadNodeData save = studyRoadNodeRepository.save(result);

        int res;
        if (save!=null) {
            res = 0;
        } else {
            res = 1;
        }

        log.info("result after: "+ result.toString());

        log.info(this.getClass().getName()+".updateRoadNodeData End!");

        return res;
    }

    @Override
    public int deleteRoadData(String roadId) throws Exception {

        log.info(this.getClass().getName()+".deleteRoadData Start!");

        StudyRoadData roadData = studyRoadRepository.deleteByRoadId(roadId);
        int res;
        if (roadData!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info("roadData: "+roadData);

        log.info(this.getClass().getName()+".deleteRoadData End!");

        return res;
    }

    @Override
    public int deleteRoadDiagram(String diagramId) throws Exception {

        log.info(this.getClass().getName()+".deleteRoadDiagram Start!");

        StudyRoadDiagramData diagramData = studyRoadDiagramRepository.deleteByDiagramId(diagramId);
        int res;
        if (diagramData!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info("diagramData: "+diagramData);

        log.info(this.getClass().getName()+".deleteRoadDiagram End!");

        return res;
    }

    @Override
    public int deleteRoadNode(String nodeId) throws Exception {

        log.info(this.getClass().getName()+".deleteRoadNode Start!");

        StudyRoadNodeData nodeData = studyRoadNodeRepository.deleteByNodeId(nodeId);
        int res;
        if (nodeData!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName()+".deleteRoadNode End!");


        return res;
    }
}

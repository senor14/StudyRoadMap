package service.impl;

import domain.StudyMindData;
import domain.StudyMindNodeData;
import jpa.StudyMindNodeRepository;
import jpa.StudyMindRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import service.IStudyMindService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("StudyMindService")
public class StudyMindService implements IStudyMindService {

    private final MongoTemplate mongoTemplate;
    private final StudyMindRepository studyMindRepository;
    private final StudyMindNodeRepository studyMindNodeRepository;

    @Override
    public List<StudyMindData> getMindMapData(StudyMindData mindData) throws Exception {

        log.info(this.getClass().getName()+".getMindMapData Start!");

        List<StudyMindData> results = studyMindRepository.findAllByStudyRoadNodeId(mindData.getStudyRoadNodeId());

        log.info("results: "+results.toString());
        log.info(this.getClass().getName()+".getMindMapData End!");

        return results;
    }

    @Override
    public List<StudyMindNodeData> getMindMapNode(StudyMindNodeData nodeData) throws Exception {

        log.info(this.getClass().getName()+".getMindMapNode Start!");

        List<StudyMindNodeData> results = studyMindNodeRepository.findAllByStudyRoadNodeId(nodeData.getStudyRoadNodeId());

        log.info("results: "+results.toString());
        log.info(this.getClass().getName()+".getMindMapNode End!");

        return results;
    }

    @Override
    public int insertMindData(StudyMindData mindData) throws Exception {
        
        log.info(this.getClass().getName() + "insertMindData Start!");

        int res = 0;

        StudyMindData save = mongoTemplate.insert(mindData, "StudyMindData");

        log.info("save: " + save);
        if (save == null || save.equals("")) {
            res = 1;
        }

        log.info("res : " + res);
        log.info(this.getClass().getName() + "insertMindData End!");

        return res;
    }

    @Override
    public int insertNodeData(StudyMindNodeData nodeData) throws Exception {

        log.info(this.getClass().getName() + "insertNodeData Start!");

        int res = 0;

        StudyMindNodeData save = mongoTemplate.insert(nodeData, "StudyMindNodeData");

        log.info("save: " + save);
        if (save == null || save.equals("")) {
            res = 1;
        }

        log.info("res : " + res);
        log.info(this.getClass().getName() + "insertNodeData End!");

        return res;
    }


    @Override
    public StudyMindData getMindMapInfoByMindId(String mindId) throws Exception {

        log.info(this.getClass().getName() + ".getMindMapInfoByMindId Start!");

        StudyMindData result = studyMindRepository.findByMindId(mindId);

        log.info("result:"+result.toString());
        log.info(this.getClass().getName() + ".getMindMapInfoByMindId End!");

        return result;
    }

    @Override
    public StudyMindNodeData getMindMapNodeByMindId(String mindId) throws Exception {

        log.info(this.getClass().getName() + ".getMindMapNodeByMindId Start!");

        StudyMindNodeData result = studyMindNodeRepository.findByMindId(mindId);

        log.info("result:"+result.toString());
        log.info(this.getClass().getName() + ".getMindMapNodeByMindId End!");

        return result;
    }

    @Override
    public int updateMindData(StudyMindData mindData) throws Exception {

        log.info(this.getClass().getName() + ".updateMindData Start!");

        StudyMindData result = studyMindRepository.findByMindId(mindData.getMindId());
        log.info("result before: "+ result);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.map(mindData, result);

        StudyMindData save = studyMindRepository.save(result);
        int res;
        if (save!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info("result after: "+result.toString());

        log.info(this.getClass().getName() + ".updateMindData End!");

        return res;
    }

    @Override
    public int updateNodeData(StudyMindNodeData nodeData) throws Exception {

        log.info(this.getClass().getName() + ".updateNodeData Start!");

        StudyMindNodeData result = studyMindNodeRepository.findByMindId(nodeData.getMindId());
        log.info("result before: "+ result);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.map(nodeData, result);

        StudyMindNodeData save = studyMindNodeRepository.save(result);
        int res;
        if (save!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info("result after: "+result.toString());

        log.info(this.getClass().getName() + ".updateNodeData End!");

        return res;
    }

    @Override
    public int updateNodePosition(StudyMindNodeData position) throws Exception {

        log.info(this.getClass().getName() + ".updateNodePosition Start!");

        StudyMindNodeData result = studyMindNodeRepository.findByMindId(position.getMindId());
        log.info("result before: "+ result);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.map(position, result);

        StudyMindNodeData save = studyMindNodeRepository.save(result);
        int res;
        if (save!=null) {
            res = 0;
        } else {
            res = 1;
        }

        log.info(this.getClass().getName() + ".updateNodePosition End!");

        return res;
    }

    @Override
    public int deleteMindData(String mindId) throws Exception {

        log.info(this.getClass().getName() + ".deleteMindData Start!");

        StudyMindData mindData = studyMindRepository.deleteByMindId(mindId);
        int res;
        if (mindData!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info("mindData: "+mindData);

        log.info(this.getClass().getName() + ".deleteMindData End!");

        return res;
    }

    @Override
    public int deleteMindNodeData(String mindId) throws Exception {

        log.info(this.getClass().getName() + ".deleteMindNodeData Start!");

        StudyMindNodeData nodeData = studyMindNodeRepository.deleteByMindId(mindId);

        List<StudyMindNodeData> deleteNodes = studyMindNodeRepository.findAllBySourceOrTarget(mindId, mindId);

        int res;
        if (deleteNodes!=null) {
            res = 0;
        } else {
            res = 1;
        }

        studyMindNodeRepository.deleteAll(deleteNodes);

        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName() + ".deleteMindNodeData End!");

        return res;
    }

    @Override
    public List<StudyMindData> getMindDataByRoadNodeId(String roadNodeId) throws Exception {

        log.info(this.getClass().getName()+".getMindDataByRoadNodeId Start!");

        List<StudyMindData> studyMindData = studyMindRepository.findStudyMindDataByStudyRoadNodeId(roadNodeId);
        log.info("studyRoadNodeId: "+roadNodeId);
        log.info("studyMindData:"+studyMindData);

        log.info(this.getClass().getName()+".getMindDataByRoadNodeId End!");

        return studyMindData;
    }

    @Override
    public List<StudyMindNodeData> getMindNodeByRoadNodeId(String roadNodeId) throws Exception {

        log.info(this.getClass().getName()+".getMindNodeByRoadNodeId Start!");

        List<StudyMindNodeData> studyMindNodeData = studyMindNodeRepository.findStudyMindNodeDataByStudyRoadNodeId(roadNodeId);

        log.info("studyMindNodeData: "+ studyMindNodeData);

        log.info(this.getClass().getName()+".getMindNodeByRoadNodeId End!");

        return studyMindNodeData;
    }

}

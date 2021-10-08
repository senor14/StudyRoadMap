package service.impl;

import com.mongodb.client.result.DeleteResult;
import domain.StudyMindData;
import domain.StudyMindNodeData;
import jpa.StudyMindNodeRepository;
import jpa.StudyMindRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import service.IStudyMindService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

        log.info("save: " + save.toString());
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

        log.info("save: " + save.toString());
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
    public StudyMindData updateMindData(StudyMindData mindData) throws Exception {

        log.info(this.getClass().getName() + ".updateMindData Start!");

        StudyMindData result = studyMindRepository.findByMindId(mindData.getMindId());
        log.info("result before: "+ result);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.map(mindData, result);

        studyMindRepository.save(result);
        log.info("result after: "+result.toString());

        log.info(this.getClass().getName() + ".updateMindData End!");

        return result;
    }

    @Override
    public StudyMindNodeData updateNodeData(StudyMindNodeData nodeData) throws Exception {

        log.info(this.getClass().getName() + ".updateNodeData Start!");

        StudyMindNodeData result = studyMindNodeRepository.findByMindId(nodeData.getMindId());
        log.info("result before: "+ result);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.map(nodeData, result);

        studyMindNodeRepository.save(result);
        log.info("result after: "+result.toString());

        log.info(this.getClass().getName() + ".updateNodeData End!");

        return result;
    }

    @Override
    public StudyMindData deleteMindData(String mindId) throws Exception {

        log.info(this.getClass().getName() + ".deleteMindData Start!");

        StudyMindData mindData = studyMindRepository.deleteByMindId(mindId);

        log.info("mindData: "+mindData);

        log.info(this.getClass().getName() + ".deleteMindData End!");

        return mindData;
    }

    @Override
    public StudyMindNodeData deleteMindNodeData(String mindId) throws Exception {

        log.info(this.getClass().getName() + ".deleteMindNodeData Start!");

        StudyMindNodeData nodeData = studyMindNodeRepository.deleteByMindId(mindId);

        List<StudyMindNodeData> deleteNodes = studyMindNodeRepository.findAllBySourceOrTarget(mindId, mindId);

        log.info("##########################################################");
        log.info("deleteNodes: "+deleteNodes.toString());
        log.info("##########################################################");
        studyMindNodeRepository.deleteAll(deleteNodes);

        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName() + ".deleteMindNodeData End!");

        return nodeData;
    }


}

package service.impl;

import dto.StudyRoadInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import persistence.mongo.IStudyMindMapper;
import service.IStudyMindService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("StudyMindSerivce")
public class StudyMindService implements IStudyMindService {

    @Resource(name = "StudyMindMapper")
    IStudyMindMapper studyMindMapper;

    @Override
    public List<Map<String, String>> getMindMap(StudyRoadInfoDTO pDto) throws Exception {

        return studyMindMapper.getMindMap("StudyMindData", pDto);
    }

    @Override
    public List<Map<String, String>> getMindMapNode(StudyRoadInfoDTO pDto) throws Exception {

        return studyMindMapper.getMindMapNode("StudyMindNodeData", pDto);
    }

    @Override
    public int insertDefaultMindMap(Map<String, Object> pMap, String colNm) throws Exception {

        return 0;
    }

    @Override
    public int insertRootNode(Map<String, Object> nMap, Map<String, String> mMap, String colNm) throws Exception {
        return 0;
    }
}

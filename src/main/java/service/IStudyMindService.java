package service;

import dto.StudyRoadInfoDTO;

import java.util.List;
import java.util.Map;

public interface IStudyMindService {

    public List<Map<String, String>> getMindMap(StudyRoadInfoDTO pDto) throws Exception;

    public List<Map<String, String>> getMindMapNode(StudyRoadInfoDTO pDto) throws Exception;

    public int insertDefaultMindMap(Map<String, Object> pMap, String colNm) throws Exception;

    public int insertRootNode(Map<String, Object> nMap, Map<String, String> mMap, String colNm) throws Exception;

}

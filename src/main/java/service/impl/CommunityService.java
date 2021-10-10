package service.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import persistence.mongo.ICommunityMapper;
import service.ICommunityService;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service("CommunityService")
public class CommunityService implements ICommunityService {

    @Resource(name = "CommunityMapper")
    private ICommunityMapper CommunityMapper;

    @Override
    public JSONArray getRoadMap(String category) {
        log.info(this.getClass().getName());
        return category.equals("road") ? CommunityMapper.getStudyRoadMap() : CommunityMapper.getCareerRoadMap();
    }

    @Override
    public JSONArray findRoadMap(String category, String searchType, String keyWord) {
        return category.equals("road") ? CommunityMapper.findStudyRoadMap(searchType, keyWord) : CommunityMapper.findCareerRoadMap(keyWord);
    }

    @Override
    public boolean insertComment(Map<String, Object> pMap) {
        return CommunityMapper.insertComment(pMap);
    }

    @Override
    public JSONArray getComment(String studyRoadId) {
        return CommunityMapper.getComment(studyRoadId);
    }

    @Override
    public boolean copyRoadMap(String oldRoadId, Map<String, Object> pMap) {
        return CommunityMapper.copyRoadMap(oldRoadId,pMap);
    }


}

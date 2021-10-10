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
        return category.equals("road_") ? CommunityMapper.getStudyRoadMap() : CommunityMapper.getCareerRoadMap();
    }

    @Override
    public JSONArray findRoadMap(String category, String searchType, String keyWord) {
        return category.equals("road_") ? CommunityMapper.findStudyRoadMap(searchType, keyWord) : CommunityMapper.findCareerRoadMap(keyWord);
    }

    @Override
    public boolean insertComment(Map<String, Object> pMap) {
        return CommunityMapper.insertComment(pMap);
    }

    @Override
    public JSONArray getComment(String studyRoad_id) {
        return CommunityMapper.getComment(studyRoad_id);
    }

    @Override
    public boolean copyRoadMap(String oldRoad_id, Map<String, Object> pMap) {
        return CommunityMapper.copyRoadMap(oldRoad_id,pMap);
    }


}

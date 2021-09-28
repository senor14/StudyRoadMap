package service.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import persistence.mongo.ICommunityMapper;
import service.ICommunityService;

import javax.annotation.Resource;

@Slf4j
@Service("CommunityService")
public class CommunityService implements ICommunityService {

    @Resource(name = "CommunityMapper")
    private ICommunityMapper CommunityMapper;

    @Override
    public JSONArray getStudyMap(String r_mCheck) {
        log.info(this.getClass().getName());
        return r_mCheck.equals("r") ? CommunityMapper.getStudyRoadMap() : CommunityMapper.getStudyMindMap();
    }
}

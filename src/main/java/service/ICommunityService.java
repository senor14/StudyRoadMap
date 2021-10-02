package service;

import org.json.simple.JSONArray;

import java.util.Map;

public interface ICommunityService {

    JSONArray getStudyMap(String typeCheck);

    JSONArray findStudyMap(String category, String searchType, String keyWord);

    boolean insertComment(Map<String, Object> pMap);

    JSONArray getComment(String studyRoad_id);
}

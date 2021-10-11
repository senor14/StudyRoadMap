package service;

import org.json.simple.JSONArray;

import java.util.Map;

public interface ICommunityService {

    JSONArray getRoadMap(String typeCheck);

    JSONArray findRoadMap(String category, String searchType, String keyWord);

    boolean insertComment(Map<String, Object> pMap);

    JSONArray getComment(String studyRoadId);

    boolean copyRoadMap(String oldRoadId, Map<String, Object> pMap);
}

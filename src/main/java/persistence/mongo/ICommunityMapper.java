package persistence.mongo;

import org.json.simple.JSONArray;

import java.util.Map;

public interface ICommunityMapper {

    JSONArray getStudyRoadMap();

    JSONArray getCareerRoadMap();

    JSONArray findStudyRoadMap(String searchType, String keyWord);

    JSONArray findCareerRoadMap(String keyWord);

    boolean insertComment(Map<String, Object> pMap);

    JSONArray getComment(String studyRoad_id);
}

package persistence.mongo;

import org.json.simple.JSONArray;

public interface ICommunityMapper {

    JSONArray getStudyRoadMap();

    JSONArray getCareerRoadMap();
}

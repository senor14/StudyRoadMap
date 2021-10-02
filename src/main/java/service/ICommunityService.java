package service;

import org.json.simple.JSONArray;

public interface ICommunityService {

    JSONArray getStudyMap(String typeCheck);

    JSONArray findStudyMap(String category, String searchType, String keyWord);
}

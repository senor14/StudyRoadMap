package service;

import org.json.simple.JSONArray;

import java.util.Map;

public interface ICommentService {

    JSONArray getComment(String studyRoadId);

    JSONArray findMyComment(String roadMapId, String userUuid);

    boolean insertComment(Map<String, Object> pMap);

    boolean deleteComment(String commentId, String userUuid);
}

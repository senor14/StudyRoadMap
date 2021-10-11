package persistence.mongo;

import org.json.simple.JSONArray;

import java.util.Map;

public interface ICommentMapper {

    JSONArray getComment(String studyRoadId);

    JSONArray findMyComment(String roadMapId, String userUuid);

    boolean insertComment(Map<String, Object> pMap);

    boolean deleteComment(String commentId, String userUuid);
}

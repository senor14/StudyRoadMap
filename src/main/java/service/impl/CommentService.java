package service.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;
import persistence.mongo.ICommentMapper;
import service.ICommentService;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service("CommentService")
public class CommentService implements ICommentService {

    @Resource(name = "CommentMapper")
    private ICommentMapper CommentMapper;

    @Override
    public JSONArray getComment(String studyRoadId) {
        return CommentMapper.getComment(studyRoadId);
    }

    @Override
    public JSONArray findMyComment(String roadMapId, String userUuid) {
        return CommentMapper.findMyComment(roadMapId, userUuid);
    }

    @Override
    public boolean insertComment(Map<String, Object> pMap) {
        return CommentMapper.insertComment(pMap);
    }

    @Override
    public boolean deleteComment(String commentId, String userUuid) {
        return CommentMapper.deleteComment(commentId, userUuid);
    }

}

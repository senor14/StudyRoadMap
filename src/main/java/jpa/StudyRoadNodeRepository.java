package jpa;

import domain.StudyRoadNodeData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudyRoadNodeRepository extends MongoRepository<StudyRoadNodeData, String> {

    StudyRoadNodeData getStudyRoadNodeDataByNodeId(String nodeId);

    List<StudyRoadNodeData> getAllByRoadId(String roadId);

    StudyRoadNodeData deleteByNodeId(String nodeId);
}

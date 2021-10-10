package jpa;

import domain.StudyRoadNodeData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudyRoadNodeRepository extends MongoRepository<StudyRoadNodeData, String> {

    List<StudyRoadNodeData> getAllByRoadId(String roadId);
}

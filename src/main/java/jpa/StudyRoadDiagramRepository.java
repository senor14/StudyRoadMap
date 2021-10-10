package jpa;

import domain.StudyRoadDiagramData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudyRoadDiagramRepository extends MongoRepository<StudyRoadDiagramData, String> {

    List<StudyRoadDiagramData> getAllByRoadId(String roadId);

}

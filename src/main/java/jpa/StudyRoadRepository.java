package jpa;

import domain.StudyRoadData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StudyRoadRepository  extends MongoRepository<StudyRoadData, String> {

    StudyRoadData findByRoadId(String roadId);

}

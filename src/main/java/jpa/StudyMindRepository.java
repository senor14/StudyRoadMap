package jpa;

import domain.StudyMindData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudyMindRepository extends MongoRepository<StudyMindData, String> {

        StudyMindData findByMindId(String mindId);

        List<StudyMindData> findAllByStudyRoadNodeId(String studyRoadNodeId);

        StudyMindData deleteByMindId(String mindId);

        List<StudyMindData> findStudyMindDataByStudyRoadNodeId(String roadNodeId);

}

package jpa;

import domain.StudyMindData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface StudyMindRepository extends MongoRepository<StudyMindData, String> {

        StudyMindData findByMindId(String mindId);

        List<StudyMindData> findAllByStudyRoadNodeId(String studyRoadNodeId);

        StudyMindData deleteByMindId(String mindId);

}

package jpa;

import domain.StudyMindData;
import domain.StudyMindNodeData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudyMindNodeRepository extends MongoRepository<StudyMindNodeData, String> {

    StudyMindNodeData findByMindId(String mindId);

    List<StudyMindNodeData> findAllByStudyRoadNodeId(String studyRoadNodeId);

    StudyMindNodeData deleteByMindId(String mindId);

    List<StudyMindNodeData> findAllBySourceOrTarget(String source, String target);

}


package jpa;

import domain.CareerRoadData;
import domain.StudyMindNodeData;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CareerRoadRepository extends MongoRepository<CareerRoadData, String> {

    List<CareerRoadData> findAllByNodeTypeAndUserUuid(String nodeType, String userUuid, Sort sort);
    CareerRoadData deleteByCareerRoadNodeIdAndUserUuid(String careerRoadNodeId, String userUuid);
    CareerRoadData findByCareerRoadNodeId(String careerRoadNodeId);
    List<CareerRoadData> findAllByUserUuidAndImportance(String userUuid, boolean importance, Sort sort);
}


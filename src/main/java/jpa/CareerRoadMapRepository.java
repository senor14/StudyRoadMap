package jpa;

import domain.CareerRoadData;
import domain.CareerRoadMap;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CareerRoadMapRepository extends MongoRepository<CareerRoadMap, String> {

    CareerRoadMap findByUserUuid(String userUuid);

}


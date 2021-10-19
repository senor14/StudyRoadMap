package service.impl;

import domain.CareerRoadData;
import domain.CareerRoadMap;
import jpa.CareerRoadMapRepository;
import jpa.CareerRoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import service.ICareerRoadService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("CareerRoadService")
public class CareerRoadService implements ICareerRoadService {

    private final MongoTemplate mongoTemplate;
    private final CareerRoadRepository careerRoadRepository;
    private final CareerRoadMapRepository careerRoadMapRepository;

    @Override
    public int insertNodeData(CareerRoadData node) throws Exception {
        log.info(this.getClass().getName() + "insertNodeData Start!");
        int res = 0;

        CareerRoadData save = mongoTemplate.insert(node, "CareerRoadData");

        log.info("save: " + save);
        if (save == null || save.equals("")) {
            res = 1;
        }

        log.info("res : " + res);
        log.info(this.getClass().getName() + "insertNodeData End!");

        return res;
    }

    @Override
    public int deleteNodeData(CareerRoadData node) throws Exception {
        log.info(this.getClass().getName() + "deleteNodeData Start!");
        int res = 0;

        CareerRoadData delete = careerRoadRepository.deleteByCareerRoadNodeIdAndUserUuid(node.getCareerRoadNodeId(), node.getUserUuid());

        log.info("delete: " + delete);
        if (delete!=null) {
            res = 0;
        } else {
            res = 1;
        }

        log.info("res : " + res);
        log.info(this.getClass().getName() + "deleteNodeData End!");

        return res;
    }

    @Override
    public int chkUpdateNodeData(CareerRoadData node) throws Exception {
        log.info(this.getClass().getName() + "chkUpdateNodeData Start!");

        CareerRoadData result = careerRoadRepository.findByCareerRoadNodeId(node.getCareerRoadNodeId());
        log.info("result before: "+ result);

        result.setImportance(node.getImportance());

        CareerRoadData save = careerRoadRepository.save(result);

        int res = 0;
        log.info("save : " + save);
        if (save!=null) {
            res = 0;
        } else {
            res = 1;
        }

        log.info("res : " + res);
        log.info(this.getClass().getName() + "chkUpdateNodeData End!");

        return res;
    }

    @Override
    public List<CareerRoadData> getImportanceNode(CareerRoadData node) throws Exception {
        log.info(this.getClass().getName()+".getImportanceNode Start!");

        Sort sort = sortByDateASD();
        List<CareerRoadData> result = careerRoadRepository.findAllByUserUuidAndImportance(node.getUserUuid(), node.getImportance(), sort);


        if(result==null){
         log.info("조회 데이터 없음");
        }

        log.info("results: "+result.toString());
        log.info(this.getClass().getName()+".getImportanceNode End!");

        return result;
    }

    @Override
    public CareerRoadMap getCareerRoadMapInfo(CareerRoadMap node) throws Exception {
        log.info(this.getClass().getName()+".getCareerRoadMapInfo Start!");

        log.info(node.getUserUuid());
        CareerRoadMap result = careerRoadMapRepository.findByUserUuid(node.getUserUuid());

        int res = 0;
        log.info("result : " + result);
        if (result!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info(this.getClass().getName()+".getCareerRoadMapInfo End!");
        return result;
    }

    @Override
    public int makeCareerRoadMap(CareerRoadMap node) throws Exception {
        log.info(this.getClass().getName()+".makeCareerRoadMap Start!");

        log.info(node.getUserUuid());
        CareerRoadMap result = mongoTemplate.insert(node, "CareerRoadMap");

        int res = 0;
        log.info("result : " + result);
        if (result!=null) {
            res = 0;
        } else {
            res = 1;
        }
        log.info(this.getClass().getName()+".makeCareerRoadMap End!");
        return res;
    }

    @Override
    public int chkCareerRoadMap(CareerRoadMap node) throws Exception {
        log.info(this.getClass().getName()+".makeCareerRoadMap Start!");

        log.info(node.getUserUuid());
        boolean result = careerRoadMapRepository.existsByUserUuid(node.getUserUuid());

        int res = 0;
        log.info("result : " + result);
        if (result==true) {
            res = 0;
        } else {
            res = 1;
        }

        log.info(this.getClass().getName()+".makeCareerRoadMap End!");
        return res;
    }

    @Override
    public List<CareerRoadData> getCareerNodeByNodeTypeAndUserUuid(CareerRoadData node) throws Exception {
        log.info(this.getClass().getName()+".getCareerNodeByNodeTypeAndUserUuid Start!");

        Sort sort = sortByDateDESC();
        List<CareerRoadData> result = careerRoadRepository.findAllByNodeTypeAndUserUuid(node.getNodeType(), node.getUserUuid(), sort);

        log.info("results: "+result.toString());
        log.info(this.getClass().getName()+".getCareerNodeByNodeTypeAndUserUuid End!");

        return result;
    }

    /* sort */
    private Sort sortByDateDESC() {
        return Sort.by(Sort.Direction.DESC, "date");
    }
    private Sort sortByDateASD() {
        return Sort.by(Sort.Direction.ASC, "date");
    }
}

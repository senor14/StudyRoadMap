package service;

import domain.CareerRoadData;
import domain.CareerRoadMap;

import java.util.List;

public interface ICareerRoadService {

    /* careerRoadData */
    public int insertNodeData(CareerRoadData node)throws Exception;
    public  List<CareerRoadData> getCareerNodeByNodeTypeAndUserUuid(CareerRoadData node)throws Exception;
    public int deleteNodeData(CareerRoadData node)throws Exception;
    public int chkUpdateNodeData(CareerRoadData node)throws Exception;
    public List<CareerRoadData> getImportanceNode(CareerRoadData node)throws Exception;

    /* careerRoadMap */
    public CareerRoadMap getCareerRoadMapInfo(CareerRoadMap node)throws Exception;


}

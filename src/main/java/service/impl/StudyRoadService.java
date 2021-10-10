package service.impl;

import domain.StudyRoadData;
import domain.StudyRoadDiagramData;
import domain.StudyRoadNodeData;
import jpa.StudyRoadDiagramRepository;
import jpa.StudyRoadNodeRepository;
import jpa.StudyRoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import service.IStudyRoadService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("StudyRoadService")
public class StudyRoadService implements IStudyRoadService {

    private final MongoTemplate mongoTemplate;
    private final StudyRoadRepository studyRoadRepository;
    private final StudyRoadNodeRepository studyRoadNodeRepository;
    private final StudyRoadDiagramRepository studyRoadDiagramRepository;

    @Override
    public StudyRoadData getRoadMapData(String roadId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapData Start!");

        StudyRoadData roadData = studyRoadRepository.findByRoadId(roadId);

        log.info("roadData:"+roadData.toString());
        log.info(this.getClass().getName()+".getRoadMapData End!");

        return roadData;
    }

    @Override
    public List<StudyRoadData> getPublicRoadTitle(String roadTitle) throws Exception {

        log.info(this.getClass().getName()+".getPublicRoadTitle Start!");

        

        log.info(this.getClass().getName()+".getPublicRoadTitle End!");

        return null;
    }

    @Override
    public List<StudyRoadDiagramData> getRoadMapDiagram(String roadId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapDiagram Start!");

        List<StudyRoadDiagramData> results = studyRoadDiagramRepository.getAllByRoadId(roadId);

        log.info("results: "+results);
        log.info(this.getClass().getName()+".getRoadMapDiagram End!");

        return results;
    }

    @Override
    public List<StudyRoadNodeData> getRoadMapNode(String roadId) throws Exception {

        log.info(this.getClass().getName()+".getRoadMapNode Start!");

        List<StudyRoadNodeData> results = studyRoadNodeRepository.getAllByRoadId(roadId);

        log.info("results: "+results);
        log.info(this.getClass().getName()+".getRoadMapNode End!");
        return results;
    }
}

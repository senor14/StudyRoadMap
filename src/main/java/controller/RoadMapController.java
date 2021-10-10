package controller;

import domain.StudyRoadData;
import domain.StudyRoadDiagramData;
import domain.StudyRoadNodeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IStudyRoadService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class RoadMapController {

    @Resource(name = "StudyRoadService")
    IStudyRoadService studyRoadService;

    @GetMapping("/roadMap")
    public String roadMap() {
        return "roadMap";
    }

    @GetMapping("/stroadMap")
    public String stroadMap() {
        return "stroadMap";
    }


    // 스터디 로드맵 상세보기
    @GetMapping("/roadMap/{roadId}")
    public String getRoadMap(@PathVariable String roadId,
                             ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".getRoadMap Start!");

        StudyRoadData roadData = new StudyRoadData();
        StudyRoadDiagramData diagramData = new StudyRoadDiagramData();
        StudyRoadNodeData nodeData = new StudyRoadNodeData();
        roadData.setRoadId("f63c5537-4644-42e0-b11d-bf92291de4f5");
        diagramData.setRoadId("f63c5537-4644-42e0-b11d-bf92291de4f5");
        nodeData.setRoadId("f63c5537-4644-42e0-b11d-bf92291de4f5");

        StudyRoadData roadMapInfo = studyRoadService.getRoadMapData("f63c5537-4644-42e0-b11d-bf92291de4f5");
        List<StudyRoadDiagramData> diagramInfo = studyRoadService.getRoadMapDiagram("f63c5537-4644-42e0-b11d-bf92291de4f5");
        List<StudyRoadNodeData> nodeInfo = studyRoadService.getRoadMapNode("f63c5537-4644-42e0-b11d-bf92291de4f5");


        log.info("roadMapInfo: "+roadMapInfo.toString());
        log.info("diagramInfo: "+diagramInfo.toString());
        log.info("nodeInfo: "+nodeInfo.toString());

        model.addAttribute("roadMapInfo",roadMapInfo);
        model.addAttribute("diagramInfo",diagramInfo);
        model.addAttribute("nodeInfo",nodeInfo);

        roadMapInfo = null;
        diagramInfo = null;
        nodeInfo = null;

        log.info(this.getClass().getName() + ".getRoadMap End!");

        return "stroadMap";
    }

}

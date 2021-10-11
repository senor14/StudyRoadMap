package controller;

import domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.IStudyMindService;
import service.IStudyRoadService;
import util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/")
public class RoadMapController {

    @Resource(name = "StudyRoadService")
    IStudyRoadService studyRoadService;

    @Resource(name = "StudyMindService")
    IStudyMindService studyMindService;

    @GetMapping("/roadMap")
    public String roadMap() {
        return "roadMap";
    }

    @GetMapping("/stroadMap")
    public String stroadMap() {
        return "stroadMap";
    }


    // 스터디 로드맵 상세보기
    @GetMapping("/roadmaps/{roadId}")
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
        List<StudyRoadDiagramData> diagramInfo = studyRoadService.getRoadMapDiagramByRoadId("f63c5537-4644-42e0-b11d-bf92291de4f5");
        List<StudyRoadNodeData> nodeInfo = studyRoadService.getRoadMapNodeByRoadId("f63c5537-4644-42e0-b11d-bf92291de4f5");


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

    // 로드타이틀로 로드맵 검색
    @GetMapping("/roadmaps/search/roadTitle")
    public ResponseEntity<List<StudyRoadData>> searchRoadMapByRoadTitle(String roadTitle) throws Exception {

        log.info(this.getClass().getName()+".searchRoadMapByRoadTitle Start!");

        List<StudyRoadData> publicRoadTitles = studyRoadService.getPublicRoadTitle(roadTitle);

        log.info("publicRoadTitles: "+publicRoadTitles.toString());

        log.info(this.getClass().getName()+".searchRoadMapByRoadTitle End!");

        return ResponseEntity.status(HttpStatus.OK).body(publicRoadTitles);
    }

    // 카테고리로 로드맵 검색
    @GetMapping("/roadmaps/search/category")
    public ResponseEntity<List<StudyRoadData>> searchRoadMapByNodeCategory(String category) throws  Exception {

        log.info(this.getClass().getName()+".searchRoadMapByNodeCategory Start!");

        List<String> roadIds = studyRoadService.getRoadIdsByNodeCategory(category);
        log.info("roadIds: "+roadIds);
        List<StudyRoadData> results = studyRoadService.getPublicNodeCategory(roadIds);
        log.info("results: "+ results.toString());
        log.info(this.getClass().getName()+".searchRoadMapByNodeCategory End!");

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }


    // 유저 Uuid 로 스터디로드 데이터 리스트 가져오기
    @GetMapping("/roadmaps/user")
    public ResponseEntity<List<StudyRoadData>> getStudyDataByUserUuid() throws Exception {

        log.info(this.getClass().getName()+".getStudyDataByUserUuid Start!");

        List<StudyRoadData> results = studyRoadService.getRoadDataByUserUuid("9db17796-2357-4171-edbe-f4b54b040497");

        log.info("results: "+results);
        log.info(this.getClass().getName()+".getStudyDataByUserUuid End!");
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    // 스터디로드 데이터 삽입
    @PostMapping("/roadmaps/{roadId}")
    public ResponseEntity<Integer> insertStudyRoadData(
                                    @PathVariable String roadId,
                                    HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".insertStudyRoadData Start!");

        String randomRoadId = UUID.randomUUID().toString();
        log.info("randomRoadId: "+randomRoadId);

        StudyRoadData road = new StudyRoadData();
        road.setRoadId(randomRoadId);
        road.setUserUuid("9db17796-2357-4171-edbe-f4b54b040497");
        road.setPublicYn("Y");
        road.setRoadTitle("그림그리기");
        road.setCreated(DateUtil.getDateTime());

        log.info("road: "+ road.toString());

        studyRoadService.insertRoadData(road);

        log.info(this.getClass().getName() + ".insertStudyRoadData End!");

        return ResponseEntity.status(HttpStatus.OK).body(0);
    }

    // 스터디로드 다이어그램 데이터 삽입
    @PostMapping("/roadmaps/{roadId}/diagram")
    public ResponseEntity<StudyRoadDiagramData> insertStudyRoadDiagram(
            @PathVariable String roadId,
            HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".insertStudyRoadDiagram Start!");

        String randomDiagramId = UUID.randomUUID().toString();
        log.info("randomDiagramId: "+randomDiagramId);

        StudyRoadDiagramData diagramData = new StudyRoadDiagramData();
        diagramData.setDiagramId(randomDiagramId);
        diagramData.setRoadId(roadId);
        diagramData.setLaneWidth("100px");
        diagramData.setKey("lane000");
        diagramData.setColor("green");
        diagramData.setSize("210");
        diagramData.setLoc("-157.54195404052734 75.76346029127669");
        diagramData.setCreated(DateUtil.getDateTime());

        log.info("diagramData: "+ diagramData.toString());

        studyRoadService.insertRoadDiagram(diagramData);

        log.info(this.getClass().getName() + ".insertStudyRoadDiagram End!");

        return ResponseEntity.status(HttpStatus.OK).body(diagramData);
    }

    // 스터디로드 노드 데이터 삽입
    @PostMapping("/roadmaps/{roadId}/node")
    public ResponseEntity<List<StudyRoadNodeData>> insertStudyRoadNode(
            @PathVariable String roadId,
            HttpServletRequest request) throws Exception {

        List<StudyRoadNodeData> rList = new ArrayList<>();

        log.info(this.getClass().getName() + ".insertStudyRoadDiagram Start!");

        String randomNodeId = UUID.randomUUID().toString();
        String randomEdgeId = UUID.randomUUID().toString();
        log.info("randomNodeId: "+randomNodeId);
        log.info("randomEdgeId: "+randomEdgeId);

        StudyRoadNodeData nodeData = new StudyRoadNodeData();
        nodeData.setNodeId(randomNodeId);
        nodeData.setRoadId(roadId);
        nodeData.setGroup("nodes");
        nodeData.setKey(randomNodeId);
        nodeData.setNodeText("리액트");
        nodeData.setNodeLoc("250 350");
        nodeData.setNodeCategory("FrontEnd");

        rList.add(nodeData);
        log.info("nodeData: "+ nodeData.toString());
        studyRoadService.insertRoadNode(nodeData);

        StudyRoadNodeData edgeData = new StudyRoadNodeData();
        edgeData.setNodeId(randomEdgeId);
        edgeData.setRoadId(roadId);
        edgeData.setGroup("edges");
        edgeData.setFrom("ae0cabfa-5b94-44b3-dfc2-edadfdf409b3");
        edgeData.setTo(randomNodeId);

        rList.add(edgeData);
        studyRoadService.insertRoadNode(edgeData);
        log.info("edgeData: "+ edgeData.toString());

        log.info(this.getClass().getName() + ".insertStudyRoadDiagram End!");

        return ResponseEntity.status(HttpStatus.OK).body(rList);
    }

    // 스터디로드 데이터 수정
    @PutMapping("/roadmaps/{roadId}")
    public ResponseEntity<Integer> updateStudyRoadData(
                                    @PathVariable String roadId,
                                    @RequestBody StudyRoadData road,
                                    HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".updateStudyRoadData Start!");

        StudyRoadData roadData = new StudyRoadData();
        roadData.setRoadId(roadId);
        roadData.setPublicYn("Y");
        roadData.setRoadTitle("웹 프로젝트");

        int res = studyRoadService.updateRoadData(roadData);

        log.info(this.getClass().getName() + ".updateStudyRoadData End!");

        return ResponseEntity.status(HttpStatus.OK).body(res);

    }

    // 스터디 다이어그램 데이터 수정
    @PutMapping("/roadmaps/{roadId}/diagrams/{diagramId}")
    public ResponseEntity<Integer> updateStudyRoadDiagram (
                                        @PathVariable String roadId,
                                        @PathVariable String diagramId,
                                        @RequestBody StudyRoadDiagramData diagram
                                        ) throws Exception {

        log.info(this.getClass().getName()+".updateStudyRoadDiagram Start!");

        StudyRoadDiagramData diagramData = studyRoadService.getRoadMapDiagramData(diagramId);

        diagramData.setLaneWidth("100px");
        diagramData.setKey("lane0000");
        diagramData.setText("10학기");
        diagramData.setColor("yellow");
        diagramData.setSize("215");
        diagramData.setLoc("-182.54195404052734 60.76346029127669");

        log.info("diagramData: "+ diagramData.toString());

        int res = studyRoadService.updateRoadDiagramData(diagramData);

        log.info("res: "+ res);

        log.info(this.getClass().getName()+".updateStudyRoadDiagram End!");

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 스터디 노드 데이터 수정
    @PutMapping("/roadmaps/{roadId}/nodes/{nodeId}")
    public ResponseEntity<Integer> updateStudyRoadNode (
            @PathVariable String roadId,
            @PathVariable String nodeId,
            @RequestBody StudyRoadNodeData node
    ) throws Exception {

        log.info(this.getClass().getName()+".updateStudyRoadNode Start!");

        StudyRoadNodeData nodeData = studyRoadService.getRoadMapNodeData(nodeId);

        if (nodeData.getGroup().equals("nodes")) {
            nodeData.setNodeText("타입스크립트");
            nodeData.setNodeLoc("250 350");
            nodeData.setNodeCategory("DevOps");
        }

        log.info("nodeData: "+ nodeData.toString());

        int res = studyRoadService.updateRoadNodeData(nodeData);

        log.info("res: "+ res);

        log.info(this.getClass().getName()+".updateStudyRoadNode End!");

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 로드 id로 다이어그램 데이터 삭제
    @DeleteMapping("/roadmaps/{roadId}")
    public ResponseEntity<Integer> deleteRoadMapData(
                                    @PathVariable String roadId) throws Exception {

        log.info(this.getClass().getName() + ".deleteRoadMapData Start!");

        int roadData = studyRoadService.deleteRoadData(roadId);
        log.info("roadData: "+roadData);

        log.info(this.getClass().getName() + ".deleteRoadMapData End!");

        return ResponseEntity.status(HttpStatus.OK).body(roadData);
    }

    // 다이어그램 id로 다이어그램 데이터 삭제
    @DeleteMapping("/roadmaps/{roadId}/diagrams/{diagramId}")
    public ResponseEntity<Integer> deleteStudyRoadDiagram(
            @PathVariable String roadId,
            @PathVariable String diagramId) throws Exception {

        log.info(this.getClass().getName() + ".deleteStudyRoadDiagram Start!");

        int diagramData = studyRoadService.deleteRoadDiagram(diagramId);
        log.info("diagramId: "+diagramId);

        log.info(this.getClass().getName() + ".deleteStudyRoadDiagram End!");

        return ResponseEntity.status(HttpStatus.OK).body(diagramData);
    }

    // 노드 id로 노드 데이터 삭제
    @DeleteMapping("/roadmaps/{roadId}/nodes/{nodeId}")
    public ResponseEntity<Integer> deleteStudyRoadNode(
            @PathVariable String roadId,
            @PathVariable String nodeId) throws Exception {

        log.info(this.getClass().getName() + ".deleteStudyRoadNode Start!");

        int nodeData = studyRoadService.deleteRoadNode(nodeId);
        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName() + ".deleteStudyRoadNode End!");

        return ResponseEntity.status(HttpStatus.OK).body(nodeData);
    }

    // 스터디 로드맵 복제
    @PostMapping("/roadmaps/{roadId}/duplicate")
    public ResponseEntity<Integer> duplicateStudyRoadMap(
            @PathVariable String roadId) throws Exception {

        log.info(this.getClass().getName() + ".duplicateStudyRoadMap Start!");

//        String newRoadId = UUID.randomUUID().toString();
        String newRoadId = "44904743-bc65-459d-ab7b-7a632ba02178";
        String userUuid = "2033c8ef-4970-4217-b67b-ba6b65a01231";

        // 스터디로드 id로 StudyRoadData 데이터 가져오기
        StudyRoadData roadMapData = studyRoadService.getRoadMapData(roadId);
        roadMapData.setUserUuid(userUuid);
        roadMapData.setRoadId(newRoadId);

        // StudyRoadData 데이터를 유저 UUID, 새로운 스터디로드 id 로 삽입
        int res1 = studyRoadService.insertRoadData(roadMapData);
        log.info("roadMapData: "+res1);

        // 스터디로드 id로 StudyRoadDiagramData 데이터 리스트 가져오기
        List<StudyRoadDiagramData> roadDiagramData = studyRoadService.getRoadMapDiagramByRoadId(roadId);
        int res2 = 0;

        // StudyRoadDiagramData 리스트를 각각 새로운 스터디로드 id 로 삽입
        for (StudyRoadDiagramData diagramData : roadDiagramData) {
            diagramData.setDiagramId(UUID.randomUUID().toString());
            diagramData.setRoadId(newRoadId);
            res2 += studyRoadService.insertRoadDiagram(diagramData);
        }
        log.info("roadDiagramData: "+res2);

        // 스터디로드 id로 StudyRoadNodeData 데이터 리스트 가져오기
        List<StudyRoadNodeData> roadNodeData = studyRoadService.getRoadMapNodeByRoadId(roadId);
        int res3 = 0;
        int res4 = 0;
        // StudyRoadNodeData 리스트를 각각 새로운 스터디로드 id 로 삽입
        for (StudyRoadNodeData nodeData : roadNodeData) {
            String newRoadNodeId = UUID.randomUUID().toString();

            // 스터디로드노드 id로 StudyMindData 데이터 리스트 가져오기
            List<StudyMindData> mindData =studyMindService.getMindDataByRoadNodeId(nodeData.getNodeId());
            int res5 = 0;
            List<String> mindIdTemp = new LinkedList<>();
            // StudyMindData 리스트를 각각 유저 UUID, 새로운 스터디로드 id  로 삽입
            for (StudyMindData mind : mindData) {
                String newMindId = UUID.randomUUID().toString();
                mindIdTemp.add(newMindId);
                mind.setMindId(newMindId);
                mind.setUserUuid(userUuid);
                mind.setStudyRoadId(newRoadId);
                mind.setStudyRoadNodeId(newRoadNodeId);
                mind.setCreated(DateUtil.getDateTime());
                res5 += studyMindService.insertMindData(mind);
                res4 += res5;
            }
            log.info("res5: "+res5);

            // 스터디로드노드 id로 StudyMindNodeData 데이터 리스트 가져오기
            List<StudyMindNodeData> mindNode = studyMindService.getMindNodeByRoadNodeId(nodeData.getNodeId());
            int res6 = 0;
            int i = 0;
            // StudyMindNodeData 리스트를 각각 유저 UUID, 새로운 스터디로드 id로 삽입
            for (StudyMindNodeData node : mindNode) {
                if(node.getGroup().equals("nodes")) {
                    node.setMindId(mindIdTemp.get(i++));
                } else {
                    node.setMindId(UUID.randomUUID().toString());
                }
                node.setUserUuid(userUuid);
                node.setStudyRoadId(newRoadId);
                node.setStudyRoadNodeId(newRoadNodeId);
                res6 += studyMindService.insertNodeData(node);
                res4 += res6;
            }
            log.info("mindNode: "+res6);

            nodeData.setNodeId(newRoadNodeId);
            nodeData.setKey(newRoadNodeId);
            nodeData.setRoadId(newRoadId);
            res3 += studyRoadService.insertRoadNode(nodeData);

            mindIdTemp = null;
        }
        log.info("roadNodeData: "+res3);
        log.info("mindData+mindNode: "+res4);

        log.info(this.getClass().getName() + ".duplicateStudyRoadMap End!");

        return ResponseEntity.status(HttpStatus.OK).body(res1+res2+res3+res4);
    }


}

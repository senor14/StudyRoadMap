package controller;

import static util.CmmUtil.nvl;

import com.sun.corba.se.impl.orbutil.graph.NodeData;
import domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.IStudyMindService;
import service.IStudyRoadService;
import util.DateUtil;
import vo.RequestNodeData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    
    // 유저 Uuid 로 스터디로드 데이터 리스트 가져오기
    @GetMapping("/roadmaps")
    public ResponseEntity<List<StudyRoadData>> getStudyDataByUserUuid(HttpSession session) throws Exception {

        log.info(this.getClass().getName()+".getStudyDataByUserUuid Start!");

        List<StudyRoadData> results = studyRoadService.getRoadDataByUserUuid(((String)session.getAttribute("SS_USER_UUID")));

        log.info("results: "+results);
        log.info(this.getClass().getName()+".getStudyDataByUserUuid End!");
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    // 스터디 로드맵 상세보기
    @GetMapping("/roadmaps/{roadId}")
    public String getRoadMap(@PathVariable String roadId,
                             ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".getRoadMap Start!");

        StudyRoadData roadData = new StudyRoadData();
        StudyRoadNodeData nodeData = new StudyRoadNodeData();
        roadData.setRoadId(roadId);
        nodeData.setRoadId(roadId);

        StudyRoadData roadMapInfo = studyRoadService.getRoadMapData(roadId);
        List<StudyRoadNodeData> nodeInfo = studyRoadService.getRoadMapNodeByRoadId(roadId);

        log.info("roadMapInfo: "+roadMapInfo);
        log.info("nodeInfo: "+nodeInfo);

        model.addAttribute("roadMapInfo",roadMapInfo);
        model.addAttribute("nodeInfo",nodeInfo);

        roadMapInfo = null;
        nodeInfo = null;

        log.info(this.getClass().getName() + ".getRoadMap End!");

        return "stroadMap";
    }

    // 로드타이틀로 로드맵 검색
    @GetMapping("/roadmaps/roadTitle")
    public ResponseEntity<List<StudyRoadData>> searchRoadMapByRoadTitle(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName()+".searchRoadMapByRoadTitle Start!");
        log.info("keyword: "+nvl(request.getParameter("name")));
        List<StudyRoadData> publicRoadTitles = studyRoadService.getPublicRoadTitle(nvl(request.getParameter("name")));

        log.info("publicRoadTitles: "+publicRoadTitles);

        log.info(this.getClass().getName()+".searchRoadMapByRoadTitle End!");

        return ResponseEntity.status(HttpStatus.OK).body(publicRoadTitles);
    }

    // 카테고리로 로드맵 검색
    @GetMapping("/roadmaps/category")
    public ResponseEntity<List<StudyRoadData>> searchRoadMapByNodeCategory(HttpServletRequest request) throws  Exception {

        log.info(this.getClass().getName()+".searchRoadMapByNodeCategory Start!");
        log.info("keyword: "+nvl(request.getParameter("name")));
        List<String> roadIds = studyRoadService.getRoadIdsByCategory(nvl(request.getParameter("name")));
        log.info("roadIds: "+roadIds);
        List<StudyRoadData> results = studyRoadService.getPublicCategory(roadIds);
        log.info("results: "+ results.toString());
        log.info(this.getClass().getName()+".searchRoadMapByNodeCategory End!");

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    // 스터디로드 데이터 삽입 / 초기 다이어그램 노드 삽입
    @PostMapping("/roadmaps")
    public ResponseEntity<String> insertDefaultStudyRoadData(
            HttpServletRequest request,
            HttpSession session,
            ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertDefaultStudyRoadData Start!");

        String randomRoadId = UUID.randomUUID().toString();
        log.info("randomRoadId: "+randomRoadId);

        StudyRoadData road = new StudyRoadData();
        road.setRoadId(randomRoadId);
        road.setUserUuid((String)session.getAttribute("SS_USER_UUID"));
        road.setPublicYn(nvl(request.getParameter("publicYn")));
        road.setRoadTitle(nvl(request.getParameter("roadTitle")));

        road.setCreated(DateUtil.getDateTime());

        studyRoadService.insertRoadData(road);

        String randomNodeId = UUID.randomUUID().toString();

        // 초기 다이어그램 노드 생성
        StudyRoadNodeData diagramData = new StudyRoadNodeData();
        diagramData.setNodeId(randomNodeId);
        diagramData.setRoadId(randomRoadId);
        diagramData.setCanvasClass("Diagram");
        diagramData.setCategory("Pool");
        diagramData.setKey("Pool");
        diagramData.setText(nvl(request.getParameter("roadTitle")));

        diagramData.setIsGroup("true");
        // 사이즈랑 좌표는 생성시는 넣지 않아도 됨 -> 랜더링시 자동생성
//        diagramData.setLoc("-171.54195404052734 29.240612792968747");

        log.info("diagramData: "+ diagramData);
        studyRoadService.insertRoadNode(diagramData);

//        model.addAttribute("url", "/roadmaps/"+randomRoadId);

        log.info(this.getClass().getName() + ".insertDefaultStudyRoadData End!");

//        return ResponseEntity.status(HttpStatus.OK).body(0);
        return ResponseEntity.status(HttpStatus.OK).body("/roadmaps/"+randomRoadId);
    }

    // 스터디로드 노드 데이터 삽입
    @PostMapping("/roadmaps/{roadId}/nodes/{canvasClass}")
    public ResponseEntity<StudyRoadNodeData> insertStudyRoadNode(
            @PathVariable String roadId,
            @PathVariable String canvasClass,
            HttpServletRequest request) throws Exception {


        log.info(this.getClass().getName() + ".insertStudyRoadNode Start!");

        String randomNodeId = UUID.randomUUID().toString();

        log.info("randomNodeId: "+randomNodeId);

        StudyRoadNodeData result = new StudyRoadNodeData();

        if (canvasClass.equals("Lane")) {

            StudyRoadNodeData laneData = new StudyRoadNodeData();
            laneData.setNodeId(randomNodeId);
            laneData.setRoadId(roadId);
            laneData.setCanvasClass("Lane");
            laneData.setKey(nvl(request.getParameter("laneKey")));
            laneData.setText(nvl(request.getParameter("laneText")));
            laneData.setIsGroup("true");
            laneData.setGroup("Pool");
            laneData.setColor(nvl(request.getParameter("laneColor")));

            // 사이즈랑 좌표는 생성시는 넣지 않아도 됨 -> 랜더링시 자동생성
//            laneData.setSize("110 200");
//            laneData.setLoc("250 350");

            log.info("laneData: "+ laneData);

            studyRoadService.insertRoadNode(laneData);

            result = laneData;
        } else if (canvasClass.equals("Node")) {

            StudyRoadNodeData nodeData = new StudyRoadNodeData();
            nodeData.setNodeId(randomNodeId);
            nodeData.setRoadId(roadId);
            nodeData.setCanvasClass("Node");
            nodeData.setCategory(nvl(request.getParameter("category")));
            nodeData.setKey(randomNodeId);
            nodeData.setText(nvl(request.getParameter("nodeText")));

            // 좌표는 생성시는 넣지 않아도 됨 -> 랜더링시 자동생성
            nodeData.setLoc(nvl(request.getParameter("loc")));

            log.info("nodeData: "+ nodeData);

            studyRoadService.insertRoadNode(nodeData);

            String randomMindId = UUID.randomUUID().toString();

            StudyMindData mind = new StudyMindData();
            mind.setMindId(randomMindId);
            mind.setStudyRoadId(roadId);
            mind.setStudyRoadNodeId(randomNodeId);
            mind.setMindLabel(nvl(request.getParameter("nodeText")));
            mind.setMindContents(nvl(request.getParameter("nodeText"))+" 입니다.");
            mind.setUrl("x");
            mind.setBookTitle("x");
            mind.setBookLink("x");
            mind.setCreated(DateUtil.getDateTime());
            log.info("mind: "+mind);
            int mRes = studyMindService.insertMindData(mind);
            log.info("mRes: "+mRes);

            StudyMindNodeData node = new StudyMindNodeData();
            node.setMindId(randomMindId);
            node.setStudyRoadId(roadId);
            node.setStudyRoadNodeId(randomNodeId);
            node.setKey(randomMindId);
            node.setGroup("nodes");
            node.setMindLabel(nvl(request.getParameter("nodeText")));
            node.setX("0");
            node.setY("0");
            log.info("node: "+node);
            int nRes = studyMindService.insertNodeData(node);
            log.info("nRes: "+nRes);

            result = nodeData;

        } else if (canvasClass.equals("Edge")) {

            StudyRoadNodeData edgeData = new StudyRoadNodeData();
            edgeData.setNodeId(randomNodeId);
            edgeData.setRoadId(roadId);
            edgeData.setCanvasClass("Edge");
            edgeData.setFrom(nvl(request.getParameter("from")));
            edgeData.setTo(nvl(request.getParameter("to")));

            // 좌표는 생성시는 넣지 않아도 됨 -> 랜더링시 자동생성
//            EdgeData.setLoc("-162.86665532820518 122.60000347951444");

            log.info("edgeData: "+ edgeData);

            studyRoadService.insertRoadNode(edgeData);

            result = edgeData;

        } else if (canvasClass.equals("Category")) {

            StudyRoadNodeData categoryData = new StudyRoadNodeData();
            categoryData.setNodeId(randomNodeId);
            categoryData.setRoadId(roadId);
            categoryData.setCanvasClass("Category");
            categoryData.setText(nvl(request.getParameter("categoryText")));
            categoryData.setColor(nvl(request.getParameter("categoryColor")));

            log.info("categoryData: "+ categoryData);

            studyRoadService.insertRoadNode(categoryData);

            result = categoryData;

        }

        log.info(this.getClass().getName() + ".insertStudyRoadNode End!");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 스터디로드 데이터 수정
    @PutMapping("/roadmaps/{roadId}")
    public ResponseEntity<Integer> updateStudyRoadData(
                                    @PathVariable String roadId,
                                    @RequestBody StudyRoadData road) throws Exception {

        log.info(this.getClass().getName() + ".updateStudyRoadData Start!");

        road.setRoadId(roadId);

        int res = studyRoadService.updateRoadData(road);

        log.info(this.getClass().getName() + ".updateStudyRoadData End!");

        return ResponseEntity.status(HttpStatus.OK).body(res);

    }

    // 스터디 노드 데이터 수정
    @PutMapping("/roadmaps/{roadId}/nodes/{nodeId}")
    public ResponseEntity<StudyRoadNodeData> updateStudyRoadNode (
            @PathVariable String roadId,
            @PathVariable String nodeId,
            @RequestBody RequestNodeData node
    ) throws Exception {

        log.info(this.getClass().getName()+".updateStudyRoadNode Start!");

        StudyRoadNodeData nodeData = studyRoadService.getRoadMapNodeData(nodeId);

        if (node.getType().equals("loc")) {
            nodeData.setLoc(node.getLoc());
        } else if (node.getType().equals("size")) {
            nodeData.setSize(node.getSize());
        } else {
            if (nodeData.getCanvasClass().equals("Diagram")) {
                nodeData.setText(node.getDiagramText());
                if (node.getLoc() != null && !node.getLoc().equals("")) {
                    nodeData.setLoc(node.getLoc());
                }
            } else if (nodeData.getCanvasClass().equals("Lane")) {
                nodeData.setKey(node.getLaneKey());
                nodeData.setText(node.getLaneText());
                nodeData.setColor(node.getLaneColor());
                if (node.getSize() != null && !node.getSize().equals("")) {
                    nodeData.setSize(node.getSize());
                }
                if (node.getLoc() != null && !node.getLoc().equals("")) {
                    nodeData.setLoc(node.getLoc());
                }
            } else if (nodeData.getCanvasClass().equals("Node")) {
                nodeData.setText(node.getNodeText());
                nodeData.setCategory(node.getCategory());
                if (node.getLoc() != null && !node.getLoc().equals("")) {
                    nodeData.setLoc(node.getLoc());
                }
            } else if (nodeData.getCanvasClass().equals("Category")) {
                nodeData.setText(node.getCategoryText());
                nodeData.setColor(node.getCategoryColor());
            }
        }

        log.info("nodeData: "+ nodeData);

        int res = studyRoadService.updateRoadNodeData(nodeData);

        log.info("res: "+ res);

        log.info(this.getClass().getName()+".updateStudyRoadNode End!");

        return ResponseEntity.status(HttpStatus.OK).body(nodeData);
    }

    // roadId로 로드맵 데이터 삭제
    @DeleteMapping("/roadmaps/{roadId}")
    public ResponseEntity<Integer> deleteStudyRoadData(
            @PathVariable String roadId) throws Exception {

        log.info(this.getClass().getName() + ".deleteStudyRoadData Start!");

        int roadData = studyRoadService.deleteRoadData(roadId);
        log.info("roadData: "+roadData);

        log.info(this.getClass().getName() + ".deleteStudyRoadData End!");

        return ResponseEntity.status(HttpStatus.OK).body(roadData);
    }

    // nodeId로 노드 데이터 삭제
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
            @PathVariable String roadId,
            HttpSession session) throws Exception {

        log.info(this.getClass().getName() + ".duplicateStudyRoadMap Start!");

        String newRoadId = UUID.randomUUID().toString();
        String userUuid = (String)session.getAttribute("SS_USER_UUID");
        log.info("userUuid: "+userUuid);

        // 스터디로드 id로 StudyRoadData 데이터 가져오기
        StudyRoadData roadMapData = studyRoadService.getRoadMapData(roadId);
        roadMapData.setUserUuid(userUuid);
        roadMapData.setRoadId(newRoadId);
        roadMapData.setCreated(DateUtil.getDateTime());

        // StudyRoadData 데이터를 유저 UUID, 새로운 스터디로드 id 로 삽입
        int res1 = studyRoadService.insertRoadData(roadMapData);
        log.info("roadMapData: "+res1);

        // 스터디로드 id로 StudyRoadNodeData 데이터 리스트 가져오기
        List<StudyRoadNodeData> roadNodeData = studyRoadService.getRoadMapNodeByRoadId(roadId);
        int res2 = 0;
        int res3 = 0;
        // StudyRoadNodeData 리스트를 각각 새로운 스터디로드 id 로 삽입
        for (StudyRoadNodeData nodeData : roadNodeData) {
            String newRoadNodeId = UUID.randomUUID().toString();

            // 스터디로드노드 id로 StudyMindData 데이터 리스트 가져오기
            List<StudyMindData> mindData =studyMindService.getMindDataByRoadNodeId(nodeData.getNodeId());
            int res4 = 0;
            List<String> mindIdTemp = new LinkedList<>();
            // StudyMindData 리스트를 각각 유저 UUID, 새로운 스터디로드 id  로 삽입
            for (StudyMindData mind : mindData) {
                String newMindId = UUID.randomUUID().toString();
                mindIdTemp.add(newMindId);
                mind.setMindId(newMindId);
                mind.setStudyRoadId(newRoadId);
                mind.setStudyRoadNodeId(newRoadNodeId);
                mind.setCreated(DateUtil.getDateTime());
                res4 += studyMindService.insertMindData(mind);
                res3 += res4;
            }
            log.info("mindData: "+res4);

            // 스터디로드노드 id로 StudyMindNodeData 데이터 리스트 가져오기
            List<StudyMindNodeData> mindNode = studyMindService.getMindNodeByRoadNodeId(nodeData.getNodeId());
            int res5 = 0;
            int i = 0;
            // StudyMindNodeData 리스트를 각각 유저 UUID, 새로운 스터디로드 id로 삽입
            for (StudyMindNodeData node : mindNode) {
                if(node.getGroup().equals("nodes")) {
                    node.setMindId(mindIdTemp.get(i++));
                } else {
                    node.setMindId(UUID.randomUUID().toString());
                }
                node.setStudyRoadId(newRoadId);
                node.setStudyRoadNodeId(newRoadNodeId);
                res5 += studyMindService.insertNodeData(node);
                res3 += res5;
            }
            log.info("mindNode: "+res5);

            nodeData.setNodeId(newRoadNodeId);
            nodeData.setRoadId(newRoadId);
            res2 += studyRoadService.insertRoadNode(nodeData);

            mindIdTemp = null;
        }
        log.info("roadNodeData: "+res2);
        log.info("mindData+mindNode: "+res3);

        log.info(this.getClass().getName() + ".duplicateStudyRoadMap End!");

        return ResponseEntity.status(HttpStatus.OK).body(res1+res2+res3);
    }

}

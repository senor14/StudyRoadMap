package controller;

import domain.StudyMindData;
import domain.StudyMindNodeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.IStudyMindService;
import util.DateUtil;
import vo.ResponseNodeData;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static util.CmmUtil.nvl;

/**
 * Developer: 김선열
 * Comment: 마인드맵 관련 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/")
public class StudyMindController {

    @Resource(name = "StudyMindService")
    IStudyMindService studyMindService;

    // 스터디 로드맵 노드 속의 마인드맵 조회 ------------------------------및 초기 생성
    @GetMapping("/roadmaps/{roadId}/nodes/{nodeId}")
    public String getMindMap(@PathVariable String nodeId,
                             @PathVariable String roadId,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap model
                             ) throws Exception {

        log.info(this.getClass().getName() + ".getMindMap Start!");

        StudyMindData mindData = new StudyMindData();
        StudyMindNodeData nodeData = new StudyMindNodeData();
        mindData.setStudyRoadId(roadId);
        nodeData.setStudyRoadId(roadId);
        mindData.setStudyRoadNodeId(nodeId);
        nodeData.setStudyRoadNodeId(nodeId);

        List<StudyMindData> mindMapInfo = studyMindService.getMindMapData(mindData);
        List<StudyMindNodeData> mindMapNode = studyMindService.getMindMapNode(nodeData);

        log.info("mindMap size: "+ mindMapInfo.size());
        log.info("mindMapNode size: "+ mindMapNode.size());

//        // 기존 마인드맵이 존재하지 않을 시 새로 마인드맵 생성 후 재조회
//        if (mindMapInfo.size() == 0) {
//
//            String msg;
//            String url = "/roadmaps/"+roadId+"/nodes/"+nodeId;
//            String randomMindId = UUID.randomUUID().toString();
//
//
//            StudyMindData mind = new StudyMindData();
//            mind.setStudyRoadId(roadId);
//            mind.setStudyRoadNodeId(nodeId);
//            mind.setMindId(randomMindId);
//            mind.setMindLabel("영상편집");
//            mind.setMindContents("영상편집 입니다.");
//            mind.setUrl("x");
//            mind.setBookTitle("x");
//            mind.setBookLink("x");
//            mind.setCreated(DateUtil.getDateTime());
//
//            int mRes = studyMindService.insertMindData(mind);
//
//            if (mRes == 0) {
//                msg = "마인드맵 생성중입니다.";
//            } else {
//                msg = "마인드맵 생성 실패.";
//            }
//
//            StudyMindNodeData node = new StudyMindNodeData();
//            node.setStudyRoadId(roadId);
//            node.setStudyRoadNodeId(nodeId);
//            node.setMindId(randomMindId);
//            node.setKey(randomMindId);
//            node.setGroup("nodes");
//            node.setMindLabel("영상편집");
//            node.setX("0");
//            node.setY("0");
//
//            int nRes = studyMindService.insertNodeData(node);
//
//            if (nRes == 0) {
//                msg = "마인드맵 생성중입니다.";
//            } else {
//                msg = "마인드맵 생성 실패.";
//            }
//
//            model.addAttribute("msg", msg);
//            model.addAttribute("url", url);
//
//            return "/redirect";
//        }

        log.info("mindMapInfo: "+ mindMapInfo);
        log.info("mindMapNode: "+ mindMapNode);

        model.addAttribute("mindMapInfo", mindMapInfo);
        model.addAttribute("mindMapNode", mindMapNode);

        mindMapInfo = null;
        mindMapNode = null;

        log.info(this.getClass().getName() + ".getMindMap End!");

        return "mindMap";
    }


    // 마인드 id로 마인드맵 정보 조회
    @GetMapping("/mindmaps/{mindId}")
    public ResponseEntity<StudyMindData> getMindMapByMindId(@PathVariable String mindId) throws Exception {

        log.info(this.getClass().getName() + ".getMindMapByMindId Start!");

        StudyMindData mindMapInfoByMindId = studyMindService.getMindMapInfoByMindId(mindId);

        log.info("mindMapInfoByMindId: "+mindMapInfoByMindId.toString());

        log.info(this.getClass().getName() + ".getMindMapByMindId End!");

        return ResponseEntity.status(HttpStatus.OK).body(mindMapInfoByMindId);
    }

    @PostMapping("/mindmaps")
    public ResponseEntity<ResponseNodeData> insertNodeData(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".insertNodeData Start!");

        String randomMindId = UUID.randomUUID().toString();
        log.info("randomMindId: "+randomMindId);

        StudyMindData mind = new StudyMindData();
        mind.setStudyRoadId(nvl(request.getParameter("roadId")));
        mind.setStudyRoadNodeId(nvl(request.getParameter("nodeId")));
        mind.setMindId(randomMindId);
        mind.setMindLabel(nvl(request.getParameter("mindLabel")));
        mind.setMindContents(nvl(request.getParameter("mindContents")));
        mind.setUrl(nvl(request.getParameter("url")));
        mind.setBookTitle(nvl(request.getParameter("bookTitle")));
        mind.setBookLink(nvl(request.getParameter("bookLink")));
        mind.setCreated(DateUtil.getDateTime());

        studyMindService.insertMindData(mind);
        log.info("mind: "+ mind);

        StudyMindNodeData node = new StudyMindNodeData();
        node.setStudyRoadId(nvl(request.getParameter("roadId")));
        node.setStudyRoadNodeId(nvl(request.getParameter("nodeId")));
        node.setMindId(randomMindId);
        node.setKey(randomMindId);
        node.setGroup("nodes");
        node.setMindLabel(nvl(request.getParameter("mindLabel")));
        node.setX(nvl(request.getParameter("x")));
        node.setY(nvl(request.getParameter("y")));

        studyMindService.insertNodeData(node);

        String randomEdgeId = UUID.randomUUID().toString();
        log.info("randomEdgeId: "+randomEdgeId);

        StudyMindNodeData edge = new StudyMindNodeData();
        edge.setStudyRoadId(nvl(request.getParameter("roadId")));
        edge.setStudyRoadNodeId(nvl(request.getParameter("nodeId")));
        edge.setMindId(randomEdgeId);
        edge.setGroup("edges");
        edge.setSource(randomMindId);
        edge.setTarget(nvl(request.getParameter("key")));

        studyMindService.insertNodeData(edge);

        ResponseNodeData nodeData = new ResponseNodeData();

        nodeData.setNodeMindId(randomMindId);
        nodeData.setEdgeMindId(randomEdgeId);
        nodeData.setRoadId(nvl(request.getParameter("roadId")));
        nodeData.setNodeId(nvl(request.getParameter("nodeId")));
        nodeData.setMindLabel(nvl(request.getParameter("mindLabel")));
        nodeData.setSource(randomMindId);
        nodeData.setTarget(nvl(request.getParameter("key")));
        log.info("nodeData: "+ nodeData);

        log.info(this.getClass().getName() + ".insertNodeData End!");

        return ResponseEntity.status(HttpStatus.OK).body(nodeData);

    }


    // 마인드, 노드 데이터 수정
    @PutMapping("/mindmaps/{mindId}")
    public ResponseEntity<Integer> updateMindNodeData(@PathVariable String mindId,
                                     @RequestBody StudyMindData mind) throws Exception {

        log.info(this.getClass().getName() + ".updateMindNodeData Start!");

        log.info("mind: "+ mind.toString());
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        StudyMindData mindData = studyMindService.getMindMapInfoByMindId(mindId);

        mindData.setMindLabel(nvl(mind.getMindLabel()));
        mindData.setMindContents(nvl(mind.getMindContents()));
        mindData.setUrl(nvl(mind.getUrl()));
        mindData.setBookTitle(nvl(mind.getBookTitle()));
        mindData.setBookLink(nvl(mind.getBookLink()));
        mindData.setCreated(DateUtil.getDateTime());

        log.info("mindData: "+mindData);

        StudyMindNodeData nodeData = studyMindService.getMindMapNodeByMindId(mindId);

        nodeData.setMindLabel(nvl(mind.getMindLabel()));

        log.info("nodeData: "+nodeData);

        int mRes = studyMindService.updateMindData(mindData);
        int nRes = studyMindService.updateNodeData(nodeData);

        log.info("mRes: "+mRes);
        log.info("nRes: "+nRes);

        log.info(this.getClass().getName() + ".updateMindNodeData End!");

        return ResponseEntity.status(HttpStatus.OK).body(mRes+nRes);

    }

    // 노드 좌표 데이터 수정
    @PutMapping("/mindmaps/{mindId}/position")
    public ResponseEntity<ResponseNodeData> updateNodePosition(@PathVariable String mindId,
                                                      @RequestBody StudyMindNodeData position) throws Exception {

        log.info(this.getClass().getName() + ".updateNodePosition Start!");

        log.info("position: "+ position.toString());

        StudyMindNodeData updatedPosition = studyMindService.getMindMapNodeByMindId(mindId);

        updatedPosition.setX(nvl(position.getX()));
        updatedPosition.setY(nvl(position.getY()));

        log.info("updatedPosition: "+updatedPosition);

        int pRes = studyMindService.updateNodePosition(updatedPosition);

        log.info("pRes: "+pRes);

        ResponseNodeData positionData = new ResponseNodeData();
        positionData.setX(nvl(position.getX()));
        positionData.setY(nvl(position.getY()));

        log.info(this.getClass().getName() + ".updateNodePosition End!");

        return ResponseEntity.status(HttpStatus.OK).body(positionData);

    }

    // 마인드 정보, 노드, 엣지 삭제
    @DeleteMapping("/mindmaps/{mindId}")
    public ResponseEntity<Integer> deleteMindNodeData(@PathVariable String mindId) throws Exception {

        log.info(this.getClass().getName() + ".deleteMindNodeData Start!");

        int mindData = studyMindService.deleteMindData(mindId);
        log.info("mindData: "+mindData);

        int nodeData = studyMindService.deleteMindNodeData(mindId);
        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName() + ".deleteMindNodeData End!");

        return ResponseEntity.status(HttpStatus.OK).body(mindData+nodeData);
    }

}

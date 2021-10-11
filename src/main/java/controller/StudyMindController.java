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

    // 스터디 로드맵 노드 속의 마인드맵 조회 및 초기 생성
    @GetMapping("/mindmap/{studyRoadNodeId}")
    public String getMindMap(@PathVariable String studyRoadNodeId,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap model
                             ) throws Exception {

        log.info(this.getClass().getName() + ".getMindMap Start!");

        StudyMindData mindData = new StudyMindData();
        StudyMindNodeData nodeData = new StudyMindNodeData();
        mindData.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        nodeData.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        mindData.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
        nodeData.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");

        List<StudyMindData> mindMapInfo = studyMindService.getMindMapData(mindData);
        List<StudyMindNodeData> mindMapNode = studyMindService.getMindMapNode(nodeData);

        log.info("mindMap size: "+ mindMapInfo.size());
        log.info("mindMapNode size: "+ mindMapNode.size());

        // 기존 마인드맵이 존재하지 않을 시 새로 마인드맵 생성 후 재조회
        if (mindMapInfo.size() == 0) {

            String msg;
            String url = "/mindmap/"+studyRoadNodeId;

            StudyMindData mind = new StudyMindData();
            mind.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
            mind.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
            mind.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
            mind.setMindId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
            mind.setMindLabel("영상편집");
            mind.setMindContents("영상편집 입니다.");
            mind.setUrl("x");
            mind.setBookTitle("x");
            mind.setBookLink("x");
            mind.setCreated(DateUtil.getDateTime());

            int mRes = studyMindService.insertMindData(mind);

            if (mRes == 0) {
                msg = "마인드맵 생성중입니다.";
            } else {
                msg = "마인드맵 생성 실패.";
            }

            StudyMindNodeData node = new StudyMindNodeData();
            node.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
            node.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
            node.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
            node.setMindId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
            node.setGroup("nodes");
            node.setMindLabel("영상편집");
            node.setX("0");
            node.setY("0");

            int nRes = studyMindService.insertNodeData(node);

            if (nRes == 0) {
                msg = "마인드맵 생성중입니다.";
            } else {
                msg = "마인드맵 생성 실패.";
            }

            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            return "/redirect";
        }

        log.info("mindMapInfo: "+ mindMapInfo.toString());
        log.info("mindMapNode: "+ mindMapNode.toString());

        model.addAttribute("mindMapInfo", mindMapInfo);
        model.addAttribute("mindMapNode", mindMapNode);

        mindMapInfo = null;
        mindMapNode = null;

        log.info(this.getClass().getName() + ".getMindMap End!");

        return "mindMap";
    }

    // 마인드 id로 마인드맵 정보 조회
    @GetMapping("/mindmap/{studRoadId}/{mindId}")
    public ResponseEntity<StudyMindData> getMindMapByMindId(@PathVariable String studRoadId,
                                     @PathVariable String mindId,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".getMindMapByMindId Start!");

        StudyMindData mindMapInfoByMindId = studyMindService.getMindMapInfoByMindId(mindId);

        log.info("mindMapInfoByMindId: "+mindMapInfoByMindId.toString());

        log.info(this.getClass().getName() + ".getMindMapByMindId End!");

        return ResponseEntity.status(HttpStatus.OK).body(mindMapInfoByMindId);
    }

    @PostMapping("/mindmap/{roadNodeId}/{mindId}")
    public ResponseEntity<ResponseNodeData> insertNodeData(@PathVariable String roadNodeId,
                                                           @PathVariable String mindId,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertNodeData Start!");

        String randomMindId = UUID.randomUUID().toString();
        log.info("randomMindId: "+randomMindId);

        StudyMindData mind = new StudyMindData();
        mind.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
        mind.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        mind.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
        mind.setMindId(randomMindId);
        mind.setMindLabel(nvl(request.getParameter("mindLabel")));
        mind.setMindContents(nvl(request.getParameter("mindContents")));
        mind.setUrl(nvl(request.getParameter("url")));
        mind.setBookTitle(nvl(request.getParameter("bookTitle")));
        mind.setBookLink(nvl(request.getParameter("bookLink")));
        mind.setCreated(DateUtil.getDateTime());

        studyMindService.insertMindData(mind);
        log.info("mind: "+ mind.toString());

        StudyMindNodeData node = new StudyMindNodeData();
        node.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
        node.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        node.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
        node.setMindId(randomMindId);
        node.setGroup("nodes");
        node.setMindLabel(nvl(request.getParameter("mindLabel")));
        node.setX(nvl(request.getParameter("x")));
        node.setY(nvl(request.getParameter("y")));

        studyMindService.insertNodeData(node);

        String randomEdgeId = UUID.randomUUID().toString();
        log.info("randomEdgeId: "+randomEdgeId);

        StudyMindNodeData edge = new StudyMindNodeData();
        edge.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
        edge.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        edge.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
        edge.setMindId(randomEdgeId);
        edge.setGroup("edges");
        edge.setSource(randomMindId);
        edge.setTarget(mindId);

        studyMindService.insertNodeData(edge);

        ResponseNodeData nodeData = new ResponseNodeData();

        nodeData.setNodeMindId(randomMindId);
        nodeData.setEdgeMindId(randomEdgeId);
        nodeData.setMindLabel(nvl(request.getParameter("mindLabel")));
        nodeData.setSource(randomMindId);
        nodeData.setTarget(mindId);
        log.info("nodeData: "+ nodeData);

        log.info(this.getClass().getName() + ".insertNodeData End!");

        return ResponseEntity.status(HttpStatus.OK).body(nodeData);

    }


    // 마인드, 노드 데이터 수정
    @PutMapping("/mindmap/{roadNodeId}/{mindId}")
    public ResponseEntity<Integer> updateMindNodeData(@PathVariable String roadNodeId,
                                     @PathVariable String mindId,
                                     @RequestBody StudyMindData mind,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelMap model) throws Exception {

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

        log.info("mindData: "+mindData.toString());

        StudyMindNodeData nodeData = studyMindService.getMindMapNodeByMindId(mindId);

        nodeData.setMindLabel(nvl(mind.getMindLabel()));

        log.info("nodeData: "+nodeData.toString());

        int mRes = studyMindService.updateMindData(mindData);
        int nRes = studyMindService.updateNodeData(nodeData);

        log.info("mRes: "+mRes);
        log.info("nRes: "+nRes);

        log.info(this.getClass().getName() + ".updateMindNodeData End!");

        return ResponseEntity.status(HttpStatus.OK).body(mRes+nRes);

    }

    // 노드 좌표 데이터 수정
    @PutMapping("/mindmap/{roadNodeId}/{mindId}/position")
    public ResponseEntity<ResponseNodeData> updateNodePosition(@PathVariable String roadNodeId,
                                                      @PathVariable String mindId,
                                                      @RequestBody StudyMindNodeData position,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".updateNodePosition Start!");

        log.info("position: "+ position.toString());

        StudyMindNodeData updatedPosition = studyMindService.getMindMapNodeByMindId(mindId);

        updatedPosition.setX(nvl(position.getX()));
        updatedPosition.setY(nvl(position.getY()));

        log.info("updatedPosition: "+updatedPosition.toString());

        int pRes = studyMindService.updateNodePosition(updatedPosition);

        log.info("pRes: "+pRes);

        ResponseNodeData positionData = new ResponseNodeData();
        positionData.setX(nvl(position.getX()));
        positionData.setY(nvl(position.getY()));

        log.info(this.getClass().getName() + ".updateNodePosition End!");

        return ResponseEntity.status(HttpStatus.OK).body(positionData);

    }

    // 마인드 정보, 노드, 엣지 삭제
    @DeleteMapping("/mindmap/{roadNodeId}/{mindId}")
    public ResponseEntity<Integer> deleteMindNodeData(@PathVariable String roadNodeId,
                                     @PathVariable String mindId,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".deleteMindNodeData Start!");

        int mindData = studyMindService.deleteMindData(mindId);
        log.info("mindData: "+mindData);

        int nodeData = studyMindService.deleteMindNodeData(mindId);
        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName() + ".deleteMindNodeData End!");

        return ResponseEntity.status(HttpStatus.OK).body(mindData+nodeData);
    }

}

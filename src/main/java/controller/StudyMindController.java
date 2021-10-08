package controller;

import domain.StudyMindData;
import domain.StudyMindNodeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.IStudyMindService;
import util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @GetMapping("/mindmap")
    public String mindMap() { return "mindMap";  }


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
    @ResponseBody
    @GetMapping("/mindmap/{studRoadId}/{mindId}")
    public String getMindMapByMindId(@PathVariable String studRoadId,
                                     @PathVariable String mindId,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".getMindMapByMindId Start!");

        StudyMindData mindMapInfoByMindId = studyMindService.getMindMapInfoByMindId(mindId);

        log.info("mindMapInfoByMindId: "+mindMapInfoByMindId.toString());

        log.info(this.getClass().getName() + ".getMindMapByMindId End!");

        return "Good Lookup!";
    }

    @PostMapping("/mindmap/{studyRoadNodeId}/{mindId}")
    public String insertNodeData(@PathVariable String studyRoadNodeId,
                                 @PathVariable String mindId,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertNodeData Start!");

        String msg;
        String url = "/mindmap/"+studyRoadNodeId;

        StudyMindData mind = new StudyMindData();
        mind.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
        mind.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        mind.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
        mind.setMindId("c910c43b-821c-4401-b47e-b42025a78bdb");
        mind.setMindLabel("Adobe Pro");
        mind.setMindContents("Adobe Pro 입니다.");
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
        node.setMindId("c910c43b-821c-4401-b47e-b42025a78bdb");
        node.setGroup("nodes");
        node.setMindLabel("adobe pro");

        int nRes = studyMindService.insertNodeData(node);

        if (nRes == 0) {
            msg = "노드 생성중입니다.";
        } else {
            msg = "노드 생성 실패.";
        }

        StudyMindNodeData edge1 = new StudyMindNodeData();
        edge1.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
        edge1.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        edge1.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
        edge1.setMindId("2f2f46d2-c4c8-46f6-b93f-d6c789f97534");
        edge1.setGroup("edges");
        edge1.setSource("c910c43b-821c-4401-b47e-b42025a78bdb");
        edge1.setTarget(mindId);

        int eRes = studyMindService.insertNodeData(edge1);

        StudyMindNodeData edge2 = new StudyMindNodeData();
        edge2.setUserUuid("4548bf57-33cc-4a4b-9b04-89d579a53e3c");
        edge2.setStudyRoadId("d47203ff-e63c-468c-9eb7-6e576276fb27");
        edge2.setStudyRoadNodeId("b3e8b0de-f975-42f5-ac85-73ff80cd8c55");
        edge2.setMindId("3f2f46d2-c4c8-46f6-b93f-d6c789f97534");
        edge2.setGroup("edges");
        edge2.setSource(mindId);
        edge2.setTarget("c910c43b-821c-4401-b47e-b42025a78bdb");

        int eRes2 = studyMindService.insertNodeData(edge2);

        if (eRes==0 && eRes2==0) {
            msg = "엣지 생성중입니다.";
        } else {
            msg = "엣지 생성 실패.";
        }

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        log.info(this.getClass().getName() + ".insertNodeData End!");

        return "/redirect";

    }


    // 마인드, 노드 데이터 수정
    @ResponseBody
    @PutMapping("/mindmap/{studyRoadNodeId}/{mindId}")
    public String updateMindNodeData(@PathVariable String studyRoadNodeId,
                                     @PathVariable String mindId,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".updateMindNodeData Start!");

        StudyMindData mindData = studyMindService.getMindMapInfoByMindId(mindId);

        mindData.setMindLabel("유튜브");
        mindData.setMindContents("유튜브 입니다.");
        mindData.setUrl("x");
        mindData.setBookTitle("x");
        mindData.setBookLink("x");
        mindData.setCreated(DateUtil.getDateTime());

        StudyMindNodeData nodeData = studyMindService.getMindMapNodeByMindId(mindId);

        nodeData.setMindLabel("유튜브");

        StudyMindData studyMindData = studyMindService.updateMindData(mindData);
        StudyMindNodeData studyMindNodeData = studyMindService.updateNodeData(nodeData);

        log.info("studyMindData: "+studyMindData);
        log.info("studyMindNodeData: "+studyMindNodeData);

        log.info(this.getClass().getName() + ".updateMindNodeData End!");

        return "Good Update!";

    }

    @ResponseBody
    @DeleteMapping("/mindmap/{studyRoadNodeId}/{mindId}")
    public String deleteMindNodeData(@PathVariable String studyRoadNodeId,
                                     @PathVariable String mindId,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".deleteMindNodeData Start!");

        StudyMindData mindData = studyMindService.deleteMindData(mindId);
        log.info("mindData: "+mindData);

        StudyMindNodeData nodeData = studyMindService.deleteMindNodeData(mindId);
        log.info("nodeData: "+nodeData);

        log.info(this.getClass().getName() + ".deleteMindNodeData End!");

        return "Good Delete!";
    }

}

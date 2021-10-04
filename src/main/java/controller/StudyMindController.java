package controller;

import dto.StudyRoadInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IStudyMindService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Developer: 김선열
 * Comment: 마인드맵 관련 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/")
public class StudyMindController {

    @Resource(name = "StudyMindSerivce")
    IStudyMindService studyMindService;

    @GetMapping("/mindmap")
    public String mindMap() { return "mindMap";  }


    // 스터디 로드맵 노드 속의 마인드맵
    @GetMapping("/mindmap/{studyroadid}")
    public String getMindMap(@PathVariable String studyroadid,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap model
                             ) throws Exception {

        log.info(this.getClass().getName() + ".getMindMap Start!");

        StudyRoadInfoDTO pDto = new StudyRoadInfoDTO();
        pDto.setStudyRoadId("3b357972-3c00-4b00-a3ba-8199065ea1db");
        pDto.setStudyRoadNodeTitle("네트워크");
        pDto.setStudyRoadNodeId("4d45b752-e80a-41a6-b3e4-e58695ee499d");

        List<Map<String, String>> mindMap = studyMindService.getMindMap(pDto);
        List<Map<String, String>> mindMapNode = studyMindService.getMindMapNode(pDto);

        model.addAttribute("mindMap", mindMap);
        model.addAttribute("mindMapNode", mindMapNode);

        mindMap = null;
        mindMapNode = null;

        log.info(this.getClass().getName() + ".getMindMap End!");

        return "mindMap";
    }
}

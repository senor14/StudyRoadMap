package controller;

import domain.CareerRoadData;
import domain.CareerRoadMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.ICareerRoadService;
import util.CmmUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;


/**
 * Developer: 김승범
 * Comment: 커리어 로드맵 관련 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/")
public class CareerRoadController {

    @Resource(name = "CareerRoadService")
    ICareerRoadService careerRoadService;


    @PostMapping("career/chk")
    public ResponseEntity<String> CareerChk(HttpSession session,HttpServletRequest request,
                                            HttpServletResponse response, ModelMap model)throws Exception{
        log.info(this.getClass().getName() + ".CareerChk Start!");

        //세션 NULL CHECK
        if(CmmUtil.nvl((String)session.getAttribute("SS_USER_UUID")).equals("")){
            return ResponseEntity.status(HttpStatus.OK).body("N");
        }

        CareerRoadMap node = new CareerRoadMap();
        node.setUserUuid((String)session.getAttribute("SS_USER_UUID"));

        int result = careerRoadService.chkCareerRoadMap(node);

        log.info("결과 : " + result);
        log.info(this.getClass().getName() + ".CareerChk End!");

        if(result==0){
            return ResponseEntity.status(HttpStatus.OK).body("Y");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("N");
        }
    }

    @PostMapping("career/new")
    public ResponseEntity<String> newCareer(HttpSession session,HttpServletRequest request,
                         HttpServletResponse response, ModelMap model)throws Exception{
        log.info(this.getClass().getName() + ".newCareer Start!");

        //세션 NULL CHECK
        if(CmmUtil.nvl((String)session.getAttribute("SS_USER_UUID")).equals("")){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("실패");
        }

        String publicYn = request.getParameter("publicYn");
        String careerTitle = request.getParameter("roadTitle");

        CareerRoadMap node = new CareerRoadMap();
        node.setUserUuid((String)session.getAttribute("SS_USER_UUID"));
        node.setCreated(util.DateUtil.getDateTime());
        node.setPublicYn(publicYn);
        node.setCareerTitle(careerTitle);

        int result = careerRoadService.makeCareerRoadMap(node);

        log.info("결과 : " + result);
        log.info(this.getClass().getName() + ".newCareer End!");

        if(result==0){
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("실패");
        }
    }


    @GetMapping("career/{userUuid}")
    public String roadMap(@PathVariable String userUuid,  HttpSession session,HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".getMindMap Start!");

        CareerRoadMap node = new CareerRoadMap();
        node.setUserUuid(userUuid);

        CareerRoadMap nRes = careerRoadService.getCareerRoadMapInfo(node);

        model.addAttribute("pageId", userUuid);
        model.addAttribute("careerTitle", nRes.getCareerTitle());
        model.addAttribute("created", nRes.getCreated());
        log.info("커리어타이틀 : "+nRes.getCareerTitle());

        return "career";
    }

    // 노드 생성
    @PostMapping ("career/insert")
    public ResponseEntity<String> insertNodeData(HttpSession session,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".insertNodeData Start!");

        String SS_USER_UUID = session.getAttribute("SS_USER_UUID").toString();

        if(SS_USER_UUID==null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("UUID가 없음");
        }

        String date = CmmUtil.nvl(request.getParameter("date").trim());
        String year = CmmUtil.nvl(request.getParameter("year").trim());
        String month = CmmUtil.nvl(request.getParameter("month").trim());
        String day = CmmUtil.nvl(request.getParameter("day").trim());
        String nodeType = CmmUtil.nvl(request.getParameter("nodeType").trim());
        String nodeId = UUID.randomUUID().toString();
        String content = CmmUtil.nvl(request.getParameter("content").trim());

        boolean importance = false;
        if(CmmUtil.nvl(request.getParameter("importance")).equals("true")){
            importance = true;
        }

        CareerRoadData node = new CareerRoadData();

        node.setCareerRoadNodeId(nodeId);
        node.setNodeYear(year);
        node.setNodeMonth(month);
        node.setNodeDay(day);
        node.setUserUuid(SS_USER_UUID);
        node.setNodeType(nodeType);
        node.setImportance(importance);
        node.setDate(date);
        node.setNodeContent(content);

        int nRes = careerRoadService.insertNodeData(node);

        log.info(this.getClass().getName() + ".insertNodeData End!");

        if (nRes == 0) {
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("실패");
        }

    }

    // 노드 조회
    @PostMapping("career/{userUuid}/search")
    public ResponseEntity<List<CareerRoadData>> searchNodeData(@PathVariable String userUuid, HttpSession session,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 ModelMap model) throws Exception {


        String nodeType = CmmUtil.nvl(request.getParameter("nodeType").trim());

        CareerRoadData node = new CareerRoadData();

        node.setUserUuid(userUuid);
        node.setNodeType(nodeType);
        log.info("node: "+ node);

        List<CareerRoadData> result = careerRoadService.getCareerNodeByNodeTypeAndUserUuid(node);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 노드 삭제
    @PostMapping("career/delete")
    public ResponseEntity<String> deleteNodeData(HttpSession session,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".deleteNodeData Start!");

        String SS_USER_UUID = session.getAttribute("SS_USER_UUID").toString();

        if(SS_USER_UUID==null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("UUID가 없음");
        }

        String nodeId = CmmUtil.nvl(request.getParameter("careerRoadNodeId").trim());

        CareerRoadData node = new CareerRoadData();

        node.setUserUuid(SS_USER_UUID);
        node.setCareerRoadNodeId(nodeId);

        int nRes = careerRoadService.deleteNodeData(node);

        if (nRes == 0) {
           log.info("성공");
        } else {
            log.info("실패");
        }

        log.info(this.getClass().getName() + ".deleteNodeData End!");

        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }

    // 노드 체크박스 업데이트
    @PostMapping("career/chkupdate")
    public ResponseEntity<String> chkUpdateNodeData(HttpSession session,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".chkUpdateNodeData Start!");

        String SS_USER_UUID = session.getAttribute("SS_USER_UUID").toString();

        if(SS_USER_UUID==null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("UUID가 없음");
        }

        String nodeId = CmmUtil.nvl(request.getParameter("careerRoadNodeId").trim());
        boolean importance = false;
        if(CmmUtil.nvl(request.getParameter("importance")).equals("true")){
            importance = true;
        }


        CareerRoadData node = new CareerRoadData();

        node.setUserUuid(SS_USER_UUID);
        node.setCareerRoadNodeId(nodeId);
        node.setImportance(importance);

        int nRes = careerRoadService.chkUpdateNodeData(node);

        if (nRes == 0) {
            log.info("성공");
        } else {
            log.info("실패");
        }

        log.info(this.getClass().getName() + ".chkUpdateNodeData End!");
        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }

    // 중요 노드 조회
    @PostMapping("career/{userUuid}/important_search")
    public ResponseEntity<List<CareerRoadData>> important_searchNodeData(@PathVariable String userUuid, HttpSession session,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               ModelMap model) throws Exception {

        CareerRoadData node = new CareerRoadData();

        node.setUserUuid(userUuid);
        node.setImportance(true);

        List<CareerRoadData> result = careerRoadService.getImportanceNode(node);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

package controller;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ICommunityService;
import util.CmmUtil;
import util.DateUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
public class CommunityController {

    //싱글톤 패턴 적용
    @Resource(name = "CommunityService")
    private ICommunityService communityService;

    @ResponseBody
    @GetMapping("/getStudyMap")
    public JSONArray getRoadMap(HttpServletRequest request){

        String category = CmmUtil.nvl(request.getParameter("category"));

        return communityService.getRoadMap(category);
    }

    @ResponseBody
    @GetMapping("/findRoadMap")
    public JSONArray findRoadMap(HttpServletRequest request){

        String category = CmmUtil.nvl(request.getParameter("category"));
        String searchType = CmmUtil.nvl(request.getParameter("searchType"));
        String keyWord = CmmUtil.nvl(request.getParameter("keyWord"));

        return communityService.findRoadMap(category, searchType, keyWord);
    }

    @ResponseBody
    @GetMapping("/insertComment/{roadMapId}")
    public boolean insertComment(HttpServletRequest request, HttpSession session, @PathVariable String roadMapId){

        Map<String, Object> pMap = new HashMap<>();

        String studyRoad_id = roadMapId;
        String user_id = (String) session.getAttribute("USER_ID");
        String user_uuid = (String) session.getAttribute("SS_USER_ID");
        String comment_id = UUID.randomUUID().toString();
        String comment_contents = CmmUtil.nvl(request.getParameter("comment"));
        String created = DateUtil.getDateTime();

        pMap.put("studyRoad_id",studyRoad_id);
        pMap.put("user_id",user_id);
        pMap.put("user_uuid",user_uuid);
        pMap.put("comment_id",comment_id);
        pMap.put("comment_contents",comment_contents);
        pMap.put("created",created);

        return communityService.insertComment(pMap);
    }

    @ResponseBody
    @GetMapping("/getComment/{roadMapId}")
    public JSONArray getComment(@PathVariable String roadMapId){

        String roadMap_id = roadMapId;

        return communityService.getComment(roadMap_id);
    }

}

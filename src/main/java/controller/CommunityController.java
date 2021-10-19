package controller;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/community")
    public String community() {
        return "/community/community";
    }

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

        if(keyWord.equals(" ")) return null;
        return communityService.findRoadMap(category, searchType, keyWord+" ");
    }

    @ResponseBody
    @GetMapping("/copyRoadMap/")
    public boolean copyRoadMap(HttpServletRequest request, HttpSession session){

        String oldRoad_id = request.getParameter("roadId");

        Map<String, Object> pMap = new HashMap<>();

        String newRoadId = UUID.randomUUID().toString();
        String newTitle = "복사된 "+request.getParameter("roadTitle");
        String userUuid = (String) session.getAttribute("SS_USER_ID");
        String created = DateUtil.getDateTime();
        String publicise = "N";

        pMap.put("roadId", newRoadId);
        pMap.put("roadTitle", newTitle);
        pMap.put("userUuid", userUuid);
        pMap.put("created", created);
        pMap.put("public", publicise);

        return communityService.copyRoadMap(oldRoad_id, pMap);
    }

}

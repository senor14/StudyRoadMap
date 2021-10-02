package controller;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ICommunityService;
import util.CmmUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CommunityController {

    //싱글톤 패턴 적용
    @Resource(name = "CommunityService")
    private ICommunityService communityService;

    @ResponseBody
    @GetMapping("/getStudyMap")
    public JSONArray getStudyMap(HttpServletRequest request){

        String category = CmmUtil.nvl(request.getParameter("category"));

        return communityService.getStudyMap(category);
    }

    @ResponseBody
    @GetMapping("/searchStudyMap")
    public JSONArray findStudyMap(HttpServletRequest request){

        String category = CmmUtil.nvl(request.getParameter("category"));
        String serch = CmmUtil.nvl(request.getParameter("serch"));

        return communityService.findStudyMap(serch);
    }
}

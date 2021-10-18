package controller;

import domain.StudyRoadData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IStudyRoadService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
public class MainController {

    @Resource(name = "StudyRoadService")
    IStudyRoadService studyRoadService;

    @GetMapping("/index")
    public String homeTest(HttpSession session, ModelMap model) throws Exception {

        String userUuid = (String) session.getAttribute("SS_USER_ID");

        if(userUuid == null) {
            return "/Main/Login_or_Signup";
        }

        List<StudyRoadData> roadDataInfo = studyRoadService.getRoadDataByUserUuid((String) session.getAttribute("SS_USER_UUID"));

        log.info("roadDataInfo: "+roadDataInfo);

        model.addAttribute("roadDataInfo", roadDataInfo);

        return "index";
    }

    @GetMapping("/community")
    public String community() {
        return "/community/community";
    }

    @GetMapping("/comment/{roadId}")
    public String comment() {

        return "/comment/comment";

    }
}

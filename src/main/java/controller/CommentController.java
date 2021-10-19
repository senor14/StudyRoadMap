package controller;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ICommentService;
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
public class CommentController {

    //싱글톤 패턴 적용
    @Resource(name = "CommentService")
    private ICommentService CommentService;

    @GetMapping("/comment/{roadId}")
    public String comment() {

        return "/comment/comment";

    }

    @ResponseBody
    @GetMapping("/getComment/{roadId}")
    public JSONArray getComment(@PathVariable String roadId){

        log.info(this.getClass().getName() + "roadId: " + roadId);
        log.info(this.getClass().getName() + "roadId: " + roadId);
        log.info(this.getClass().getName() + "roadId: " + roadId);
        log.info(this.getClass().getName() + "roadId: " + roadId);
        return CommentService.getComment(roadId);
    }

    @ResponseBody
    @GetMapping("/findMyComment/{roadId}")
    public JSONArray findMyComment(@PathVariable String roadId, HttpSession session){
        log.info("findMyComment 시작");
        String userUuid = (String) session.getAttribute("SS_USER_UUID");

        log.info("userUuid: " + userUuid);
        return CommentService.findMyComment(roadId,userUuid);

    }

    @ResponseBody
    @GetMapping("/insertComment/{roadId}")
    public boolean insertComment(HttpServletRequest request, HttpSession session, @PathVariable String roadId){

        Map<String, Object> pMap = new HashMap<>();

        String userId = (String) session.getAttribute("SS_USER_ID");
        String userUuid = (String) session.getAttribute("SS_USER_UUID");
        String commentId = UUID.randomUUID().toString();
        String commentContents = CmmUtil.nvl(request.getParameter("comment"));
        String created = DateUtil.getDateTime();

        pMap.put("roadId",roadId);
        pMap.put("userId",userId);
        pMap.put("userUuid",userUuid);
        pMap.put("commentId",commentId);
        pMap.put("commentContents",commentContents);
        pMap.put("created",created);

        return CommentService.insertComment(pMap);
    }

    @ResponseBody
    @GetMapping("/deleteComment")
    public boolean deleteComment(HttpServletRequest request, HttpSession session){

        String commentId = CmmUtil.nvl(request.getParameter("commentId"));
        String userUuid = (String) session.getAttribute("SS_USER_UUID");

        return  CommentService.deleteComment(commentId, userUuid);
    }

}

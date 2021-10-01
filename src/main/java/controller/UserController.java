package controller;

import static util.CmmUtil.nvl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.UserDTO;
import service.IMailService;
import service.IUserService;
import util.EncryptUtil;
import util.RandomUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Developer: 김창영
 * Comment: 회원 정보들이 처리되는 컨트롤러
 * */

@Controller
public class UserController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource(name = "UserService")
    IUserService userService;

    @Resource(name = "MailService")
    IMailService MailService;

    // 로그인 페이지
    @RequestMapping(value = "RoadMap/Login")
    public String ReminderLogin(HttpSession session, ModelMap model) throws Exception {

        log.info("Login 시작");
        // 로그인 페이지 접속 시 로그인 상태 지우기 위해 invalidate 실행(세션 올라가있는 상태에서 또 올리면 오류가 나기 때문에)
        session.invalidate();

        log.info("Login 종료");

        return "/Main/Login";
    }

    // 로그인 처리
    @RequestMapping(value = "RoadMap/LoginProc")
    public String TheLoginProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {

        log.info("/Reminder/ReminderLoginProc start");
        String id = nvl(request.getParameter("id"));
        String pwd = nvl(request.getParameter("pwd"));

        String encId = EncryptUtil.encAES128CBC(id);
        String HashEnc = EncryptUtil.enHashSHA256(pwd);

        log.info("id :" + encId);
        log.info("pwd :" + HashEnc);

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("user_id", encId);
        uMap.put("user_id", HashEnc);

        List<Map<String,String>> rList = userService.getUserInfo(uMap);

        String msg = "";
        String url = "";
        if (rList == null) {
            msg = "아이디 비밀번호가 틀렸거나 가입하지 않은 회원입니다.";
            url = "/Reminder/ReminderLogin.do";
        } else {
            String decId = "";
            String decEmail = "";

            for (Map<String,String> data: rList) {
                decId = EncryptUtil.decAES128CBC(data.get("user_id"));
                decEmail = EncryptUtil.decAES128CBC(data.get("user_email"));
            }

            session.setAttribute("SS_USER_ID", decId);
            session.setAttribute("SS_USER_EMAIL", decEmail);
            url = "/RoadMap/Main.do";
            model.addAttribute("url", url);
            return "/redirectNArt";
        }

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        log.info("Reminder/ReminderLoginProc end");

        return "/redirect";
    }

    // 로그아웃
    @RequestMapping(value = "RoadMap/Logout")
    public String TheLogout(HttpSession session, Model model) throws Exception {

        log.info("/RoadMap/Logout 시작");

        session.invalidate();

        log.info("/Reminder/ReminderLogout 종료");

        return "/Main/Login";
    }

    // 회원가입 페이지
    @RequestMapping(value = "RoadMap/SignUp")
    public String ReminderSignUp() {

        log.info("SignUp 시작");

        log.info("SignUp 종료");

        return "/Main/SignUp";
    }

    // 회원가입 처리
    @RequestMapping(value = "RoadMap/SignUpProc", method = RequestMethod.POST)
    public String ReminderSignUpProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

        log.info("/RoadMap/SignUpProc 시작");

        log.info("request.getParameter 시작");
        String user_id = request.getParameter("id");
        String user_pwd = nvl(request.getParameter("pwd"));
        String user_email = nvl(request.getParameter("email"));
        String user_uuid = UUID.randomUUID().toString();
        log.info("request.getParameter 종료");

        String encId = EncryptUtil.encAES128CBC(user_id);
        String HashEnc = EncryptUtil.enHashSHA256(user_pwd);
        String encEmail = EncryptUtil.encAES128CBC(user_email);

        log.info("user_id : " + encId);
        log.info("user_pwd : " + HashEnc);
        log.info("user_email : " + encEmail);
        log.info("uuid : " + user_uuid);

        log.info("put 시작");
        Map<String, Object> uMap = new HashMap<>();
        uMap.put("user_id", encId);
        uMap.put("user_pwd", HashEnc);
        uMap.put("user_email", encEmail);
        uMap.put("user_uuid", user_uuid);
        log.info("put 종료");

        log.info("UserSignUp 시작");
        int res = userService.UserSignUp(uMap);
        log.info("UserSignUp 종료");
        log.info("res : " + res);

        String msg;
        String url = "/RoadMap/Login.do";

        if (res > 0) {
            msg = "회원가입에 성공했습니다.";
        } else {
            msg = "회원가입에 실패했습니다.";
        }

        log.info("model.addAttribute 시작");
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        log.info("model.addAttribute 종료");

        log.info("/RoadMap/SignUpProc 종료");

        return "/redirect";
    }

    // 이메일 인증코드 전송
    // res는 에러코드 0: 실패, 1: 정상
    @ResponseBody
    @RequestMapping(value = "RoadMap/emailAuth", method = RequestMethod.POST)
    public int emailAuth(HttpServletRequest request) throws Exception {
        log.info("emailAuth 시작");

        String user_email = request.getParameter("user_email");
        String auth_num = RandomUtil.RandomNum();

        Map<String, String> uMap = new HashMap<>();

        uMap.put("user_email", user_email);
        uMap.put("auth_num", auth_num);

        int res = MailService.doSendMail(uMap);

        if (res == 1) {
            log.info(this.getClass().getName() + "mail.sendMail success");
        } else {
            log.info(this.getClass().getName() + "mail.sendMail fail");
        }

        String enc_email = EncryptUtil.encAES128CBC(user_email);
        Map<String, Object> pMap = new HashMap<>();

        pMap.put("user_email", enc_email);
        pMap.put("auth_num", auth_num);

        int result = userService.insertAuthNum(pMap);

        if(result == 1){
            log.info(this.getClass().getName() + "save_auth_num success");
        } else {
            log.info(this.getClass().getName() + "save_auth_num failed");
        }

        log.info("emailAuth end");

        return res;
    }

    // 이메일 인증코드 확인
    // res는 에러코드 0: 인증 실패, 1: 인증 성공
    @ResponseBody
    @RequestMapping(value = "RoadMap/emailAuth", method = RequestMethod.POST)
    public int emailCertified(HttpServletRequest request) throws Exception {
        log.info("emailCertified 시작");

        String user_email = request.getParameter("user_email");
        String enc_email = EncryptUtil.encAES128CBC(user_email);
        String auth_num = request.getParameter("auth_num");

        Map<String, Object> uMap = new HashMap<>();
        uMap.put("user_email", enc_email);
        uMap.put("auth_num", auth_num);

        int res = userService.getAuthNum(uMap);

        if(res == 1){
            log.info(this.getClass().getName() + "Certified success");
        } else {
            log.info(this.getClass().getName() + "Certified failed");
        }

        log.info("emailCertified end");

        return res;
    }
    // 0: 단일, 1: 중복
    // 이메일 중복확인
    @ResponseBody
    @RequestMapping(value = "RoadMap/emailCheck", method = RequestMethod.POST)
    public int emailCheck(HttpServletRequest request) throws Exception {
        log.info("emailCheck 시작");

        String user_email = request.getParameter("user_email");
        String enc_email = EncryptUtil.encAES128CBC(user_email);

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("user_email", enc_email);

        int res = userService.getUserEmail(uMap);

        log.info("emailCheck 종료");

        return res;
    }

    // 0: 단일, 1: 중복
    // 아이디 중복확인
    @ResponseBody
    @RequestMapping(value = "RoadMap/idCheck", method = RequestMethod.POST)
    public int idCheck(HttpServletRequest request) throws Exception {
        log.info("idCheck 시작");

        String user_id = request.getParameter("user_id");

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("user_id", user_id);

        log.info("TheService.idCheck 시작");
        int res =  userService.idCheck(uMap);
        log.info("TheService.idCheck 종료");

        log.info("result : " + res);

        log.info("idCheck 종료");
        return res;
    }

    // 비밀번호 찾기 페이지
    @RequestMapping(value = "RoadMap/ForgotPassWord")
    public String forgotPassWord() {

        log.info("ForgotPassWord 시작");

        log.info("ForgotPassWord 종료");

        return "/Main/ForgotID";
    }

    // 비밀번호 찾기 처리
    @RequestMapping(value = "RoadMap/ReMakePW", method = RequestMethod.POST)
    public String ReMakePW(HttpServletRequest request, ModelMap model) throws Exception {

        log.info("RoadMap/ReMakePW 시작");

        String user_email = nvl(request.getParameter("user_email"));
        String enc_email = EncryptUtil.encAES128CBC(user_email);
        String user_id = nvl(request.getParameter("user_id"));
        String enc_id = EncryptUtil.encAES128CBC(user_id);
        String random = RandomUtil.RandomNum();
        String hash_pw = EncryptUtil.enHashSHA256(random);

        Map<String, Object> beforeMap = new HashMap<>();

        beforeMap.put("user_email", enc_email);
        beforeMap.put("user_id", enc_id);

        int res = userService.getUserEmail(beforeMap);

        String msg = "";
        String url = "";

        if(res == 0) {
            msg = "존재하지 않는 이메일 혹은 아이디입니다. 이메일 주소와 아이디를 확인해주세요.";
            url = "/RoadMap/ForgotPassWord.do";
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);
            return "/redirect";
        }

        Map<String, Object> afterMap = new HashMap<>();

        afterMap.put("user_email", enc_email);
        afterMap.put("user_pwd", hash_pw);

        int result = userService.reMakePW(beforeMap, afterMap);

        Map<String, String> uMap = new HashMap<>();

        uMap.put("user_email", user_email);
        uMap.put("user_pwd", random);

        if(result == 1) {
            int mailRes = MailService.doSendPassWordMail(uMap);
            if(mailRes <= 1) {
                msg = "임시 비밀번호가 가입하신 메일 주소로 전송되었습니다.";
                url = "/RoadMap/Login.do";
            } else {
                msg = "메일 서버의 오류로 임시 비밀번호 전송을 실패했습니다. 잠시 후 다시 시도해주세요.";
                url = "/RoadMap/ReMakePW.do";
            }
        }

        log.info("model.addAttribute 시작");
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        log.info("model.addAttribute 종료");

        log.info("RoadMap/ReMakePW 종료");

        return "/redirect";
    }

    // 아이디 찾기 페이지
    @RequestMapping(value = "RoadMap/ForgotID")
    public String forgotID() {

        log.info("ForgotPassWord 시작");

        log.info("ForgotPassWord 종료");

        return "/Main/ForgotID";
    }

    // 아이디 찾기 처리
    @RequestMapping(value = "RoadMap/SendId", method = RequestMethod.POST)
    public String SendId(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {

        log.info("RoadMap/SendId 시작");

        String user_email = nvl(request.getParameter("user_email"));
        String enc_email = EncryptUtil.encAES128CBC(user_email);

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("user_email", enc_email);

        int res = userService.getUserEmail(uMap);

        String msg = "";
        String url = "";

        if(res == 0) {
            msg = "존재하지 않는 이메일입니다. 이메일 주소를 확인해주세요.";
            url = "/RoadMap/ForgotID.do";
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);
            return "/redirect";
        }

        List<Map<String, String>> rList = userService.getUserInfo(uMap);

        Map<String, String> pMap = rList.get(0);

        String email = pMap.get("user_email");
        String id = pMap.get("user_id");

        pMap = null;

        pMap = new HashMap<>();

        pMap.put("user_email", email);
        pMap.put("user_id", id);

        int mailRes = MailService.doSendIdMail(pMap);
        if(mailRes <= 1) {
            msg = "아이디가 가입하신 메일 주소로 전송되었습니다.";
            url = "/RoadMap/Login.do";
        } else {
            msg = "서버의 오류로 아이디 전송을 실패했습니다. 잠시 후 다시 시도해주세요.";
            url = "/RoadMap/ReMakePW.do";
        }


        log.info("model.addAttribute 시작");
        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        log.info("model.addAttribute 종료");

        log.info("RoadMap/SendId 종료");

        return "/redirect";
    }

    // 회원 탈퇴 페이지
    @RequestMapping(value = "RoadMap/userWithdrawal")
    public String userWithdrawal() {

        log.info("userWithdrawal 시작");

        log.info("userWithdrawal 종료");

        return "/Main/userWithdrawal";
    }

    // 회원 탈퇴 처리
    @RequestMapping(value = "RoadMap/userWithdrawal")
    public String userWithdrawalProc() {

        log.info("userWithdrawalProc 시작");

        log.info("userWithdrawalProc 종료");

        return "/redirect";
    }


}

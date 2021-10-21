package controller;

import static util.CmmUtil.nvl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import domain.StudyRoadData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.IMailService;
import service.IStudyRoadService;
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
@Slf4j
@Controller
public class UserController {

    // userService에 연결하기 위한 객체
    @Resource(name = "UserService")
    IUserService userService;

    // mail 서비스에 연결하기 위한 객체
    @Resource(name = "MailService")
    IMailService MailService;

    @Resource(name = "StudyRoadService")
    IStudyRoadService studyRoadService;

    // 로그인 페이지
    @RequestMapping(value = "RoadMap/LoginOrSignUp")
    public String Login(HttpSession session) {

        log.info("Login 시작");

        if((String) session.getAttribute("SS_USER_ID") == null){
            log.info("Login 종료");
            return "/Main/Login_or_Signup";
        } else {
            log.info("Login 종료");
            return "/index";
        }

    }

    // 로그인 처리
    @RequestMapping(value = "RoadMap/LoginProc")
    public String LoginProc(HttpServletRequest request, Model model, HttpSession session) throws Exception {

        log.info("/RoadMap/LoginProc start");

        String encId = EncryptUtil.encAES128CBC(nvl(request.getParameter("id")));
        String HashEnc = EncryptUtil.enHashSHA256(nvl(request.getParameter("pwd")));

        // 몽고디비에 도큐먼트 방식으로 사용 하기 위한 <String, Object>형 Map 객체
        Map<String, Object> uMap = new HashMap<>();

        uMap.put("userId", encId);
        uMap.put("userPwd", HashEnc);

        // 입력받은 아이디와 비밀번호를 조회하여 유저의 정보를 가져온다.
        List<Map<String,String>> rList = userService.getUserInfo(uMap);

        // 조회 결과가 없으면 안내 메세지를 띄우고 다시 로그인 페이지로 보냄
        if (rList.size() == 0) {
            model.addAttribute("msg", "아이디 비밀번호가 틀렸거나 가입하지 않은 회원입니다.");
            model.addAttribute("url", "/RoadMap/LoginOrSignUp");

            log.info("RoadMap/LoginProc end");

            return "/redirect";
        // 조회 결과가 있으면 세션에 올리고 메인페이지로 이동
        } else {
            session.setAttribute("SS_USER_ID", EncryptUtil.decAES128CBC(rList.get(0).get("userId")));
            session.setAttribute("SS_USER_UUID", rList.get(0).get("userUuid"));
            log.info("session_SS_USER_ID: "+session.getAttribute("SS_USER_ID"));
            log.info("session_SS_USER_UUID: "+session.getAttribute("SS_USER_UUID"));

            List<StudyRoadData> roadDataInfo = studyRoadService.getRoadDataByUserUuid((String) session.getAttribute("SS_USER_UUID"));


            log.info("##################################################");
            log.info("roadDataInfo: "+roadDataInfo);
            log.info("##################################################");

            model.addAttribute("url", "/index");

            log.info("RoadMap/LoginProc end");

            // redirectNArt 파일은 리다이렉트에 alert 없앤 것
            return "/redirectNArt";
        }
    }

    // 로그아웃
    @RequestMapping(value = "RoadMap/Logout")
    public String Logout(HttpSession session, ModelMap model) {

        log.info("/RoadMap/Logout 시작");

        session.invalidate();

        model.addAttribute("url", "/RoadMap/LoginOrSignUp");

        log.info("/RoadMap/Logout 종료");

        return "/redirectNArt";
    }

    /**
     * Comment: 회원가입 후 인증번호 폐기
     * */
    // 회원가입 처리
    @RequestMapping(value = "RoadMap/SignUpProc", method = RequestMethod.POST)
    public String ReminderSignUpProc(HttpServletRequest request, ModelMap model) throws Exception {

        log.info("/RoadMap/SignUpProc 시작");

        log.info("request.getParameter 시작");
        String encId = EncryptUtil.encAES128CBC(nvl(request.getParameter("id")));
        String HashEnc = EncryptUtil.enHashSHA256(nvl(request.getParameter("pwd")));
        String encEmail = EncryptUtil.encAES128CBC(nvl(request.getParameter("email")));
        String enc_auth_num = EncryptUtil.encAES128CBC(nvl(request.getParameter("auth")));
        String user_uuid = UUID.randomUUID().toString();
        log.info("request.getParameter 종료");

        log.info("put 시작");
        Map<String, Object> uMap = new HashMap<>();
        uMap.put("userId", encId);
        uMap.put("userPwd", HashEnc);
        uMap.put("userEmail", encEmail);
        uMap.put("userUuid", user_uuid);
        log.info("put 종료");

        log.info("UserSignUp 시작");
        int res = userService.UserSignUp(uMap);
        log.info("UserSignUp 종료");
        log.info("res : " + res);

        String msg;
        String url = "/RoadMap/LoginOrSignUp";

        if (res > 0) {
            msg = "회원가입에 성공했습니다.";
        } else {
            msg = "회원가입에 실패했습니다.";
        }
        Map<String, Object> pMap = new HashMap<>();

        pMap.put("userEmail", encEmail);
        pMap.put("authNum", enc_auth_num);

        int del_res = userService.deleteAuthNum(pMap);

        if(del_res == 1){
            log.info("인증번호 삭제 성공");
        } else {
            log.info("인증번호 삭제 실패");
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

        String user_email = nvl(request.getParameter("user_email"));
        String auth_num = RandomUtil.RandomNum();

        log.info("user_email: "+ user_email);
        log.info("auth_num: "+ auth_num);

        Map<String, String> uMap = new HashMap<>();

        uMap.put("userEmail", user_email);
        uMap.put("authNum", auth_num);

        int res = MailService.doSendMail(uMap);

        if (res == 1) {
            log.info(this.getClass().getName() + "mail.sendMail success");
        } else {
            log.info(this.getClass().getName() + "mail.sendMail fail");
        }

        String enc_email = EncryptUtil.encAES128CBC(user_email);
        String enc_auth_num = EncryptUtil.encAES128CBC(auth_num);
        Map<String, Object> pMap = new HashMap<>();

        pMap.put("userEmail", enc_email);
        pMap.put("authNum", enc_auth_num);

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
    @RequestMapping(value = "RoadMap/emailCertified", method = RequestMethod.POST)
    public int emailCertified(HttpServletRequest request) throws Exception {
        log.info("emailCertified 시작");

        String enc_email = EncryptUtil.encAES128CBC(nvl(request.getParameter("user_email")));
        String enc_auth_num = EncryptUtil.encAES128CBC(nvl(request.getParameter("auth_num")));

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("userEmail", enc_email);
        uMap.put("authNum", enc_auth_num);

        log.info("getAuthNum Start");
        int res = userService.getAuthNum(uMap);
        log.info("getAuthNum End");

        if(res == 1){
            log.info(this.getClass().getName() + "Certified success");
        } else {
            log.info(this.getClass().getName() + "Certified failed");
        }

        log.info("emailCertified end");

        return res;
    }

    // 0: 단일, 1: 중복, 2: 올바르지 않은 형식
    // 이메일 중복확인
    @ResponseBody
    @RequestMapping(value = "RoadMap/emailCheck", method = RequestMethod.POST)
    public int emailCheck(HttpServletRequest request) throws Exception {
        log.info("emailCheck 시작");

        String user_email = request.getParameter("user_email");

        // 이메일 주소의 99% 커버 가능한 정규식
        if(!(user_email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$"))) {
            return 2;
        }

        String enc_email = EncryptUtil.encAES128CBC(user_email);

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("userEmail", enc_email);

        int res = userService.getUserEmail(uMap);

        log.info("emailCheck 종료");

        return res;
    }

    // 0: 단일, 1: 중복, 2: 올바르지 않은 형식
    // 아이디 중복확인
    @ResponseBody
    @RequestMapping(value = "RoadMap/idCheck", method = RequestMethod.POST)
    public int idCheck(HttpServletRequest request) throws Exception {
        log.info("idCheck 시작");

        String user_id = request.getParameter("user_id");

        // 숫자로만 아이디 만드는 경우 방지
        if((user_id.matches("^[0-9]*$"))){
            log.info("result : 2, 숫자로만 생성");
            log.info("idCheck 종료");
            return 2;
        // 영어 대문자 혹은 소문자로 시작하게 하고 -_.특수문자만 허용. 6 ~ 14자리로 아이디 생성
        } else if(!(user_id.matches("^[a-zA-Z][a-zA-Z0-9-_.]{5,13}$"))) {
            log.info("result : 2, 숫자로 시작 혹은 자릿수 초과");
            log.info("idCheck 종료");
            return 2;
        }

        String enc_id = EncryptUtil.encAES128CBC(user_id);

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("userId", enc_id);

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

        return "/Main/ForgotPwd";
    }

    /**
     * Comment: 입력받은 아이디와` 이메일을 사용하여 유저가 존재하는지 조회 후 존재하면 랜덤번호를 생성해서 비밀번호로 교체한다.
     * */
    // 비밀번호 찾기 처리
    @RequestMapping(value = "RoadMap/ReMakePW", method = RequestMethod.POST)
    public String ReMakePW(HttpServletRequest request, ModelMap model) throws Exception {

        log.info("RoadMap/ReMakePW 시작");

        String user_email = nvl(request.getParameter("userEmail"));
        String enc_email = EncryptUtil.encAES128CBC(user_email);
        String random = RandomUtil.RandomNum();
        String hash_pw = EncryptUtil.enHashSHA256(random);
        String enc_id = EncryptUtil.encAES128CBC(nvl(request.getParameter("userId")));

        Map<String, Object> beforeMap = new HashMap<>();

        beforeMap.put("userEmail", enc_email);
        beforeMap.put("userId", enc_id);

        int res = userService.getUserEmail(beforeMap);

        if(res == 0) {
            model.addAttribute("msg", "존재하지 않는 이메일 혹은 아이디입니다. 이메일 주소와 아이디를 확인해주세요.");
            model.addAttribute("url", "/RoadMap/LoginOrSignUp");
            return "/redirect";
        }

        Map<String, Object> afterMap = new HashMap<>();

        afterMap.put("userEmail", enc_email);
        afterMap.put("userPwd", hash_pw);

        int result = userService.reMakePW(beforeMap, afterMap);

        Map<String, String> uMap = new HashMap<>();

        uMap.put("userEmail", user_email);
        uMap.put("userPwd", random);

        if(result == 1) {
            int mailRes = MailService.doSendPassWordMail(uMap);
            if(mailRes <= 1) {
                model.addAttribute("msg", "임시 비밀번호가 가입하신 메일 주소로 전송되었습니다.");
                model.addAttribute("url", "/RoadMap/LoginOrSignUp");
            } else {
                model.addAttribute("msg", "메일 서버의 오류로 임시 비밀번호 전송을 실패했습니다. 잠시 후 다시 시도해주세요.");
                model.addAttribute("url", "/RoadMap/LoginOrSignUp");
            }
        }
        log.info("RoadMap/ReMakePW 종료");

        return "/redirect";
    }

    // 아이디 찾기 페이지
    @RequestMapping(value = "RoadMap/ForgotID")
    public String forgotID() {

        log.info("ForgotID 시작");

        log.info("ForgotID 종료");

        return "/Main/ForgotId";
    }

    /**
     * Comment: 유저에게 입력받은 이메일 주소로 해당하는 아이디를 가져와서 메일로 전송해줌.
     * */
    // 아이디 찾기 처리
    @RequestMapping(value = "RoadMap/SendId", method = RequestMethod.POST)
    public String SendId(HttpServletRequest request, ModelMap model) throws Exception {

        log.info("RoadMap/SendId 시작");

        String user_email = nvl(request.getParameter("userEmail"));
        String enc_email = EncryptUtil.encAES128CBC(user_email);

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("userEmail", enc_email);

        // 이메일 주소가 존재하는지 조회
        int res = userService.getUserEmail(uMap);

        // 조회 결과가 없으면 안내 메세지를 띄우고 다시 아이디 찾기 페이지로 보냄.
        if(res == 0) {
            model.addAttribute("msg", "존재하지 않는 이메일입니다. 이메일 주소를 확인해주세요.");
            model.addAttribute("url", "/RoadMap/LoginOrSignUp");

            log.info("RoadMap/SendId 종료");

            return "/redirect";
        }

        List<Map<String, String>> rList = userService.getUserInfo(uMap);

        Map<String, String> pMap = rList.get(0);

        pMap.replace("userEmail", EncryptUtil.decAES128CBC(pMap.get("userEmail")));
        pMap.replace("userId", EncryptUtil.decAES128CBC(pMap.get("userId")));

        int email_res = MailService.doSendIdMail(pMap);

        if(email_res <= 1) {
            model.addAttribute("msg", "아이디가 가입하신 메일 주소로 전송되었습니다.");
            model.addAttribute("url", "/RoadMap/LoginOrSignUp");
        } else {
            model.addAttribute("msg", "서버의 오류로 아이디 전송을 실패했습니다. 잠시 후 다시 시도해주세요.");
            model.addAttribute("url", "/RoadMap/LoginOrSignUp");
        }

        log.info("RoadMap/SendId 종료");

        return "/redirect";
    }

    // 비밀번호 변경 페이지
    @RequestMapping(value = "RoadMap/PassWordChange")
    public String PassWordChange() {

        log.info("PassWordChange 시작");

        log.info("PassWordChange 종료");

        return "/Main/PassWordChange";
    }
    
    /**
     * Comment: 세션에 올라가 있는 아이디로 정보를 찾아서 입력받은 비밀번호로 교체
     **/
    // 비밀번호 변경 처리
    @RequestMapping(value = "RoadMap/PassWordChangeProc")
    public String PassWordChangeProc(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception{

        log.info("PassWordChange 시작");

        String enc_id = EncryptUtil.encAES128CBC(nvl((String) session.getAttribute("SS_USER_ID")));
        String Hash_pwd = EncryptUtil.enHashSHA256(nvl(request.getParameter("pwd")));

        Map<String, Object> beforeMap = new HashMap<>();

        beforeMap.put("userId", enc_id);

        Map<String, Object> afterMap = new HashMap<>();

        afterMap.put("userPwd", Hash_pwd);

        int res = userService.passWordChange(beforeMap, afterMap);

        if(res == 1){
            model.addAttribute("msg", "비밀번호 변경이 성공하였습니다.");
        } else {
            model.addAttribute("msg", "서버의 오류로 인해 비밀번호 변경이 실패했습니다. 잠시 후 다시 시도해주세요. 변경이 계속 실패하는 경우 고객센터에 문의 바랍니다.");
        }
        model.addAttribute("url", "/RoadMap/LoginOrSignUp");

        session.invalidate();

        log.info("PassWordChange 종료");

        return "/redirect";
    }


    // 회원 탈퇴 페이지
    @RequestMapping(value = "RoadMap/userWithdrawal")
    public String userWithdrawal() {

        log.info("userWithdrawal 시작");

        log.info("userWithdrawal 종료");

        return "/Main/UserWithdrawal";
    }

    // 탈퇴 입력문자열 확인
    @ResponseBody
    @RequestMapping(value = "/RoadMap/userWithdrawalCheck", method = RequestMethod.POST)
    public int TheUserDeleteCheck(HttpServletRequest request) {

        log.info("/RoadMap/userWithdrawalCheck 시작");

        int result;
        log.info("String 변수저장 시작");
        String DeleteCheck = request.getParameter("DeleteCheck");
        log.info("String 변수저장 종료");
        log.info("DeleteCheck : " + DeleteCheck);

        if (DeleteCheck.equals("Account_withdrawal")) {
            result = 1;
        } else {
            result = 0;
        }

        log.info("result :" + result);

        log.info("/RoadMap/userWithdrawalCheck 종료");

        return result;
    }

    // 회원 탈퇴 처리
    @RequestMapping(value = "RoadMap/userWithdrawalProc")
    public String userWithdrawalProc(HttpSession session, ModelMap model) throws Exception{

        log.info("userWithdrawalProc 시작");

        String user_id = (String) session.getAttribute("SS_USER_ID");
        String enc_id = EncryptUtil.encAES128CBC(user_id);
        String user_email = (String) session.getAttribute("SS_USER_EMAIL");
        String enc_email = EncryptUtil.encAES128CBC(user_email);

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("userId", enc_id);
        uMap.put("userEmail", enc_email);

        List<Map<String, Object>> rList = userService.getDeleteUserInfo(uMap);

        Map<String, Object> pMap = rList.get(0);

        pMap.put("publicYn", "N");

        int career_res = userService.deleteCareerRoadMap(pMap);
        int mind_res = userService.deleteStudyMinddMap(pMap);
        int study_res = userService.deleteStudyRoadMap(pMap);

        String msg = "회원탈퇴가 완료되었습니다. 이용해주셔서 감사합니다. 비공개 커리어 로드맵 " + career_res + "개, 스터디 마인드맵 " + mind_res + "개, 스터디 로드맵" + study_res + "개가 삭제되었습니다.";
        String url = "/RoadMap/LoginOrSignUp";

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);

        log.info("userWithdrawalProc 종료");

        return "/redirect";
    }

    // uuid로 id 조회
    @ResponseBody
    @RequestMapping(value = "RoadMap/getId", method = RequestMethod.POST)
    public String getId(HttpServletRequest request) throws Exception {
        log.info("getId 시작");

        String user_uuid = request.getParameter("user_uuid");

        Map<String, Object> uMap = new HashMap<>();

        uMap.put("userUuid", user_uuid);

        List<Map<String, String>> rList = userService.getUserId(uMap);

        String output = EncryptUtil.decAES128CBC(rList.get(0).get("userId"));

        log.info("getId 시작");
        return output;
    }



}

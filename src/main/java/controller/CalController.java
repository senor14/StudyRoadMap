package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ICalService;
import util.CmmUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/*
 * <D-day>
*/
@Slf4j
@Controller
public class CalController {
	
	@Resource(name = "CalService")
	private ICalService calService;


	//임시 캘린더 접속
	@GetMapping("/calendar")
	public String calendar(HttpSession session) {

		//세션 NULL CHECK
		if(CmmUtil.nvl((String)session.getAttribute("SS_USER_UUID")).equals("")){
			//임시 세션 로그인 셋팅
			session.setAttribute("SS_USER_UUID","KIM");
		}

		return "calendar";
	}


	@RequestMapping(value="setdday")
	@ResponseBody
	public String setdday(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		String title = request.getParameter("title");
		String date = request.getParameter("date");
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		log.info(title);
		log.info(date);
		log.info(User_id);
		
		if(User_id==null) {
			return "0";
		}
		
		Map<String, String> rMap = new HashMap<>(); 
		
		rMap.put("title", title);
		rMap.put("date", date);
		rMap.put("User_id", User_id);
		
		int res = calService.DdaySet(rMap);			
		
		
		return "1";
		
	}


	@RequestMapping(value="getdday")
	@ResponseBody
	public List<Map<String,String>> getdday(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".getdday start");
		
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		log.info(User_id);
		
		List<Map<String, String>> rList = new LinkedList<Map<String, String>>();
		
		if(User_id==null) {
			return rList;
		}
		
		rList = calService.DdayGet(User_id);			
		
		
		log.info(this.getClass().getName() + ".getdday End");
		
		return rList;
		
	}
	
	@RequestMapping(value="/deldday")
	@ResponseBody
	public int deldday(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".deldday start");
		
		String id = (String) request.getParameter("id");
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		log.info(User_id);
		log.info(id);
		
		if(User_id==null || id==null) {
			return 0;
		}
		
		int res = calService.deldday(User_id, id);			
		
		
		log.info(this.getClass().getName() + ".deldday End");
		
		return res;
		
	}
	
	/*
	 * <Memo>
	*/
	@RequestMapping(value="setmemo")
	@ResponseBody
	public String setmemo(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		log.info(title);
		log.info(desc);
		log.info(User_id);
		
		if(User_id==null) {
			return "0";
		}
		
		Map<String, String> rMap = new HashMap<>(); 
		
		rMap.put("title", title);
		rMap.put("desc", desc);
		rMap.put("User_id", User_id);
		
		int res = calService.memoSet(rMap);			
		
		
		return "1";
		
	}
	
	
	@RequestMapping(value="getmemo")
	@ResponseBody
	public List<Map<String,String>> getmemo(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".getmemo start");
		
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		log.info(User_id);
		
		List<Map<String, String>> rList = new LinkedList<Map<String, String>>();
		
		if(User_id==null) {
			return rList;
		}
		
		rList = calService.memoGet(User_id);			
		
		
		log.info(this.getClass().getName() + ".getmemo End");
		
		return rList;
		
	}
	
	@RequestMapping(value="delmemo")
	@ResponseBody
	public int delmemo(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".delmemo start");
		
		String id = (String) request.getParameter("id");
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		log.info(User_id);
		
		if(User_id==null || id==null) {
			return 0;
		}
		
		int res = calService.delmemo(User_id, id);			
		
		
		log.info(this.getClass().getName() + ".delmemo End");
		
		return res;
		
	}
	
	/*
	 * <Schedule>
	*/
	
	@RequestMapping(value="getSchedule")
	@ResponseBody
	public List<Map<String,Object>> getSchedule(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".getSchedule start");
		
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		log.info(User_id);
		
		List<Map<String, Object>> rList = new LinkedList<Map<String, Object>>();
		
		if(User_id==null) {
			return rList;
		}
		
		rList = calService.getSchedule(User_id);			
		
		
		log.info(this.getClass().getName() + ".getSchedule End");
		
		return rList;
		
	}
	
	/*
	 * <Schedule Delete>
	*/
	
	@RequestMapping(value="delSchedule")
	@ResponseBody
	public int delSchedule(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".delSchedule start");
		
		String id = (String) request.getParameter("id");
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		
		
		if(User_id==null || id == null) {
			return 0;
		}
		
		int res = calService.delSchedule(User_id, id);

		log.info(this.getClass().getName() + ".delSchedule End");
		
		
		return res;
		
	}
	
	/*
	 * <Schedule Update>
	*/
	
	@RequestMapping(value="updateSchedule")
	@ResponseBody
	public int updateSchedule(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".updateSchedule start");
		
		String _id = (String) request.getParameter("_id");
		String allDay = (String) request.getParameter("allDay");
		String title = (String) request.getParameter("title");
		String start = (String) request.getParameter("start");
		String end = (String) request.getParameter("end");
		String type = (String) request.getParameter("type");
		String backgroundColor = (String) request.getParameter("backgroundColor");
		String description = (String) request.getParameter("description");
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		
		if(User_id==null || _id == null) {
			return 0;
		}
		Map<String,Object> rMap = new LinkedHashMap<String, Object>();
		rMap.put("_id", _id);
		rMap.put("allDay", allDay);
		rMap.put("title", title);
		rMap.put("start", start);
		rMap.put("end", end);
		rMap.put("type", type);
		rMap.put("backgroundColor", backgroundColor);
		rMap.put("description", description);
		
		
		int res = calService.updateSchedule(User_id, rMap);			
		
		log.info(this.getClass().getName() + ".updateSchedule End");
		
		
		return res;
		
	}
	
	/*
	 * <Schedule Drop>
	*/
	
	@RequestMapping(value="DropSchedule")
	@ResponseBody
	public int DropSchedule(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".DropSchedule start");
		
		String _id = (String) request.getParameter("_id");
		String start = (String) request.getParameter("start");
		String end = (String) request.getParameter("end");
		String User_id =(String) session.getAttribute("SS_USER_UUID");
		
		if(User_id==null || _id == null) {
			return 0;
		}
		Map<String,Object> rMap = new LinkedHashMap<String, Object>();
		rMap.put("_id", _id);
		rMap.put("start", start);
		rMap.put("end", end);
		
		int res = calService.DropSchedule(User_id, rMap);			
		
		log.info(this.getClass().getName() + ".DropSchedule End");
		
		
		return res;
		
	}
	
	/*
	 * <Schedule Add>
	*/
	
	@RequestMapping(value="addSchedule")
	@ResponseBody
	public int addSchedule(HttpSession session, HttpServletRequest request, ModelMap model) throws Exception {
		
		log.info(this.getClass().getName() + ".addSchedule start");
		
		String allDay = (String) request.getParameter("allDay");
		String title = (String) request.getParameter("title");
		String start = (String) request.getParameter("start");
		String end = (String) request.getParameter("end");
		String type = (String) request.getParameter("type");
		String backgroundColor = (String) request.getParameter("backgroundColor");
		String description = (String) request.getParameter("description");
		String User_id =(String) session.getAttribute("SS_USER_UUID");

		log.info(allDay);
		log.info(title);
		log.info(start);
		log.info(end);
		log.info(type);
		log.info(backgroundColor);
		log.info(description);
		log.info(User_id);

		if(User_id==null) {
			return 0;
		}
		Map<String,Object> rMap = new LinkedHashMap<String, Object>();
		
		rMap.put("allDay", allDay);
		rMap.put("title", title);
		rMap.put("start", start);
		rMap.put("end", end);
		rMap.put("type", type);
		rMap.put("backgroundColor", backgroundColor);
		rMap.put("description", description);
		
		
		
		int res = calService.addSchedule(User_id, rMap);
		
		log.info(this.getClass().getName() + ".addSchedule End");
		
		
		return res;
		
	}
	
}

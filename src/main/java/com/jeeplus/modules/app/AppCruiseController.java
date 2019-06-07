package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.liner.*;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.linertrip.LinerTrip;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.comconsult.ComConsultService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceRelationModService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.liner.*;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.linerroom.LinerRoomService;
import com.jeeplus.modules.meiguotong.service.linertrip.LinerTripService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.orderliner.OrderLinerService;
import com.jeeplus.modules.meiguotong.service.orderlinerroom.OrderLinerRoomService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.sys.utils.CodeGenUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/interface/cruise")
public class AppCruiseController {
	@Autowired
	private LinerLineService linerlineService;
	@Autowired
	private LinerCompanyService linerCompanyService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private LinerTripService linerTripService;
	@Autowired
	private ComCommentService comCommentService;
	@Autowired
	private ComConsultService comConsultService;
	@Autowired
	private LinerService linerService;
	@Autowired
	private LinerRoomService linerRoomService;
	@Autowired
	private OrderLinerService orderLinerService;
	@Autowired
	private InsuranceRelationModService insuranceRelationModService;
	@Autowired
	private OrderMemberService orderMemberService;
	@Autowired
	private LinerLineService linerLineService;
	@Autowired
	private LinerCourseService linerCourseService;	
	@Autowired
	private LinerPortService linerPortService;
	@Autowired
	private OrderLinerRoomService orderLinerRoomService;
	@Autowired
	private OrderInsuranceService orderInsuranceService;
	@Autowired
	private OrderSysService orderSysService;	
	@Autowired
	private LinerDateService linerDateService;	
	@Autowired
	private AppUtils appUtils;
	
	
	/**
	 * 获取游轮出发港口接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getStartCity")
	@ResponseBody
	public AjaxJson getStartCity(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid=request.getParameter("languageid");//语言id
			
			if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			LinerPort linerPort = new LinerPort();
			linerPort.setLanguageId(Integer.parseInt(languageid));
			List<LinerPort> list =  linerPortService.getPortList(linerPort);
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取游轮出发港口成功");
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_47);
			ajaxJson.setMsg(AppErrorUtils.error_47_desc);
			return ajaxJson;
		}
	}
	/**
	 * 获取邮轮航线接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getCourse")
	@ResponseBody
	public AjaxJson getCourse(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid=request.getParameter("languageid");//语言id
			
			
			if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			LinerCourse linerCourse = new LinerCourse();
			linerCourse.setLanguageId(Integer.parseInt(languageid));
			List<LinerCourse> list = linerCourseService.findCourseList(linerCourse);
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取邮轮航线成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_48);
			ajaxJson.setMsg(AppErrorUtils.error_48_desc);
			return ajaxJson;
		}
	}
	/**
	 * 获取邮轮公司接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getLinerCompany")
	@ResponseBody
	public AjaxJson getLinerCompany(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid=request.getParameter("languageid");//语言id
			
			
			if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			LinerCompany linerCompany = new LinerCompany();
			linerCompany.setLanguageId(Integer.parseInt(languageid));
			List<LinerCompany> list = linerCompanyService.findCompanyList(linerCompany);
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取邮轮公司成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_49);
			ajaxJson.setMsg(AppErrorUtils.error_49_desc);
			return ajaxJson;
		}
	}
	/**
	 * 游轮搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "cruiseSearch")
	@ResponseBody
	public AjaxJson cruiseSearch(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String  name=request.getParameter("searchContent");//搜索内容
		String  languageid=request.getParameter("languageid");//语言id
		String  pageNo=request.getParameter("pageNo");//页码
		
		if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
			if (StringUtils.isBlank(uid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("uid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(time)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("time传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(key)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("key传参为空");
				return ajaxJson;
			}
			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
		}
		if (StringUtils.isBlank(languageid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(name)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("name传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(pageNo)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("pageNo传参为空");
			return ajaxJson;
		}
		try {
			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
			LinerLine linerline=new LinerLine();
			linerline.setName(name);
			linerline.setLanguageId(Integer.parseInt(languageid));
			
			Page<LinerLine> page=new Page<LinerLine>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);		
			Page<LinerLine> page1 = linerlineService.findCruiseList(page, linerline);
			List<LinerLine> list=page1.getList();
			
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", list);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取搜索列表成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg("获取搜索列表失败");
			return ajaxJson;
		}
	}
	
	/**
	 * 游轮筛选接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "cruiseScreen")
	@ResponseBody
	public AjaxJson cruiseScreen(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //密钥，登陆后返回

		String startCity = request.getParameter("startCity");       //出发城市
		String portid = request.getParameter("portid");       //出发港口
		String route = request.getParameter("route");       //航区
		String linerid = request.getParameter("linerid");       //游轮id
		String startDate = request.getParameter("startDate");       //出发日期
		String day = request.getParameter("day");       //行程天数
		String labelAttrid = request.getParameter("labelAttrid");       //属性

		String pageNo = request.getParameter("pageNo");//页码
		String orderByType = request.getParameter("orderByType");//1.销量2.价格降序3.价格升序4好评
		
		if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
			if (StringUtils.isBlank(uid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("uid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(time)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("time传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(key)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("key传参为空");
				return ajaxJson;
			}
			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
		}
		if (StringUtils.isBlank(pageNo)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("pageNo传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(languageid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageid传参为空");
			return ajaxJson;
		}
		
		try {
			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			LinerLine linerline=new LinerLine();
			linerline.setLanguageId(Integer.parseInt(languageid));
			if(StringUtils.isNotBlank(linerid)){
				linerline.setLinerId(Integer.parseInt(linerid));
			}
			linerline.setStartCity(startCity);
			linerline.setPortid(portid);
			linerline.setRoute(route);
			if (!StringUtils.isBlank(startDate)) {
				JSONArray jsonArray = JSONArray.fromObject(startDate); 
			    List<Date> list= new ArrayList<>();
				for(int i=0;i<jsonArray.size();i++){
					//取出数组第i个元素 
				    JSONObject jpro = jsonArray.getJSONObject(i);
				    //年
				    String year = jpro.getString("year"); 
				    //月
				    String month = jpro.getString("month"); 
				    //日
				    String days = jpro.getString("days"); 
				    String[] days1 = days.split(",");
				    String selectDate = year+"-"+month+"-";
				    for(String a:days1){
				    	selectDate = selectDate+a;
				    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				    	Date bt=sdf.parse(selectDate); 
				    	list.add(bt);
				    	linerline.setDateList(list);
				    }
				}
			}
			linerline.setTravelDay(day);
			List<String> attrList = new ArrayList<>();
			if(StringUtils.isNotBlank(labelAttrid)){
				String[] a = labelAttrid.split(",");
				for(String b:a){
					attrList.add(b);
				}
				linerline.setAttrList(attrList);
			}
			if(StringUtils.isNotBlank(orderByType)){
				linerline.setOrderByType(Integer.parseInt(orderByType));
			}
			Page<LinerLine> page=new Page<LinerLine>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);		
			Page<LinerLine> page1 = linerlineService.findCruiseScreenList(page, linerline);		
			List<LinerLine> list=page1.getList();
			
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", list);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取游轮路线筛选列表成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg("获取游轮路线筛选列表失败");
			return ajaxJson;
		}
	}
	 /**
    *邮轮路线详情接口
    * @param request
    * @param response
    * @param model
    * @return
    */
	@ResponseBody
	@RequestMapping(value = "linerlineDetails")
	public AjaxJson linerlineDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");//1.游客 2.会员
			String uid = request.getParameter("uid");
			String time = request.getParameter("time");
			String key = request.getParameter("key");
			String languageid=request.getParameter("languageid");//语言ID
			String lineid=request.getParameter("lineid");//邮轮路线的ID
			if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(lineid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("lineid传参为空");
				return ajaxJson;
			}
			LinerLine linerline1 = new LinerLine();
			linerline1.setId(lineid);
			if(StringUtils.isNotBlank(uid)){
				linerline1.setMemberid(uid); 
			}
			LinerLine linerline=linerlineService.getLinerline(linerline1);
			
			Liner liner1=new Liner();
			liner1.setId(linerline.getLinerId().toString());
			Liner liner=linerService.getLiner(liner1);
		
			//修改查看数量
			LinerLine linerLine2=new LinerLine();
			linerLine2.setId(lineid);
			linerlineService.changeLookNum(linerLine2);
			
			ajaxJson.getBody().put("linerline", linerline);
			ajaxJson.getBody().put("liner", liner);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取邮轮路线详情成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	/**
	 * 邮轮航线行程信息接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tripList")
	@ResponseBody
	public AjaxJson tripList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid=request.getParameter("languageid");//语言id
			String lineid = request.getParameter("lineid");//邮轮航线id
			if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(lineid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("lineid传参为空");
				return ajaxJson;
			}
			LinerTrip linerTrip = new LinerTrip();
			linerTrip.setLinerLineId(Integer.parseInt(lineid)); 
			List<LinerTrip> list = linerTripService.getTripList(linerTrip);
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
		
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取邮轮行程信息成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg("获取邮轮行程信息失败");
			return ajaxJson;
		}
	}/**
	 * 获取邮轮路线日期价格详情接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getLinePriceDetails")
	@ResponseBody
	public AjaxJson getLinePriceDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String lineid = request.getParameter("lineid");       //游轮路线id
			String priceDate = request.getParameter("priceDate");       //日期
		
			if (StringUtils.isBlank(priceDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("priceDate传参为空");
				return ajaxJson;
			}
			if(!StringUtils.isBlank(type)&&Integer.parseInt(type)==2){
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(lineid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("lineid传参为空");
				return ajaxJson;
			}
			//获取产品日期价格
			LinerDate linerDate=new LinerDate();
			
			Calendar c = Calendar.getInstance();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
			Date bt=sdf.parse(priceDate); 
			c.setTime(bt);
			c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天   
			c.add(Calendar.MONTH, -1);
			Date beginDate = c.getTime();
			c.add(Calendar.MONTH, +4);
			Date endDate = c.getTime();
			linerDate.setBeginDate(beginDate);
			linerDate.setEndDate(endDate);
			
			LinerLine linerLine = linerLineService.get(lineid);
			c.setTime(new Date());
			c.add(Calendar.DAY_OF_MONTH, +linerLine.getAdvanceNum());
			
			linerDate.setStartDate(c.getTime());
			
			linerDate.setLineid(Integer.parseInt(lineid));
			List<LinerDate> list = linerDateService.getDateList(linerDate);
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取邮轮路线日期价格详情成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			return ajaxJson;
		}
	}
	/**
	 * 邮轮房间列表接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "linerRoomList")
	@ResponseBody
	public AjaxJson linerRoomList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String lineid=request.getParameter("lineid");//邮轮ID
			String languageid=request.getParameter("languageid");//语言ID
			
			if(StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2){
				if (StringUtils.isBlank(uid)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("uid传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(time)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("time传参为空");
					return ajaxJson;
				}
				if (StringUtils.isBlank(key)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("key传参为空");
					return ajaxJson;
				}
				Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
				if (memberStatus != 0) {
					ajaxJson.getBody().put("memberStatus", memberStatus);
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_7);
					ajaxJson.setMsg(AppErrorUtils.error_7_desc);
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(lineid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("lineid传参为空");
				return ajaxJson;
			}
			
			LinerRoom linerRoom=new LinerRoom();
			linerRoom.setLinerLineId(Integer.parseInt(lineid));		
		
			List<LinerRoom> list = linerRoomService.linerRoomList(linerRoom);	
			
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取邮轮房间列表成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			return ajaxJson;
		}
	}
	/**
	 * 邮轮路线确认订单接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveLineOrder")
	@ResponseBody
	public AjaxJson saveLineOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
        ajaxJson=this.checkSaveLineOrder(request);
        if(ajaxJson!=null) {
        	return ajaxJson;
        }else {
        	 ajaxJson = new AjaxJson();
        }
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String linerLineid=request.getParameter("linerLineid");//邮轮路线Id
			String contactsName = request.getParameter("contactsName");       //联系人姓名
			String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
			String remark = request.getParameter("remark");       //备注
			String startDate = request.getParameter("startDate");       //开始时间
			String adultNum = request.getParameter("adultNum");       //大人
			String childNum = request.getParameter("childNum");       //小孩
			//房间详情[{roomid:"",adultNum:"",childNum:"",roomNum:""},{},{}]
			String roomDetails = request.getParameter("roomDetails");       //房间详情
			String insuranceid = request.getParameter("insuranceid");       //保险id
			/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
			certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
			String orderMember = request.getParameter("orderMember");       //出游人信息
			
		
			
			//获取邮轮路线
			LinerLine line = linerlineService.get(linerLineid);
			
			OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
			OrderLiner orderLiner = new OrderLiner();
			OrderInsurance orderInsurance = new OrderInsurance();
			
			orderSys.setType(6);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
								//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
			orderSys.setFatherid(0);		// 父id
			orderSys.setMemberid(Integer.parseInt(uid));      //下单人
			orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

			orderSys.setContactsName(contactsName);	//联系人姓名
			orderSys.setContactsMobile(contactsMobile);	//联系人电话
			orderSys.setRemark(remark);			//备注
			orderSys.setPeopleNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));
			orderSys.setTitle(line.getName());			//订单标题
			orderSys.setAdultNum(Integer.parseInt(adultNum));		// 大人数量
			orderSys.setChildNum(Integer.parseInt(childNum));		// 孩子数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			orderSys.setBeginDate(bt);       //开始时间
            orderSys.setAgentid(line.getAgentid());
            String image=line.getImgUrl();
            if(image!=null) {
            	image=image.split(",")[0];
            }
            orderSys.setImage(image);
			
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd"); 
			String bt1=sdf1.format(new Date()); 
			String orderNo = "YL-"+line.getAgentid()+"-"+bt1+"[]"+"-"+line.getId();
			orderLiner.setOrderNo(orderNo);  //订单号,后面替换[]
			orderLiner.setAgentid(line.getAgentid());		// 供应商ID
			orderLiner.setLinerLineId(Integer.parseInt(linerLineid)); //'邮轮路线id'
			orderLiner.setName(line.getName()); //'订单标题'
			orderLiner.setProductName(line.getName()); //'订单产品名称'
			orderLiner.setMemberId(uid); //'下单人id'
			orderLiner.setUseDate(bt); //'使用时间'
			orderLiner.setStatus(1); //'订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败'
			orderLiner.setContacts(contactsName); //'联系人'
			orderLiner.setContactMobile(contactsMobile); //'联系电话'
			orderLiner.setContactRemark(remark); //'备注'
			orderLiner.setLanguageId(Integer.parseInt(languageid));  //'语言id'
			orderLiner.setCityid(Integer.parseInt(line.getStartCity())); //'出发城市id'
			orderLiner.setCityName(line.getStartCityName()); //'出发城市名称'
			orderLiner.setPortid(Integer.parseInt(line.getUpPort())); //'出发港口id'
			orderLiner.setPortName(line.getUpPortName()); //'出发港口名称'
			orderLiner.setDownPortid(Integer.parseInt(line.getDownPort())); //'出发港口id'
			orderLiner.setDownPortName(line.getDownPortName()); //'出发港口名称'
			orderLiner.setLineNo(line.getLineNo()); //'路线编号'
			orderLiner.setCourseid(line.getLinerCompanyId()); //'邮轮航区id'
			orderLiner.setCourseName(line.getLinerCompanyName());//'邮轮航区名称'
			
			BigDecimal roomPrice= BigDecimal.ZERO;
			JSONArray jsonArray1 = JSONArray.fromObject(roomDetails); 
			List<OrderLinerRoom> orderLinerRoomList= new ArrayList<>();
			for(int i=0;i<jsonArray1.size();i++){
				OrderLinerRoom orderLinerRoom = new OrderLinerRoom();
					//取出数组第i个元素 
				    JSONObject jpro = jsonArray1.getJSONObject(i);
				    String roomid = jpro.getString("id"); 
				    String adultNum1 = jpro.getString("adultNum"); 
				    String childNum1 = jpro.getString("childNum"); 
				    String roomNum = jpro.getString("roomNum"); 
				    //获取放房间详情
				LinerRoom room = linerRoomService.get(roomid);
				orderLinerRoom.setRoomId(Integer.parseInt(roomid)); //'邮轮房间id'
				orderLinerRoom.setAdultNum(Integer.parseInt(adultNum1)); //'成人入住数量'
				orderLinerRoom.setChildrenNum(Integer.parseInt(childNum1)); //'儿童入住数量'
				orderLinerRoom.setRoomNum(Integer.parseInt(roomNum)); //'房间数'
//				    orderLinerRoom.setOrderId(orderId); //'邮轮订单id'(订单保存后添加)
				orderLinerRoom.setLanguageId(Integer.parseInt(languageid)); //'语言id'
				orderLinerRoom.setName(room.getName()); //'房间类型名称'
				orderLinerRoom.setSpec(room.getSpec()); //'房间规格'
				orderLinerRoom.setFloor(room.getFloor()); //'楼层'
				roomPrice = roomPrice.add(
						new BigDecimal(Integer.parseInt(adultNum1)+Integer.parseInt(childNum1))
								.multiply(room.getPrice())
								.setScale(2,BigDecimal.ROUND_HALF_UP));
				    orderLinerRoomList.add(orderLinerRoom);
				    
				    orderLinerRoom.setPrice(room.getPrice());
			}
			//邮轮订单总价
			orderLiner.setPrice(roomPrice.add(line.getPrice().multiply(new BigDecimal(orderSys.getPeopleNum()))));
			
			if(StringUtils.isNotBlank(insuranceid)){
				//获取保险信息
				Insurance insurance = insuranceService.get(insuranceid);
				
				orderInsurance.setName(insurance.getName());		// 保险名称
				orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));		// 保险ID
				orderInsurance.setAgentid(insurance.getAgentid());		// 供应商ID
				
				String orderNo1 = "BX-"+insurance.getAgentid()+"-"+bt1+"[]"+"-"+insurance.getId();
				orderInsurance.setOrderNo(orderNo1);		// 订单号，后面替换[]
				
				orderInsurance.setMemberId(Integer.parseInt(uid));		// 下单人id
				BigDecimal orderInsuranceprice = new BigDecimal(Integer.parseInt(adultNum)+Integer.parseInt(childNum))
						.multiply(insurance.getPrice())
						.setScale(2,BigDecimal.ROUND_HALF_UP);
				orderInsurance.setPrice((orderInsuranceprice));		// 订单价格
				orderInsurance.setStatus(1);		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
				orderInsurance.setContacts(contactsName);		// 联系人
				orderInsurance.setContactMobile(contactsMobile);		// 联系电话
				orderInsurance.setContactRemark(remark);		// 备注
				orderInsurance.setLanguageId(languageid);		// 语言id
				orderInsurance.setNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));		// 预定数量
			}
		
			JSONArray jsonArray = JSONArray.fromObject(orderMember); 
			List<OrderMember> orderMemberList= new ArrayList<>();
			for(int i=0;i<jsonArray.size();i++){
					OrderMember orderMember1 = new OrderMember();
					//取出数组第i个元素 
				    JSONObject jpro = jsonArray.getJSONObject(i);
				    String chineseName = jpro.getString("chineseName"); 
				    String englishName = jpro.getString("englishName"); 
				    String certType = jpro.getString("certType"); 
				    String certNo = jpro.getString("certNo"); 
				    String certValidDate = jpro.getString("certValidDate"); 
				    String birthday = jpro.getString("birthday"); 
				    String area = jpro.getString("area"); 
				    String mobile = jpro.getString("mobile"); 
				    String orderMemberType = jpro.getString("type"); 
				    
				    orderMember1.setChineseName(chineseName);		// 中文姓名
				    orderMember1.setEnglishName(englishName);		// 英文姓名
				    orderMember1.setCertType(Integer.parseInt(certType));		// 证件类型：1.身份证 2.护照 3.本地ID',
				    orderMember1.setCertNo(certNo);		// 证件号
				    Date certValidDate1=sdf.parse(certValidDate); 
				    orderMember1.setCertValidDate(certValidDate1);		// 有效期
				    Date birthday1=sdf.parse(birthday); 
				    orderMember1.setBirthday(birthday1);		// 出生年月
				    orderMember1.setArea(area);		// 区号
				    orderMember1.setMobile(mobile);		// 手机号
				    orderMember1.setLanguageId(Integer.parseInt(languageid));		// 语言id
				    orderMember1.setType(4);		// 1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//				    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
				    if (StringUtils.isNotBlank(orderMemberType)) {
				    	orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
				    }
				    orderMemberList.add(orderMember1);
			}
			BigDecimal insurancePrice = BigDecimal.ZERO;
			if(orderInsurance.getPrice()!=null){
				insurancePrice = orderInsurance.getPrice();
			}
			orderSys.setPrice(orderLiner.getPrice().add(insurancePrice));//主订单总价
		
			String orderid = orderSysService.saveLinerOrder(orderSys,orderLiner,orderLinerRoomList,orderInsurance,orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("邮轮添加订单成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			return ajaxJson;
		}
	}
	
    private AjaxJson checkSaveLineOrder(HttpServletRequest request) {
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String linerLineid=request.getParameter("linerLineid");//邮轮路线Id
		String contactsName = request.getParameter("contactsName");       //联系人姓名
		String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
		String remark = request.getParameter("remark");       //备注
		String startDate = request.getParameter("startDate");       //开始时间
		String adultNum = request.getParameter("adultNum");       //大人
		String childNum = request.getParameter("childNum");       //小孩
		//房间详情[{roomid:"",adultNum:"",childNum:"",roomNum:""},{},{}]
		String roomDetails = request.getParameter("roomDetails");       //房间详情
		String insuranceid = request.getParameter("insuranceid");       //保险id
		/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
		certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
		String orderMember = request.getParameter("orderMember");       //出游人信息
    	
        AjaxJson ajaxJson = new AjaxJson();
    	
	if (StringUtils.isBlank(type)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("type传参为空");
		return ajaxJson;
	}
	if(Integer.parseInt(type)!=2){
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("type传参错误");
		return ajaxJson;
	}
	if (StringUtils.isBlank(uid)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("uid传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(time)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("time传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(key)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("key传参为空");
		return ajaxJson;
	}
	Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
	if (memberStatus != 0) {
		ajaxJson.getBody().put("memberStatus", memberStatus);
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_7);
		ajaxJson.setMsg(AppErrorUtils.error_7_desc);
		return ajaxJson;
	}	

	if (StringUtils.isBlank(languageid)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("languageid传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(linerLineid)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("linerLineid传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(contactsName)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("contactsName传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(contactsMobile)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("contactsMobile传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(startDate)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("startDate传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(adultNum)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("adultNum传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(childNum)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("childNum传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(roomDetails)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("roomDetails传参为空");
		return ajaxJson;
	}
	if (StringUtils.isBlank(orderMember)) {
		ajaxJson.setSuccess(false);
		ajaxJson.setErrorCode(AppErrorUtils.error_2);
		ajaxJson.setMsg("orderMember传参为空");
		return ajaxJson;
	}
    return null;
   }
	
}

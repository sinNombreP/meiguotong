package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.car.CarTimePrice;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraft;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftCar;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftDetails;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftGuide;
import com.jeeplus.modules.meiguotong.entity.draft.ProductDraftHotel;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guide.GuideDate;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoom;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomDate;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.meiguotong.entity.travel.TravelDetails;
import com.jeeplus.modules.meiguotong.service.car.CarInfoService;
import com.jeeplus.modules.meiguotong.service.car.CarTimePriceService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.draft.ProductDraftService;
import com.jeeplus.modules.meiguotong.service.guide.GuideDateService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelRoomDateService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelRoomService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
import com.jeeplus.modules.meiguotong.service.travel.TravelCustomizationService;
import com.jeeplus.modules.meiguotong.service.travel.TravelDetailsService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping(value = "${adminPath}/interface/customization")
public class AppCustomizationController {
	@Autowired
	private AppUtils appUtils;
	@Autowired
	private ComCityService comCityService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private TravelCustomizationService travelCustomizationService;
	@Autowired
	private TravelDetailsService travelDetailsService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private GuideDateService guideDateService;
	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private CarTimePriceService carTimePriceServcie;
	@Autowired
	private HotelRoomService hotelRoomService;
	@Autowired
	private HotelRoomDateService hotelRoomDateService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private ScenicSpotTicketService scenicSpotTicketService;
	@Autowired
	private ProductDraftService productDraftService;
	
	/**
	 * 定制城市关键字搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getCityByTitle")
	@ResponseBody
	public AjaxJson getCityByTitle(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String cityName = request.getParameter("cityName");       //城市名称
		
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
		if (StringUtils.isBlank(cityName)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("cityName传参为空");
			return ajaxJson;
		}
		
		try {
			ComCity comCity = new ComCity();	
			comCity.setLanguageId(languageid);
			comCity.setCityName(cityName);
			
			List<ComCity> list = comCityService.getCityByTitle(comCity);
		
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("定制城市关键字搜索成功");  
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
	 * 定制城市介绍接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getCity")
	@ResponseBody
	public AjaxJson getCity(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String cityid = request.getParameter("cityid");       //城市名称
		
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
		if (StringUtils.isBlank(cityid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("cityid传参为空");
			return ajaxJson;
		}
		
		try {
			ComCity comCity = new ComCity();	
			comCity.setLanguageId(languageid);
			comCity.setId(cityid);
			
			ComCity city= comCityService.getCity(comCity);
		
			ajaxJson.getBody().put("cityInfo", city);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取定制城市介绍信息成功");  
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
	 * 定制景点关键字搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getScenicByTitle")
	@ResponseBody
	public AjaxJson getScenicByTitle(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String name = request.getParameter("scenicName");       //景点名称
		String cityid = request.getParameter("cityid");       //城市ID
		
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
		if (StringUtils.isBlank(cityid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("cityid传参为空");
			return ajaxJson;
		}
		
		try {
			ScenicSpot scenicSpot = new ScenicSpot();	
			scenicSpot.setLanguageId(Integer.parseInt(languageid));
			scenicSpot.setCityId(Integer.parseInt(cityid));
			if(StringUtils.isNotBlank(name))
			scenicSpot.setName(name);
			
			List<ScenicSpot> list = scenicSpotService.getScenicByTitle(scenicSpot);
		
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("定制景点关键字搜索成功");  
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
	 * 定制景点介绍接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getScenic")
	@ResponseBody
	public AjaxJson getScenic(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String scenicSpotid = request.getParameter("scenicSpotid");       //景点名称
		
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
		if (StringUtils.isBlank(scenicSpotid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("scenicSpotid传参为空");
			return ajaxJson;
		}
		
		try {
			ScenicSpot scenicSpot1 = new ScenicSpot();	
			scenicSpot1.setLanguageId(Integer.parseInt(languageid));
			scenicSpot1.setId(scenicSpotid);
			
			ScenicSpot scenicSpot= scenicSpotService.getScenic(scenicSpot1);
		
			ajaxJson.getBody().put("scenicSpot", scenicSpot);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取定制景点介绍成功");  
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
	 * 获取后台配置的旅游定制的天数列表接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getTravelDay")
	@ResponseBody
	public AjaxJson getTravelDay(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String cityid = request.getParameter("cityid");       //城市id
		
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
		if (StringUtils.isBlank(cityid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("cityid传参为空");
			return ajaxJson;
		}
		try {
			TravelCustomization travelCustomization = new TravelCustomization();	
			travelCustomization.setLanguage(Integer.parseInt(languageid));
			travelCustomization.setCity(Integer.parseInt(cityid));
			List<Integer> list= travelCustomizationService.getTravelDay(travelCustomization);
		
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取后台配置的旅游定制的天数列表成功");  
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
	 * 根据旅游天数获取后台旅游定制列表接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getTravelByDay")
	@ResponseBody
	public AjaxJson getTravelByDay(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String dayNum = request.getParameter("dayNum");       //天数
		String cityid = request.getParameter("cityid");       //城市id
		String pageNo = request.getParameter("pageNo");//页码
		
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
		if (StringUtils.isBlank(cityid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("cityid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(pageNo)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("pageNo传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(dayNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("dayNum传参为空");
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
			
			TravelCustomization travelCustomization = new TravelCustomization();	
			travelCustomization.setLanguage(Integer.parseInt(languageid));
			travelCustomization.setDayNum(Integer.parseInt(dayNum));
			travelCustomization.setCity(Integer.parseInt(cityid));
			
			Page<TravelCustomization> page=new Page<TravelCustomization>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);
		
			Page<TravelCustomization> page1 = travelCustomizationService.getTravelByDay(page, travelCustomization);
		
			List<TravelCustomization> list=page1.getList();
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", list);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg(" 根据旅游天数获取后台旅游定制列表成功");  
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
	 * 获取旅游定制的详细信息接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getTravelDetails")
	@ResponseBody
	public AjaxJson getTravelDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String travelid = request.getParameter("travelid");       //旅游定制id
		
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
		if (StringUtils.isBlank(travelid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("travelid传参为空");
			return ajaxJson;
		}
		try {
			List<TravelDetails> list = travelDetailsService.getTravelDetails(travelid);
		
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			//修改查看数量
			TravelCustomization travelCustomization=new TravelCustomization();
			travelCustomization.setId(travelid);
			travelCustomizationService.changeLookNum(travelCustomization);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取旅游定制的详细信息成功");  
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
	 * 定制生成订单接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveTravelOrder")
	@ResponseBody
	public AjaxJson saveTravelOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String orderType = request.getParameter("orderType");       //订单类型1.旅游2.商务3旅游商务
			String contactsName = request.getParameter("contactsName");       //联系人姓名
			String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
			String remark = request.getParameter("remark");       //备注
			String startCity = request.getParameter("startCity");       //出发地id
			String startAddress = request.getParameter("startAddress");       //出发地详细地址 
			String startDate = request.getParameter("startDate");       //开始时间
			String endDate = request.getParameter("endDate");       //结束时间
			String dayNum = request.getParameter("dayNum");       //天数
			String adultNum = request.getParameter("adultNum");       //大人
			String childNum = request.getParameter("childNum");       //小孩
			String bagNum = request.getParameter("bagNum");       //包裹数量
			String guideid = request.getParameter("guideid");       //导游id
			String guideType = request.getParameter("guideType");       //导游类型
//			String hotelInfor = request.getParameter("hotelInfor");       //酒店信息
			String carInfor = request.getParameter("carInfor");       //车辆信息
			String travelInfor = request.getParameter("travelInfor");       //行程信息
			String insuranceid = request.getParameter("insuranceid");       //保险id
			/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
			certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
			String orderMember = request.getParameter("orderMember");       //出游人信息
			
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
			if (StringUtils.isBlank(orderType)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderType传参为空");
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
			if (StringUtils.isBlank(startAddress)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startAddress传参为空");
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
			if (StringUtils.isBlank(travelInfor)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("travelInfor传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(carInfor)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("carInfor传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(bagNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("bagNum传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(endDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("endDate传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(dayNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("dayNum传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(startCity)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("startCity传参为空");
				return ajaxJson;
			}
			
			if (StringUtils.isBlank(orderMember)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderMember传参为空");
				return ajaxJson;
			}
			Date createDate=new Date();
			BigDecimal orderPrice = new BigDecimal("0");
			OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
			OrderTravelBusiness orderTravelBusiness = new OrderTravelBusiness();
			OrderInsurance orderInsurance = new OrderInsurance();
			OrderGuide orderGuide = new OrderGuide();
			
			if(Integer.parseInt(orderType)==1){
				orderSys.setType(11);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
				//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
			}else if(Integer.parseInt(orderType)==2){
				orderSys.setType(13);
			}else if(Integer.parseInt(orderType)==3){
				orderSys.setType(14);
			}
															
			orderSys.setFatherid(0);		// 父id
			orderSys.setMemberid(Integer.parseInt(uid));      //下单人
			orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

			orderSys.setContactsName(contactsName);	//联系人姓名
			orderSys.setContactsMobile(contactsMobile);	//联系人电话
			orderSys.setRemark(remark);			//备注
			orderSys.setPeopleNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));

			orderSys.setAdultNum(Integer.parseInt(adultNum));		// 大人数量
			orderSys.setChildNum(Integer.parseInt(childNum));		// 孩子数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			Date et=sdf.parse(endDate); 
			orderSys.setBeginDate(bt);       //开始时间
			orderSys.setEndDate(et);         //结束时间
			orderSys.setCreateDate(createDate);
			
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd"); 
			String bt1=sdf1.format(new Date());
			String carAgentid="-";
			String orderTitle="";
			//定制订单详情
			List<OrderTravelDetails> orderTravelDetailsList= new ArrayList<>();
			//酒店订单
			List<OrderHotel> orderHotelList= new ArrayList<>();
			
			if(StringUtils.isNotBlank(travelInfor)){
				JSONArray jsonArray = JSONArray.fromObject(travelInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String date = jpro.getString("date"); //日期
					 String day = jpro.getString("day"); //第几天
					 String travelDetails = jpro.getString("travelDetails"); //行程详情信息
					 String hotelInforDetails = jpro.getString("hotelInforDetails"); //酒店信息
					 if (StringUtils.isBlank(travelDetails)||
							 StringUtils.isBlank(date)||StringUtils.isBlank(day)) {
						ajaxJson.setSuccess(false);
						ajaxJson.setErrorCode(AppErrorUtils.error_2);
						ajaxJson.setMsg("定制行程信息不完善");
						return ajaxJson;
					}
					 //酒店
					 List<OrderHotel> orderHotelList1= new ArrayList<>();
					 if(StringUtils.isNotBlank(hotelInforDetails)){//是否预订酒店
							JSONArray jsonArrayHotel = JSONArray.fromObject(hotelInforDetails); 
							 for(int j=0;j<jsonArrayHotel.size();j++){
								 OrderHotel orderHotel = new OrderHotel();
								 //取出数组第i个元素 
								 JSONObject jproHotel = jsonArrayHotel.getJSONObject(j);
								 String hotelid = jproHotel.getString("hotelid"); //酒店id
								 String roomid = jproHotel.getString("roomid"); //房间id
								 String num = jproHotel.getString("num"); //房间数量
								 
								 if (StringUtils.isBlank(hotelid)||StringUtils.isBlank(roomid)||
										 StringUtils.isBlank(num)) {
										ajaxJson.setSuccess(false);
										ajaxJson.setErrorCode(AppErrorUtils.error_2);
										ajaxJson.setMsg("酒店信息不完善");
										return ajaxJson;
									}
								 //获取酒店信息
								 Hotel hotel = hotelService.get(hotelid);
								 orderHotel.setName(hotel.getName()); //'订单标题'
								 orderHotel.setHotelId(Integer.parseInt(hotelid)); //'酒店id'
								 String orderNo4 = "HT-"+hotel.getAgentid()+"-"+bt1+"[]"+"-"+hotel.getId();
								 orderHotel.setOrderNo(orderNo4); //'订单号',后面替换[]
								 orderHotel.setProductName(hotel.getName()); //'酒店名称'
								 orderHotel.setMemberId(Integer.parseInt(uid)); //'下单人id'
								 orderHotel.setStatus(1); //'订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败'
								 orderHotel.setContacts(contactsName); //'联系人'
								 orderHotel.setContactMobile(contactsMobile); //'联系电话'
								 orderHotel.setContactRemark(remark); //'备注'
								 orderHotel.setLanguageId(Integer.parseInt(languageid)); //'语言id'
								 Date useDate=sdf.parse(date);
								 orderHotel.setStartDate(useDate);  //'使用时间'
								 orderHotel.setAgentid(hotel.getAgentid());  //'供应商ID'
								 //订单价格
								 BigDecimal orderHotelPrice = BigDecimal.ZERO;
								 //酒店房间集合
								 List<OrderHotelRoom> orderHotelRoomList= new ArrayList<>();
								 
								 OrderHotelRoom orderHotelRoom = new OrderHotelRoom();
								 //获取酒店房间信息
								 HotelRoom hotelRoom = hotelRoomService.get(roomid);
			//					 orderHotelRoom.setOrderHotelId(orderHotelId); //'酒店订单id'
								 orderHotelRoom.setRoomId(Integer.parseInt(roomid)); //'房间id'
								 orderHotelRoom.setRoomName(hotelRoom.getRoomName()); //'房间名称'
								 orderHotelRoom.setRoomDate(useDate); //'日期'
								 orderHotelRoom.setRoomDay(Integer.parseInt(day)); //'定制行程第几天'
								 //获取酒店房间价格库存
								 HotelRoomDate hotelRoomDate = new HotelRoomDate(); 
								 hotelRoomDate.setPriceDate(useDate);
								 hotelRoomDate.setHotelRoomId(Integer.parseInt(roomid));
								 HotelRoomDate hotelRoomDate1 = hotelRoomDateService.getHotelRoomDate(hotelRoomDate);
//								 if(hotelRoomDate1.getStockNum()<Integer.parseInt(num)){
//									 ajaxJson.setSuccess(false);
//										ajaxJson.setErrorCode(AppErrorUtils.error_39);
//										ajaxJson.setMsg(AppErrorUtils.error_39_desc);
//										return ajaxJson;
//								 }
								 BigDecimal roomPrice = hotelRoomDate1.getPrice();
								 //计算订单价格
								 orderHotelPrice = roomPrice.add(orderHotelPrice).multiply(new BigDecimal(num)).setScale(2, BigDecimal.ROUND_HALF_UP); 
								 orderHotelRoom.setPrice(roomPrice); //'价格'
								 orderPrice.add(roomPrice);
								 orderHotelRoom.setNum(Integer.parseInt(num)); //房间数量
								 orderHotelRoom.setHotelId(hotelRoom.getHotelId());//'酒店id'
								 orderHotelRoom.setName(hotelRoom.getName());//'酒店名称'
								 orderHotelRoom.setPeople(hotelRoom.getPeople());//'最大可入住人数'
								 orderHotelRoom.setImgUrl(hotelRoom.getImgUrl());//'酒店房间照片'
								 orderHotelRoom.setContent(hotelRoom.getContent()); //'详情介绍'
								 orderHotelRoom.setLanguageId(Integer.parseInt(languageid));//'语言id'
								 orderHotelRoom.setAgentid(hotelRoom.getAgentid());  //'供应商ID'
								 
								 orderHotelRoomList.add(orderHotelRoom);
								 
								 orderHotel.setPrice(orderHotelPrice); //'订单价格'
								 orderHotel.setOrderHotelRoomList(orderHotelRoomList);
								 orderHotel.setCreateDate(createDate);
								 orderHotelList1.add(orderHotel);
							 }
							
							 HashMap<Integer,OrderHotel> tempMap = new HashMap<Integer,OrderHotel>(); //去掉重复的key 
							 for(OrderHotel orderHotel : orderHotelList1){ 
								 int temp = orderHotel.getHotelId(); 
								 //containsKey(Object key)该方法判断Map集合中是否包含指定的键名，如果包含返回true，不包含返回false 
								 //containsValue(Object value)该方法判断Map集合中是否包含指定的键值，如果包含返回true，不包含返回false 
								 if(tempMap.containsKey(temp)){ 
									 OrderHotel newOrderHotel = tempMap.get(temp); 
									 newOrderHotel.getOrderHotelRoomList().addAll(orderHotel.getOrderHotelRoomList());
									 //HashMap不允许key重复，当有key重复时，前面key对应的value值会被覆盖 
									 tempMap.put(temp,newOrderHotel ); 
								}else{ 
									tempMap.put(temp,orderHotel ); 
								} 
							}
							 //去除重复key的list 
							 for(Integer temp:tempMap.keySet()){ 
								 orderHotelList.add(tempMap.get(temp)); 
							}
							
						}
					 
					 
					 
					 //行程
					 JSONArray jsonArrayTravel = JSONArray.fromObject(travelDetails); 
					 for(int j=0;j<jsonArrayTravel.size();j++){
						 //取出数组第j个元素 
						 JSONObject jproTravel = jsonArrayTravel.getJSONObject(j);
						 String cityid = jproTravel.getString("cityid"); //城市id
						 String cityName = jproTravel.getString("cityName"); //城市id
						 String scenicSpot = jproTravel.getString("scenicSpot"); //景点或商务详细信息
						 if (StringUtils.isBlank(cityid)||StringUtils.isBlank(scenicSpot)) {
							ajaxJson.setSuccess(false);
							ajaxJson.setErrorCode(AppErrorUtils.error_2);
							ajaxJson.setMsg("定制行程信息不完善");
							return ajaxJson;
						 }
						 //定制订单详情数据
						
						 if(StringUtils.isNotBlank(scenicSpot)){
							 orderTitle +=cityName+",";
							 JSONArray jsonArrayScenic = JSONArray.fromObject(scenicSpot); 
							 for(int k=0;k<jsonArrayScenic.size();k++){
								 OrderTravelDetails orderTravelDetails = new OrderTravelDetails();
								 //取出数组第k个元素 
								 JSONObject jproScenic = jsonArrayScenic.getJSONObject(k);
								 //{type:"",scenicpotid:"",adress:"",startDate:"",endDate:"",tagid:"",adultNum:"",childNum:"",infor:"",}
								 String travelType = jproScenic.getString("type"); //行程类型1.景点2.商务
								 String scenicpotid = "";
								 String busiItem = "";
								 String travelStartDate = "";
								 String travelEndDate = "";
								 String tagid = "";
								 String travelAdultNum = "";
								 String travelChildNum = "";
								 String infor = "";
								 if(Integer.parseInt(travelType) == 1){
								 	 scenicpotid = jproScenic.getString("scenicpotid"); //景点id
									 orderTravelDetails.setScenic(scenicpotid); //'景点id'
								 }else if(Integer.parseInt(travelType) == 2){
								 	 busiItem = jproScenic.getString("busiItem"); //项目名称，多个用，隔开
									 travelStartDate = jproScenic.getString("startDate"); //商务开始时间
									 travelEndDate = jproScenic.getString("endDate"); //商务结束时间
									 tagid = jproScenic.getString("tagid"); //商务项目id
									 travelAdultNum = jproScenic.getString("adultNum"); //商务成人个数
									 travelChildNum = jproScenic.getString("childNum"); //商务儿童个数
									 infor = jproScenic.getString("infor"); //商务详细信息
								 }

								 String address = jproScenic.getString("address"); //商务详细地址
								 String busiInfo = jproScenic.getString("busiInfo"); //商务项目详细名称

								 if (StringUtils.isBlank(travelType)) {
									ajaxJson.setSuccess(false);
									ajaxJson.setErrorCode(AppErrorUtils.error_2);
									ajaxJson.setMsg("定制行程信息不完善");
									return ajaxJson;
								 }
//								 orderTravelDetails.setOrderTrvelId(orderTrvelId); //'定制订单id'
								 orderTravelDetails.setDay(Integer.parseInt(day)); //'第几天'
								 orderTravelDetails.setCitySort(j+1);//'城市排序'
								 orderTravelDetails.setSort(k+1); //'城市行程排序'
								 orderTravelDetails.setType(Integer.parseInt(travelType)); //'类型1.旅游2.商务'
								 orderTravelDetails.setCity(Integer.parseInt(cityid)); //'城市id'

								 orderTravelDetails.setBusiAddress(address);//'商务详细地址'
								 SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm");
								 if (StringUtils.isNotBlank(travelStartDate)){
									 Date sd=sdf2.parse(travelStartDate); 
									 orderTravelDetails.setBusiBeginDate(sd);//'商务开始时间'
								 }
								 if (StringUtils.isNotBlank(travelEndDate)){
									 Date ed=sdf2.parse(travelEndDate); 
									 orderTravelDetails.setBusiEndDate(ed); //'商务结束时间'
								 }
								 orderTravelDetails.setBusiItemId(tagid); //'商务定制项目id'
								 orderTravelDetails.setBusiItem(busiItem); //'商务定制项目'
								 orderTravelDetails.setBusiInfo(busiInfo); //'商务信息（地址+leixing+时间段）'
								 orderTravelDetails.setBusiContent(infor); //'商务详细'
								 if (StringUtils.isNotBlank(travelAdultNum)){
									 orderTravelDetails.setBusiAdultNum(Integer.parseInt(travelAdultNum)); //'商务大人数量'
								 }
								 if (StringUtils.isNotBlank(travelChildNum)){
									 orderTravelDetails.setBusiChildNum(Integer.parseInt(travelChildNum)); //'商务孩子数量'
								 }
								 Date useDate=sdf.parse(date); 
								 orderTravelDetails.setUseDate(useDate); //'使用时间'
								 if(StringUtils.isNotBlank(scenicpotid)){
									//景点订单
									//获取景点的默认定制门票
									ScenicSpotTicket scenicSpotTicket = new ScenicSpotTicket();
									scenicSpotTicket.setScenicid(Integer.parseInt(scenicpotid));
									ScenicSpotTicket scenicSpotTicket1 = scenicSpotTicketService.getTicket(scenicSpotTicket); 
									if(scenicSpotTicket1!=null){
										//获取景点
										ScenicSpot scenicSpot1 = scenicSpotService.get(scenicpotid);
										OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
										orderScenicSpot.setName(scenicSpot1.getName());		// 订单标题
										orderScenicSpot.setSecnicSpotId(Integer.parseInt(scenicpotid));// 景点id
										String orderNo = "JD-"+scenicSpot1.getAgentid()+"-"+bt1+"[]"+"-"+scenicSpot1.getId();
										orderScenicSpot.setOrderNo(orderNo);  //订单号,后面替换[]
										orderScenicSpot.setProductName(scenicSpot1.getName());		// 订单产品名称
										orderScenicSpot.setMemberId(Integer.parseInt(uid));		// 下单人id
										orderScenicSpot.setUseDate(useDate);		// 使用时间
										
										BigDecimal scenicSpotPrice = scenicSpotTicket1.getPrice();
										Integer num = (Integer.parseInt(adultNum)+Integer.parseInt(childNum));
										
										orderScenicSpot.setPrice(scenicSpotPrice.multiply(new BigDecimal(num.toString())));		// 订单价格
										orderScenicSpot.setPrice2(scenicSpotTicket1.getPrice());
										orderPrice.add(scenicSpotPrice);
										orderScenicSpot.setStatus(1);		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
										orderScenicSpot.setContacts(contactsName);		// 联系人
										orderScenicSpot.setContactMobile(contactsMobile);		// 联系电话
										orderScenicSpot.setContactRemark(remark);		// 备注
										orderScenicSpot.setLanguageId(Integer.parseInt(languageid));	// 语言id
										orderScenicSpot.setNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));		// 预定数量
										
										orderScenicSpot.setSceniciCompany(scenicSpot1.getSceniciCompany());//景点代理商 
										
										orderScenicSpot.setAdultNum(adultNum);
										orderScenicSpot.setChildNum(childNum);
										orderScenicSpot.setEndCity(scenicSpot1.getCityId().toString());
										orderScenicSpot.setAddress(scenicSpot1.getAddress()); //景点地址
										orderScenicSpot.setContent(scenicSpot1.getContent()); //景点特色详情
										orderScenicSpot.setAgentid(scenicSpot1.getAgentid());
										//定制订单详情属性赋值
										orderTravelDetails.setPrice(scenicSpotPrice);
										orderTravelDetails.setOrderScenicSpot(orderScenicSpot);
										orderTravelDetails.setCreateDate(createDate);
										orderTravelDetailsList.add(orderTravelDetails);
									}
								 }
							 }
						 }
						
					 }
				 }
			}
			//订单标题
			orderTitle = orderTitle.substring(0, orderTitle.length()-1);
			orderTitle += "-"+dayNum;
			orderSys.setTitle(orderTitle);			//订单标题
			//车辆订单
			List<OrderCar> orderCarList= new ArrayList<>();
			if(StringUtils.isNotBlank(carInfor)){//是否选择车辆
				 JSONArray jsonArray = JSONArray.fromObject(carInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 OrderCar orderCar1 = new OrderCar();
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String carid = jpro.getString("carid"); 
					 String carType = jpro.getString("carType"); 
					 String dayDate = jpro.getString("dayDate");
					 String dayRange = jpro.getString("dayRange");
					 //获取车辆信息
					 CarInfo car = carInfoService.get(carid);
					 carAgentid += car.getAgentid()+"-";
					 String orderNo3 = "VE-"+car.getAgentid()+"-"+bt1+"[]";
					 orderCar1.setOrderNo(orderNo3); //'订单号',后面替换[]
					 orderCar1.setType(4); //'订单类型1.包车租车2.短程接送3.接送机4.定制'
					 orderCar1.setName(car.getCarName()); //'订单标题'
					 orderCar1.setAdultNum(Integer.parseInt(adultNum)); //'大人数量'
					 orderCar1.setChildNum(Integer.parseInt(childNum)); //'孩子数量'
					 orderCar1.setBagNum(Integer.parseInt(bagNum)); //'包裹数量'
					 orderCar1.setStartCity(startCity); //'出发城市'
					 orderCar1.setStartAddress(startAddress); //'出发详细地址'
					 orderCar1.setBeginDate(bt); //'上车时间'
					 orderCar1.setEndDate(et); //'结束时间'
					 orderCar1.setContactsName(contactsName); //'联系人姓名'
					 orderCar1.setContactsMobile(contactsMobile); //'联系人电话'
					 orderCar1.setRemark(remark); //'备注'
					 
					 //查询汽车价格
					 CarTimePrice carTimePrice = new CarTimePrice();
					 carTimePrice.setCarid(carid);
					 carTimePrice.setBusinessType(carType);
					 carTimePrice.setBusiDate(bt);
					 CarTimePrice carTimePrice1 = carTimePriceServcie.getPrice(carTimePrice);
					 BigDecimal carPrice = carTimePrice1.getEmptyPrice()
							 .add(carTimePrice1.getPrice())
							 .add(carTimePrice1.getFoodPrice())
							 .add(carTimePrice1.getHeadPrice())
							 .add(carTimePrice1.getStartPrice())
							 .setScale(2,BigDecimal.ROUND_HALF_UP);
					 orderCar1.setPrice(carPrice); //'金额'
					 orderPrice.add(carPrice);
					 orderCar1.setCarid(carid); //'汽车id'
					 orderCar1.setCarName(car.getCarName()); //'汽车名称'
					 orderCar1.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过'
					 orderCar1.setLanguageId(Integer.parseInt(languageid)); //'语言'
					 orderCar1.setMemberId(uid);  //'下单人ID'
					 orderCar1.setUseDate(bt); //'使用时间'
					 orderCar1.setCarTime(dayDate); //行程行驶时间
					 orderCar1.setCarDistance(dayRange);//行程行驶距离
					 orderCar1.setSeatNum(car.getSeatNum());//汽车座位数
					 orderCar1.setComfort(car.getComfort().toString());//汽车舒适度
					 orderCar1.setCarBagNum(car.getBagNum());//汽车包裹容量
					 orderCar1.setAgentid(car.getAgentid());
					 orderCar1.setCreateDate(createDate);
					 orderCarList.add(orderCar1);
				 }
			}
			//定制订单
			String orderNo = "DZ"+carAgentid+bt1+"[]";
			orderTravelBusiness.setOrderNo(orderNo);  //订单号,后面替换[]
			orderTravelBusiness.setLanguageId(Integer.parseInt(languageid)); //'语言',
			orderTravelBusiness.setName(orderTitle); //'订单名称'
			orderTravelBusiness.setType(Integer.parseInt(orderType)); //'1.旅游定制2.商务定制3.旅游商务定制'
			orderTravelBusiness.setCity(Integer.parseInt(startCity)); //'出发城市'
			orderTravelBusiness.setAddress(startAddress); //'出发地址'
			orderTravelBusiness.setStartDate(bt); //'开始时间'
			orderTravelBusiness.setEndDate(et); //'结束时间'
			orderTravelBusiness.setAdultNum(Integer.parseInt(adultNum)); //'大人数量'
			orderTravelBusiness.setChildNum(Integer.parseInt(childNum)); //'孩子数量'
			orderTravelBusiness.setBagNum(Integer.parseInt(bagNum)); //'包裹数量'
			orderTravelBusiness.setDayNum(Integer.parseInt(dayNum)); //'旅游天数'
			orderTravelBusiness.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过'
			orderTravelBusiness.setContactsName(contactsName); //'联系人姓名'
			orderTravelBusiness.setContactsMobile(contactsMobile); //'联系人电话'
			orderTravelBusiness.setRemark(remark); //'备注'
			orderTravelBusiness.setMemberId(Integer.parseInt(uid)); //'下单人id'
			orderTravelBusiness.setUseDate(bt); //'使用时间'
			orderTravelBusiness.setCreateDate(createDate);
//			orderTravelBusiness.setPrice(price); //'总价'

			
			//导游订单
			if(StringUtils.isNotBlank(guideid)){//是否选择导游
				if (StringUtils.isBlank(guideType)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("guideType传参为空");
					return ajaxJson;
				}
				//获取导游信息
				Guide guide = guideService.get(guideid);
				String orderNo2 = "DY-"+guide.getId()+"-"+bt1+"[]";
				orderGuide.setOrderNo(orderNo2); //'订单号',后面替换[]
				orderGuide.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过',
				orderGuide.setName("DY-"+guide.getRealName()); //'订单标题',
				orderGuide.setCreateDate(new Date());
				orderGuide.setLanguageid(Integer.parseInt(languageid));
				//获取导游价格
				GuideDate guideDate = new GuideDate();
				guideDate.setGuideid(Integer.parseInt(guideid));
				guideDate.setType(Integer.parseInt(guideType));
				guideDate.setPriceDate(bt);
				GuideDate guideDate1 = guideDateService.getGuideDate(guideDate);
				BigDecimal guidePrice = guideDate1.getPrice();
				orderGuide.setPrice(guidePrice.multiply(new BigDecimal(dayNum))); //'金额',
				orderPrice.add(guidePrice);
				orderGuide.setBeginDate(bt); //'开始时间',
				orderGuide.setEndDate(et); //'结束时间',
				orderGuide.setType(5); //'1.包车租车 2.当地玩家 3.酒店 4.保险 5.定制',
				orderGuide.setTypeid(Integer.parseInt(guideid)); //'对应的导游ID',
				orderGuide.setContactsName(contactsName); //'联系人姓名',
				orderGuide.setContactsMobile(contactsMobile); //'联系人电话',
				orderGuide.setRemark(remark); //'备注',
				orderGuide.setUseDate(bt); //'使用时间',
				orderGuide.setAdultNum(adultNum);  //'大人数量',
				orderGuide.setChildNum(childNum); //'孩子数量',
				orderGuide.setEndCity(startCity); //'目的地',
				orderGuide.setMemberId(uid); //'下单人Id',
				orderGuide.setCreateDate(createDate);
			}
			//保险订单
			if(StringUtils.isNotBlank(insuranceid)){
				//获取保险信息
				Insurance insurance = insuranceService.get(insuranceid);
				
				orderInsurance.setName(insurance.getName());		// 保险名称
				orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));		// 保险ID
				orderInsurance.setAgentid(insurance.getAgentid());		// 供应商ID
				
				String orderNo1 = "BX-"+insurance.getAgentid()+"-"+bt1+"[]"+"-"+insurance.getId();
				orderInsurance.setOrderNo(orderNo1);		// 订单号，后面替换[]
				
				orderInsurance.setMemberId(Integer.parseInt(uid));		// 下单人id
				BigDecimal insurancePrice = insurance.getPrice();
				orderInsurance.setPrice(insurancePrice.multiply(new BigDecimal((Integer.parseInt(adultNum)+Integer.parseInt(childNum))+"")));		// 订单价格
				orderPrice.add(insurancePrice);
				orderInsurance.setStatus(1);		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
				orderInsurance.setContacts(contactsName);		// 联系人
				orderInsurance.setContactMobile(contactsMobile);		// 联系电话
				orderInsurance.setContactRemark(remark);		// 备注
				orderInsurance.setLanguageId(languageid);		// 语言id
				orderInsurance.setNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));		// 预定数量
				orderInsurance.setCreateDate(createDate);
			}
			
			//出行人信息		
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
				    orderMember1.setType(9);		//1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//				    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid
				    if (StringUtils.isNotBlank(orderMemberType)) {
				    	orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
				    }
				    orderMemberList.add(orderMember1);
			}
			

			orderSys.setPrice(orderPrice);
			orderTravelBusiness.setPrice(orderPrice);
			String orderid = orderSysService.saveTravelOrder(orderSys,orderTravelBusiness,orderTravelDetailsList,
					orderCarList,orderHotelList,orderGuide,orderInsurance,orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("定制添加订单成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_40);
			ajaxJson.setMsg(AppErrorUtils.error_40_desc);
			return ajaxJson;
		}
	}
	/**
	 * 旅游定制保存草稿接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveTravelDraft")
	@ResponseBody
	public AjaxJson saveTravelDraft(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");       //语言id
		String orderType = request.getParameter("orderType");       //订单类型1.旅游2.商务3旅游商务
		
		String startCity = request.getParameter("startCity");       //出发地id
		String startAddress = request.getParameter("startAddress");       //出发地详细地址 
		String startDate = request.getParameter("startDate");       //开始时间
		String endDate = request.getParameter("endDate");       //结束时间
		String dayNum = request.getParameter("dayNum");       //天数
		String adultNum = request.getParameter("adultNum");       //大人
		String childNum = request.getParameter("childNum");       //小孩
		String bagNum = request.getParameter("bagNum");       //包裹数量
		String guideid = request.getParameter("guideid");       //导游id
//		String hotelInfor = request.getParameter("hotelInfor");       //酒店信息
		String carInfor = request.getParameter("carInfor");       //车辆信息
		String travelInfor = request.getParameter("travelInfor");       //行程信息
		/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
		certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
		String orderMember = request.getParameter("orderMember");       //出游人信息
		
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
		if (StringUtils.isBlank(orderType)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("orderType传参为空");
			return ajaxJson;
		}
		
		if (StringUtils.isBlank(startDate)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("startDate传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(startAddress)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("startAddress传参为空");
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
		if (StringUtils.isBlank(travelInfor)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("travelInfor传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(carInfor)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("carInfor传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(bagNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("bagNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(endDate)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("endDate传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(dayNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("dayNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(startCity)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("startCity传参为空");
			return ajaxJson;
		}
		
		if (StringUtils.isBlank(orderMember)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("orderMember传参为空");
			return ajaxJson;
		}
		try {
			ProductDraft productDraft = new ProductDraft();
			List<ProductDraftCar> carList= new ArrayList<>();
			List<ProductDraftDetails> detailsList= new ArrayList<>();
			ProductDraftGuide productDraftGuide = new ProductDraftGuide();
			List<ProductDraftHotel> hotelList= new ArrayList<>();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			Date et=sdf.parse(endDate); 
			
			productDraft.setMemberid(Integer.parseInt(uid)); //'用户id',
			productDraft.setLanguageid(Integer.parseInt(languageid)); //'语言id',
			productDraft.setCityid(Integer.parseInt(startCity)); //'出发城市id',
			productDraft.setAddress(startAddress); //'出发详细地址',
			productDraft.setDayNum(Integer.parseInt(dayNum)); //'旅游天数',
			productDraft.setAdultNum(Integer.parseInt(adultNum)); //'大人数量',
			productDraft.setChildNum(Integer.parseInt(childNum)); //'孩子数量',
			productDraft.setBagNum(Integer.parseInt(bagNum)); //'行李箱数量',
			productDraft.setBeginDate(bt); //'开始时间',
			productDraft.setEndDate(et); //'结束时间',
			productDraft.setCreateDate(new Date()); //'创建时间',
			
			
			
			
			//定制草稿详情
			String draftTitle = "";
			if(StringUtils.isNotBlank(travelInfor)){
				JSONArray jsonArray = JSONArray.fromObject(travelInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String date = jpro.getString("date"); //日期
					 String day = jpro.getString("day"); //第几天
					 String travelDetails = jpro.getString("travelDetails"); //行程详情信息
					 String hotelInforDetails = jpro.getString("hotelInforDetails"); //酒店信息
					 if (StringUtils.isBlank(travelDetails)||
							 StringUtils.isBlank(date)||StringUtils.isBlank(day)) {
						ajaxJson.setSuccess(false);
						ajaxJson.setErrorCode(AppErrorUtils.error_2);
						ajaxJson.setMsg("草稿定制行程信息不完善");
						return ajaxJson;
					}
					 //酒店
					 if(StringUtils.isNotBlank(hotelInforDetails)){//是否预订酒店
							JSONArray jsonArrayHotel = JSONArray.fromObject(hotelInforDetails); 
							 for(int j=0;j<jsonArrayHotel.size();j++){
								 //取出数组第i个元素 
								 JSONObject jproHotel = jsonArrayHotel.getJSONObject(j);
								 String hotelid = jproHotel.getString("hotelid"); //酒店id
								 String roomid = jproHotel.getString("roomid"); //房间id
								 String num = jproHotel.getString("num"); //房间数量
								 
								 if (StringUtils.isBlank(hotelid)||StringUtils.isBlank(roomid)||
										 StringUtils.isBlank(num)) {
										ajaxJson.setSuccess(false);
										ajaxJson.setErrorCode(AppErrorUtils.error_2);
										ajaxJson.setMsg("草稿酒店信息不完善");
										return ajaxJson;
									}
								 ProductDraftHotel productDraftHotel = new ProductDraftHotel();
								 productDraftHotel.setDay(Integer.parseInt(day)); //'第几天',
								 productDraftHotel.setHotelid(Integer.parseInt(hotelid)); //'酒店id',
								 productDraftHotel.setRoomid(Integer.parseInt(roomid)); //'房间id',
								 productDraftHotel.setNum(Integer.parseInt(num)); //'房间数量',
								 hotelList.add(productDraftHotel);
							 }
						}
					 
					 //行程
					 JSONArray jsonArrayTravel = JSONArray.fromObject(travelDetails); 
					 for(int j=0;j<jsonArrayTravel.size();j++){
						 //取出数组第j个元素 
						 JSONObject jproTravel = jsonArrayTravel.getJSONObject(j);
						 String cityid = jproTravel.getString("cityid"); //城市id
						 String cityName = jproTravel.getString("cityName"); //城市id
						 String scenicSpot = jproTravel.getString("scenicSpot"); //景点或商务详细信息
						 if (StringUtils.isBlank(cityid)||StringUtils.isBlank(scenicSpot)) {
							ajaxJson.setSuccess(false);
							ajaxJson.setErrorCode(AppErrorUtils.error_2);
							ajaxJson.setMsg("定制行程信息不完善");
							return ajaxJson;
						 }
						 //定制订单详情数据
						if(StringUtils.isNotBlank(scenicSpot)){
							 draftTitle +=cityName+",";
							 JSONArray jsonArrayScenic = JSONArray.fromObject(scenicSpot); 
							 for(int k=0;k<jsonArrayScenic.size();k++){
								 //取出数组第k个元素 
								 JSONObject jproScenic = jsonArrayScenic.getJSONObject(k);
								 //{type:"",scenicpotid:"",adress:"",startDate:"",endDate:"",tagid:"",adultNum:"",childNum:"",infor:"",}
								 String travelType = jproScenic.getString("type"); //行程类型1.景点2.商务
								 String scenicpotid = jproScenic.getString("scenicpotid"); //景点id
								 String adress = jproScenic.getString("adress"); //商务详细地址
								 String busiItem = jproScenic.getString("busiItem"); //项目名称，多个用，隔开
								 String travelStartDate = jproScenic.getString("startDate"); //商务开始时间
								 String travelEndDate = jproScenic.getString("endDate"); //商务结束时间
								 String tagid = jproScenic.getString("tagid"); //商务项目id
								 String travelAdultNum = jproScenic.getString("adultNum"); //商务成人个数
								 String travelChildNum = jproScenic.getString("childNum"); //商务儿童个数
								 String infor = jproScenic.getString("infor"); //商务详细信息
								 if (StringUtils.isBlank(travelType)) {
									ajaxJson.setSuccess(false);
									ajaxJson.setErrorCode(AppErrorUtils.error_2);
									ajaxJson.setMsg("定制行程信息不完善");
									return ajaxJson;
								 }
								 ProductDraftDetails productDraftDetails = new ProductDraftDetails();
								 productDraftDetails.setDay(Integer.parseInt(day)); //'第几天'
								 productDraftDetails.setCitySort(j+1);//'城市排序'
								 productDraftDetails.setSort(k+1); //'城市行程排序'
								 productDraftDetails.setType(Integer.parseInt(travelType)); //'类型1.旅游2.商务'
								 productDraftDetails.setCity(Integer.parseInt(cityid)); //'城市id'
								 productDraftDetails.setScenic(scenicpotid); //'景点id'
								 productDraftDetails.setBusiAddress(adress);//'商务详细地址'
								 SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
								 if (StringUtils.isNotBlank(travelStartDate)){
									 Date sd=sdf2.parse(travelStartDate); 
									 productDraftDetails.setBusiBeginDate(sd);//'商务开始时间'
								 }
								 if (StringUtils.isNotBlank(travelEndDate)){
									 Date ed=sdf2.parse(travelEndDate); 
									 productDraftDetails.setBusiEndDate(ed); //'商务结束时间'
								 }
								 productDraftDetails.setBusiItemId(tagid); //'商务定制项目id'
								 productDraftDetails.setBusiItem(busiItem); //'商务定制项目'
								 productDraftDetails.setBusiContent(infor); //'商务详细'
								 if (StringUtils.isNotBlank(travelAdultNum)){
									 productDraftDetails.setBusiAdultNum(Integer.parseInt(travelAdultNum)); //'商务大人数量'
								 }
								 if (StringUtils.isNotBlank(travelChildNum)){
									 productDraftDetails.setBusiChildNum(Integer.parseInt(travelChildNum)); //'商务孩子数量'
								 }
								 Date useDate=sdf.parse(date); 
								 productDraftDetails.setUseDate(useDate); //'使用时间'
								 
								 detailsList.add(productDraftDetails);
							 }
						 }
						 
					 }
				 }
			}
			
			//草稿标题
			draftTitle = draftTitle.substring(0, draftTitle.length()-1);
			draftTitle += "-"+dayNum;
			productDraft.setTitle(draftTitle); //'草稿标题',
			
			//车辆
			if(StringUtils.isNotBlank(carInfor)){//是否选择车辆
				 JSONArray jsonArray = JSONArray.fromObject(carInfor); 
				 for(int i=0;i<jsonArray.size();i++){
					 ProductDraftCar productDraftCar = new ProductDraftCar();
					 //取出数组第i个元素 
					 JSONObject jpro = jsonArray.getJSONObject(i);
					 String carid = jpro.getString("carid"); 
					
					 productDraftCar.setCarid(Integer.parseInt(carid)); //'车辆id',
					
					 carList.add(productDraftCar);
				 }
			}

			
			//导游
			if(StringUtils.isNotBlank(guideid)){//是否选择导游
				productDraftGuide.setGuideid(Integer.parseInt(guideid));
			}
			
			productDraftService.saveProductDraft(productDraft,carList,detailsList,
					productDraftGuide,hotelList);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("旅游定制保存草稿成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
			return ajaxJson;
		}
	}
}
	

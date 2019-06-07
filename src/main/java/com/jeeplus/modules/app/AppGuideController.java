package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guide.GuideDate;
import com.jeeplus.modules.meiguotong.entity.guide.GuideTime;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteDate;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.service.guide.GuideDateService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.guide.GuideTimeService;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteDateService;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.sys.entity.member.MemberRefund;
import com.jeeplus.modules.sys.service.member.MemberRefundService;
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
@RequestMapping(value = "${adminPath}/interface/guide")
public class AppGuideController {
	@Autowired
	private GuideService guideService;
	@Autowired
	private GuideRouteService guideRouteService;	
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private GuideTimeService guideTimeService;
	@Autowired
	private MemberRefundService memberRefundService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private AppUtils appUtils;
	@Autowired
	private GuideDateService guideDateService;
	@Autowired
	private GuideRouteDateService guideRouteDateService;
	
	
	
	/**
	 * 导游筛选接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "guideScreen")
	@ResponseBody
	public AjaxJson guideScreen(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String  cityid=request.getParameter("cityid");//城市
			String  cityName=request.getParameter("cityName");//城市
			String pageNo = request.getParameter("pageNo");//页码
			String guideAge = request.getParameter("guideAge");       //“”不限 年龄段(1980)多个用，隔开
			String guideSex = request.getParameter("guideSex");       //“”不限  1.男2.女多个用，隔开
			String minPrice = request.getParameter("minPrice");       //最低价格
			String maxPrice = request.getParameter("maxPrice");       //最高价格
			String tagId = request.getParameter("tagId");       //“”不限  擅长多个，隔开
			
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
		
		
			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
			Guide guide = new Guide();
			if (StringUtils.isNotBlank(cityid)) {
				guide.setCityid(Integer.parseInt(cityid));
			}
			guide.setCityName(cityName);
			List<String> tagIds = new ArrayList<>();
			if(StringUtils.isNotBlank(tagId)){
				String[] a = tagId.split(",");
				for(String b:a){
					tagIds.add(b);
				}
				guide.setTagIds(tagIds);
			}
			guide.setGuideAge(guideAge);
			guide.setGuideSex(guideSex);
			guide.setMinPrice(minPrice);
			guide.setMaxPrice(maxPrice);
			Page<Guide> page=new Page<Guide>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);		
			Page<Guide> page1 = guideService.findGuideScreenList(page, guide);		
			List<Guide> guideList=page1.getList();
			
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", guideList);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取筛选列表成功");  
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
    *导游详情接口
    * @param request
    * @param response
    * @param model
    * @return
    */
	@ResponseBody
	@RequestMapping(value = "guideDetails")
	public AjaxJson guideDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");//1.游客 2.会员
			String uid = request.getParameter("uid");
			String time = request.getParameter("time");
			String key = request.getParameter("key");
			String guideId=request.getParameter("guideId");//导游Id
			if (StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2) {
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
			if (StringUtils.isBlank(guideId)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideId传参为空");
				return ajaxJson;
			}
			Guide guide1 = new Guide();
			guide1.setId(guideId);
			guide1.setMemberid(uid);//uid判断是否收藏
			Guide guide=guideService.getGuideInfo(guide1);
			
			ajaxJson.getBody().put("guide", guide);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取导游详情成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	/**
	 * 导游推荐路线接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "guideRoute")
	@ResponseBody
	public AjaxJson guideRoute(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String languageid = request.getParameter("languageid");//语言id
		String  guideId=request.getParameter("guideId");//岛游ID
		String pageNo = request.getParameter("pageNo");//页码
		
		if (StringUtils.isNotBlank(type)&&Integer.parseInt(type)==2) {
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
		if (StringUtils.isBlank(guideId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guideId传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(languageid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(pageNo)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("pageNo传参为空");
			return ajaxJson;
		}
		
		try {
            GuideRoute guideRoute =new GuideRoute();
            guideRoute.setLanguageid(Integer.parseInt(languageid));
            guideRoute.setGuideid(Integer.parseInt(guideId));
			Page<GuideRoute> page=new Page<GuideRoute>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(2);		
			Page<GuideRoute> page1 = guideRouteService.findGuideRouteList(page, guideRoute);		
			List<GuideRoute> guideRouteList=page1.getList();
			
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", guideRouteList);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取导游推荐路线列表成功");  
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
	 * 导游日期详情接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getGuideDateDetails")
	@ResponseBody
	public AjaxJson getGuideDateDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String guideid = request.getParameter("guideid");       //路线id
			String priceDate = request.getParameter("priceDate");       //路线id
		
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
			if (StringUtils.isBlank(guideid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideid传参为空");
				return ajaxJson;
			}
		
			
			GuideDate guideDate =new GuideDate();
			
			Calendar c = Calendar.getInstance();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
			Date bt=sdf.parse(priceDate); 
			c.setTime(bt);
			c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天   
			c.add(Calendar.MONTH, -1);
			Date beginDate = c.getTime();
			c.add(Calendar.MONTH, +4);
			Date endDate = c.getTime();
			guideDate.setBeginDate(beginDate);
			guideDate.setEndDate(endDate);
			c.setTime(new Date());
			guideDate.setStartDate(c.getTime());
			guideDate.setGuideid(Integer.parseInt(guideid));
			List<GuideDate> list = guideDateService.getDateList(guideDate);
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取导游日期详情成功");  
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
	 * 导游路线日期详情接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getGuideRouteDateDetails")
	@ResponseBody
	public AjaxJson getGuideRouteDateDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String guideRouteid = request.getParameter("guideRouteid");       //路线id
			String priceDate = request.getParameter("priceDate");       //路线id
		
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
			if (StringUtils.isBlank(guideRouteid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideRouteid传参为空");
				return ajaxJson;
			}
		
			GuideRouteDate guideRouteDate=new GuideRouteDate();
			
			Calendar c = Calendar.getInstance();
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
			Date bt=sdf.parse(priceDate); 
			c.setTime(bt);
			c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天   
			c.add(Calendar.MONTH, -1);
			Date beginDate = c.getTime();
			c.add(Calendar.MONTH, +4);
			Date endDate = c.getTime();
			guideRouteDate.setBeginDate(beginDate);
			guideRouteDate.setEndDate(endDate);
			c.setTime(new Date());
			guideRouteDate.setStartDate(c.getTime());
			
			guideRouteDate.setRouteid(Integer.parseInt(guideRouteid));
			List<GuideRouteDate> list = guideRouteDateService.getDateList(guideRouteDate);
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList<>());
			}
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取导游路线日期详情成功");  
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
	 * 当地玩家确认订单接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveGuideOrder")
	@ResponseBody
	public AjaxJson saveGuideOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String contactsName = request.getParameter("contactsName");       //联系人姓名
			String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
			String remark = request.getParameter("remark");       //备注
			String startDate = request.getParameter("startDate");       //开始时间
			String endDate = request.getParameter("endDate");       //结束时间
			String adultNum = request.getParameter("adultNum");       //大人
			String childNum = request.getParameter("childNum");       //小孩
			String guideid=request.getParameter("guideid");//导游Id
			String guideRouteid=request.getParameter("guideRouteid");//导游路线Id
			String insuranceid = request.getParameter("insuranceid");       //保险id
			/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
			certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
			String orderMember = request.getParameter("orderMember");       //出游人信息
			
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
			if (StringUtils.isBlank(guideid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideid传参为空");
				return ajaxJson;
			}

			if (StringUtils.isBlank(guideRouteid)) {
				if (StringUtils.isBlank(endDate)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("endDate传参为空");
					return ajaxJson;
				}
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
			if (StringUtils.isBlank(orderMember)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderMember传参为空");
				return ajaxJson;
			}
			
			
			//获取导游和导游路线
			Guide guide = guideService.get(guideid);
			GuideRoute guideRoute = null;
			if(StringUtils.isNotBlank(guideRouteid)){
				guideRoute=guideRouteService.get(guideRouteid);
			}
			OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
			OrderGuide orderGuide = new OrderGuide();
			OrderInsurance orderInsurance = new OrderInsurance(); 
			
			orderSys.setType(8);		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
										//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
			orderSys.setFatherid(0);		// 父id
			orderSys.setMemberid(Integer.parseInt(uid));      //下单人
			orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

			orderSys.setContactsName(contactsName);	//联系人姓名
			orderSys.setContactsMobile(contactsMobile);	//联系人电话
			orderSys.setRemark(remark);			//备注
			orderSys.setPeopleNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));
			if(guideRoute!=null){
				orderSys.setTitle(guideRoute.getTitle()); //订单标题
			}else{
				orderSys.setTitle("DY-"+guide.getRealName()); //订单标题
			}
			orderSys.setAdultNum(Integer.parseInt(adultNum));		// 大人数量
			orderSys.setChildNum(Integer.parseInt(childNum));		// 孩子数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			orderSys.setBeginDate(bt);       //开始时间
			Date et = null;
			if(StringUtils.isNotBlank(endDate)){
				et=sdf.parse(endDate); 
			}else{
				//选择了路线，计算出结束时间
				Calendar c = Calendar.getInstance();
				c.setTime(bt);
				c.add(Calendar.DAY_OF_MONTH, +guideRoute.getDay());
				et = c.getTime();
				orderSys.setEndDate(et);
			}
			
			
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd"); 
			String bt1=sdf1.format(new Date()); 
			String orderNo = "DY-"+guide.getId()+"-"+bt1+"[]";
			if(guideRoute!=null){
				orderNo += "-"+guideRoute.getId();
			}
			orderGuide.setOrderNo(orderNo);  //订单号,后面替换[]
			orderGuide.setStatus(1); //'订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过',
			
			if(guideRoute!=null){
				orderGuide.setName(guideRoute.getTitle()); //'订单标题',
				orderGuide.setGuideRouteId(guideRoute.getId()); //'导游路线id',
			}else{
				orderGuide.setName("DY-"+guide.getRealName()); //'订单标题'
			}
			orderGuide.setBeginDate(bt);//'开始时间',
			orderGuide.setEndDate(et); //'结束时间',
			orderGuide.setType(2); //' 1.包车租车 2.当地玩家 3.酒店 4.保险 5.定制',
			orderGuide.setTypeid(Integer.parseInt(guide.getId())); //'对应的导游ID',
			orderGuide.setContactsName(contactsName); //'联系人姓名',
			orderGuide.setContactsMobile(contactsMobile); //'联系人电话',
			orderGuide.setRemark(remark); //'备注',
			orderGuide.setUseDate(bt); //'使用时间',
			orderGuide.setAdultNum(adultNum); //'大人数量',
			orderGuide.setChildNum(childNum); //'孩子数量',
			orderGuide.setEndCity(guide.getCityid().toString()); //'目的地',
			orderGuide.setMemberId(uid); //'下单人Id',
			orderGuide.setLanguageid(Integer.parseInt(languageid)); //'语言id',
			
			if(guideRoute!=null){//选择了路线
//				Double guidePrice =  guideRoute.getPrice()*guideRoute.getDay()
//						*(Integer.parseInt(adultNum)+Integer.parseInt(childNum))
//						+guide.getPrice()*guideRoute.getDay();
				BigDecimal guidePrice =  guideRoute.getPrice().multiply(new BigDecimal(guideRoute.getDay()))
						.multiply(new BigDecimal(Integer.parseInt(adultNum)+Integer.parseInt(childNum)))
						.add(guide.getPrice().multiply(new BigDecimal(guideRoute.getDay())))
						.setScale(2,BigDecimal.ROUND_HALF_UP);
				orderGuide.setPrice(guidePrice); //'金额',
			}else{//没选择路线
				long d1 = et.getTime();
				long d2 = bt.getTime();
				Integer guideDay=(int)(d1-d2)/(1000 * 60 * 60 * 24);
				BigDecimal guidePrice =  guide.getPrice().multiply(new BigDecimal(guideDay));
				orderGuide.setPrice(guidePrice); //'金额',
			}
			
			if(StringUtils.isNotBlank(insuranceid)){
				//获取保险信息
				Insurance insurance = insuranceService.get(insuranceid);
				
				orderInsurance.setName(insurance.getName());		// 保险名称
				orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));		// 保险ID
				orderInsurance.setAgentid(insurance.getAgentid());		// 供应商ID
				
				String orderNo1 = "BX-"+insurance.getAgentid()+"-"+bt1+"[]"+"-"+insurance.getId();
				orderInsurance.setOrderNo(orderNo1);		// 订单号，后面替换[]
				
				orderInsurance.setMemberId(Integer.parseInt(uid));		// 下单人id
				BigDecimal orderInsurancePrice = new BigDecimal(Integer.parseInt(adultNum)+Integer.parseInt(childNum))
						.multiply(insurance.getPrice())
						.setScale(2,BigDecimal.ROUND_HALF_UP);
				orderInsurance.setPrice(orderInsurancePrice);		// 订单价格
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
				    orderMember1.setType(6);		//1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
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
			orderSys.setPrice(orderGuide.getPrice().add(insurancePrice));//主订单总价
			
			String orderid = orderSysService.saveGuideOrder(orderSys,orderGuide,orderInsurance,orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("提交订单成功");  
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
	 * 获取导游价格设置和退款设置接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getGuidePrice")
	@ResponseBody
	public AjaxJson getGuidePrice(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
			if (memberStatus != 0) {
				ajaxJson.getBody().put("memberStatus", memberStatus);
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_7);
				ajaxJson.setMsg(AppErrorUtils.error_7_desc);
				return ajaxJson;
			}
			try {
				Guide guide = guideService.findGuide(uid);
				GuideTime guideTime=new GuideTime();
				guideTime.setGuideid(Integer.parseInt(guide.getId()));
				List<GuideTime> list = guideTimeService.getGuideTime(guideTime);
				if(list!=null&&list.size()>0){
					ajaxJson.getBody().put("list", list);
				}else{
					ajaxJson.getBody().put("list", new ArrayList<>());
				}
				//获取导游类型
				String guideType =  guideTimeService.getGuideType(uid);
				ajaxJson.getBody().put("guideType", guideType);
				MemberRefund memberRefund = new MemberRefund();
				memberRefund.setTypeId(Integer.parseInt(guide.getId()));
				List<MemberRefund> list2 = memberRefundService.getGuideRefund(memberRefund);
				if(list2!=null&&list2.size()>0){
					ajaxJson.getBody().put("list2", list2);
				}else{
					ajaxJson.getBody().put("list2", new ArrayList<>());
				}
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取导游价格设置和退款设置成功");  
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
	 * 修改导游价格设置和退款设置接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateGuidePrice")
	@ResponseBody
	public AjaxJson updateGuidePrice(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String guideLists = request.getParameter("guideLists");       //导游价格设置
			if (StringUtils.isBlank(guideLists)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideLists传参为空");
				return ajaxJson;
			}
			if(Integer.parseInt(type)!=2){
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("type传参错误");
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
			try {
				Guide guide = guideService.findGuide(uid);
				List<GuideTime> list = new ArrayList<>();
				List<MemberRefund> list2 = new ArrayList<>();
				JSONArray jsonArray = JSONArray.fromObject(guideLists);
				for(int i=0 ;i<jsonArray.size(); i++){
					//取出数组第i个元素
					JSONObject jpro = jsonArray.getJSONObject(i);
					GuideTime guideTime = new GuideTime();
					guideTime.setGuideid(Integer.parseInt(guide.getId()));
					if(jpro.getBoolean("flag")){
						JSONObject guideTimeJson = jpro.getJSONObject("guideTime");
						if(guideTimeJson != null){
//							String id = guideTimeJson.getString("id");
							String guideType = jpro.getString("id");
							String dateType = guideTimeJson.getString("dateType");
							String beginDate = guideTimeJson.getString("beginDate");
							String endDate = guideTimeJson.getString("endDate");
							String weekDate = guideTimeJson.getString("weekDate");
							String dayDate = guideTimeJson.getString("dayDate");
							String price = guideTimeJson.getString("price");
							if(StringUtils.isBlank(guideType)||StringUtils.isBlank(dateType)||StringUtils.isBlank(beginDate)||
									StringUtils.isBlank(endDate)||StringUtils.isBlank(price)){
								ajaxJson.setSuccess(false);
								ajaxJson.setErrorCode("-1");
								ajaxJson.setMsg("导游价格设置传参为空");
								return ajaxJson;
							}
//							guideTime.setId(id);
							guideTime.setType(Integer.parseInt(guideType));
							guideTime.setDateType(Integer.parseInt(dateType));
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
							Date bt=sdf.parse(beginDate);
							Date et=sdf.parse(endDate);
							guideTime.setBeginDate(bt);
							guideTime.setEndDate(et);
							guideTime.setWeekDate(weekDate);
							guideTime.setDayDate(dayDate);
							guideTime.setPrice(new BigDecimal(price));
							list.add(guideTime);
						}

						String memberRefundListString = jpro.getString("memberRefundList");

						if(StringUtils.isNotBlank(memberRefundListString)){
							JSONArray memberRefundListJSONArray  = JSONArray.fromObject(memberRefundListString);
							for(int k=0 ;k<memberRefundListJSONArray.size(); k++){
								JSONObject memberRefundListJson = memberRefundListJSONArray.getJSONObject(k);
								MemberRefund memberRefund = new MemberRefund();
								memberRefund.setType(1);
								memberRefund.setTypeId(Integer.parseInt(guide.getId()));
								String refundType = jpro.getString("id");
								String refundDay = memberRefundListJson.getString("refundDay");
								String refundNum = memberRefundListJson.getString("refundNum");
								if(StringUtils.isBlank(refundType)||StringUtils.isBlank(refundDay)||StringUtils.isBlank(refundNum)){
									ajaxJson.setSuccess(false);
									ajaxJson.setErrorCode("-1");
									ajaxJson.setMsg("导游退款设置传参为空");
									return ajaxJson;
								}
								memberRefund.setRefundType(Integer.parseInt(refundType) + 10);
								memberRefund.setRefundDay(Integer.parseInt(refundDay));
								memberRefund.setRefundNum(Double.valueOf(refundNum));
								if(Integer.parseInt(refundType)==1){
									memberRefund.setRefundContent("当地玩家");
								}else  if(Integer.parseInt(refundType)==1){
									memberRefund.setRefundContent("旅游定制-导游");
								}else  if(Integer.parseInt(refundType)==3){
									memberRefund.setRefundContent("旅游定制-司兼导");
								}else  if(Integer.parseInt(refundType)==4){
									memberRefund.setRefundContent("包车/租车-导游");
								}else  if(Integer.parseInt(refundType)==5){
									memberRefund.setRefundContent("包车/租车-司兼导");
								}
								memberRefund.setCreateDate(new Date());
								list2.add(memberRefund);
							}
						}
					}
				}
				guideService.updateGuidePrice(list,list2, guide);
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("修改导游价格设置和退款设置成功");  
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
	 * 获取导游添加的路线规划列表接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getGuideRoute")
	@ResponseBody
	public AjaxJson getGuideRoute(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();
		try {	
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //密钥，登陆后返回
			String pageNo = request.getParameter("pageNo");       //页码
			if (StringUtils.isBlank(pageNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageNo传参为空");
				return ajaxJson;
			}
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
			
			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
			GuideRoute guideRoute=new GuideRoute();
			guideRoute.setMemberid(Integer.parseInt(uid));
			guideRoute.setLanguageid(Integer.parseInt(languageid)); 
			
			Page<GuideRoute> page=new Page<GuideRoute>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);	
			Page<GuideRoute> page1 = guideRouteService.getGuideRoute(page, guideRoute);
			
			List<GuideRoute> list=page1.getList();
			
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", null);
			}else{
				ajaxJson.getBody().put("list", list);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			
			
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取导游添加的路线规划列表成功");  
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
	 * 获取导游的路线规划详情接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getGuideRouteDetail")
	@ResponseBody
	public AjaxJson getGuideRouteDetail(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String guideRouteid = request.getParameter("guideRouteid");       //导游路线id
			
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
			if (StringUtils.isBlank(guideRouteid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideRouteid传参为空");
				return ajaxJson;
			}
			try {
				GuideRoute guideRoute = guideRouteService.getGuideRouteDetail(guideRouteid);
				
				ajaxJson.getBody().put("guideRoute", guideRoute);
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取导游的路线规划详情成功");  
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
	 * 添加、编辑导游路线规划接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateGuideRoute")
	@ResponseBody
	public AjaxJson updateGuideRoute(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //密钥，登陆后返回
			String guideRouteid = request.getParameter("guideRouteid");//导游路线id
			String title = request.getParameter("title");  			//标题
			String photo = request.getParameter("photo");  			//封面
			String scenice = request.getParameter("scenice");  			//景点id，多个用，隔开
			String details = request.getParameter("details");  			//详情
			String miniNum = request.getParameter("miniNum");  			//最低人数
			String dateType = request.getParameter("dateType");  			//日期类型1.所有日期2.按星期3.按号数
			String beginDate = request.getParameter("beginDate");  			//开始时间
			String endDate = request.getParameter("endDate");  			//结束时间
			String weekDate = request.getParameter("weekDate");  			//选择的星期
			String dayDate = request.getParameter("dayDate");  			//选择的号数
			String price = request.getParameter("price");  			//价格
			String day = request.getParameter("day");  			//天数
			
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
			if (StringUtils.isBlank(day)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("day传参为空");
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
			if (StringUtils.isBlank(title)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("title传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(photo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("photo传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(scenice)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("scenice传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(details)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("details传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(miniNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("miniNum传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(dateType)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("dateType传参为空");
				return ajaxJson;
			}
			if(Integer.parseInt(dateType)==2){
				if (StringUtils.isBlank(weekDate)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("weekDate传参为空");
					return ajaxJson;
				}
			}else if(Integer.parseInt(dateType)==3){
				if (StringUtils.isBlank(dayDate)) {
					ajaxJson.setSuccess(false);
					ajaxJson.setErrorCode(AppErrorUtils.error_2);
					ajaxJson.setMsg("dayDate传参为空");
					return ajaxJson;
				}
			}
			if (StringUtils.isBlank(beginDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("beginDate传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(endDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("endDate传参为空");
				return ajaxJson;
			}
			
			if (StringUtils.isBlank(price)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("price传参为空");
				return ajaxJson;
			}
			try {
				GuideRoute guideRoute=new GuideRoute();
				guideRoute.setId(guideRouteid);
				guideRoute.setLanguageid(Integer.parseInt(languageid));
				guideRoute.setMemberid(Integer.parseInt(uid));
				Guide guide = guideService.findGuide(uid);
				guideRoute.setGuideid(Integer.parseInt(guide.getId()));
				guideRoute.setCityid(guide.getCityid());
				
				guideRoute.setTitle(title);
				guideRoute.setPhoto(photo);
				guideRoute.setScenice(scenice);
				guideRoute.setDetails(details);
				guideRoute.setDay(Integer.parseInt(day));
				guideRoute.setMiniNum(Integer.parseInt(miniNum));
				guideRoute.setDateType(Integer.parseInt(dateType));
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
				 Date bt=sdf.parse(beginDate); 
				 Date et=sdf.parse(endDate); 
				guideRoute.setBeginDate(bt);
				guideRoute.setEndDate(et);
				guideRoute.setWeekDate(weekDate);
				guideRoute.setDayDate(dayDate);
				guideRoute.setPrice(new BigDecimal(price));
				guideRoute.setCreateDate(new Date());
				
				guideRouteService.saveGuideRoute(guideRoute);
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("添加、编辑导游路线规划成功");  
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
	 * 删除路线规划接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "deleteGuideRoute")
	@ResponseBody
	public AjaxJson deleteGuideRoute(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //密钥，登陆后返回
			String guideRouteid = request.getParameter("guideRouteid");//导游路线id
			
			
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
			if (StringUtils.isBlank(guideRouteid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideRouteid传参为空");
				return ajaxJson;
			}
			try {
				GuideRoute guideRoute=new GuideRoute();
				guideRoute.setId(guideRouteid);
				
				guideRouteService.deleteGuideRoute(guideRoute);
				
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg(" 删除路线规划成功");  
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
	 * 获取导游当地的景点列表接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getCityScenic")
	@ResponseBody
	public AjaxJson getCityScenic(HttpServletRequest request, HttpServletResponse response, Model model) {

		AjaxJson ajaxJson = new AjaxJson();
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			
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
			try {
				Guide guide = guideService.findGuide(uid);
				ScenicSpot scenicSpot = new ScenicSpot();
				scenicSpot.setCityId(guide.getCityid());
				scenicSpot.setLanguageId(Integer.parseInt(languageid));
				List<ScenicSpot> list =scenicSpotService.getCityScenic(scenicSpot);
				if(list!=null&&list.size()>0){
					ajaxJson.getBody().put("list", list);
				}else{
					ajaxJson.getBody().put("list", new ArrayList<>());
				}
				
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("获取导游当地的景点列表成功");  
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
	

package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
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
import java.util.List;


@Controller
@RequestMapping(value = "${adminPath}/interface/scenic")
public class AppScenicController {
	@Autowired
	private AppUtils appUtils;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotTicketService scenicSpotTicketService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private ComCityService comCityService;
	
	

	/**
	* @Title: getScenicNum
	* @Description: 获取景点门票数量
	* @author  彭善智
	* @Data 2018年10月19日下午4:12:55
	*/
	@RequestMapping(value = "getScenicNum")
	@ResponseBody
	public AjaxJson getScenicNum(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String type = request.getParameter("type");      //1.游客 2.会员
		String uid = request.getParameter("uid");      //会员ID，登陆后返回
		String time = request.getParameter("time");		//时间，登陆后返回
		String key = request.getParameter("key");       //密钥，登陆后返回
		String scenicSpotTicketId = request.getParameter("scenicSpotTicketId");//景点门票ID
		String scenicDate = request.getParameter("scenicDate");//时间
		if(!StringUtils.isBlank(type)&&Integer.parseInt(type)==2){
			if (StringUtils.isBlank(uid)){
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
		try {
			List<ScenicSpotTicket> list = scenicSpotTicketService.getScenicNum(scenicSpotTicketId,scenicDate);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取景点门票数量成功");  
			return ajaxJson;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_45);
			ajaxJson.setMsg(AppErrorUtils.error_45_desc);
			return ajaxJson;
		}
	}
	
	/**
	 * 根据语言获取城市和城市景点数量接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getCityScenicNum")
	@ResponseBody
	public AjaxJson getCityScenicNum(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
	
		String languageid = request.getParameter("languageid");//语言id
		
		
		if (StringUtils.isBlank(languageid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageid传参为空");
			return ajaxJson;
		}
		try {
			ComCity comCity = new ComCity();	
			comCity.setLanguageId(languageid);
			List<ComCity> list = comCityService.getCityScenicNum(comCity);					
			if(list!=null&&list.size()>0){
				ajaxJson.getBody().put("list", list);
			}else{
				ajaxJson.getBody().put("list", new ArrayList());
			}
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取城市和城市景点数量成功");  
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
	 * 景点名称关键字匹配搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectScenicTitle")
	@ResponseBody
	public AjaxJson selectScenicTitle(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String cityid = request.getParameter("cityid");       //城市id
			String pageNo = request.getParameter("pageNo");//页码
			
			String name = request.getParameter("name");       //名称关键字
		
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
			if (StringUtils.isBlank(pageNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageNo传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(name)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("name传参为空");
				return ajaxJson;
			}
			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
			ScenicSpot scenicSpot = new ScenicSpot();	
			
			scenicSpot.setLanguageId(Integer.parseInt(languageid));
			if (StringUtils.isNotBlank(cityid)) {
				scenicSpot.setCityId(Integer.parseInt(cityid));
			}
			scenicSpot.setName(name);

			Page<ScenicSpot> page=new Page<ScenicSpot>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);


			Page<ScenicSpot> page1 = scenicSpotService.selectScenicTitle(page, scenicSpot);
		
			List<ScenicSpot> routeList=page1.getList();
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList());
			}else{
				ajaxJson.getBody().put("list", routeList);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("景点名称关键字匹配搜索成功");  
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
	 * 景点列表搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectScenicList")
	@ResponseBody
	public AjaxJson selectScenicList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String cityid = request.getParameter("cityid");       //城市id
			String pageNo = request.getParameter("pageNo");//页码
			String labelAttrid = request.getParameter("labelAttrid");       //属性
	
			String orderByType = request.getParameter("orderByType");//排序方式1.好评2.价格降序3.价格升序
		
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
			
			ScenicSpot scenicSpot = new ScenicSpot();	
			scenicSpot.setLanguageId(Integer.parseInt(languageid));
			if (StringUtils.isNotBlank(cityid)) {
				scenicSpot.setCityId(Integer.parseInt(cityid));
			}
			scenicSpot.setLabelAttrid(labelAttrid);
			List<String> attrList = new ArrayList<>();
			if(StringUtils.isNotBlank(labelAttrid)){
				String[] a = labelAttrid.split(",");
				for(String b:a){
					attrList.add(b);
				}
				scenicSpot.setAttrList(attrList);
			}
			Page<ScenicSpot> page=new Page<ScenicSpot>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(pageSize);
			if(StringUtils.isNotBlank(orderByType)){
				scenicSpot.setOrderByType(Integer.parseInt(orderByType));
			}
			Page<ScenicSpot> page1 = scenicSpotService.selectScenicList(page, scenicSpot);
		
			List<ScenicSpot> routeList = page1.getList();
			for(ScenicSpot s : routeList){
				if(StringUtils.isNotBlank(s.getImgUrl())){
					s.setImgUrl(s.getImgUrl().split(",")[0]);
				}
			}
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList());
			}else{
				ajaxJson.getBody().put("list", routeList);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("景点列表搜索成功");  
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
	 * 景点详情接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getScenicDetails")
	@ResponseBody
	public AjaxJson getScenicDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String scenicid = request.getParameter("scenicid");       //景点id
			
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
			if (StringUtils.isBlank(scenicid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("scenicid传参为空");
				return ajaxJson;
			}
			ScenicSpot scenicSpot1 = new ScenicSpot();
			scenicSpot1.setId(scenicid);
			scenicSpot1.setMemberid(uid);
			ScenicSpot scenicSpot = scenicSpotService.getScenicDetails(scenicSpot1);
			//获取景点门票信息
			ScenicSpotTicket scenicSpotTicket = new ScenicSpotTicket();
			scenicSpotTicket.setScenicid(Integer.parseInt(scenicid));
			List<ScenicSpotTicket> list= scenicSpotTicketService.getList(scenicSpotTicket);
			
			//修改查看数量
			ScenicSpot scenicSpot2=new ScenicSpot();
			scenicSpot2.setId(scenicid);
			scenicSpotService.changeLookNum(scenicSpot2);
			ajaxJson.getBody().put("scenicSpot", scenicSpot);
			ajaxJson.getBody().put("list", list);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取景点详情成功");  
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
	 * 景点添加订单接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "saveScenicOrder")
	@ResponseBody
	public AjaxJson saveScenicOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
	
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String scenicid = request.getParameter("scenicid");       //景点id

			String contactsName = request.getParameter("contactsName");       //联系人姓名
			String contactsMobile = request.getParameter("contactsMobile");       //联系人电话
			String remark = request.getParameter("remark");       //备注
			String startDate = request.getParameter("startDate");       //开始时间
			String adultNum = request.getParameter("adultNum");       //大人
			String childNum = request.getParameter("childNum");       //小孩
			
			String insuranceid = request.getParameter("insuranceid");       //保险id
			/*出游人信息集合[{chineseName: "",englishName : "", certType: "",certNo : "", 
			certValidDate: "", birthday: "", area: "", mobile: "",type : "" },{},{}]*/
			String orderMember = request.getParameter("orderMember");       //出游人信息
			
			String ticketid = request.getParameter("ticketid"); //门票id
			String ticketNum = request.getParameter("ticketNum");       //门票数量
			
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
			if (StringUtils.isBlank(scenicid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("scenicid传参为空");
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
//			if (StringUtils.isBlank(remark)) {
//				ajaxJson.setSuccess(false);
//				ajaxJson.setErrorCode(AppErrorUtils.error_2);
//				ajaxJson.setMsg("remark传参为空");
//				return ajaxJson;
//			}
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
			
			if (StringUtils.isBlank(ticketid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("ticketid传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(ticketNum)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("ticketNum传参为空");
				return ajaxJson;
			}
			
//			if (StringUtils.isBlank(insuranceid)) {
//				ajaxJson.setSuccess(false);
//				ajaxJson.setErrorCode(AppErrorUtils.error_2);
//				ajaxJson.setMsg("insuranceid传参为空");
//				return ajaxJson;
//			}
			if (StringUtils.isBlank(orderMember)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("orderMember传参为空");
				return ajaxJson;
			}
			//获取景点
			ScenicSpot scenicSpot = scenicSpotService.get(scenicid);
			//查询门票信息
			ScenicSpotTicket scenicSpotTicket =scenicSpotTicketService.get(ticketid);
			
			OrderSys orderSys = new OrderSys();
			orderSys.setPayOrderNo(CodeGenUtils.getNowDate());
			OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
			OrderInsurance orderInsurance = new OrderInsurance();
			
			orderSys.setType(7);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
							//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
			orderSys.setFatherid(0);		// 父id
			orderSys.setMemberid(Integer.parseInt(uid));      //下单人
			orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

			orderSys.setContactsName(contactsName);	//联系人姓名
			orderSys.setContactsMobile(contactsMobile);	//联系人电话
			orderSys.setRemark(remark);			//备注
			orderSys.setPeopleNum(Integer.parseInt(adultNum)+Integer.parseInt(childNum));
			orderSys.setTitle(scenicSpot.getName());			//订单标题
			orderSys.setAdultNum(Integer.parseInt(adultNum));		// 大人数量
			orderSys.setChildNum(Integer.parseInt(childNum));		// 孩子数量
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date bt=sdf.parse(startDate); 
			orderSys.setBeginDate(bt);       //开始时间
			
			
			
			orderScenicSpot.setName(scenicSpot.getName());		// 订单标题
			orderScenicSpot.setSecnicSpotId(Integer.parseInt(scenicid));// 景点id
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMdd"); 
			String bt1=sdf1.format(new Date()); 
			String orderNo = "JD-"+scenicSpot.getAgentid()+"-"+bt1+"[]"+"-"+scenicSpot.getId();
			orderSys.setOrderNo(orderNo);
			orderScenicSpot.setOrderNo(orderNo);  //订单号,后面替换[]
			orderScenicSpot.setProductName(scenicSpot.getName());		// 订单产品名称
			orderScenicSpot.setMemberId(Integer.parseInt(uid));		// 下单人id
			orderScenicSpot.setUseDate(bt);		// 使用时间
			
			BigDecimal price =new BigDecimal(ticketNum).multiply(scenicSpotTicket.getPrice())
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			orderScenicSpot.setPrice(price);		// 订单价格
			
			orderScenicSpot.setStatus(1);		// 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
			orderScenicSpot.setContacts(contactsName);		// 联系人
			orderScenicSpot.setContactMobile(contactsMobile);		// 联系电话
			orderScenicSpot.setContactRemark(remark);		// 备注
			orderScenicSpot.setLanguageId(Integer.parseInt(languageid));	// 语言id
			orderScenicSpot.setNum(Integer.parseInt(ticketNum));		// 预定数量
			
			orderScenicSpot.setSceniciCompany(scenicSpot.getSceniciCompany());//景点代理商 
			
			orderScenicSpot.setAdultNum(adultNum);
			orderScenicSpot.setChildNum(childNum);
			orderScenicSpot.setEndCity(scenicSpot.getCityId().toString());
			orderScenicSpot.setAddress(scenicSpot.getAddress()); //景点地址
			orderScenicSpot.setContent(scenicSpot.getContent()); //景点特色详情
			orderScenicSpot.setTicketName(scenicSpotTicket.getName()); //门票名称
			orderScenicSpot.setTicketid(Integer.parseInt(ticketid)); //门票id
			
			if(StringUtils.isNotBlank(insuranceid)){
				//获取保险信息
				Insurance insurance = insuranceService.get(insuranceid);
				
				orderInsurance.setName(insurance.getName());		// 保险名称
				orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));		// 保险ID
				orderInsurance.setAgentid(insurance.getAgentid());		// 供应商ID
				
				String orderNo1 = "BX-"+insurance.getAgentid()+"-"+bt1+"[]"+"-"+insurance.getId();
				orderInsurance.setOrderNo(orderNo1);		// 订单号，后面替换[]
				
				orderInsurance.setMemberId(Integer.parseInt(uid));		// 下单人id

				orderInsurance.setPrice((new BigDecimal(adultNum).add(new BigDecimal(childNum))).multiply(insurance.getPrice()));		// 订单价格
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
				    Date certValidDate1 = sdf.parse(certValidDate);
				    orderMember1.setCertValidDate(certValidDate1);		// 有效期
				    Date birthday1 = sdf.parse(birthday);
				    orderMember1.setBirthday(birthday1);		// 出生年月
				    orderMember1.setArea(area);		// 区号
				    orderMember1.setMobile(mobile);		// 手机号
				    orderMember1.setLanguageId(Integer.parseInt(languageid));		// 语言id
				    orderMember1.setType(5);		//1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
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
			orderSys.setPrice(orderScenicSpot.getPrice().add(insurancePrice));//主订单总价
			
			String orderid = orderSysService.saveScenicOrder(orderSys,orderScenicSpot,orderInsurance,orderMemberList);

			ajaxJson.getBody().put("payOrderNo", orderSys.getPayOrderNo());
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("景点添加订单成功");  
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
	

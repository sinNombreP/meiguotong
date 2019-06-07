package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.citystrategyson.CityStrategySon;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcitytravel.ComCityTravel;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;
import com.jeeplus.modules.meiguotong.entity.comprotocol.ComProtocol;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.service.citystrategy.CityStrategyService;
import com.jeeplus.modules.meiguotong.service.citystrategyson.CityStrategySonService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comcitytravel.ComCityTravelService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.comnavigation.ComNavigationService;
import com.jeeplus.modules.meiguotong.service.comprotocol.ComProtocolService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
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
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping(value = "${adminPath}/interface/city")
public class AppComCityController {
	@Autowired
	private AppUtils appUtils;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ComCityService comCityService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private ComCityTravelService comCityTravelService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private ComCommentService comCommentService;
	@Autowired
	private CityStrategyService cityStrategyService;
	@Autowired
	private CityStrategySonService cityStrategySonService;
	@Autowired
	private ComNavigationService comNavigationService;
	@Autowired
	private ComProtocolService comProtocolService;
	
	/**
	 * 景点名称关键字匹配搜索接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectCity")
	@ResponseBody
	public AjaxJson selectCity(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String pageNo = request.getParameter("pageNo");//页码
			String name = request.getParameter("searchContent");       //搜索内容
		
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
			
			
			ComCity comCity = new ComCity();
			
			comCity.setLanguageId(languageid);
			comCity.setCityName(name);

			Page<ComCity> page=new Page<ComCity>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(Integer.valueOf(pageSize));


			Page<ComCity> page1 = comCityService.selectCityTitle(page, comCity);
		
			List<ComCity> cityList=page1.getList();
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", cityList);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("城市名称关键字匹配搜索成功");  
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
	 * 景点搜索下面的热门城市推荐
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "selectHotCity")
	@ResponseBody
	public AjaxJson selectHotCity(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String pageNo = request.getParameter("pageNo");//页码
			String pageSize = request.getParameter("pageSize");
		
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
			if (StringUtils.isBlank(pageSize)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageSize传参为空");
				return ajaxJson;
			}
			ComNavigation comNavigation = new ComNavigation();
			comNavigation.setLanguage(languageid);

			Page<ComNavigation> page=new Page<ComNavigation>();
			page.setPageNo(Integer.parseInt(pageNo));
			page.setPageSize1(Integer.valueOf(pageSize));

			Page<ComNavigation> page1 = comNavigationService.HotCity(page, comNavigation);
		
			List<ComNavigation> comNavigationList=page1.getList();
			int totalPage=page1.getTotalPage();
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", comNavigationList);
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取热门城市列表成功");  
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
	 * 城市详情接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getCityInfo")
	@ResponseBody
	public AjaxJson getCityInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String cityid = request.getParameter("cityid");       //景点id
			
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
			if (StringUtils.isBlank(cityid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("cityid传参为空");
				return ajaxJson;
			}
			ComCity comCity = new ComCity();
			comCity.setId(cityid);
			ComCity comCity1 = comCityService.getCityDetails(comCity);
			
			comCity1.setPhotoList(Arrays.asList(comCity1.getPhotoUrl().split(",")));
			
			ajaxJson.getBody().put("comCity", comCity1);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取城市详情成功");  
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
	 * 目的地详情页面 获取当地参团列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getOfferedList")
	@ResponseBody
	public AjaxJson getOfferedList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid");       //语言id
			String pageNo = request.getParameter("pageNo");//页码
			String pageSize = request.getParameter("pageSize");
			String id = request.getParameter("id");       //城市id
		
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
			if (StringUtils.isBlank(pageSize)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageSize传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(id)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("id传参为空");
				return ajaxJson;
			}
			
			ComCity comCity = comCityService.get(id);
			if(comCity==null) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_53);
				ajaxJson.setMsg(AppErrorUtils.error_53_desc);
				return ajaxJson;
			}
			if(comCity.getIsOffered()!=null&&comCity.getIsOffered()==2) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_52);
				ajaxJson.setMsg(AppErrorUtils.error_52_desc);
				return ajaxJson;
			}
			String strRouteId = comCity.getOfferenTop();
			if(StringUtils.isBlank(strRouteId)) {
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("当前城市暂无当地参团");  
				return ajaxJson;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("offerenTop", strRouteId);
			param.put("language", Integer.valueOf(languageid));
			param.put("sort", comCity.getOfferedSort());
			param.put("pageNumber", (Integer.parseInt(pageNo)-1)*Integer.valueOf(pageSize));
			param.put("pageSize", Integer.valueOf(pageSize));
			List<Route> routeList = routeService.findOfferedList(param);
			for (Route route : routeList) {
				route.setAttrList(Arrays.asList(route.getTagContent().split(",")));
				route.setTagContent(null);
				if(StringUtils.isNotBlank(route.getCarImg())){
					route.setImgLIst(Arrays.asList(route.getCarImg().split(",")));
				}
				route.setCarImg(null);
			}
			Integer totalPage = (int) Math.ceil((double)routeService.countOffered(param)/Double.valueOf(pageSize));
			if(Integer.parseInt(pageNo)>totalPage){
				ajaxJson.getBody().put("list", new ArrayList<>());
			}else{
				ajaxJson.getBody().put("list", routeList);
			}
			if(totalPage==0) {
				totalPage=1;
			}
			ajaxJson.getBody().put("totalPage", totalPage);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取当地参团列表成功");  
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
	 * 目的地详情页面 旅游定制
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getTravelCustomizationList")
	@ResponseBody
	public AjaxJson getTravelCustomizationList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			
			String id = request.getParameter("id");       //城市id
		
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
			if (StringUtils.isBlank(id)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("id传参为空");
				return ajaxJson;
			}
			ComCity comCity = comCityService.get(id);
			if(comCity==null) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_53);
				ajaxJson.setMsg(AppErrorUtils.error_53_desc);
				return ajaxJson;
			}
			if(comCity.getIsTourism()!=null&&comCity.getIsTourism()==2) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_52);
				ajaxJson.setMsg(AppErrorUtils.error_52_desc);
				return ajaxJson;
			}
			List<ComCityTravel> comCityTravelList = comCityTravelService.findComCityTravelByCityId3(Integer.valueOf(id));
			
			ajaxJson.getBody().put("list", comCityTravelList);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取旅游定制列表成功");
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
	 * 目的地景点
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getScenicSpotList")
	@ResponseBody
	public AjaxJson getScenicSpotList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			
			String id = request.getParameter("id");       //城市id
		
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
			if (StringUtils.isBlank(id)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("id传参为空");
				return ajaxJson;
			}
		
			ComCity comCity = comCityService.get(id);
			if(comCity==null) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_53);
				ajaxJson.setMsg(AppErrorUtils.error_53_desc);
				return ajaxJson;
			}
			//1综合排序  2  时间排序 3 订单量排序
			Integer sortNumber = comCity.getScenicSort();
			String scenicTop = comCity.getScenicTop();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("scenicTop", scenicTop);
			if(sortNumber==null||sortNumber==1) {
			}else if(sortNumber==2) {
				param.put("order", "create_date desc");
			}else if(sortNumber==3) {
				param.put("order", "finish_num desc");
			}
			List<ScenicSpot> scenicSpotList =  scenicSpotService.findScenicsByscenicTop2(param);
			//把图片放进list
			for (ScenicSpot scenicSpot : scenicSpotList) {
				String imgUrl = scenicSpot.getImgUrl();
				if(StringUtils.isBlank(imgUrl)) {
					continue;
				}
				List<String> imgList = new ArrayList<String>(Arrays.asList(imgUrl.split(",")));
				scenicSpot.setImgList(imgList);
				scenicSpot.setImgUrl(null);
			}
			ajaxJson.getBody().put("list", scenicSpotList);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取目的地景点成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	 * 当地玩家
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getPlayList")
	@ResponseBody
	public AjaxJson getPlayList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			
			String id = request.getParameter("id");       //城市id
			//String pageNo = request.getParameter("pageNo");
			//String pageSize = request.getParameter("pageSize");
		
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
			if (StringUtils.isBlank(id)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("id传参为空");
				return ajaxJson;
			}
			ComCity comCity = comCityService.get(id);
			if(comCity==null) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_53);
				ajaxJson.setMsg(AppErrorUtils.error_53_desc);
				return ajaxJson;
			}
			if(comCity.getIsPlayer()!=null&&comCity.getIsPlayer()==2) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_52);
				ajaxJson.setMsg(AppErrorUtils.error_52_desc);
				return ajaxJson;
			}
			String playIds = comCity.getPlayerTop();
			if(StringUtils.isBlank(playIds)) {
				ajaxJson.setSuccess(true);
				ajaxJson.setErrorCode("0");
				ajaxJson.setMsg("当前城市暂无当地玩家");  
				return ajaxJson;
			}
			/*if (StringUtils.isBlank(pageNo)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageNo传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(pageSize)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("pageSize传参为空");
				return ajaxJson;
			}*/
			String order = "";
			if(comCity.getPlayerSort()==null||comCity.getPlayerSort()==1) {
			}else if(comCity.getPlayerSort()==2) {
				order = "create_date desc";
			}else if(comCity.getPlayerSort()==3) {
				order = "finish_num desc";
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ids", playIds);
			param.put("order", order);
			/*Integer no = Integer.valueOf(pageNo);
			Integer size = Integer.valueOf(pageSize);
			param.put("pageNo", (no-1)*size);
			param.put("pageSize", size);*/
			List<Guide> playList = guideService.getPlayerList(param);
			for (Guide guide : playList) {
				List<String> list = new ArrayList<String>();
				for (String str : Arrays.asList(guide.getPhotoUrl().split(","))) {
					list.add(str);
				}
				guide.setPhotoUrlList(list);
				guide.setPhotoUrl(null);
				guide.setCreateDate(null);
				guide.setFinishNum(null);
			}
			ajaxJson.getBody().put("list", playList);
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取当地玩家列表成功");  
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
	 * 写城市攻略
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addStrategy")
	@ResponseBody
	public AjaxJson addStrategy(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String uid = request.getParameter("uid");
		String time = request.getParameter("time");
		String key = request.getParameter("key");
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String imgUrl = request.getParameter("imgUrl");
		String languageid = request.getParameter("languageid");
		String cityId = request.getParameter("cityId");
		
		String strategySon = request.getParameter("strategySon");
		
		Integer memberStatus = appUtils.keyIsTrue(uid, time, key);
		if (memberStatus != 0) {
			ajaxJson.getBody().put("memberStatus", memberStatus);
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_7);
			ajaxJson.setMsg(AppErrorUtils.error_7_desc);
			return ajaxJson;
		}
		if (StringUtils.isBlank(title)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("title传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(content)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("content传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(imgUrl)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("imgUrl传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(languageid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(cityId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("cityId传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(strategySon)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("strategySon传参为空");
			return ajaxJson;
		}
		try {
			CityStrategy cityStrategy = new CityStrategy();
			cityStrategy.setTitle(title);
			cityStrategy.setContent(content);
			cityStrategy.setImgUrl(imgUrl);
			cityStrategy.setMemberId(Integer.valueOf(uid));
			cityStrategy.setStatus(1);
			cityStrategy.setIsEssence(1);
			cityStrategy.setLanguageId(Integer.valueOf(languageid));
			cityStrategy.setCreateDate(new Date());
			cityStrategy.setCityId(Integer.valueOf(cityId));
			cityStrategyService.save(cityStrategy);
			Integer strategyId = Integer.valueOf(cityStrategy.getId());
			
			JSONArray jsonArray = JSONArray.fromObject(strategySon); 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			for(int i=0;i<jsonArray.size();i++){
				CityStrategySon cityStrategySon = new CityStrategySon();
					//取出数组第i个元素 
				    JSONObject jpro = jsonArray.getJSONObject(i);
				    String secnicInfo = jpro.getString("secnicInfo");
				    String playTime = jpro.getString("playTime");
				    String hotelInfo = jpro.getString("hotelInfo");
				    String cityInfo = jpro.getString("cityInfo");
				    
				    Date playTime1=sdf.parse(playTime);
				    cityStrategySon.setSecnicInfo(secnicInfo);
				    cityStrategySon.setPlayTime(playTime1);
				    cityStrategySon.setHotelInfo(hotelInfo);
				    cityStrategySon.setCityInfo(cityInfo);
				    cityStrategySon.setStrategyId(strategyId);
				    cityStrategySon.setLanguageId(Integer.valueOf(languageid));
				    cityStrategySon.setStatus(1);
				    cityStrategySon.setCreateDate(new Date());
				    cityStrategySonService.save(cityStrategySon);
			}
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("提交城市攻略成功");
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
	 * 查看城市攻略接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findStrategy")
	@ResponseBody
	public AjaxJson findStrategy(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String id = request.getParameter("id");       //攻略id
			String languageid = request.getParameter("languageid");       //攻略id
			
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
			if (StringUtils.isBlank(id)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("id传参为空");
				return ajaxJson;
			}
			if (StringUtils.isBlank(languageid)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("languageid传参为空");
				return ajaxJson;
			}
			CityStrategy cityStrategy1=new CityStrategy();
			cityStrategy1.setId(id);
			if (StringUtils.isNotBlank(uid)) {
				cityStrategy1.setMemberId(Integer.parseInt(uid));
			}
			CityStrategy cityStrategy = cityStrategyService.get(cityStrategy1);
			if(StringUtils.isBlank(cityStrategy.getPhoto())){
				ComProtocol cp1 = new ComProtocol();
            	cp1.setLanguageid(Integer.parseInt(languageid));
            	ComProtocol cp = comProtocolService.getProtocol(cp1);
            	if(cp!=null&&cp.getDefaultPhoto()!=null){
            		cityStrategy.setPhoto(cp.getDefaultPhoto());
            	}
			}
			CityStrategySon cityStrategySon = new CityStrategySon();
			cityStrategySon.setStrategyId(Integer.valueOf(id));
			List<CityStrategySon> cityStrategySonList = cityStrategySonService.findListByStrategyId(cityStrategySon);
			cityStrategy.setCityStrategySonList(cityStrategySonList);
			
			ajaxJson.getBody().put("cityStrategy", cityStrategy);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取城市攻略成功");  
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
	 * 查看城市攻略列表接口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findStrategyList")
	@ResponseBody
	public AjaxJson findStrategyList(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		AjaxJson ajaxJson = new AjaxJson();
		
		try {
			String type = request.getParameter("type");      //1.游客 2.会员
			String uid = request.getParameter("uid");      //会员ID，登陆后返回
			String time = request.getParameter("time");		//时间，登陆后返回
			String key = request.getParameter("key");       //密钥，登陆后返回
			String languageid = request.getParameter("languageid"); //语言id
			String cityid = request.getParameter("cityid");       //城市id
			String pageNo = request.getParameter("pageNo");       //分页数
			
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

			String pageSize1 = request.getParameter("pageSize");       //每页数
			Integer pageSize;
			if(StringUtils.isBlank(pageSize1)){
				pageSize = appUtils.getPageSize();
			}else{
				pageSize = Integer.parseInt(pageSize1);
			}
			
			CityStrategy cityStrategy = new CityStrategy();
	        cityStrategy.setLanguageId(Integer.parseInt(languageid));
	        cityStrategy.setCityId(Integer.parseInt(cityid));
	        if(StringUtils.isNotBlank(uid)){
	        	 cityStrategy.setMemberId(Integer.parseInt(uid));
			}
            Page<CityStrategy> page = new Page<CityStrategy>();
            page.setPageNo(Integer.parseInt(pageNo));
            page.setPageSize1(pageSize);
            Page<CityStrategy> page1 = cityStrategyService.findStrategyList(page, cityStrategy);

            List<CityStrategy> cityStrategyList = page1.getList();
            if(cityStrategyList!=null&&cityStrategyList.size()>0){
    			for(CityStrategy a:cityStrategyList){
    				if(StringUtils.isBlank(a.getPhoto())){
    					ComProtocol cp1 = new ComProtocol();
    	            	cp1.setLanguageid(Integer.parseInt(languageid));
    	            	ComProtocol cp = comProtocolService.getProtocol(cp1);
    	            	if(cp!=null&&cp.getDefaultPhoto()!=null){
    	            		a.setPhoto(cp.getDefaultPhoto());
    	            	}
    				}
    			}
    		}
            
            int totalPage = page1.getTotalPage();
            if (Integer.parseInt(pageNo) > totalPage) {
                ajaxJson.getBody().put("list", new ArrayList<>());
            } else {
                ajaxJson.getBody().put("list", cityStrategyList);
            }
            ajaxJson.getBody().put("totalPage", totalPage);
			
			ajaxJson.setSuccess(true);
			ajaxJson.setErrorCode("0");
			ajaxJson.setMsg("获取城市攻略列表成功");  
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
	

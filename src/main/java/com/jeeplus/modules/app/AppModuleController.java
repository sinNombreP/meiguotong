package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.GsonUtil;
import com.jeeplus.modules.base.task.SerializeUtil;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.comadd.ComAd;
import com.jeeplus.modules.meiguotong.entity.comarticle.ComArticle;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.module.ModuleContent;
import com.jeeplus.modules.meiguotong.entity.module.ModuleDetails;
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtml;
import com.jeeplus.modules.meiguotong.entity.module.ModuleName;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleContentMapper;
import com.jeeplus.modules.meiguotong.service.car.CarInfoService;
import com.jeeplus.modules.meiguotong.service.citystrategy.CityStrategyService;
import com.jeeplus.modules.meiguotong.service.comadd.ComAdService;
import com.jeeplus.modules.meiguotong.service.comarticle.ComArticleService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.module.ModuleContentService;
import com.jeeplus.modules.meiguotong.service.module.ModuleHtmlService;
import com.jeeplus.modules.meiguotong.service.module.ModuleNameService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
import com.jeeplus.modules.meiguotong.service.travel.TravelCustomizationService;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;

import net.oschina.j2cache.redis.client.RedisClient;
import net.oschina.j2cache.redis.support.RedisClientFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Controller
@RequestMapping(value = "${adminPath}/interface/module")
public class AppModuleController {
	
	@Autowired
	private ModuleHtmlService moduleHtmlService;
	@Autowired
	private ModuleNameService moduleNameService;
//	@Autowired
//	private ModuleHtmlNameService moduleHtmlNameService;
	@Autowired
	private ModuleContentService moduleContentService;
	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private ComCommentService comCommentService;
	@Autowired
	private ComCityService comCityService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private LinerLineService linerLineService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private TravelCustomizationService travelCustomizationService;
	@Autowired
	private CityStrategyService cityStrategyService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private GuideRouteService guideRouteService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private ComArticleService comArticleService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ScenicSpotTicketService scenicSpotTicketService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ModuleContentMapper moduleContentMapper;
	@Autowired
	private ComAdService comAdService;
	@Autowired
	private RedisClientFactory redisClientFactory;
	
	/**
	* @Title: getModuleHtmlList
	* @Description: 获取所有网站页面
	* @author  彭善智
	* @Data 2018年12月4日上午11:58:29
	*/
	@RequestMapping(value = "getModuleHtmlList")
	@ResponseBody
	public AjaxJson getModuleHtmlList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			List<ModuleHtml> list = moduleHtmlService.getModuleHtmlList();
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取网站页面成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getModuleNameList
	* @Description: 获取可添加模块
	* @author  彭善智
	* @Data 2018年12月4日下午3:38:32
	*/
	@RequestMapping(value = "getModuleNameList")
	@ResponseBody
	public AjaxJson getModuleNameList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		try {
			List<ModuleName> list = moduleNameService.findList(new ModuleName());
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取可添加模块成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: getModuleHtmlNameList
	* @Description: 获取关联模块
	* @author  彭善智
	* @Data 2018年12月4日下午4:40:22
	*/
	@RequestMapping(value = "getModuleHtmlNameList")
	@ResponseBody
	public AjaxJson getModuleHtmlNameList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		String moduleHtmlId = request.getParameter("moduleHtmlId");//网站页面ID
		if (StringUtils.isBlank(moduleHtmlId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("moduleHtmlId传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ModuleContent moduleContent = new ModuleContent();
			moduleContent.setLanguageId(Integer.parseInt(languageId));
			moduleContent.setModuleHtmlId(Integer.parseInt(moduleHtmlId));
			List<ModuleContent> list = moduleContentService.getModuleHtmlNameList(moduleContent);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取关联模块成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getCarList
	* @Description: 获取车辆列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:04:47
	*/
	@RequestMapping(value = "getCarList")
	@ResponseBody
	public AjaxJson getCarList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			CarInfo carInfo = new CarInfo();
			carInfo.setLanguage(Integer.parseInt(languageId));
			carInfo.setStatus(3);
			List<CarInfo> list = carInfoService.findList(carInfo);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取车辆列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: getComCommentList
	* @Description: 获取评论列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:09:43
	*/
	@RequestMapping(value = "getComCommentList")
	@ResponseBody
	public AjaxJson getComCommentList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ComComment comComment = new ComComment();
			comComment.setLanguageId(Integer.parseInt(languageId));
			comComment.setFatherId(0);
			comComment.setStatus(1);
			List<ComComment> list = comCommentService.findList(comComment);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取评论列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: getComCityList
	* @Description: 获取城市列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:19:57
	*/
	@RequestMapping(value = "getComCityList")
	@ResponseBody
	public AjaxJson getComCityList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ComCity comCity = new ComCity();
			comCity.setLanguageId(languageId);
			comCity.setStatus(1);
			List<ComCity> list = comCityService.findList(comCity);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取城市列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getScenicSpotList
	* @Description: 获取景点列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:24:47
	*/
	@RequestMapping(value = "getScenicSpotList")
	@ResponseBody
	public AjaxJson getScenicSpotList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ScenicSpot scenicSpot = new ScenicSpot();
			scenicSpot.setLanguageId(Integer.parseInt(languageId));
			scenicSpot.setStatus(3);
			List<ScenicSpot> list = scenicSpotService.getScenicSpotList(scenicSpot);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取景点列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getLinertLineList
	* @Description: 获取游轮路线列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:40:16
	*/
	@RequestMapping(value = "getLinertLineList")
	@ResponseBody
	public AjaxJson getLinertLineList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			LinerLine LinerLine = new LinerLine();
			LinerLine.setLanguageId(Integer.parseInt(languageId));
			LinerLine.setStatus(3);
			List<LinerLine> list = linerLineService.findList(LinerLine);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取邮轮路线列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: getOrderSysList
	* @Description: 获取订单列表
	* @author  彭善智
	* @Data 2018年12月5日上午11:51:10
	*/
	@RequestMapping(value = "getOrderSysList")
	@ResponseBody
	public AjaxJson getOrderSysList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			OrderSys orderSys = new OrderSys();
			orderSys.setLanguageid(Integer.parseInt(languageId));
			orderSys.setFatherid(0);
			List<OrderSys> list = orderSysService.findList(orderSys);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取订单列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getTravelCustomizationList
	* @Description: 获取定制列表
	* @author  彭善智
	* @Data 2018年12月5日下午12:00:48
	*/
	@RequestMapping(value = "getTravelCustomizationList")
	@ResponseBody
	public AjaxJson getTravelCustomizationList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			TravelCustomization travelCustomization = new TravelCustomization();
			travelCustomization.setLanguage(Integer.parseInt(languageId));
			travelCustomization.setStatus(1);
			List<TravelCustomization> list = travelCustomizationService.findList(travelCustomization);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取定制列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getCityStrategyList
	* @Description: 获取攻略列表
	* @author  彭善智
	* @Data 2018年12月5日下午1:54:57
	*/
	@RequestMapping(value = "getCityStrategyList")
	@ResponseBody
	public AjaxJson getCityStrategyList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			CityStrategy cityStrategy = new CityStrategy();
			cityStrategy.setLanguageId(Integer.parseInt(languageId));
			cityStrategy.setStatus(1);
			List<CityStrategy> list = cityStrategyService.getCityStrategyList(cityStrategy);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取攻略列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getGuideRouteList
	* @Description: 获取导游路线列表
	* @author  彭善智
	* @Data 2018年12月5日下午3:05:55
	*/
	@RequestMapping(value = "getGuideRouteList")
	@ResponseBody
	public AjaxJson getGuideRouteList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			GuideRoute guideRoute = new GuideRoute();
			guideRoute.setLanguageid(Integer.parseInt(languageId));
			List<GuideRoute> list = guideRouteService.findList(guideRoute);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取导游路线列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getGuideList
	* @Description: 获取导游列表
	* @author  彭善智
	* @Data 2018年12月5日下午2:06:01
	*/
	@RequestMapping(value = "getGuideList")
	@ResponseBody
	public AjaxJson getGuideList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			Guide guide = new Guide();
			guide.setStatus(1);
			guide.setType(1);
			List<Guide> list = guideService.getGuideList(guide);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取导游列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	

	
	
	/**
	* @Title: getRoute1List
	* @Description: 常规路线列表
	* @author  彭善智
	* @Data 2018年12月5日下午3:15:22
	*/
	@RequestMapping(value = "getRoute1List")
	@ResponseBody
	public AjaxJson getRoute1List(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			Route route = new Route();
			route.setLanguage(Integer.parseInt(languageId));
			route.setStatus(3);
			route.setType(1);
			List<Route> list = routeService.findList(route);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取常规路线列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: getRoute2List
	* @Description: 获取当地参团列表
	* @author  彭善智
	* @Data 2018年12月5日下午3:31:21
	*/
	@RequestMapping(value = "getRoute2List")
	@ResponseBody
	public AjaxJson getRoute2List(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			Route route = new Route();
			route.setLanguage(Integer.parseInt(languageId));
			route.setStatus(3);
			route.setType(2);
			List<Route> list = routeService.findList(route);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取当地参团列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: getComArticle1List
	* @Description: 获取网站文章
	* @author  彭善智
	* @Data 2018年12月5日下午3:33:55
	*/
	@RequestMapping(value = "getComArticle1List")
	@ResponseBody
	public AjaxJson getComArticle1List(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ComArticle comArticle = new ComArticle();
			comArticle.setLanguageId(Integer.parseInt(languageId));
			comArticle.setStatus(1);
			comArticle.setType(1);
			List<ComArticle> list = comArticleService.getComArticleList(comArticle);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取网站文章成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getComArticle2List
	* @Description: 获取资讯
	* @author  彭善智
	* @Data 2018年12月10日上午10:14:25
	*/
	@RequestMapping(value = "getComArticle2List")
	@ResponseBody
	public AjaxJson getComArticle2List(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ComArticle comArticle = new ComArticle();
			comArticle.setLanguageId(Integer.parseInt(languageId));
			comArticle.setStatus(1);
			comArticle.setType(2);
			List<ComArticle> list = comArticleService.getComArticleList(comArticle);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取资讯列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	/**
	 * 获取广告列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getAdvertListUrl")
	@ResponseBody
	public AjaxJson getAdvertListUrl(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ComAd comAd = new ComAd();
			comAd.setLanguageId(languageId);
			List<ComAd> list = comAdService.getAdvertList(comAd);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取广告列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getSysUserList
	* @Description: 获取租车供应商列表
	* @author  彭善智
	* @Data 2018年12月5日下午3:49:10
	*/
	@RequestMapping(value = "getSysUserList")
	@ResponseBody
	public AjaxJson getSysUserList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			SysUser sysUser = new SysUser();
			sysUser.setUserType(2);
			sysUser.setFatherid(0);
			sysUser.setCompanyType("1");
			List<SysUser> list = sysUserService.findList(sysUser);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取租车供应商列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: getScenicSpotTicketList
	* @Description: 获取景点门票列表
	* @author  彭善智
	* @Data 2018年12月5日下午4:20:29
	*/
	@RequestMapping(value = "getScenicSpotTicketList")
	@ResponseBody
	public AjaxJson getScenicSpotTicketList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String languageId = request.getParameter("languageId");//语言ID
		if (StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("languageId传参为空");
			return ajaxJson;
		}
		try {
			ScenicSpotTicket scenicSpotTicket = new ScenicSpotTicket();
			scenicSpotTicket.setStatus(1);
			scenicSpotTicket.setLanguageId(Integer.parseInt(languageId));
			List<ScenicSpotTicket> list = scenicSpotTicketService.getScenicSpotTicketList(scenicSpotTicket);
			ajaxJson.getBody().put("list", list);
			ajaxJson.setMsg("获取景点门票列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	/**
	* @Title: addModuleContent
	* @Description: 添加模板内容
	* @author  彭善智
	* @Data 2018年12月6日上午11:28:14
	*/
	@RequestMapping(value = "addModuleContent")
	@ResponseBody
	public AjaxJson addModuleContent(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String id = request.getParameter("id");
		String moduleHtmlId = request.getParameter("moduleHtmlId");
		String moduleNameId = request.getParameter("moduleNameId");
		String languageId = request.getParameter("languageId");
//		String sort = request.getParameter("sort");
		String name = request.getParameter("name");
		String typeid = request.getParameter("typeid");
		String orderType = request.getParameter("orderType");
		String oneFooterShow = request.getParameter("oneFooterShow");
		String oneFooter = request.getParameter("oneFooter");
		String twoFooterShow = request.getParameter("twoFooterShow");
		String twoFooter = request.getParameter("twoFooter");
		String threeFooterShow = request.getParameter("threeFooterShow");
		String threeFooter = request.getParameter("threeFooter");
		String img = request.getParameter("img");
		String url = request.getParameter("url");
		String moduleDetails = request.getParameter("moduleDetails");
		if (StringUtils.isBlank(moduleHtmlId) || StringUtils.isBlank(moduleNameId) || StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("添加模块传参为空");
			return ajaxJson;
		}
		try {
			ModuleContent moduleContent = new ModuleContent();
			moduleContent.setModuleHtmlId(Integer.parseInt(moduleHtmlId));
			moduleContent.setModuleNameId(Integer.parseInt(moduleNameId));
			moduleContent.setLanguageId(Integer.parseInt(languageId));
			if(StringUtils.isNotBlank(id)) moduleContent.setId(id);
			if(StringUtils.isNotBlank(name)) moduleContent.setName(name);
			if(StringUtils.isNotBlank(typeid)) moduleContent.setTypeid(typeid);
			if(StringUtils.isNotBlank(orderType)) moduleContent.setOrderType(Integer.parseInt(orderType));
			if(StringUtils.isNotBlank(oneFooterShow)) moduleContent.setOneFooterShow(Integer.parseInt(oneFooterShow));
			if(StringUtils.isNotBlank(oneFooter)) moduleContent.setOneFooter(oneFooter);
			if(StringUtils.isNotBlank(twoFooterShow)) moduleContent.setTwoFooterShow(Integer.parseInt(twoFooterShow));
			if(StringUtils.isNotBlank(twoFooter)) moduleContent.setTwoFooter(twoFooter);
			if(StringUtils.isNotBlank(threeFooterShow)) moduleContent.setThreeFooterShow(Integer.parseInt(threeFooterShow));
			if(StringUtils.isNotBlank(threeFooter)) moduleContent.setThreeFooter(threeFooter);
			if(StringUtils.isNotBlank(img)) moduleContent.setImg(img);
			if(StringUtils.isNotBlank(url)) moduleContent.setUrl(url);
			if(StringUtils.isNotBlank(moduleDetails)) 
			moduleContent.setModuleDetails(GsonUtil.jsonToArrayList(moduleDetails, ModuleDetails.class));
			moduleContentService.addModuleContent(moduleContent);
			ajaxJson.setMsg("保存模板内容成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: updateSort
	* @Description: 排序
	* @author  彭善智
	* @Data 2018年12月11日下午2:38:08
	*/
	@RequestMapping(value = "updateSort")
	@ResponseBody
	public AjaxJson updateSort(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String id = request.getParameter("id");  //模块内容ID
		String sort = request.getParameter("sort");  //1.top  2.bottom
		if (StringUtils.isBlank(id) || StringUtils.isBlank(sort)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("排序传参为空");
			return ajaxJson;
		}
		try {
			moduleContentService.updateSort(id,Integer.parseInt(sort)==1?1:-1);
			ajaxJson.setMsg("排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}
	
	
	/**
	* @Title: getModuleContentById
	* @Description: 获取模块详情
	* @author  彭善智
	* @Data 2018年12月11日下午4:23:08
	*/
	@RequestMapping(value = "getModuleContentById")
	@ResponseBody
	public AjaxJson getModuleContentById(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("id传参为空");
			return ajaxJson;
		}
		try {
			ajaxJson.getBody().put("moduleContent", moduleContentService.getModuleContentById(id));
			ajaxJson.setMsg("获取详情成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}

	 /**
	   * @method: deleteModule
	   * @Description:  删除模板
	   * @Author:   彭善智
	   * @Date:     2018/12/18 20:23
	   */
	@RequestMapping(value = "deleteModule")
	@ResponseBody
	public AjaxJson deleteModule(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("id传参为空");
			return ajaxJson;
		}
		try {
			moduleContentService.deleteModule(id);
			ajaxJson.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}
		return ajaxJson;
	}


	
	/**
	* @Title: getModuleDataInfo
	* @Description: 获取模块数据
	* @author  彭善智
	* @Data 2018年12月12日下午2:50:38
	*/
	@RequestMapping(value = "getModuleDataInfo")
	@ResponseBody
	public AjaxJson getModuleDataInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String htmlName = request.getParameter("htmlName");   //页面名称
		String htmlSeal = request.getParameter("htmlSeal");   //页面位置
		String languageId = request.getParameter("languageid");
		if (StringUtils.isBlank(htmlName) || StringUtils.isBlank(htmlSeal) || StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("getModuleDataInfo传参为空");
			return ajaxJson;
		}
		RedisClient client=redisClientFactory.getResource();
		try {
			
			//缓存取数据
			//List<ModuleContent> moduleContentList = (List<ModuleContent>)CacheUtils.get("appHome", "contentList_"+htmlName+"_"+htmlSeal+"_"+languageId);
			
			byte[] in = client.get(("contentList_"+htmlName+"_"+htmlSeal+"_"+languageId).getBytes());
			List<ModuleContent> moduleContentList = SerializeUtil.unserializeForList(in);
			if(moduleContentList!=null&&moduleContentList.size()>0){
				ajaxJson.getBody().put("list", moduleContentList);
			}else{
				//判断语言有没有数据
	            Integer count = moduleContentMapper.getCount(languageId);
	            if(count>0){
	            	ModuleHtml moduleHtml = new ModuleHtml();
					moduleHtml.setHtmlName(htmlName);
					moduleHtml.sethtmlSeal(htmlSeal);
					moduleHtml = moduleHtmlService.getModuleDataInfo(moduleHtml);
					List<ModuleContent> moduleContentList1 = systemService.getHomeContent(moduleHtml.getId() , languageId);
					ajaxJson.getBody().put("list", moduleContentList1);
	            }else{
	            	ajaxJson.getBody().put("list", new ArrayList<>());
	            }
				
			}
			
			ajaxJson.setMsg("获取模板数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}finally{
        	try {
    			client.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
		return ajaxJson;
	}
	/**
	 * 获取页面广告配置
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getAdvertList")
	@ResponseBody
	public AjaxJson getAdvertList(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String htmlName = request.getParameter("htmlName");   //页面名称
		String htmlSeal = request.getParameter("htmlSeal");   //页面位置
		String languageId = request.getParameter("languageid");
		if (StringUtils.isBlank(htmlName) || StringUtils.isBlank(htmlSeal) || StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("getAdvertList传参为空");
			return ajaxJson;
		}
		RedisClient client=redisClientFactory.getResource();
		try {
			
			//缓存取数据
			//List<ModuleContent> moduleAdvertList = (List<ModuleContent>)CacheUtils.get("appHome", "advertList_"+htmlName+"_"+htmlSeal+"_"+languageId);
			byte[] in = client.get(("advertList_"+htmlName+"_"+htmlSeal+"_"+languageId).getBytes());
			List<ModuleContent> moduleAdvertList = SerializeUtil.unserializeForList(in);
			if(moduleAdvertList!=null&&moduleAdvertList.size()>0){
				ajaxJson.getBody().put("list", moduleAdvertList);
			}else{
				//判断语言有没有数据
	            Integer count = moduleContentMapper.getCount(languageId);
	            if(count>0){
	            	ModuleHtml moduleHtml = new ModuleHtml();
					moduleHtml.setHtmlName(htmlName);
					moduleHtml.sethtmlSeal(htmlSeal);
					moduleHtml = moduleHtmlService.getModuleDataInfo(moduleHtml);
					List<ModuleContent> advertList = systemService.getAdvertContent(moduleHtml.getId() , languageId);
					ajaxJson.getBody().put("list", advertList);
	            }else{
	            	ajaxJson.getBody().put("list", new ArrayList<>());
	            }
				
			}
			
			ajaxJson.setMsg("获取广告模板数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}finally{
        	try {
    			client.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
		return ajaxJson;
	}
	/**
	 * 获取页面底部导航配置
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getFootContent")
	@ResponseBody
	public AjaxJson getFootContent(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		String htmlName = request.getParameter("htmlName");   //页面名称
		String htmlSeal = request.getParameter("htmlSeal");   //页面位置
		String languageId = request.getParameter("languageid");
		if (StringUtils.isBlank(htmlName) || StringUtils.isBlank(htmlSeal) || StringUtils.isBlank(languageId)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("getAdvertList传参为空");
			return ajaxJson;
		}
		RedisClient client=redisClientFactory.getResource();
		try {
			//缓存取数据
			//List<ModuleContent> moduleFootList = (List<ModuleContent>)CacheUtils.get("appHome", "footList_"+htmlName+"_"+htmlSeal+"_"+languageId);
			
			byte[] in = client.get(("footList_"+htmlName+"_"+htmlSeal+"_"+languageId).getBytes());
			List<ModuleContent> moduleFootList = SerializeUtil.unserializeForList(in);
			
			if(moduleFootList!=null&&moduleFootList.size()>0){
				ajaxJson.getBody().put("list", moduleFootList);
			}else{
				//判断语言有没有数据
	            Integer count = moduleContentMapper.getCount(languageId);
	            if(count>0){
	            	ModuleHtml moduleHtml = new ModuleHtml();
					moduleHtml.setHtmlName(htmlName);
					moduleHtml.sethtmlSeal(htmlSeal);
					moduleHtml = moduleHtmlService.getModuleDataInfo(moduleHtml);
					List<ModuleContent> footList = systemService.getFootContent(moduleHtml.getId() , languageId);
					ajaxJson.getBody().put("list", footList);
	            }else{
	            	ajaxJson.getBody().put("list", new ArrayList<>());
	            }
				
			}
			
			ajaxJson.setMsg("获取页面底部导航成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_3);
			ajaxJson.setMsg(AppErrorUtils.error_3_desc);
		}finally{
        	try {
    			client.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
		return ajaxJson;
	}
}
	

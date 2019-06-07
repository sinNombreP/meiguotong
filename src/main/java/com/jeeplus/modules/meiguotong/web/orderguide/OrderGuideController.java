/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.web.orderguide;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.order.OrderRouteService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;
import com.jeeplus.modules.meiguotong.service.orderguide.OrderGuideService;
import com.jeeplus.modules.meiguotong.service.orderliner.OrderLinerService;
import com.jeeplus.modules.meiguotong.service.orderlinerroom.OrderLinerRoomService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.meiguotong.service.orderscenicspot.OrderScenicSpotService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelBusinessService;

/**
 * 导游订单Controller
 * @author cdq
 * @version 2018-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/meiguotong/orderguide/orderGuide")
public class OrderGuideController extends BaseController {

	@Autowired
	private OrderGuideService orderGuideService;
	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private OrderCarService orderCarService;
	@Autowired
	private OrderInsuranceService orderInsuranceService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private OrderRouteService orderRouteService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private OrderLinerService orderLinerService;
	@Autowired
	private LinerLineService linerLineService;
	@Autowired
	private OrderLinerRoomService orderLinerRoomService;
	@Autowired
	private OrderScenicSpotService orderScenicSpotService;
	@Autowired
	private OrderTravelBusinessService orderTravelBusinessService;
	@Autowired
	private OrderMemberService orderMemberService;
	@ModelAttribute
	public OrderGuide get(@RequestParam(required=false) String id) {
		OrderGuide entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderGuideService.get(id);
		}
		if (entity == null){
			entity = new OrderGuide();
		}
		return entity;
	}
	
	/**
	 * 当地玩家订单列表页面
	 */
	@RequiresPermissions("meiguotong:orderguide:orderGuide:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Guide> guides=guideService.findGuideList();
		model.addAttribute("guides", guides);
		return "modules/meiguotong/orderguide/orderGuideList";
	}
	/**
	 * 当地玩家售后订单列表页面
	 */
	@RequiresPermissions("meiguotong:orderguide:orderGuide:list")
	@RequestMapping(value = {"AfterSale"})
	public String AfterSale(Model model) {
		List<Guide> guides=guideService.findGuideList();
		model.addAttribute("guides", guides);
		return "modules/meiguotong/orderguide/AfterSaleOrderList";
	}	
	/**
	 * 当地玩家订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderguide:orderGuide:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderGuide orderGuide, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderGuide> page = orderGuideService.findPage(new Page<OrderGuide>(request, response), orderGuide); 
		return getBootstrapData(page);
	}
	/**
	 * 当地玩家售后订单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderguide:orderGuide:list")
	@RequestMapping(value = "AfterSaleData")
	public Map<String, Object> AfterSaledata(OrderGuide orderGuide, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderGuide> page = orderGuideService.AfterSale(new Page<OrderGuide>(request, response), orderGuide); 
		return getBootstrapData(page);
	}
	/**
	 * 查看，增加，编辑导游订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "ViewForm")
	public String ViewForm(OrderGuide orderGuide, Model model) {
/*		OrderSys getorderSys=new OrderSys();
		getorderSys.setId(orderGuide.getOrderSysid().toString());
		getorderSys.setType(orderGuide.getOrderType());
		OrderSys orderSys= orderSysService.get(getorderSys);
		String url = "";			
		switch (orderSys.getType()) {
		case 1:
			url= "/meiguotong/orderguide/orderGuide/orderCar?&id="+orderSys.getOrderid();
			break;
		case 2:
			url= "/meiguotong/orderguide/orderGuide/orderCar2?&id="+orderSys.getOrderid();
			break;
		case 3:
			url= "/meiguotong/orderguide/orderGuide/orderCar3?&id="+orderSys.getOrderid();
			break;
		case 4:
			url= "/meiguotong/orderguide/orderGuide/orderRoute?&id="+orderSys.getOrderid();
			break;
		case 5:
			url= "/meiguotong/orderguide/orderGuide/orderRoute2?&id="+orderSys.getOrderid();
			break;
		case 6:
			url= "/meiguotong/orderguide/orderGuide/orderLine?&id="+orderSys.getOrderid();
			break;
		case 7:
			url= "/meiguotong/orderguide/orderGuide/form?&id="+orderSys.getOrderid();
			break;
		case 8:
			url= "/meiguotong/orderguide/orderGuide/form?&id="+orderSys.getOrderid();
			break;
		case 9:
			url= "/meiguotong/orderguide/orderGuide/insurance?&id="+orderSys.getOrderid();
			break;
		case 10:
			url= "/meiguotong/orderguide/orderGuide/orderInsurance?&id="+orderSys.getOrderid();
			break;
		case 11:
			url= "/meiguotong/orderguide/orderGuide/orderTravel?&id="+orderSys.getOrderid();
			break;
		case 12:
			url= "/meiguotong/orderguide/orderGuide/orderGuide?&id="+orderSys.getOrderid();
			break;
		default:
			break;
		}		
		return "redirect:"+Global.getAdminPath()+url;*/
		     /*OrderSys orderSys=new OrderSys();
		     orderSys.setType(8);
		     orderSys.setOrderid(Integer.parseInt(orderGuide.getId()));
		     //查找当地玩家保险数据
		     OrderSys orderSys1=orderSysService.findInsurance(orderSys);
		     OrderSys orderSys2=new OrderSys();
		     orderSys2.setType(10);
		     orderSys2.setFatherid(orderSys1.getFatherid());
		     OrderSys orderSys3=orderSysService.findInsurance1(orderSys2); 
		     OrderInsurance orderInsurance=new OrderInsurance();
		     orderInsurance.setId(orderSys3.getOrderid().toString());
		     OrderInsurance orderInsurance1=orderInsuranceService.get(orderInsurance);
		     model.addAttribute("orderInsurance1", orderInsurance1);
		     Insurance insurance=new Insurance();
		     insurance.setId(orderInsurance1.getInsuranceid().toString());
		     Insurance insurance1=insuranceService.get(insurance);
		     model.addAttribute("insurance1", insurance1);*/
		//出游人信息
			OrderMember orderMember = new OrderMember();
			orderMember.setTypeId(Integer.parseInt(orderGuide.getOrderSys1().toString()));
			model.addAttribute("orderMemberList", orderMemberService.findList(orderMember));
		    OrderGuide orderGuide1=orderGuideService.findOrderGuide(orderGuide.getId());
		    model.addAttribute("orderGuide1", orderGuide1);
		return "modules/meiguotong/orderguide/orderGuideForm";
	}
	/**
	 * 查看包车租车订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderCar")
	public String orderCar(OrderGuide orderGuide, Model model) {	
		OrderCar getorderCar =new OrderCar();
		getorderCar.setId(orderGuide.getId());
		getorderCar.setType(1);
		OrderCar orderCar=orderCarService.getList(getorderCar);
		model.addAttribute("orderCar", orderCar);
		//包车租车保险方案
		OrderInsurance getorderInsurance=new OrderInsurance();
		getorderInsurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getorderInsurance);
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("orderInsurance", orderInsurance);
	    model.addAttribute("insurance", insurance);
	    //包车租车导游
	    try{
	  OrderGuide getorderGuide=new OrderGuide();
	  getorderGuide.setId(orderGuide.getId());
	  getorderGuide.setType(1);
	  OrderGuide orderGuide1=orderGuideService.getGuide(getorderGuide);
	  Guide getGuide=new Guide();
	  getGuide.setId(orderGuide1.getTypeid().toString());
	  Guide guide=guideService.get(getGuide);
	  model.addAttribute("guide", guide);  
	    }catch(Exception e){}
		return "modules/meiguotong/orderguide/orderCarForm";
	}
	/**
	 * 查看短程接送订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderCar2")
	public String orderCar2(OrderGuide orderGuide, Model model,OrderCar orderCar1) {	
		OrderCar getorderCar =new OrderCar();
		getorderCar.setId(orderGuide.getId());
		getorderCar.setType(2);
		OrderCar orderCar=orderCarService.getList(getorderCar);
		model.addAttribute("orderCar", orderCar);
		//短程接送保险方案
		OrderInsurance getorderInsurance=new OrderInsurance();
		getorderInsurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getorderInsurance);
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    model.addAttribute("orderInsurance", orderInsurance);
	    
		model.addAttribute("orderGuide", orderGuide);
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderCarForm";
	}
	
	/**
	 * 查看接送机订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderCar3")
	public String orderCar3(OrderGuide orderGuide, Model model,OrderCar orderCar1) {	
		OrderCar getorderCar =new OrderCar();
		getorderCar.setId(orderGuide.getId());
		getorderCar.setType(3);
		OrderCar orderCar=orderCarService.getList(getorderCar);
		model.addAttribute("orderCar", orderCar);
		//接送机保险方案
		OrderInsurance getorderInsurance=new OrderInsurance();
		getorderInsurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getorderInsurance);
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    model.addAttribute("orderInsurance", orderInsurance);
	    
		model.addAttribute("orderGuide", orderGuide);
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderCarForm";
	}
	/**
	 * 查看常规路线订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderRoute")
	public String orderRoute(OrderGuide orderGuide, Model model) {	
		OrderRoute getorderRoute =new OrderRoute();
		getorderRoute.setId(orderGuide.getId());
		OrderRoute orderRoute=orderRouteService.get(getorderRoute);
		model.addAttribute("orderRoute", orderRoute);
		Route getroute=new Route();
		getroute.setType(1);
		getroute.setId(orderRoute.getRouteid().toString());
		Route route=routeService.get(getroute);
		model.addAttribute("route",route );
		//常规路线保险方案
		OrderInsurance getorderInsurance=new OrderInsurance();
		getorderInsurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getorderInsurance);
		try{
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    model.addAttribute("orderInsurance", orderInsurance);
		}catch(Exception e){		
		}    
		model.addAttribute("orderGuide", orderGuide);
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderRouteForm";
	}
	/**
	 * 当地参团订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderRoute2")
	public String orderRoute2(OrderGuide orderGuide, Model model) {	
		OrderRoute getorderRoute =new OrderRoute();
		getorderRoute.setId(orderGuide.getId());
		OrderRoute orderRoute=orderRouteService.get(getorderRoute);
		model.addAttribute("orderRoute", orderRoute);
		Route getroute=new Route();
		getroute.setType(2);
		getroute.setId(orderRoute.getRouteid().toString());
		Route route=routeService.get(getroute);
		model.addAttribute("route",route );
		//当地参团保险方案
		OrderInsurance getorderInsurance=new OrderInsurance();
		getorderInsurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getorderInsurance);
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    model.addAttribute("orderInsurance", orderInsurance);
	    
		model.addAttribute("orderGuide", orderGuide);
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderRouteForm";
	}
	/**
	 *邮轮订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderLiner")
	public String orderLiner(OrderGuide orderGuide, Model model) {	
		OrderLiner getOrderLiner =new OrderLiner();
		getOrderLiner.setId(orderGuide.getId());
		OrderLiner orderLiner=orderLinerService.get(getOrderLiner);
		model.addAttribute("orderLiner", orderLiner);
		//邮轮房间
		OrderLinerRoom getOrderLinerRoom=new OrderLinerRoom();
		getOrderLinerRoom.setId(orderGuide.getId());
		OrderLinerRoom orderLinerRoom=orderLinerRoomService.get(getOrderLinerRoom);
		model.addAttribute("orderLinerRoom", orderLinerRoom);
		LinerLine getLinerline=new LinerLine();
		getLinerline.setId(orderLiner.getLinerLineId().toString());
		LinerLine linerline=linerLineService.get(getLinerline);
		model.addAttribute("linerline",linerline );
		//邮轮保险方案
		OrderInsurance getorderInsurance=new OrderInsurance();
		getorderInsurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getorderInsurance);
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    model.addAttribute("orderInsurance", orderInsurance);
	    
		model.addAttribute("orderGuide", orderGuide);
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderLinerForm";
	}
	/**
	 *景点门票订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderScenice")
	public String orderScenice(OrderGuide orderGuide, Model model) {	
		OrderScenicSpot getOrderScenicSpot =new OrderScenicSpot();
		getOrderScenicSpot.setId(orderGuide.getId());
		OrderScenicSpot orderScenicSpot=orderScenicSpotService.get(getOrderScenicSpot);
		model.addAttribute("orderScenicSpot", orderScenicSpot);
		//景点保险方案
		OrderInsurance getorderInsurance=new OrderInsurance();
		getorderInsurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getorderInsurance);
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    model.addAttribute("insurance", insurance);
		model.addAttribute("orderInsurance", orderInsurance);
		
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderSceniceForm";
	}
	/**
	 *保险订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderInsurance")
	public String orderInsurance(OrderGuide orderGuide, Model model) {	
		OrderInsurance getOrderInsrurance =new OrderInsurance();
		getOrderInsrurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getOrderInsrurance);
		model.addAttribute("orderInsurance", orderInsurance);
		//保险方案
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    //保险导游
		  OrderGuide getorderGuide=new OrderGuide();
		  getorderGuide.setId(orderGuide.getId());
		  getorderGuide.setType(4);
		  OrderGuide orderGuide1=orderGuideService.getGuide(getorderGuide);
		  Guide getGuide=new Guide();
		  getGuide.setId(orderGuide1.getTypeid().toString());
		  Guide guide=guideService.get(getGuide);
		  model.addAttribute("guide", guide);  
		model.addAttribute("orderGuide1", orderGuide1);
		
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderInsuranceForm";
	}
	/**
	 *旅游定制订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderTravel")
	public String orderTravel(OrderGuide orderGuide, Model model) {	
		OrderTravelBusiness getOrderTravelBusiness =new OrderTravelBusiness();
		getOrderTravelBusiness.setId(orderGuide.getId());
		OrderTravelBusiness orderTravelBusiness=orderTravelBusinessService.get(getOrderTravelBusiness);
		model.addAttribute("orderTravelBusiness", orderTravelBusiness);
		//保险方案
		OrderInsurance getOrderInsrurance =new OrderInsurance();
		getOrderInsrurance.setId(orderGuide.getId());
		OrderInsurance orderInsurance=orderInsuranceService.get(getOrderInsrurance);
		model.addAttribute("orderInsurance", orderInsurance);
		Insurance getinsurance=new Insurance();
		getinsurance.setId((orderInsurance.getInsuranceid().toString()));
	    Insurance insurance=insuranceService.get(getinsurance);
	    model.addAttribute("insurance", insurance);
	    //旅游定制导游
		  OrderGuide getorderGuide=new OrderGuide();
		  getorderGuide.setId(orderGuide.getId());
		  getorderGuide.setType(5);
		  OrderGuide orderGuide1=orderGuideService.getGuide(getorderGuide);
		  Guide getGuide=new Guide();
		  getGuide.setId(orderGuide1.getTypeid().toString());
		  Guide guide=guideService.get(getGuide);
		  model.addAttribute("guide", guide);  
		model.addAttribute("orderGuide1", orderGuide1);
		
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderTravelForm";
	}
	/**
	 *导游订单表单页面
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:view","meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "orderGuide")
	public String orderGuide(OrderGuide orderGuide, Model model) {	
		OrderGuide getOrderGuide =new OrderGuide();
		getOrderGuide.setId(orderGuide.getId());
		OrderGuide orderGuide1=orderGuideService.get(getOrderGuide);
		model.addAttribute("orderGuide1", orderGuide1);
		 Guide getGuide=new Guide();
		  getGuide.setId(orderGuide1.getTypeid().toString());
		  Guide guide=guideService.get(getGuide);
		  model.addAttribute("guide", guide);  
		if(StringUtils.isBlank(orderGuide.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/meiguotong/orderguide/orderGuideForm";
	}
	/**
	 * 保存导游订单
	 */
	@RequiresPermissions(value={"meiguotong:orderguide:orderGuide:add","meiguotong:orderguide:orderGuide:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(OrderGuide orderGuide, Model model, RedirectAttributes redirectAttributes) throws Exception{
	
		orderGuideService.save(orderGuide);//保存
		addMessage(redirectAttributes, "保存导游订单成功");
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderguide/orderRoute/?repage";
	}
	/**
	 * 更改状态
	 * @param orderLiner
	 * @param redirectAttributes
	 * @return
	 */
@ResponseBody
@RequestMapping(value = "status")
		public AjaxJson status(OrderGuide orderGuide, RedirectAttributes redirectAttributes) {
			AjaxJson j = new AjaxJson();
			try {
                 orderGuideService.status(orderGuide);
				 j.setSuccess(true);
				 switch (orderGuide.getStatus()) {
					// 1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款中9退款通过10.退款不通过
						case 2:
							 j.setMsg("确认成功");
							break;
						case 6:
							 j.setMsg("取消订单成功");
							break;
						case 7:
							 j.setMsg("申请退款成功");
							break;
						case 9:
							 j.setMsg("退款成功");
							break;
						case 10:
							 j.setMsg("退款失败");
							break;
						default:
							break;
					}
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("更新状态失败");
			}
			return j;
		}
	/**
	 * 删除导游订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderguide:orderGuide:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderGuide orderGuide, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderGuideService.delete(orderGuide);
		j.setMsg("删除导游订单成功");
		return j;
	}
	
	/**
	 * 批量删除导游订单
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderguide:orderGuide:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderGuideService.delete(orderGuideService.get(id));
		}
		j.setMsg("删除导游订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meiguotong:orderguide:orderGuide:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderGuide orderGuide, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "导游订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderGuide> page = orderGuideService.findPage(new Page<OrderGuide>(request, response, -1), orderGuide);
    		new ExportExcel("导游订单", OrderGuide.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出导游订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("meiguotong:orderguide:orderGuide:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderGuide> list = ei.getDataList(OrderGuide.class);
			for (OrderGuide orderGuide : list){
				try{
					orderGuideService.save(orderGuide);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条导游订单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条导游订单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入导游订单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderguide/orderGuide/?repage";
    }
	
	/**
	 * 下载导入导游订单数据模板
	 */
	@RequiresPermissions("meiguotong:orderguide:orderGuide:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导游订单数据导入模板.xlsx";
    		List<OrderGuide> list = Lists.newArrayList(); 
    		new ExportExcel("导游订单数据", OrderGuide.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/meiguotong/orderguide/orderGuide/?repage";
    }

}
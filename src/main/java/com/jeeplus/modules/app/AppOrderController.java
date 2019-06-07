package com.jeeplus.modules.app;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.liner.Liner;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.order.Order;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarDate;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.product.RouteContent;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;
import com.jeeplus.modules.meiguotong.entity.productcar.ProductCar;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.meiguotong.service.collection.ProductCollectionService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.comcomment.ComCommentService;
import com.jeeplus.modules.meiguotong.service.comconsult.ComConsultService;
import com.jeeplus.modules.meiguotong.service.comtag.ComTagService;
import com.jeeplus.modules.meiguotong.service.hotel.OrderHotelService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.order.OrderRouteService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarDateService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;
import com.jeeplus.modules.meiguotong.service.orderguide.OrderGuideService;
import com.jeeplus.modules.meiguotong.service.orderhotelroom.OrderHotelRoomService;
import com.jeeplus.modules.meiguotong.service.orderliner.OrderLinerService;
import com.jeeplus.modules.meiguotong.service.orderlinerroom.OrderLinerRoomService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.meiguotong.service.orderscenicspot.OrderScenicSpotService;
import com.jeeplus.modules.meiguotong.service.product.RouteContentService;
import com.jeeplus.modules.meiguotong.service.product.RouteDateService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.productcar.ProductCarService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelBusinessService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelDetailsService;

import javassist.expr.NewArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单接口
 */
@Controller
@RequestMapping(value = "${adminPath}/interface/order")
public class AppOrderController {
    @Autowired
    private ComCityService comCityService;
    @Autowired
    private ComTagService comTagService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ScenicSpotService scenicSpotService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private ComCommentService comCommentService;
    @Autowired
    private RouteDateService routeDateService;
    @Autowired
    private RouteContentService routeContentService;
    @Autowired
    private ComConsultService comConsultService;
    @Autowired
    private ProductCollectionService productCollectionService;
    @Autowired
    private ProductCarService productCarService;
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private OrderSysService orderSysService;
    @Autowired
    private OrderCarService orderCarService;
    @Autowired
    private OrderCarDateService orderCarDateService;
    @Autowired
    private OrderRouteService orderRouteService;
    @Autowired 
    private OrderLinerService orderLinerService;
    @Autowired
    private OrderLinerRoomService orderLinerRoomService;
    @Autowired 
    private OrderScenicSpotService orderScenicSpotService;
    @Autowired
    private OrderGuideService orderGuideService;
    @Autowired
    private OrderInsuranceService orderInsuranceService;
    @Autowired
    private OrderMemberService orderMemberService;
    @Autowired
    private OrderHotelService orderHotelService;
    @Autowired
    private OrderHotelRoomService orderHotelRoomService;
    @Autowired
    private OrderTravelBusinessService orderTravelBusinessService;
    @Autowired
    private OrderTravelDetailsService orderTravelDetailsService;
    @Autowired
    private LinerLineService linerLineService;
    @Autowired
    private ScenicSpotTicketService scenicSpotTicketService;
    
    /**
     * 获取订单详情
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getOrderDetails")
    @ResponseBody
    public AjaxJson getOrderDetails(HttpServletRequest request, HttpServletResponse response, Model model) {

        AjaxJson ajaxJson = new AjaxJson();
        String type = request.getParameter("type");      //1.游客 2.会员
        String uid = request.getParameter("uid");      //会员ID，登陆后返回
        String time = request.getParameter("time");        //时间，登陆后返回
        String key = request.getParameter("key");       //密钥，登陆后返回
        String languageid = request.getParameter("languageid");       //语言id
        String getType = request.getParameter("getType");       //类型1.列表详情2.下单详情
        String orderid = request.getParameter("orderid");       //订单id
        String payOrderNo = request.getParameter("payOrderNo");       //支付订单号
        
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
        if (StringUtils.isBlank(getType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("getType传参为空");
            return ajaxJson;
        }
        
        try {
        	if(Integer.parseInt(getType)==1){
            	if (StringUtils.isBlank(orderid)) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_2);
                    ajaxJson.setMsg("orderid传参为空");
                    return ajaxJson;
                }
            }
            if(Integer.parseInt(getType)==2){
            	if (StringUtils.isBlank(payOrderNo)) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setErrorCode(AppErrorUtils.error_2);
                    ajaxJson.setMsg("payOrderNo传参为空");
                    return ajaxJson;
                }
            }
            
        	OrderSys a = new OrderSys();
        	a.setPayOrderNo(payOrderNo);
        	List<OrderSys> list = new ArrayList<>();
        	if(Integer.parseInt(getType)==1){
        		OrderSys order = orderSysService.get(orderid);
        		list.add(order);
        	}
        	if(Integer.parseInt(getType)==2){
        		 list = orderSysService.getListByPayOrderNo(a);//主订单信息
        	}
        	
        	if(list!=null&&list.size()>0){
        		for(OrderSys orderSys:list){
            		List<OrderCar> carlist =new ArrayList<>();
                	List<OrderRoute> routelist =new ArrayList<>();
                	List<OrderGuide> guilist =new ArrayList<>();
                	List<OrderInsurance> insurancelist =new ArrayList<>();
                	List<OrderHotel> hotellist =new ArrayList<>();
                	List<OrderLiner> linerlist =new ArrayList<>();
                	List<OrderScenicSpot> scenicSpotlist =new ArrayList<>();
                	List<OrderTravelBusiness> travelBusinesseslist =new ArrayList<>();
                	List<OrderSys> orderSys2=orderSysService.findDetailById(Integer.valueOf(orderSys.getId()));//子订单信息
                	for(OrderSys o :orderSys2){
                		//1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家/导游9.酒店10.保险11.旅游定制13.商务定制14.商务旅游'
                		switch (o.getType()) {//子订单类型
        				case 1: 
        					carlist.add(tentCar(o.getType(),Integer.valueOf(o.getId())));//包车租车信息
        					break;
        				case 2: 
        					carlist.add(sortCar(o.getType(),Integer.valueOf(o.getId())));//短程接送信息
        					break;
        				case 3: 
        					carlist.add(airCar(o.getType(),Integer.valueOf(o.getId())));//接送机信息
        					break;
        				case 4: 
        					routelist.add(route(Integer.valueOf(o.getId())));//常规路线信息
        					break;
        				case 5: 
        					routelist.add(local(Integer.valueOf(o.getId())));//当地参团信息
        					break;
        				case 6: 
        					OrderLiner orderLiner=liner(Integer.valueOf(o.getId()));//邮轮信息
        					linerlist.add(orderLiner);//邮轮房间信息
        					orderSys.setOrderLinerRooms(orderLinerRoomService.findLinerRoomDetailByOrderId(Integer.valueOf(orderLiner.getId())));
        					break;
        				case 7: 
        					scenicSpotlist.add(scenicSpot(Integer.valueOf(o.getId())));//景点信息
        					break;
        				case 8: 
        					guilist.add(guides(Integer.valueOf(o.getId())));//导游信息
        					break;
        				case 9: 
        					hotellist.add(hotels(Integer.valueOf(o.getId())));//酒店信息
        					break;
        				case 10: 
        					insurancelist.add(insurance(Integer.valueOf(o.getId())));//保险信息
        					break;
        				case 15: 
        					carlist.add(cusCar(o.getType(),Integer.valueOf(o.getId())));//定制车信息
        					break;
        				default:
        					travelBusinesseslist.add(travel(Integer.valueOf(o.getId())));//定制行程信息
        					break;
        				}
                	}
                	orderSys.setOrderCarDates(tentCarDetail(Integer.valueOf(orderSys.getId())));
                	orderSys.setCarlist(carlist);
                	orderSys.setRoutelist(routelist);
                	orderSys.setLinerlist(linerlist);
                	orderSys.setScenicSpotlist(scenicSpotlist);
                	orderSys.setGuilist(guilist);
                	orderSys.setHotellist(hotellist);
                	orderSys.setInsurancelist(insurancelist);
                	orderSys.setTravelBusinesseslist(travelBusinesseslist);
                	orderSys.setOrderMembers(members(Integer.valueOf(orderSys.getId())));
            	}
        	}
        	ajaxJson.getBody().put("list", list);
            ajaxJson.setSuccess(true);
            ajaxJson.setErrorCode("0");
            ajaxJson.setMsg("获取订单详情成功");
            return ajaxJson;
        } catch (Exception e) {
            e.printStackTrace();
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
    }
    
    

    public OrderCar tentCar(Integer type,Integer id){
    	return orderCarService.findCarInfoByOrderSys2(type,id);
    }
    
    public OrderCar sortCar(Integer type,Integer id){
    	return orderCarService.findCarInfoByOrderSys2(type,id);
    }
    
    public OrderCar airCar(Integer type,Integer id){
    	return orderCarService.findCarInfoByOrderSys2(type,id);
    }
    
    public OrderCar cusCar(Integer type,Integer id){
    	return orderCarService.findCarInfoByOrderSys2(type,id);
    }
    
    public List<OrderCarDate> tentCarDetail(Integer id){
    	List<OrderCarDate> orderCarDates=orderCarDateService.findOrderCarDataByOrderId(id);//定制日程
    	for(OrderCarDate d:orderCarDates){
    		d.setOrderid(id);
    		d.setOrderCarDates(orderCarDateService.findOrderCarDataByDay(d));//定制日程详情
    	}
    	return orderCarDates;
    }
    
    public OrderTravelBusiness travel(Integer id){
    	OrderTravelBusiness orderTravel=orderTravelBusinessService.findTravelDetailByOrderSys2(id);//定制信息
    	List<OrderTravelDetails> list = orderTravelDetailsService.getInfor(orderTravel);//定制日程
    	orderTravel.setOrderTravelDetails(list);
		for(OrderTravelDetails t : list) {
			t.setOrderTrvelId(Integer.valueOf(orderTravel.getId()));
			List<ComCity> city=orderTravelDetailsService.findTravelDetailByDate(t);//定制行程的城市信息
			t.setCitys(city);
				for (ComCity c :city) {
					t.setOrderTrvelId(Integer.valueOf(orderTravel.getId()));
					t.setCity(Integer.valueOf(c.getId()));
					c.setScenics(scenicSpotService.findScenicByCityId(t));//定制行程的景点信息
					c.setOrderbusness(orderTravelDetailsService.findBusinessByDate(t));//定制行程的商务信息
				}
			}
    	return orderTravel;
    }
    
    public OrderHotel hotels(Integer id){
    	OrderHotel orderHotels=orderHotelService.findhotelDetailByOrderSys2(id);//酒店日程
    	orderHotels.setOrderHotelRoomList(orderHotelRoomService.findHotelRoomByRoomDate(orderHotels));//酒店日程详情
    	return orderHotels;
    }
    
    public OrderLiner liner(Integer id){
    	return orderLinerService.findLinerDetailByOrderSys2(id);
    }
    
    public OrderGuide guides(Integer id){
    	return orderGuideService.findGuideDetailByOrderSys2(id);
    }
    
    public OrderRoute route(Integer id){
    	return orderRouteService.findRouteDetailByOrderSys2(id);
    }
    
    public OrderRoute local(Integer id){
    	return orderRouteService.findRouteDetailByOrderSys2(id);
    }
    
    public OrderInsurance insurance(Integer id){
    	return orderInsuranceService.findInsuranceByOrderSys2(id);
    }
    
    public OrderScenicSpot scenicSpot(Integer id){
    	return orderScenicSpotService.findScenicSpotDetailByOrderid(id);
    }
    
    public List<OrderMember> members(Integer id){
    	//出游人信息
    	OrderMember orderMember = new OrderMember();
		orderMember.setTypeId(Integer.parseInt(id.toString()));
    	return orderMemberService.findList(orderMember);
    }
    
    
   
   
}


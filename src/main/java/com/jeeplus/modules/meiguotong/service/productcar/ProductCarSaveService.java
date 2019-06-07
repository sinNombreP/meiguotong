/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.productcar;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.app.AppErrorUtils;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.linerroom.LinerRoom;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.guideroute.GuideRouteService;
import com.jeeplus.modules.meiguotong.service.insurance.InsuranceService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.linerroom.LinerRoomService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.product.RouteDateService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;
import com.jeeplus.modules.meiguotong.service.scenicspotticket.ScenicSpotTicketService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 购物车下单Service
 * @author dong
 * @version 2018-09-17
 */
@Service
@Transactional(readOnly = true)
public class ProductCarSaveService {

	@Autowired
	private RouteDateService routeDateService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private InsuranceService insuranceService;
	@Autowired
	private OrderSysService orderSysService; 
	@Autowired
	private GuideService guideService; 
	@Autowired
	private GuideRouteService guideRouteService;
	@Autowired
	private LinerLineService linerlineService;
	@Autowired
	private LinerRoomService linerRoomService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private ScenicSpotTicketService scenicSpotTicketService;   
	 
	/**
	 * 常规路线数据验证
	 * @param jpro
	 * @return
	 * @throws ParseException 
	 */
	public AjaxJson checkRoute1(JSONObject jpro) throws ParseException{
		AjaxJson ajaxJson = new AjaxJson();
		
        String routeid = jpro.getString("typeid");       //路线i
        String beginDate = jpro.getString("beginDate");       //开始时间
        String adultNum = jpro.getString("adultNum");       //大人
        String childNum = jpro.getString("childNum");       //小孩
        String oneNum = jpro.getString("oneNum");       //单人间
        String twoNum = jpro.getString("twoNum");       //双人间
        String threeNum = jpro.getString("threeNum");       //三人间
        String fourNum = jpro.getString("fourNum");       //四人间
        String arrangeNum = jpro.getString("arrangeNum");       //配房数量
        String orderMember = jpro.getString("orderMember");       //出游人信息
        
       
        if (StringUtils.isBlank(routeid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-routeid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(beginDate)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-beginDate传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(adultNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-adultNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(childNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-childNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(oneNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-oneNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(twoNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-twoNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(threeNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-threeNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(fourNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-fourNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(arrangeNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-arrangeNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(orderMember)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-orderMember传参为空");
            return ajaxJson;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = sdf.parse(beginDate);
        //查询余票
        RouteDate routeDate3=new RouteDate();
        routeDate3.setRouteid(Integer.valueOf(routeid));
        routeDate3.setPriceDate(bt);
        RouteDate routeDate2=routeDateService.findNumByDate(routeDate3);
        
        int stock=Integer.valueOf(adultNum)+Integer.valueOf(childNum);
        if (routeDate2==null||(routeDate2.getStock()>0&&routeDate2.getStock()-routeDate2.getNum()<stock)) {
        	ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_58);
			ajaxJson.setMsg(AppErrorUtils.error_58_desc);
            return ajaxJson;
		}
		return ajaxJson;
	};
	
	/**
	 * 当地参团数据验证
	 * @param jpro
	 * @return
	 * @throws ParseException 
	 */
	public AjaxJson checkRoute2(JSONObject jpro) throws ParseException{
		AjaxJson ajaxJson = new AjaxJson();

        String routeid = jpro.getString("typeid");       //路线i
        String beginDate = jpro.getString("beginDate");       //开始时间
        String adultNum = jpro.getString("adultNum");       //大人
        String childNum = jpro.getString("childNum");       //小孩
        String oneNum = jpro.getString("oneNum");       //单人间
        String twoNum = jpro.getString("twoNum");       //双人间
        String threeNum = jpro.getString("threeNum");       //三人间
        String fourNum = jpro.getString("fourNum");       //四人间
        String arrangeNum = jpro.getString("arrangeNum");       //配房数量
        String orderMember = jpro.getString("orderMember");       //出游人信息
        
       
        if (StringUtils.isBlank(routeid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-routeid传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(beginDate)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-beginDate传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(adultNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-adultNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(childNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-childNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(oneNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-oneNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(twoNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-twoNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(threeNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-threeNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(fourNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-fourNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(arrangeNum)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-arrangeNum传参为空");
            return ajaxJson;
        }
        if (StringUtils.isBlank(orderMember)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("route-orderMember传参为空");
            return ajaxJson;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = sdf.parse(beginDate);
        //查询余票
        RouteDate routeDate3=new RouteDate();
        routeDate3.setRouteid(Integer.valueOf(routeid));
        routeDate3.setPriceDate(bt);
        RouteDate routeDate2=routeDateService.findNumByDate(routeDate3);
        
        int stock=Integer.valueOf(adultNum)+Integer.valueOf(childNum);
        if (routeDate2==null||(routeDate2.getStock()>0&&routeDate2.getStock()-routeDate2.getNum()<stock)) {
        	ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_58);
			ajaxJson.setMsg(AppErrorUtils.error_58_desc);
            return ajaxJson;
		}
		return ajaxJson;
	};
	
	/**
	 * 导游数据验证
	 * @param jpro
	 * @return
	 */
	public AjaxJson checkGuide(JSONObject jpro){
		AjaxJson ajaxJson = new AjaxJson();
		String beginDate = jpro.getString("beginDate");       //开始时间
		String endDate = jpro.getString("endDate");       //结束时间
		String adultNum = jpro.getString("adultNum");       //大人
		String childNum = jpro.getString("childNum");       //小孩
		String guideid= jpro.getString("typeid");//导游Id
		String guideRouteid= "";//导游路线Id
		String orderMember = jpro.getString("orderMember");       //出游人信息
		
		
		if (StringUtils.isBlank(guideid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guide-guideid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(guideRouteid)) {
			if (StringUtils.isBlank(endDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guide-endDate传参为空");
				return ajaxJson;
			}
		}
		if (StringUtils.isBlank(beginDate)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guide-beginDate传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(adultNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guide-adultNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(childNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guide-childNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(orderMember)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guide-orderMember传参为空");
			return ajaxJson;
		}
		return ajaxJson;
	};
	
	/**
	 * 游轮数据验证
	 * @param jpro
	 * @return
	 */
	public AjaxJson checkLinerLine(JSONObject jpro){
		AjaxJson ajaxJson = new AjaxJson();
		String linerLineid=jpro.getString("typeid");//邮轮路线Id
		String beginDate = jpro.getString("beginDate");       //开始时间
		String adultNum = jpro.getString("adultNum");       //大人
		String childNum = jpro.getString("childNum");       //小孩
		String linerLine = jpro.getString("linerLine");       //游轮信息
		String orderMember = jpro.getString("orderMember");       //出游人信息
		
		if (StringUtils.isBlank(linerLineid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("linerLine-id传参为空");
			return ajaxJson;
		}
		
		if (StringUtils.isBlank(beginDate)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("linerLine-beginDate传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(adultNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("linerLine-adultNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(childNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("linerLine-childNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(linerLine)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("linerLine-linerLine传参为空");
			return ajaxJson;
		}

        JSONObject jpro1 =JSONObject.fromObject(linerLine);
        String linerRoomList = jpro1.getString("linerRoomList"); 
		 if (StringUtils.isBlank(linerRoomList) || "[]".equals(linerRoomList)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("linerLine-linerRoomList传参为空");
            return ajaxJson;
        }
		if (StringUtils.isBlank(orderMember)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("linerLine-orderMember传参为空");
			return ajaxJson;
		}
		
		
		return ajaxJson;
	};
	
	/**
	 * 景点数据验证
	 * @param jpro
	 * @return
	 */
	public AjaxJson checkScenicSpot(JSONObject jpro){
		AjaxJson ajaxJson = new AjaxJson();
		String beginDate = jpro.getString("beginDate");       //开始时间
		String adultNum = jpro.getString("adultNum");       //大人
		String childNum = jpro.getString("childNum");       //小孩
		String orderMember = jpro.getString("orderMember");       //出游人信息
		String scenicSpotTicket = jpro.getString("scenicSpotTicket");       //门票信息
        
        if (StringUtils.isBlank(scenicSpotTicket)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("scenic-scenicSpotTicket传参为空");
			return ajaxJson;
		}
        JSONObject jpro1 =JSONObject.fromObject(scenicSpotTicket);
        String scenicid = jpro1.getString("scenicid");       //景点id
		String ticketid = jpro1.getString("id"); //门票id
		 if (StringUtils.isBlank(scenicid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("scenicSpotTicket-scenicid传参为空");
            return ajaxJson;
        }
		 if (StringUtils.isBlank(ticketid)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_2);
            ajaxJson.setMsg("scenicSpotTicket-id传参为空");
            return ajaxJson;
        }
		
		if (StringUtils.isBlank(beginDate)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("scenic-beginDate传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(adultNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("scenic-adultNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(childNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("scenic-childNum传参为空");
			return ajaxJson;
		}
		
		if (StringUtils.isBlank(orderMember)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("scenic-orderMember传参为空");
			return ajaxJson;
		}
		return ajaxJson;
	};
	
	/**
	 * 导游路线数据验证
	 * @param jpro
	 * @return
	 */
	public AjaxJson checkGuideRoute(JSONObject jpro){
		AjaxJson ajaxJson = new AjaxJson();
		String beginDate = jpro.getString("beginDate");       //开始时间
		String endDate ="";
		if(jpro.has("endDate")){
			endDate = jpro.getString("endDate");       //结束时间
		}
		String adultNum = jpro.getString("adultNum");       //大人
		String childNum = jpro.getString("childNum");       //小孩
		String guideRoute= jpro.getString("guideRoute");//导游路线信息
		String orderMember = jpro.getString("orderMember");       //出游人信息
        if (StringUtils.isBlank(guideRoute)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guideRoute-guideRoute传参为空");
			return ajaxJson;
		}
        JSONObject jpro1 =JSONObject.fromObject(guideRoute);
		String guideid= jpro1.getString("guideid");//导游Id
		String guideRouteid= jpro1.getString("id");//导游路线Id
		
		if (StringUtils.isBlank(guideid)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guideRoute-guideid传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(guideRouteid)) {
			if (StringUtils.isBlank(endDate)) {
				ajaxJson.setSuccess(false);
				ajaxJson.setErrorCode(AppErrorUtils.error_2);
				ajaxJson.setMsg("guideRoute-endDate传参为空");
				return ajaxJson;
			}
		}
		if (StringUtils.isBlank(beginDate)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guideRoute-beginDate传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(adultNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guideRoute-adultNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(childNum)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guideRoute-childNum传参为空");
			return ajaxJson;
		}
		if (StringUtils.isBlank(orderMember)) {
			ajaxJson.setSuccess(false);
			ajaxJson.setErrorCode(AppErrorUtils.error_2);
			ajaxJson.setMsg("guideRoute-orderMember传参为空");
			return ajaxJson;
		}
		return ajaxJson;
	};
	/**
	 * 常规路线生成订单
	 * @param jpro
	 * @throws ParseException 
	 */
	public void saveRoute1(String uid,String languageid,String contactsName,
			String contactsMobile,String remark,JSONObject jproData,String payOrderNo) throws ParseException{
		
		String routeid = jproData.getString("typeid");       //路线i
        String beginDate = jproData.getString("beginDate");       //开始时间
        String adultNum = jproData.getString("adultNum");       //大人
        String childNum = jproData.getString("childNum");       //小孩
        String oneNum = jproData.getString("oneNum");       //单人间
        String twoNum = jproData.getString("twoNum");       //双人间
        String threeNum = jproData.getString("threeNum");       //三人间
        String fourNum = jproData.getString("fourNum");       //四人间
        String arrangeNum = jproData.getString("arrangeNum");       //配房数量
        String insuranceid = "";
        if(jproData.has("insuranceid")){
        	insuranceid = jproData.getString("insuranceid");       //保险
        }
        String orderMember = jproData.getString("orderMember");       //出游人信息
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = sdf.parse(beginDate);
		 //获取路线
        Route route = routeService.get(routeid);

        OrderSys orderSys = new OrderSys();
        orderSys.setPayOrderNo(payOrderNo);
        OrderRoute orderRoute = new OrderRoute();
        OrderInsurance orderInsurance = new OrderInsurance();
        //订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8.退款中9退款成功10.退款失败
        orderSys.setStatus(1);
        orderSys.setCreateDate(new Date());
        orderSys.setType(4);        // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
        //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
        orderSys.setFatherid(0);        // 父id
        orderSys.setMemberid(Integer.parseInt(uid));      //下单人
        orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

        orderSys.setContactsName(contactsName);    //联系人姓名
        orderSys.setContactsMobile(contactsMobile);    //联系人电话
        orderSys.setRemark(remark);            //备注
        orderSys.setPeopleNum(Integer.parseInt(adultNum) + Integer.parseInt(childNum));
        orderSys.setTitle(route.getTitle());            //订单标题
        orderSys.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
        orderSys.setChildNum(Integer.parseInt(childNum));        // 孩子数量
        orderSys.setBeginDate(bt);       //开始时间
        orderSys.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
        orderSys.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
        orderSys.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
        orderSys.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
        orderSys.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
        orderSys.setAgentid(route.getAgentid());
        orderSys.setStartCity(route.getStartCityContent());
        String image=route.getCarImg();
        if(image!=null) {
        	image=image.split(",")[0];
        }
        orderSys.setImage(image);
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        String bt1 = sdf1.format(new Date());
        String orderNo = "CG-" + route.getAgentid() + "-" + bt1 + "[]" + "-" + route.getId();
        orderRoute.setOrderNo(orderNo);  //订单号,后面替换[]
        orderRoute.setAgentid(route.getAgentid());        // 供应商ID
        orderRoute.setRouteid(Integer.parseInt(routeid));        // 常规路线/参团id
        orderRoute.setMemberid(Integer.parseInt(uid));        // 会员ID
        orderRoute.setDay(route.getDayNum()); //订单天数
        orderRoute.setStartCity(route.getEndCity());        // 出发城市
        orderRoute.setStartDate(bt);        // 出发时间
        orderRoute.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
        orderRoute.setChildNum(Integer.parseInt(childNum));        // 孩子数量
        orderRoute.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
        orderRoute.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
        orderRoute.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
        orderRoute.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
        orderRoute.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
        //获取出发时间的价格
        RouteDate routeDate = new RouteDate();
        routeDate.setRouteid(Integer.parseInt(routeid));
        routeDate.setPriceDate(bt);
        Map map=new HashMap();
        map.put("routeid", routeid);
        map.put("filter_priceDate", this.date2String(bt));
      List<RouteDate>  routeDates = routeDateService.findListByPage(map);
      routeDate= routeDates.get(0);
        orderRoute.setContactsName(contactsName);        // 联系人姓名
        orderRoute.setContactsMobile(contactsMobile);        // 联系人电话
        orderRoute.setRemark(remark);        // 备注
        orderRoute.setStatus(1);        // 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
        orderRoute.setLanguageId(Integer.parseInt(languageid));        // 语言
        orderRoute.setTitle(route.getTitle());        // 标题
        orderRoute.setSubtitle(route.getSubtitle());        // 副标题
        orderRoute.setInfor(route.getInfor());        // 产品详情
        orderRoute.setNo(route.getNo());            //产品编号
        orderRoute.setCompanyName(route.getCompanyName());//供应商名称
        orderRoute.setType(1);        // 1常规路线  2参团
        orderRoute.setName(route.getTitle());//订单标题


        if (StringUtils.isNotBlank(insuranceid)) {
            //获取保险信息
            Insurance insurance = insuranceService.get(insuranceid);

            orderInsurance.setName(insurance.getName());        // 保险名称
            orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));        // 保险ID
            orderInsurance.setAgentid(insurance.getAgentid());        // 供应商ID

            String orderNo1 = "BX-" + insurance.getAgentid() + "-" + bt1 + "[]" + "-" + insurance.getId();
            orderInsurance.setOrderNo(orderNo1);        // 订单号，后面替换[]
            orderInsurance.setMemberId(Integer.parseInt(uid));        // 下单人id
            //保险费*(成人数+儿童数)
            int num = Integer.parseInt(adultNum) + Integer.parseInt(childNum);
            BigDecimal price=new BigDecimal(num).multiply(insurance.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
            orderInsurance.setPrice(price);        // 订单价格
            orderInsurance.setPrice2(insurance.getPrice());   
            orderInsurance.setStatus(1);        // 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
            orderInsurance.setContacts(contactsName);        // 联系人
            orderInsurance.setContactMobile(contactsMobile);        // 联系电话
            orderInsurance.setContactRemark(remark);        // 备注
            orderInsurance.setLanguageId(languageid);        // 语言id
            orderInsurance.setNum(num);        // 预定数量
        }
        JSONArray jsonArray = JSONArray.fromObject(orderMember);
        List<OrderMember> orderMemberList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
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
            orderMember1.setIsNewRecord(true);

            orderMember1.setChineseName(chineseName);        // 中文姓名
            orderMember1.setEnglishName(englishName);        // 英文姓名
            orderMember1.setCertType(Integer.parseInt(certType));        // 证件类型：1.身份证 2.护照 3.本地ID',
            orderMember1.setCertNo(certNo);        // 证件号
            Date certValidDate1 = sdf.parse(certValidDate);
            orderMember1.setCertValidDate(certValidDate1);        // 有效期
            Date birthday1 = sdf.parse(birthday);
            orderMember1.setBirthday(birthday1);        // 出生年月
            orderMember1.setArea(area);        // 区号
            orderMember1.setMobile(mobile);        // 手机号
            orderMember1.setLanguageId(Integer.parseInt(languageid));        // 语言id
            orderMember1.setType(2);        //1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//			    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
            if (StringUtils.isNotBlank(orderMemberType)) {
                orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
            }
            orderMemberList.add(orderMember1);
        }
        //保险价格
        BigDecimal insurancePrice = BigDecimal.ZERO;
        if (orderInsurance.getPrice() != null) {
            insurancePrice = orderInsurance.getPrice();
        }
        //路线价格*(成人数+儿童数)+(单房成本+单房利润)*单人间数量+(双房成本+双房利润）*双人间数量+(三房成本+三房利润)*三人间数量
        // +(四房成本+四房利润)*四人间数量+(配房成本+配房利润)*配房数量+保险费*(成人数+儿童数)
         //路线
        //BigDecimal routePrice = route.getPrice().multiply(new BigDecimal(adultNum).add(new BigDecimal(childNum))).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal routePrice =new BigDecimal("0");
        //单人房
        BigDecimal oneRoomPrice = (routeDate.getOneCost().add(routeDate.getOneProfit())).multiply(new BigDecimal(oneNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //双人房
        BigDecimal twoRoomPrice = (routeDate.getTwoCost().add(routeDate.getTwoProfit())).multiply(new BigDecimal(twoNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //三人房
        BigDecimal threeRoomPrice = (routeDate.getThreeCost().add(routeDate.getThreeProfit())).multiply(new BigDecimal(threeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //四人房
        BigDecimal fourRoomPrice = (routeDate.getFourCost().add(routeDate.getFourProfit())).multiply(new BigDecimal(fourNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //配房价格
        BigDecimal arrangeRoomPrice = (routeDate.getArrangeCost().add(routeDate.getArrangeProfit())).multiply(new BigDecimal(arrangeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //价格=路线价格+房间价格
        BigDecimal routeTotalPrice = routePrice.add(oneRoomPrice).add(twoRoomPrice).add(threeRoomPrice).add(fourRoomPrice).add(arrangeRoomPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        orderRoute.setPrice(routeTotalPrice);
        //总价格=路线价格+房间价格+保险价格
        BigDecimal totalPrice = routeTotalPrice.add(insurancePrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        orderSys.setPrice(totalPrice);//主订单总价
      
        orderSys.setPrice1(routeDate.getOneCost().add(routeDate.getOneProfit()));
        orderSys.setPrice2(routeDate.getTwoCost().add(routeDate.getTwoProfit()));
        orderSys.setPrice3(routeDate.getThreeCost().add(routeDate.getThreeProfit()));
        orderSys.setPrice4(routeDate.getFourCost().add(routeDate.getFourProfit()));
        orderSys.setPricea(routeDate.getArrangeCost().add(routeDate.getArrangeProfit()));
        
        orderRoute.setPrice1(routeDate.getOneCost().add(routeDate.getOneProfit()));
        orderRoute.setPrice2(routeDate.getTwoCost().add(routeDate.getTwoProfit()));
        orderRoute.setPrice3(routeDate.getThreeCost().add(routeDate.getThreeProfit()));
        orderRoute.setPrice4(routeDate.getFourCost().add(routeDate.getFourProfit()));
        orderRoute.setPricea(routeDate.getArrangeCost().add(routeDate.getArrangeProfit()));
        
        orderSysService.saveRouteOrder(orderSys, orderRoute, orderInsurance, orderMemberList);
        
	};
	/**
	 * 当地参团生成订单
	 * @param jpro
	 * @throws ParseException 
	 */
	public void saveRoute2(String uid,String languageid,String contactsName,
			String contactsMobile,String remark,JSONObject jproData,String payOrderNo) throws ParseException{
		String routeid = jproData.getString("typeid");       //路线i
        String beginDate = jproData.getString("beginDate");       //开始时间
        String adultNum = jproData.getString("adultNum");       //大人
        String childNum = jproData.getString("childNum");       //小孩
        String oneNum = jproData.getString("oneNum");       //单人间
        String twoNum = jproData.getString("twoNum");       //双人间
        String threeNum = jproData.getString("threeNum");       //三人间
        String fourNum = jproData.getString("fourNum");       //四人间
        String arrangeNum = jproData.getString("arrangeNum");       //配房数量
        String insuranceid = "";
        if(jproData.has("insuranceid")){
        	insuranceid = jproData.getString("insuranceid");       //保险
        }
        String orderMember = jproData.getString("orderMember");       //出游人信息
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = sdf.parse(beginDate);
		 //获取参团
        Route route = routeService.get(routeid);

        OrderSys orderSys = new OrderSys();
        orderSys.setPayOrderNo(payOrderNo);
        OrderRoute orderRoute = new OrderRoute();
        OrderInsurance orderInsurance = new OrderInsurance();
        orderSys.setStatus(1);
        orderSys.setType(5);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
        //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
        orderSys.setFatherid(0);        // 父id
        orderSys.setMemberid(Integer.parseInt(uid));      //下单人
        orderSys.setLanguageid(Integer.parseInt(languageid));    //语言id

        orderSys.setContactsName(contactsName);    //联系人姓名
        orderSys.setContactsMobile(contactsMobile);    //联系人电话
        orderSys.setRemark(remark);            //备注
        orderSys.setPeopleNum(Integer.parseInt(adultNum) + Integer.parseInt(childNum));
        orderSys.setTitle(route.getTitle());            //订单标题
        orderSys.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
        orderSys.setChildNum(Integer.parseInt(childNum));        // 孩子数量
       
        orderSys.setBeginDate(bt);       //开始时间
        orderSys.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
        orderSys.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
        orderSys.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
        orderSys.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
        orderSys.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
        orderSys.setAgentid(route.getAgentid());
        orderSys.setEndCity(route.getEndCityContent());
        
        String image=route.getCarImg();
        if(image!=null) {
        	image=image.split(",")[0];
        }
        orderSys.setImage(image);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        String bt1 = sdf1.format(new Date());
        String orderNo = "CG-" + route.getAgentid() + "-" + bt1 + "[]" + "-" + route.getId();
        orderRoute.setOrderNo(orderNo);  //订单号,后面替换[]
        orderRoute.setAgentid(route.getAgentid());        // 供应商ID
        orderRoute.setRouteid(Integer.parseInt(routeid));        // 常规路线/参团id
        orderRoute.setMemberid(Integer.parseInt(uid));        // 会员ID
        orderRoute.setDay(route.getDayNum()); //订单天数
        orderRoute.setStartCity(route.getEndCity());        // 出发城市
        orderRoute.setStartDate(bt);        // 出发时间
        orderRoute.setAdultNum(Integer.parseInt(adultNum));        // 大人数量
        orderRoute.setChildNum(Integer.parseInt(childNum));        // 孩子数量
        orderRoute.setOneNum(Integer.parseInt(oneNum));        // 单人间数量
        orderRoute.setTwoNum(Integer.parseInt(twoNum));        // 双人间数量
        orderRoute.setThreeNum(Integer.parseInt(threeNum));        // 三人间数量
        orderRoute.setFourNum(Integer.parseInt(fourNum));        // 四人间数量
        orderRoute.setArrangeNum(Integer.parseInt(arrangeNum));        // 配房数量
        //获取出发时间的价格
        RouteDate routeDate = new RouteDate();
        routeDate.setRouteid(Integer.parseInt(routeid));
        routeDate.setPriceDate(bt);
        routeDate = routeDateService.getRouteDate(routeDate);
        orderRoute.setContactsName(contactsName);        // 联系人姓名
        orderRoute.setContactsMobile(contactsMobile);        // 联系人电话
        orderRoute.setRemark(remark);        // 备注
        orderRoute.setStatus(1);        // 订单状态1.待付款2.待确定，3.待出行，4.待评价 5.已完成6.取消订单7.申请退款8退款通过9.退款不通过
        orderRoute.setLanguageId(Integer.parseInt(languageid));        // 语言
        orderRoute.setTitle(route.getTitle());        // 标题
        orderRoute.setSubtitle(route.getSubtitle());        // 副标题
        orderRoute.setInfor(route.getInfor());        // 产品详情
        orderRoute.setNo(route.getNo());            //产品编号
        orderRoute.setCompanyName(route.getCompanyName());//供应商名称
        orderRoute.setType(2);        // 1常规路线  2参团
        orderRoute.setName(route.getTitle());//订单标题


        if (StringUtils.isNotBlank(insuranceid)) {
            //获取保险信息
            Insurance insurance = insuranceService.get(insuranceid);

            orderInsurance.setName(insurance.getName());        // 保险名称
            orderInsurance.setInsuranceid(Integer.parseInt(insuranceid));        // 保险ID
            orderInsurance.setAgentid(insurance.getAgentid());        // 供应商ID

            String orderNo1 = "BX-" + insurance.getAgentid() + "-" + bt1 + "[]" + "-" + insurance.getId();
            orderInsurance.setOrderNo(orderNo1);        // 订单号，后面替换[]

            orderInsurance.setMemberId(Integer.parseInt(uid));        // 下单人id
            //保险费*(成人数+儿童数)
            int num = Integer.parseInt(adultNum) + Integer.parseInt(childNum);
            BigDecimal price = new BigDecimal(num).multiply(insurance.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
            orderInsurance.setPrice(price);
            orderInsurance.setStatus(1);        // 订单状态  1 待付款 2 待确认 3 待出行 4 待点评 5 已完成 6 已取消 7 申请退款 8 退款成功 9 退款失败
            orderInsurance.setContacts(contactsName);        // 联系人
            orderInsurance.setContactMobile(contactsMobile);        // 联系电话
            orderInsurance.setContactRemark(remark);        // 备注
            orderInsurance.setLanguageId(languageid);        // 语言id
            orderInsurance.setNum(num);        // 预定数量
        }

        JSONArray jsonArray = JSONArray.fromObject(orderMember);
        List<OrderMember> orderMemberList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
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

            orderMember1.setChineseName(chineseName);        // 中文姓名
            orderMember1.setEnglishName(englishName);        // 英文姓名
            orderMember1.setCertType(Integer.parseInt(certType));        // 证件类型：1.身份证 2.护照 3.本地ID',
            orderMember1.setCertNo(certNo);        // 证件号
            Date certValidDate1 = sdf.parse(certValidDate);
            orderMember1.setCertValidDate(certValidDate1);        // 有效期
            Date birthday1 = sdf.parse(birthday);
            orderMember1.setBirthday(birthday1);        // 出生年月
            orderMember1.setArea(area);        // 区号
            orderMember1.setMobile(mobile);        // 手机号
            orderMember1.setLanguageId(Integer.parseInt(languageid));        // 语言id
            orderMember1.setType(3);        // 1.租车2.常规路线3.当地参团4.游轮5.景点门票6.当地玩家7.酒店8.保险9.定制10导游'
//			    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
            if (StringUtils.isNotBlank(orderMemberType)) {
                orderMember1.setSaveType(Boolean.parseBoolean(orderMemberType));
            }
            orderMemberList.add(orderMember1);
        }
        BigDecimal insurancePrice = BigDecimal.ZERO;
        if (orderInsurance.getPrice() != null) {
            insurancePrice = orderInsurance.getPrice();
        }
        //路线价格*(成人数+儿童数)+(单房成本+单房利润)*单人间数量+(双房成本+双房利润）*双人间数量+(三房成本+三房利润)*三人间数量
        // +(四房成本+四房利润)*四人间数量+(配房成本+配房利润)*配房数量+保险费*(成人数+儿童数)
        //路线
        //BigDecimal routePrice = route.getPrice().multiply(new BigDecimal(adultNum).add(new BigDecimal(childNum))).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal routePrice =new BigDecimal("0");
        //单人房
        BigDecimal oneRoomPrice = (routeDate.getOneCost()).multiply(new BigDecimal(oneNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //双人房
        BigDecimal twoRoomPrice = (routeDate.getTwoCost()).multiply(new BigDecimal(twoNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //三人房
        BigDecimal threeRoomPrice = (routeDate.getThreeCost()).multiply(new BigDecimal(threeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //四人房
        BigDecimal fourRoomPrice = (routeDate.getFourCost()).setScale(2, BigDecimal.ROUND_HALF_UP);
        //配房价格
        BigDecimal arrangeRoomPrice = (routeDate.getArrangeCost()).multiply(new BigDecimal(arrangeNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
        //价格=路线价格+房间价格
        BigDecimal routeTotalPrice = routePrice.add(oneRoomPrice).add(oneRoomPrice).add(twoRoomPrice).add(threeRoomPrice).add(fourRoomPrice).add(arrangeRoomPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        orderRoute.setPrice(routeTotalPrice);
        //总价格=路线价格+房间价格+保险价格
        BigDecimal totalPrice = routeTotalPrice.add(insurancePrice).setScale(2, BigDecimal.ROUND_HALF_UP);
        orderSys.setPrice(totalPrice);//主订单总价
        
        orderSys.setPrice1(routeDate.getOneCost());
        orderSys.setPrice2(routeDate.getTwoCost());
        orderSys.setPrice3(routeDate.getThreeCost());
        orderSys.setPrice4(routeDate.getFourCost());
        orderSys.setPricea(routeDate.getArrangeCost());
        
        orderRoute.setPrice1(routeDate.getOneCost());
        orderRoute.setPrice2(routeDate.getTwoCost());
        orderRoute.setPrice3(routeDate.getThreeCost());
        orderRoute.setPrice4(routeDate.getFourCost());
        orderRoute.setPricea(routeDate.getArrangeCost());
        
        orderSysService.saveRouteOrder(orderSys, orderRoute, orderInsurance, orderMemberList);
        
	};
	/**
	 * 导游生成订单
	 * @param jpro
	 * @throws ParseException 
	 */
	public void saveGuide(String uid,String languageid,String contactsName,
			String contactsMobile,String remark,JSONObject jproData,String payOrderNo) throws ParseException{
		
		String beginDate = jproData.getString("beginDate");       //开始时间
		String endDate = jproData.getString("endDate");       //结束时间
		String adultNum = jproData.getString("adultNum");       //大人
		String childNum = jproData.getString("childNum");       //小孩
		String guideid= jproData.getString("typeid");//导游Id
		String guideRouteid= "";//导游路线Id
		String orderMember = jproData.getString("orderMember");       //出游人信息
        String insuranceid = "";
        if(jproData.has("insuranceid")){
        	insuranceid = jproData.getString("insuranceid");       //保险
        }
		//获取导游和导游路线 
		Guide guide = guideService.get(guideid);
		GuideRoute guideRoute = null;
		if(StringUtils.isNotBlank(guideRouteid)){
			guideRoute=guideRouteService.get(guideRouteid);
		}
		OrderSys orderSys = new OrderSys();
        orderSys.setPayOrderNo(payOrderNo);
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
		Date bt=sdf.parse(beginDate);
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
//			Double guidePrice =  guideRoute.getPrice()*guideRoute.getDay()
//					*(Integer.parseInt(adultNum)+Integer.parseInt(childNum))
//					+guide.getPrice()*guideRoute.getDay();
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
//			    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
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
		
		orderSysService.saveGuideOrder(orderSys,orderGuide,orderInsurance,orderMemberList);
		
	};
	/**
	 * 游轮生成订单
	 * @param jpro
	 * @throws ParseException 
	 */
	public void saveLinerLine(String uid,String languageid,String contactsName,
			String contactsMobile,String remark,JSONObject jproData,String payOrderNo) throws ParseException{
		
		String linerLineid=jproData.getString("typeid");//邮轮路线Id
		String beginDate = jproData.getString("beginDate");       //开始时间
		String adultNum = jproData.getString("adultNum");       //大人
		String childNum = jproData.getString("childNum");       //小孩
		String linerLine = jproData.getString("linerLine");       //邮轮信息
		String orderMember = jproData.getString("orderMember");       //出游人信息
        String insuranceid = "";
        if(jproData.has("insuranceid")){
        	insuranceid = jproData.getString("insuranceid");       //保险
        }
        JSONObject jpro1 =JSONObject.fromObject(linerLine);
        String roomDetails = jpro1.getString("linerRoomList"); 
		
		//获取邮轮路线
		LinerLine line = linerlineService.get(linerLineid);
		
		OrderSys orderSys = new OrderSys();
        orderSys.setPayOrderNo(payOrderNo);
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
		Date bt=sdf.parse(beginDate);
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
//			    orderLinerRoom.setOrderId(orderId); //'邮轮订单id'(订单保存后添加)
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
//			    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
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
	
		orderSysService.saveLinerOrder(orderSys,orderLiner,orderLinerRoomList,orderInsurance,orderMemberList);
		
	};
	/**
	 * 景点生成订单
	 * @param jpro
	 * @throws ParseException 
	 */
	public void saveScenicSpot(String uid,String languageid,String contactsName,
			String contactsMobile,String remark,JSONObject jproData,String payOrderNo) throws ParseException{

		String beginDate = jproData.getString("beginDate");       //开始时间
		String adultNum = jproData.getString("adultNum");       //大人
		String childNum = jproData.getString("childNum");       //小孩
		String orderMember = jproData.getString("orderMember");       //出游人信息
        String insuranceid = "";
        if(jproData.has("insuranceid")){
        	insuranceid = jproData.getString("insuranceid");       //保险
        }
		String scenicSpotTicket1 = jproData.getString("scenicSpotTicket");       //门票信息
        
		
		JSONObject jpro1 =JSONObject.fromObject(scenicSpotTicket1);
	    String scenicid = jpro1.getString("scenicid");       //景点id
	    String ticketid = jpro1.getString("id"); //门票id
		String ticketNum = Integer.toString(Integer.parseInt(adultNum)+Integer.parseInt(childNum));       //门票数量
		//获取景点 
		ScenicSpot scenicSpot = scenicSpotService.get(scenicid);
		//查询门票信息
		ScenicSpotTicket scenicSpotTicket =scenicSpotTicketService.get(ticketid);
		
		OrderSys orderSys = new OrderSys();
        orderSys.setPayOrderNo(payOrderNo);
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
		Date bt=sdf.parse(beginDate);
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
//			    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
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
		
		orderSysService.saveScenicOrder(orderSys,orderScenicSpot,orderInsurance,orderMemberList);
		
	};
	/**
	 * 导游路线生成订单
	 * @param jpro
	 * @throws ParseException 
	 */
	public void saveGuideRoute(String uid,String languageid,String contactsName,
			String contactsMobile,String remark,JSONObject jproData,String payOrderNo) throws ParseException{
		String beginDate = jproData.getString("beginDate");       //开始时间
		String endDate ="";
		if(jproData.has("endDate")){
			endDate = jproData.getString("endDate");       //结束时间
		}
		String adultNum = jproData.getString("adultNum");       //大人
		String childNum = jproData.getString("childNum");       //小孩
		String orderMember = jproData.getString("orderMember");       //出游人信息
        String insuranceid = "";
        if(jproData.has("insuranceid")){
        	insuranceid = jproData.getString("insuranceid");       //保险
        }
		String guideRoute1= jproData.getString("guideRoute");//导游路线信息
        

        JSONObject jpro1 =JSONObject.fromObject(guideRoute1);
		String guideid= jpro1.getString("guideid");//导游Id
		String guideRouteid= jpro1.getString("id");//导游路线Id
		//获取导游和导游路线 
		Guide guide = guideService.get(guideid);
		GuideRoute guideRoute = null;
		if(StringUtils.isNotBlank(guideRouteid)){
			guideRoute=guideRouteService.get(guideRouteid);
		}
		OrderSys orderSys = new OrderSys();
        orderSys.setPayOrderNo(payOrderNo);
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
		Date bt=sdf.parse(beginDate);
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
//			Double guidePrice =  guideRoute.getPrice()*guideRoute.getDay()
//					*(Integer.parseInt(adultNum)+Integer.parseInt(childNum))
//					+guide.getPrice()*guideRoute.getDay();
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
//			    orderMember1.setTypeId(typeId);		// 根据type 对应不同表的 orderid(orderSys)
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
		
		orderSysService.saveGuideOrder(orderSys,orderGuide,orderInsurance,orderMemberList);
		
	};
	
	public  String date2String(Date date) {

		DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return format1.format(date);

	}
}
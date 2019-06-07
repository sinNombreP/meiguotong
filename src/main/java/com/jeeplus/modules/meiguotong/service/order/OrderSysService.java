/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.order;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.app.AppErrorUtils;
import com.jeeplus.modules.meiguotong.entity.agentextract.AgentExtract;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarDate;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.meiguotong.mapper.hotel.OrderHotelMapper;
import com.jeeplus.modules.meiguotong.mapper.order.OrderSysMapper;
import com.jeeplus.modules.meiguotong.mapper.ordercar.OrderCarDateMapper;
import com.jeeplus.modules.meiguotong.mapper.ordercar.OrderCarMapper;
import com.jeeplus.modules.meiguotong.mapper.orderguide.OrderGuideMapper;
import com.jeeplus.modules.meiguotong.mapper.orderhotelroom.OrderHotelRoomMapper;
import com.jeeplus.modules.meiguotong.mapper.orderscenicspot.OrderScenicSpotMapper;
import com.jeeplus.modules.meiguotong.mapper.travel.OrderTravelBusinessMapper;
import com.jeeplus.modules.meiguotong.mapper.travel.OrderTravelDetailsMapper;
import com.jeeplus.modules.meiguotong.service.hotel.OrderHotelService;
import com.jeeplus.modules.meiguotong.service.insurance.OrderInsuranceService;
import com.jeeplus.modules.meiguotong.service.ordercar.OrderCarService;
import com.jeeplus.modules.meiguotong.service.orderguide.OrderGuideService;
import com.jeeplus.modules.meiguotong.service.orderliner.OrderLinerService;
import com.jeeplus.modules.meiguotong.service.orderlinerroom.OrderLinerRoomService;
import com.jeeplus.modules.meiguotong.service.ordermember.OrderMemberService;
import com.jeeplus.modules.meiguotong.service.orderscenicspot.OrderScenicSpotService;
import com.jeeplus.modules.meiguotong.service.product.RouteDateService;
import com.jeeplus.modules.meiguotong.service.travel.OrderTravelBusinessService;
import com.jeeplus.modules.sys.entity.member.MemberContacts;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;
import com.jeeplus.modules.sys.service.member.MemberContactsService;
import com.jeeplus.modules.sys.service.sysUser.SysUserService;

import freemarker.core.ReturnInstruction.Return;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单关联表Service
 *
 * @author PSZ
 * @version 2018-08-17
 */
@Service
@Transactional(readOnly = true)
public class OrderSysService extends CrudService<OrderSysMapper, OrderSys> {
    @Autowired
    private OrderRouteService orderRouteService;
    @Autowired
    private OrderInsuranceService orderInsuranceService;
    @Autowired
    private OrderMemberService orderMemberService;
    @Autowired
    private OrderScenicSpotService orderScenicSpotService;
    @Autowired
    private MemberContactsService memberContactsService;
    @Autowired
    private OrderCarService orderCarService;
    @Autowired
    private OrderLinerService orderLinerService;
    @Autowired
    private OrderLinerRoomService orderLinerRoomService;
    @Autowired
    private OrderGuideService orderGuideService;
    @Autowired
    private OrderCarMapper orderCarMapper;
    @Autowired
    private  SysUserService sysUserService;
    @Autowired
    private  OrderTravelBusinessMapper orderTravelBusinessMapper;
    @Autowired
    private  OrderTravelDetailsMapper orderTravelDetailsMapper;
    @Autowired
    private  OrderGuideMapper orderGuideMapper;
    @Autowired
    private  OrderHotelMapper orderHotelMapper;
    @Autowired
    private OrderSysMapper orderSysMapper;
    @Autowired
    private  OrderScenicSpotMapper orderScenicSpotMapper;
    @Autowired
    private  OrderHotelRoomMapper orderHotelRoomMapper;
    @Autowired
    private RouteDateService routeDateService;
    @Autowired
    private OrderCarDateMapper orderCarDateMapper;
    @Autowired
    private OrderHotelService orderHotelService;
    @Autowired
    private OrderTravelBusinessService orderTravelBusinessService;
    
     

    public OrderSys get(String id) {
        return super.get(id);
    }

    public List<OrderSys> findList(OrderSys orderSys) {
        return super.findList(orderSys);
    }
    /**
     * 根据支付订单号获取支付列表
     * @param orderSys
     * @return
     */
    public List<OrderSys> getListByPayOrderNo(OrderSys orderSys) {
        return mapper.getListByPayOrderNo(orderSys);
    }
    
    /**
     * 获取我的订单接口
     *
     * @param page
     * @param orderSys
     * @return
     */
    public Page<OrderSys> myOrder(Page<OrderSys> page, OrderSys orderSys) {
        dataRuleFilter(orderSys);
        orderSys.setPage(page);
        page.setList(mapper.myOrder(orderSys));
        return page;
    }
    /**
     * 获取我的订单接口
     *
     * @param page
     * @param orderSys
     * @return
     */
    public Page<OrderSys> myOrderAfter(Page<OrderSys> page, OrderSys orderSys) {
        dataRuleFilter(orderSys);
        orderSys.setPage(page);
        page.setList(mapper.myOrderAfter(orderSys));
        return page;
    }
    /**
     * 获取我的非定制订单接口
     *
     * @param page
     * @param orderSys
     * @return
     */
    public List<OrderSys> getOrderSys(OrderSys orderSys) {
        List<OrderSys> list = mapper.getOrderSys(orderSys);
        return list;
    }

    /**
     * 获取我的定制订单接口
     *
     * @param page
     * @param orderSys
     * @return
     */
    public List<OrderSys> getOrderSysList(OrderSys orderSys) {
        List<OrderSys> list = mapper.getOrderSysList(orderSys);
        return list;
    }

    public Page<OrderSys> findPage(Page<OrderSys> page, OrderSys orderSys) {
        return super.findPage(page, orderSys);
    }

    @Transactional(readOnly = false)
    public void save(OrderSys orderSys, OrderGuide orderGuide) {
        super.save(orderSys);
        if (orderSys.getType() == 5) {
            OrderSys orderSys1 = new OrderSys();
            orderSys1.setType(8);
            orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));
            orderSys1.setOrderid(Integer.parseInt(orderGuide.getId()));
            super.save(orderSys1);
        }
    }

    /**
     * 保存常规路线/当地参团订单
     *
     * @param orderSys
     * @param orderRoute
     * @param orderInsurance
     * @param orderMemberList
     */
    @Transactional(readOnly = false)
    public String saveRouteOrder(OrderSys orderSys, OrderRoute orderRoute,
                                 OrderInsurance orderInsurance, List<OrderMember> orderMemberList) {



        //保存主订单
        save(orderSys);
        //保存常规路线/参团订单（完善订单编号）
        orderRoute.setOrderNo(orderRoute.getOrderNo().replace("[]", orderSys.getId()));
        orderRouteService.save(orderRoute);
        //更新已售数量
        RouteDate routeDate=new RouteDate();
        routeDate.setNum(orderRoute.getAdultNum()+orderRoute.getChildNum());
        routeDate.setRouteid(orderRoute.getRouteid());
        routeDate.setPriceDate(orderSys.getBeginDate());
        routeDateService.changeNumByDate(routeDate);

        SysUser  sysUser =sysUserService.findByAgentId(orderSys.getAgentid());
        //修改主订单
        if(sysUser != null){
            orderSys.setAgent(sysUser.getCompanyName());
        }
        orderSys.setOrderNo(orderRoute.getOrderNo());
        save(orderSys);

        //保存常规路线/参团子订单
        OrderSys orderSys1 = new OrderSys();
        BeanUtils.copyProperties(orderSys, orderSys1);
        orderSys1.setId(null);
        // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
        //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
        orderSys1.setType(orderSys.getType());
        orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
        orderSys1.setOrderid(Integer.parseInt(orderRoute.getId()));        // 订单ID
        orderSys1.setMemberid(orderSys.getMemberid());      //下单人
        orderSys1.setLanguageid(orderSys.getLanguageid());    //语言id\
        save(orderSys1);

        OrderRoute orderRouteOld = new OrderRoute();
        orderRouteOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
        orderRouteOld.setOrderSys2(Integer.parseInt(orderSys1.getId()));
        orderRouteOld.setId(orderRoute.getId());
        orderRouteService.updateObject(orderRouteOld);

        if (orderInsurance.getOrderNo() != null) {
            //保存保险订单（完善订单编号）
            orderInsurance.setOrderNo(orderInsurance.getOrderNo().replace("[]", orderSys.getId()));
            orderInsuranceService.save(orderInsurance);
            //保存保险订单子订单
            OrderSys orderSys2 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys2);
            orderSys2.setCreateDate(orderSys.getCreateDate());
            orderSys2.setId(null);
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setType(10);    // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys2.setOrderid(Integer.parseInt(orderInsurance.getId()));        // 订单ID
            orderSys2.setMemberid(orderSys.getMemberid());      //下单人
            orderSys2.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setOrderNo(orderInsurance.getOrderNo());
            save(orderSys2);

            OrderInsurance orderInsuranceOld = new OrderInsurance();
            orderInsuranceOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderInsuranceOld.setOrderSys2(Integer.parseInt(orderSys2.getId()));
            orderInsuranceOld.setId(orderInsurance.getId());
            orderInsuranceService.updateObject(orderInsuranceOld);

        }

        //保存出游人信息
        for (OrderMember a : orderMemberList) {
            a.setTypeId(Integer.parseInt(orderSys.getId()));
            orderMemberService.save(a);
            if (a.getSaveType() != null && a.getSaveType()) {
                //保存常用联系人
                MemberContacts memberContacts = new MemberContacts();
                memberContacts.setMemberid(orderSys.getMemberid());        // 用户id
                memberContacts.setChineseName(a.getChineseName());        // 中文姓名
                memberContacts.setEnglishName(a.getEnglishName());        // 英文姓名
                memberContacts.setCertType(a.getCertType());    // 证件类型  1.身份证2.护照
                memberContacts.setCertNo(a.getCertNo());        // 证件号
                memberContacts.setCertValidDate(a.getCertValidDate());        // 有效期
                memberContacts.setBirthday(a.getBirthday());        // 出生年月
                memberContacts.setArea(a.getArea());        // 区号
                memberContacts.setMobile(a.getMobile());        // 手机号
                memberContactsService.save(memberContacts);
            }
        }
        return orderSys.getId();
    }

    /**
     * 保存景点订单
     *
     * @param orderSys
     * @param orderScenicSpot
     * @param orderInsurance
     * @param orderMemberList
     */
    @Transactional(readOnly = false)
    public String saveScenicOrder(OrderSys orderSys, OrderScenicSpot orderScenicSpot,
                                  OrderInsurance orderInsurance, List<OrderMember> orderMemberList) {
        //保存主订单
        save(orderSys);
        //保存景点订单（完善订单编号）
        orderScenicSpot.setOrderNo(orderScenicSpot.getOrderNo().replace("[]", orderSys.getId()));
        orderScenicSpotService.save(orderScenicSpot);
        //保存景点子订单
        OrderSys orderSys1 = new OrderSys();
        BeanUtils.copyProperties(orderSys, orderSys1);
        orderSys1.setId(null);
        orderSys1.setType(orderSys.getType());// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
        //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
        orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
        orderSys1.setOrderid(Integer.parseInt(orderScenicSpot.getId()));        // 订单ID
        orderSys1.setMemberid(orderSys.getMemberid());      //下单人
        orderSys1.setLanguageid(orderSys.getLanguageid());    //语言id
        save(orderSys1);
        
        OrderScenicSpot orderScenicSpot1 = new OrderScenicSpot();
        orderScenicSpot1.setOrderSys1(Integer.parseInt(orderSys.getId()));
        orderScenicSpot1.setOrderSys2(Integer.parseInt(orderSys1.getId()));
        orderScenicSpot1.setId(orderScenicSpot.getId());
        orderScenicSpotService.updateObject(orderScenicSpot1);
        
        if (orderInsurance.getOrderNo() != null) {
            //保存保险订单（完善订单编号）
            orderInsurance.setOrderNo(orderInsurance.getOrderNo().replace("[]", orderSys.getId()));
            orderInsuranceService.save(orderInsurance);
            //保存保险订单子订单
            OrderSys orderSys2 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys2);
            orderSys2.setCreateDate(orderSys.getCreateDate());
            orderSys2.setId(null);
            orderSys2.setType(10);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys2.setOrderid(Integer.parseInt(orderInsurance.getId()));        // 订单ID
            orderSys2.setMemberid(orderSys.getMemberid());      //下单人
            orderSys2.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setOrderNo(orderInsurance.getOrderNo());
            save(orderSys2);
            
            OrderInsurance orderInsuranceOld = new OrderInsurance();
            orderInsuranceOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderInsuranceOld.setOrderSys2(Integer.parseInt(orderSys2.getId()));
            orderInsuranceOld.setId(orderInsurance.getId());
            orderInsuranceService.updateObject(orderInsuranceOld);
        }

        //保存出游人信息
        for (OrderMember a : orderMemberList) {
            a.setTypeId(Integer.parseInt(orderSys.getId()));
            orderMemberService.save(a);
            if (a.getSaveType() != null && a.getSaveType()) {
                //保存常用联系人
                MemberContacts memberContacts = new MemberContacts();
                memberContacts.setMemberid(orderSys.getMemberid());        // 用户id
                memberContacts.setChineseName(a.getChineseName());        // 中文姓名
                memberContacts.setEnglishName(a.getEnglishName());        // 英文姓名
                memberContacts.setCertType(a.getCertType());    // 证件类型  1.身份证2.护照
                memberContacts.setCertNo(a.getCertNo());        // 证件号
                memberContacts.setCertValidDate(a.getCertValidDate());        // 有效期
                memberContacts.setBirthday(a.getBirthday());        // 出生年月
                memberContacts.setArea(a.getArea());        // 区号
                memberContacts.setMobile(a.getMobile());        // 手机号
                memberContactsService.save(memberContacts);
            }
        }
        return orderSys.getId();
    }
    /**
     * 保存包车租车订单
     *
     * @param orderSys
     * @param orderScenicSpot
     * @param orderInsurance
     * @param orderMemberList
     */
    @Transactional(readOnly = false)
    public String saveCarOrderOne(OrderSys orderSys, List<OrderCar> orderCarList,
                               OrderInsurance orderInsurance, List<OrderMember> orderMemberList,
                               List<OrderCarDate> orderCarDateList,List<OrderHotel> orderHotelList,
                               OrderGuide orderGuide) {
        //保存主订单
        save(orderSys);
        //保存汽车订单（完善订单编号）
        for (OrderCar a : orderCarList) {
            a.setOrderNo(a.getOrderNo().replace("[]", orderSys.getId()));
            orderCarService.save(a);
            //保存汽车子订单
            OrderSys orderSys1 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys1);
            orderSys1.setId(null);
            orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys1.setOrderid(Integer.parseInt(a.getId()));        // 订单ID
            orderSys1.setMemberid(orderSys.getMemberid());      //下单人
            orderSys1.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys1.setTitle(a.getName());
            orderSys1.setOrderNo(a.getOrderNo());
            orderSys1.setAgentid(a.getAgentid());
            save(orderSys1);
            
            OrderCar orderCar = new OrderCar();
            orderCar.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderCar.setOrderSys2(Integer.parseInt(orderSys1.getId()));
            orderCar.setId(a.getId());
            orderCarService.updateObject(orderCar);
        }
        if(orderCarDateList!=null&&orderCarDateList.size()>0){
        	for(OrderCarDate a:orderCarDateList){
        		a.setOrderid(Integer.parseInt(orderSys.getId()));
        		orderCarDateMapper.insert(a);
        	}
        }
		//保存酒店订单
		if(orderHotelList!=null&&orderHotelList.size()>0){
			for(OrderHotel a:orderHotelList){
				orderHotelMapper.insert(a);
				//保存酒店房间
				List<OrderHotelRoom> list = a.getOrderHotelRoomList();
				for(OrderHotelRoom b:list){
					b.setOrderHotelId(Integer.parseInt(a.getId()));
					b.preInsertNoAgent();
					orderHotelRoomMapper.insert(b);
				}
				//保存酒店子订单
				OrderSys orderSysHotel = new OrderSys();
				BeanUtils.copyProperties(orderSys, orderSysHotel);
				orderSysHotel.setId(null);
				orderSysHotel.setType(9);		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
							//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
				orderSysHotel.setFatherid(Integer.parseInt(orderSys.getId()));		// 父id
				orderSysHotel.setOrderid(Integer.parseInt(a.getId()));		// 订单ID
				orderSysHotel.setTitle(a.getName());
				orderSysHotel.setOrderNo(a.getOrderNo());
				orderSysHotel.setAgentid(a.getAgentid());
				save(orderSysHotel);
				//修改OrderHotel orderSys1主订单id,orderSys2对应业务的订单id
				OrderHotel orderHotel = new OrderHotel();
				orderHotel.setId(a.getId());
				orderHotel.setOrderSys1(Integer.parseInt(orderSys.getId()));
				orderHotel.setOrderSys2(Integer.parseInt(orderSysHotel.getId()));
				orderHotelMapper.updateObject(orderHotel);
			}
		}
		if(orderGuide!=null&&orderGuide.getOrderNo()!=null){
			//保存导游订单
			orderGuide.setOrderNo(orderGuide.getOrderNo().replace("[]", orderSys.getId()));
			orderGuideMapper.insert(orderGuide);
			//保存导游子订单
			OrderSys orderSys3 = new OrderSys();
	        BeanUtils.copyProperties(orderSys, orderSys3);
	        orderSys3.setId(null);
	        orderSys3.setType(8);		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
	        //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
	        orderSys3.setFatherid(Integer.parseInt(orderSys.getId()));		// 父id
	        orderSys3.setOrderid(Integer.parseInt(orderGuide.getId()));		// 订单ID
	        orderSys3.setTitle(orderGuide.getName());
	        orderSys3.setOrderNo(orderGuide.getOrderNo());
			save(orderSys3);
			//修改orderGuide orderSys1主订单id,orderSys2对应业务的订单id
			OrderGuide orderGuide1 = new OrderGuide();
			orderGuide1.setId(orderGuide.getId());
			orderGuide1.setOrderSys1(Integer.parseInt(orderSys.getId()));
			orderGuide1.setOrderSys2(Integer.parseInt(orderSys3.getId()));
			orderGuideMapper.updateObject(orderGuide1);
		}
        if (orderInsurance.getOrderNo() != null) {
            //保存保险订单（完善订单编号）
            orderInsurance.setOrderNo(orderInsurance.getOrderNo().replace("[]", orderSys.getId()));
            orderInsuranceService.save(orderInsurance);
            //保存保险订单子订单
            OrderSys orderSys2 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys2);
            orderSys2.setCreateDate(orderSys.getCreateDate());
            orderSys2.setId(null);
            orderSys2.setType(10);    // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys2.setOrderid(Integer.parseInt(orderInsurance.getId()));        // 订单ID
            orderSys2.setMemberid(orderSys.getMemberid());      //下单人
            orderSys2.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setOrderNo(orderInsurance.getOrderNo());
            save(orderSys2);
            
            OrderInsurance orderInsuranceOld = new OrderInsurance();
            orderInsuranceOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderInsuranceOld.setOrderSys2(Integer.parseInt(orderSys2.getId()));
            orderInsuranceOld.setId(orderInsurance.getId());
            orderInsuranceService.updateObject(orderInsuranceOld);
        }

        //保存出游人信息
        for (OrderMember a : orderMemberList) {
            a.setTypeId(Integer.parseInt(orderSys.getId()));
            orderMemberService.save(a);
            if (a.getSaveType() != null && a.getSaveType()) {
                //保存常用联系人
                MemberContacts memberContacts = new MemberContacts();
                memberContacts.setMemberid(orderSys.getMemberid());        // 用户id
                memberContacts.setChineseName(a.getChineseName());        // 中文姓名
                memberContacts.setEnglishName(a.getEnglishName());        // 英文姓名
                memberContacts.setCertType(a.getCertType());    // 证件类型  1.身份证2.护照
                memberContacts.setCertNo(a.getCertNo());        // 证件号
                memberContacts.setCertValidDate(a.getCertValidDate());        // 有效期
                memberContacts.setBirthday(a.getBirthday());        // 出生年月
                memberContacts.setArea(a.getArea());        // 区号
                memberContacts.setMobile(a.getMobile());        // 手机号
                memberContactsService.save(memberContacts);
            }
        }
        return orderSys.getId();
    }
    /**
     * 保存短程接送/接送机汽车订单
     *
     * @param orderSys
     * @param orderScenicSpot
     * @param orderInsurance
     * @param orderMemberList
     */
    @Transactional(readOnly = false)
    public String saveCarOrder(OrderSys orderSys, List<OrderCar> orderCarList,
                               OrderInsurance orderInsurance, List<OrderMember> orderMemberList) {
        //保存主订单
        save(orderSys);
        //保存汽车订单（完善订单编号）
        for (OrderCar a : orderCarList) {
            a.setOrderNo(a.getOrderNo().replace("[]", orderSys.getId()));
            orderCarService.save(a);
            //保存汽车子订单
            OrderSys orderSys1 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys1);
            orderSys1.setId(null);
            orderSys1.setType(orderSys.getType());// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys1.setOrderid(Integer.parseInt(a.getId()));        // 订单ID
            orderSys1.setMemberid(orderSys.getMemberid());      //下单人
            orderSys1.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys1.setOrderNo(a.getOrderNo());
            save(orderSys1);
            
            OrderCar orderCar = new OrderCar();
            orderCar.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderCar.setOrderSys2(Integer.parseInt(orderSys1.getId()));
            orderCar.setId(a.getId());
            orderCarService.updateObject(orderCar);
        }

        if (orderInsurance.getOrderNo() != null) {
            //保存保险订单（完善订单编号）
            orderInsurance.setOrderNo(orderInsurance.getOrderNo().replace("[]", orderSys.getId()));
            orderInsuranceService.save(orderInsurance);
            //保存保险订单子订单
            OrderSys orderSys2 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys2);
            orderSys2.setCreateDate(orderSys.getCreateDate());
            orderSys2.setId(null);
            orderSys2.setType(10);    // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys2.setOrderid(Integer.parseInt(orderInsurance.getId()));        // 订单ID
            orderSys2.setMemberid(orderSys.getMemberid());      //下单人
            orderSys2.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setOrderNo(orderInsurance.getOrderNo());
            save(orderSys2);
            
            OrderInsurance orderInsuranceOld = new OrderInsurance();
            orderInsuranceOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderInsuranceOld.setOrderSys2(Integer.parseInt(orderSys2.getId()));
            orderInsuranceOld.setId(orderInsurance.getId());
            orderInsuranceService.updateObject(orderInsuranceOld);
        }

        //保存出游人信息
        for (OrderMember a : orderMemberList) {
            a.setTypeId(Integer.parseInt(orderSys.getId()));
            orderMemberService.save(a);
            if (a.getSaveType() != null && a.getSaveType()) {
                //保存常用联系人
                MemberContacts memberContacts = new MemberContacts();
                memberContacts.setMemberid(orderSys.getMemberid());        // 用户id
                memberContacts.setChineseName(a.getChineseName());        // 中文姓名
                memberContacts.setEnglishName(a.getEnglishName());        // 英文姓名
                memberContacts.setCertType(a.getCertType());    // 证件类型  1.身份证2.护照
                memberContacts.setCertNo(a.getCertNo());        // 证件号
                memberContacts.setCertValidDate(a.getCertValidDate());        // 有效期
                memberContacts.setBirthday(a.getBirthday());        // 出生年月
                memberContacts.setArea(a.getArea());        // 区号
                memberContacts.setMobile(a.getMobile());        // 手机号
                memberContactsService.save(memberContacts);
            }
        }
        return orderSys.getId();
    }

    /**
     * 保存定制订单
     *
     * @param orderSys
     * @param orderScenicSpot
     * @param orderInsurance
     * @param orderMemberList
     */
    @Transactional(readOnly = false)
    public String saveTravelOrder(OrderSys orderSys, OrderTravelBusiness orderTravelBusiness,
                                  List<OrderTravelDetails> orderTravelDetailsList, List<OrderCar> orderCarList,
                                  List<OrderHotel> orderHotelList, OrderGuide orderGuide,
                                  OrderInsurance orderInsurance, List<OrderMember> orderMemberList) {
        
		//保存主订单
		save(orderSys);
		//保存汽车订单（完善订单编号）
		for(OrderCar a:orderCarList){
			a.setOrderNo(a.getOrderNo().replace("[]", orderSys.getId()));
			orderCarService.save(a);
			//保存汽车子订单
			OrderSys orderSys1 = new OrderSys();
			BeanUtils.copyProperties(orderSys, orderSys1);
	        orderSys1.setId(null);
			orderSys1.setType(15);		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
						//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游15定制租车',
			orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));		// 父id
			orderSys1.setOrderid(Integer.parseInt(a.getId()));		// 订单ID
			orderSys1.setMemberid(orderSys.getMemberid());      //下单人
			orderSys1.setLanguageid(orderSys.getLanguageid());    //语言id
			orderSys1.setTitle(a.getName());
			save(orderSys1);
			//修改OrderCar orderSys1主订单id,orderSys2对应业务的订单id
			OrderCar orderCar = new OrderCar();
			orderCar.setId(a.getId());
			orderCar.setOrderSys1(Integer.parseInt(orderSys.getId()));
			orderCar.setOrderSys2(Integer.parseInt(orderSys1.getId()));
			orderCarMapper.updateObject(orderCar);
		}
		//保存定制订单
		orderTravelBusiness.setOrderNo(orderTravelBusiness.getOrderNo().replace("[]", orderSys.getId()));
		orderTravelBusinessMapper.insert(orderTravelBusiness);
		//保存定制子订单
		OrderSys orderSys1 = new OrderSys();
        BeanUtils.copyProperties(orderSys, orderSys1);
        orderSys1.setId(null);
		orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));		// 父id
		orderSys1.setOrderid(Integer.parseInt(orderTravelBusiness.getId()));		// 订单ID
		save(orderSys1);
		//修改orderTravelBusiness orderSys1主订单id,orderSys2对应业务的订单id
		OrderTravelBusiness orderTravelBusiness1 = new OrderTravelBusiness();
		orderTravelBusiness1.setId(orderTravelBusiness.getId());
		orderTravelBusiness1.setOrderSys1(Integer.parseInt(orderSys.getId()));
		orderTravelBusiness1.setOrderSys2(Integer.parseInt(orderSys1.getId()));
		orderTravelBusinessMapper.updateObject(orderTravelBusiness1);
		//保存定制详情
		if(orderTravelDetailsList!=null&&orderTravelDetailsList.size()>0){
			for(OrderTravelDetails a:orderTravelDetailsList){
				if(a.getOrderScenicSpot()!=null){
					//保存景点订单
					OrderScenicSpot orderScenicSpot = a.getOrderScenicSpot();
					orderScenicSpot.setOrderNo(orderScenicSpot.getOrderNo().replace("[]", orderSys.getId()));
					orderScenicSpot.preInsertNoAgent();
					orderScenicSpotMapper.insert(orderScenicSpot);
					//保存景点子订单
					OrderSys orderSys2 = new OrderSys();
			        BeanUtils.copyProperties(orderSys, orderSys2);
			        orderSys2.setId(null);
			        orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));		// 父id
			        orderSys2.setOrderid(Integer.parseInt(orderScenicSpot.getId()));		// 订单ID
					save(orderSys2);
					//修改orderScenicSpot orderSys1主订单id,orderSys2对应业务的订单id
					OrderScenicSpot orderScenicSpot1 = new OrderScenicSpot();
					orderScenicSpot1.setId(orderScenicSpot.getId());
					orderScenicSpot1.setOrderSys1(Integer.parseInt(orderSys.getId()));
					orderScenicSpot1.setOrderSys2(Integer.parseInt(orderSys2.getId()));
					orderScenicSpotMapper.updateObject(orderScenicSpot1);
					
					a.setOrderScenicId(Integer.parseInt(orderScenicSpot.getId()));
				};
				a.setOrderTrvelId(Integer.parseInt(orderTravelBusiness.getId()));
				orderTravelDetailsMapper.insert(a);
			}
		}
		//保存酒店订单
		if(orderHotelList!=null&&orderHotelList.size()>0){
			for(OrderHotel a:orderHotelList){
				orderHotelMapper.insert(a);
				//保存酒店房间
				List<OrderHotelRoom> list = a.getOrderHotelRoomList();
				for(OrderHotelRoom b:list){
					b.setOrderHotelId(Integer.parseInt(a.getId()));
					b.preInsertNoAgent();
					orderHotelRoomMapper.insert(b);
				}
				//保存酒店子订单
				OrderSys orderSysHotel = new OrderSys();
				BeanUtils.copyProperties(orderSys, orderSysHotel);
				orderSysHotel.setId(null);
				orderSysHotel.setType(9);		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
							//8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
				orderSysHotel.setFatherid(Integer.parseInt(orderSys.getId()));		// 父id
				orderSysHotel.setOrderid(Integer.parseInt(a.getId()));		// 订单ID
				orderSysHotel.setTitle(a.getName());
				save(orderSysHotel);
				//修改OrderHotel orderSys1主订单id,orderSys2对应业务的订单id
				OrderHotel orderHotel = new OrderHotel();
				orderHotel.setId(a.getId());
				orderHotel.setOrderSys1(Integer.parseInt(orderSys.getId()));
				orderHotel.setOrderSys2(Integer.parseInt(orderSysHotel.getId()));
				orderHotelMapper.updateObject(orderHotel);
			}
		}
		if(orderGuide!=null&&orderGuide.getOrderNo()!=null){
			//保存导游订单
			orderGuide.setOrderNo(orderGuide.getOrderNo().replace("[]", orderSys.getId()));
			orderGuideMapper.insert(orderGuide);
			//保存导游子订单
			OrderSys orderSys3 = new OrderSys();
	        BeanUtils.copyProperties(orderSys, orderSys3);
	        orderSys3.setId(null);
	        orderSys3.setType(8);		// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
	        //8.当地玩家9.酒店10.保险11.旅游定制13.商务定制14.商务旅游',
	        orderSys3.setFatherid(Integer.parseInt(orderSys.getId()));		// 父id
	        orderSys3.setOrderid(Integer.parseInt(orderGuide.getId()));		// 订单ID
	        orderSys3.setTitle(orderGuide.getName());
			save(orderSys3);
			//修改orderGuide orderSys1主订单id,orderSys2对应业务的订单id
			OrderGuide orderGuide1 = new OrderGuide();
			orderGuide1.setId(orderGuide.getId());
			orderGuide1.setOrderSys1(Integer.parseInt(orderSys.getId()));
			orderGuide1.setOrderSys2(Integer.parseInt(orderSys3.getId()));
			orderGuideMapper.updateObject(orderGuide1);
		}
		
		
        if (orderInsurance.getOrderNo() != null) {
            //保存保险订单（完善订单编号）
            orderInsurance.setOrderNo(orderInsurance.getOrderNo().replace("[]", orderSys.getId()));
            orderInsuranceService.save(orderInsurance);
            //保存保险订单子订单
            OrderSys orderSys2 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys2);
            orderSys2.setCreateDate(orderSys.getCreateDate());
            orderSys2.setId(null);
            orderSys2.setType(10);// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys2.setOrderid(Integer.parseInt(orderInsurance.getId()));        // 订单ID
            orderSys2.setMemberid(orderSys.getMemberid());      //下单人
            orderSys2.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setOrderNo(orderInsurance.getOrderNo());
            save(orderSys2);
            
            OrderInsurance orderInsuranceOld = new OrderInsurance();
            orderInsuranceOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderInsuranceOld.setOrderSys2(Integer.parseInt(orderSys2.getId()));
            orderInsuranceOld.setId(orderInsurance.getId());
            orderInsuranceService.updateObject(orderInsuranceOld);
        }

        //保存出游人信息
        for (OrderMember a : orderMemberList) {
            a.setTypeId(Integer.parseInt(orderSys.getId()));
            orderMemberService.save(a);
            if (a.getSaveType() != null && a.getSaveType()) {
                //保存常用联系人
                MemberContacts memberContacts = new MemberContacts();
                memberContacts.setMemberid(orderSys.getMemberid());        // 用户id
                memberContacts.setChineseName(a.getChineseName());        // 中文姓名
                memberContacts.setEnglishName(a.getEnglishName());        // 英文姓名
                memberContacts.setCertType(a.getCertType());    // 证件类型  1.身份证2.护照
                memberContacts.setCertNo(a.getCertNo());        // 证件号
                memberContacts.setCertValidDate(a.getCertValidDate());        // 有效期
                memberContacts.setBirthday(a.getBirthday());        // 出生年月
                memberContacts.setArea(a.getArea());        // 区号
                memberContacts.setMobile(a.getMobile());        // 手机号
                memberContactsService.save(memberContacts);
            }
        }
        return orderSys.getId();
    }

    /**
     * 保存邮轮订单
     *
     * @param orderSys
     * @param orderRoute
     * @param orderInsurance
     * @param orderMemberList
     */
    @Transactional(readOnly = false)
    public String saveLinerOrder(OrderSys orderSys, OrderLiner orderLiner,
                                 List<OrderLinerRoom> orderLinerRoomList,
                                 OrderInsurance orderInsurance, List<OrderMember> orderMemberList) {
        //保存主订单
    	orderSys.setStatus(1);
        save(orderSys);
        //保存邮轮订单（完善订单编号）
        orderLiner.setOrderNo(orderLiner.getOrderNo().replace("[]", orderSys.getId()));
        orderLiner.setOrderSys1(Integer.parseInt(orderSys.getId()));
        orderLinerService.save(orderLiner);
        
        SysUser  sysUser =sysUserService.findByAgentId(orderSys.getAgentid());
        //修改主订单
        if(sysUser != null){
            orderSys.setAgent(sysUser.getCompanyName());
        }
        orderSys.setOrderNo(orderLiner.getOrderNo());
        save(orderSys);
        
        
        //保存邮轮子订单
        OrderSys orderSys1 = new OrderSys();
        BeanUtils.copyProperties(orderSys, orderSys1);
        orderSys1.setId(null);
        orderSys1.setType(orderSys.getType());// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
        //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
        orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
        orderSys1.setOrderid(Integer.parseInt(orderLiner.getId()));        // 订单ID
        orderSys1.setMemberid(orderSys.getMemberid());      //下单人
        orderSys1.setLanguageid(orderSys.getLanguageid());    //语言id
        save(orderSys1);
        
        orderLiner.setOrderSys2(Integer.parseInt(orderSys1.getId()));
        orderLinerService.updateObject(orderLiner);
        


        //保存邮轮订单房间信息
        if (orderLinerRoomList != null && orderLinerRoomList.size() > 0) {
            for (OrderLinerRoom a : orderLinerRoomList) {
                a.setOrderId(Integer.parseInt(orderLiner.getId()));
                
                a.setOrderSys1(Integer.parseInt(orderSys.getId()));
                a.setOrderSys2(Integer.parseInt(orderSys1.getId()));
                orderLinerRoomService.save(a);
            }
        }

        if (orderInsurance.getOrderNo() != null) {
            //保存保险订单（完善订单编号）
            orderInsurance.setOrderNo(orderInsurance.getOrderNo().replace("[]", orderSys.getId()));
            orderInsuranceService.save(orderInsurance);
            //保存保险订单子订单
            OrderSys orderSys2 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys2);
            orderSys2.setCreateDate(orderSys.getCreateDate());
            orderSys2.setId(null);
            orderSys2.setType(10);    // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys2.setOrderid(Integer.parseInt(orderInsurance.getId()));        // 订单ID
            orderSys2.setMemberid(orderSys.getMemberid());      //下单人
            orderSys2.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setOrderNo(orderInsurance.getOrderNo());
            save(orderSys2);

            OrderInsurance orderInsuranceOld = new OrderInsurance();
            orderInsuranceOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderInsuranceOld.setOrderSys2(Integer.parseInt(orderSys2.getId()));
            orderInsuranceOld.setId(orderInsurance.getId());
            orderInsuranceService.updateObject(orderInsuranceOld);
        }

        //保存出游人信息
        for (OrderMember a : orderMemberList) {
            a.setTypeId(Integer.parseInt(orderSys.getId()));
            orderMemberService.save(a);
            if (a.getSaveType() != null && a.getSaveType()) {
                //保存常用联系人
                MemberContacts memberContacts = new MemberContacts();
                memberContacts.setMemberid(orderSys.getMemberid());        // 用户id
                memberContacts.setChineseName(a.getChineseName());        // 中文姓名
                memberContacts.setEnglishName(a.getEnglishName());        // 英文姓名
                memberContacts.setCertType(a.getCertType());    // 证件类型  1.身份证2.护照
                memberContacts.setCertNo(a.getCertNo());        // 证件号
                memberContacts.setCertValidDate(a.getCertValidDate());        // 有效期
                memberContacts.setBirthday(a.getBirthday());        // 出生年月
                memberContacts.setArea(a.getArea());        // 区号
                memberContacts.setMobile(a.getMobile());        // 手机号
                memberContactsService.save(memberContacts);
            }
        }
        return orderSys.getId();
    }

    /**
     * 保存当地玩家订单
     *
     * @param orderSys
     * @param orderRoute
     * @param orderInsurance
     * @param orderMemberList
     */
    @Transactional(readOnly = false)
    public String saveGuideOrder(OrderSys orderSys, OrderGuide orderGuide,
                                 OrderInsurance orderInsurance, List<OrderMember> orderMemberList) {
        //保存主订单
        save(orderSys);
        //保存当地玩家订单（完善订单编号）
        orderGuide.setOrderNo(orderGuide.getOrderNo().replace("[]", orderSys.getId()));
        orderGuideService.save(orderGuide);
        //保存当地玩家子订单
        OrderSys orderSys1 = new OrderSys();
        BeanUtils.copyProperties(orderSys, orderSys1);
        orderSys1.setId(null);
        orderSys1.setType(orderSys.getType());// 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
        //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
        orderSys1.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
        orderSys1.setOrderid(Integer.parseInt(orderGuide.getId()));        // 订单ID
        orderSys1.setMemberid(orderSys.getMemberid());      //下单人
        orderSys1.setLanguageid(orderSys.getLanguageid());    //语言id
        orderSys1.setOrderNo(orderGuide.getOrderNo());
        save(orderSys1);
        
        OrderGuide orderGuide1 = new OrderGuide();
        orderGuide1.setOrderSys1(Integer.parseInt(orderSys.getId()));
        orderGuide1.setOrderSys2(Integer.parseInt(orderSys1.getId()));
        orderGuide1.setId(orderGuide.getId());
        orderGuideService.updateObject(orderGuide1);

        if (orderInsurance.getOrderNo() != null) {
            //保存保险订单（完善订单编号）
            orderInsurance.setOrderNo(orderInsurance.getOrderNo().replace("[]", orderSys.getId()));
            orderInsuranceService.save(orderInsurance);
            //保存保险订单子订单
            OrderSys orderSys2 = new OrderSys();
            BeanUtils.copyProperties(orderSys, orderSys2);
            orderSys2.setCreateDate(orderSys.getCreateDate());
            orderSys2.setId(null);
            orderSys2.setType(10);    // 订单类型1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票
            //8.当地玩家9.酒店10.保险11.旅游定制12导游13.商务定制14.商务旅游',
            orderSys2.setFatherid(Integer.parseInt(orderSys.getId()));        // 父id
            orderSys2.setOrderid(Integer.parseInt(orderInsurance.getId()));        // 订单ID
            orderSys2.setMemberid(orderSys.getMemberid());      //下单人
            orderSys2.setLanguageid(orderSys.getLanguageid());    //语言id
            orderSys2.setTitle(orderInsurance.getName());
            orderSys2.setOrderNo(orderInsurance.getOrderNo());
            save(orderSys2);
            
            OrderInsurance orderInsuranceOld = new OrderInsurance();
            orderInsuranceOld.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderInsuranceOld.setOrderSys2(Integer.parseInt(orderSys2.getId()));
            orderInsuranceOld.setId(orderInsurance.getId());
            orderInsuranceService.updateObject(orderInsuranceOld);
        }

        //保存出游人信息
        for (OrderMember a : orderMemberList) {
            a.setTypeId(Integer.parseInt(orderSys.getId()));
            orderMemberService.save(a);
            if (a.getSaveType() != null && a.getSaveType()) {
                //保存常用联系人
                MemberContacts memberContacts = new MemberContacts();
                memberContacts.setMemberid(orderSys.getMemberid());        // 用户id
                memberContacts.setChineseName(a.getChineseName());        // 中文姓名
                memberContacts.setEnglishName(a.getEnglishName());        // 英文姓名
                memberContacts.setCertType(a.getCertType());    // 证件类型  1.身份证2.护照
                memberContacts.setCertNo(a.getCertNo());        // 证件号
                memberContacts.setCertValidDate(a.getCertValidDate());        // 有效期
                memberContacts.setBirthday(a.getBirthday());        // 出生年月
                memberContacts.setArea(a.getArea());        // 区号
                memberContacts.setMobile(a.getMobile());        // 手机号
                memberContactsService.save(memberContacts);
            }
        }
        return orderSys.getId();
    }

    @Transactional(readOnly = false)
    public void delete(OrderSys orderSys) {
        super.delete(orderSys);
    }

    /**
     * 查找OrderId
     *
     * @param getOrderSys
     * @return
     */
    public List<OrderSys> findOrderId(OrderSys getOrderSys) {
        return mapper.findOrderId(getOrderSys);
    }


    /**
     * @Title: getFathType
     * @Description: 获取父ID类型
     * @author 彭善智
     * @date 2018年8月20日下午8:05:51
     */
    public OrderSys getFathType(OrderInsurance orderInsurance) {
        return mapper.getFathType(orderInsurance);
    }

    /**
     * 获取父类ID类型
     *
     * @param orderGuide
     * @return
     */
    public OrderSys getFatherType(OrderGuide orderGuide) {
        return mapper.getFatherType(orderGuide);
    }

    /**
     * 查找保险数据
     *
     * @param orderSys
     * @return
     */
    public OrderSys findInsurance(OrderSys orderSys) {
        return mapper.findInsurance(orderSys);
    }

    /**
     * 查找保险数据
     *
     * @param orderSys
     * @return
     */
    public OrderSys findInsurance1(OrderSys orderSys2) {
        return mapper.findInsurance1(orderSys2);
    }


    

    /*获取未处理订单数量*/
	public OrderSys findOrderNum(Integer id){
		return orderSysMapper.findOrderNum(id);
	};
	
	/*获取明日出行订单数量 */
	public OrderSys findToTravelNum(Integer id){
		return orderSysMapper.findToTravelNum(id);
	};
	
	//统计某时间段订单数量 
	public OrderSys findOrderNumByDate(OrderSys orderSys){
		return orderSysMapper.findOrderNumByDate(orderSys);
	};
		
	//统计某时间段订单金额 
	public OrderSys findOrderEDuByDate(OrderSys orderSys){
		return orderSysMapper.findOrderEDuByDate(orderSys);
	};
		 
	/*获取子订单*/
	public List<OrderSys> findDetailById(Integer id){
		return orderSysMapper.findDetailById(id);
	};
	/**
     * 取消订单
     * @param orderId
     * @return
     */
    @Transactional(readOnly = false)
    public AjaxJson cancelOrder(Integer orderId) {
        AjaxJson ajaxJson = new AjaxJson();
        Map<String,Integer> map = new HashMap<>();
        map.put("id", orderId);
       Date date= new Date();
        //修改主订单
        OrderSys orderSys = this.findObj(map);
        if (orderSys.getStatus() != 1 || orderSys.getStatus() != 2) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }
        //TODO 对接支付后修改
        if(orderSys.getStatus() == 2){//退款
        	Boolean flag=true;
        	if(!flag){
        		 ajaxJson.setSuccess(false);
                 ajaxJson.setErrorCode(AppErrorUtils.error_56);
                 ajaxJson.setMsg(AppErrorUtils.error_56_desc);
                 return ajaxJson;
        	}
        }
        
        orderSys.setUpdateDate(date);
        orderSys.setStatus(6);
        this.updateObject(orderSys);
        OrderSys orderSys2 = new OrderSys();
        orderSys2.setStatus(6);
        orderSys2.setFatherid(orderId);
        orderSys2.setUpdateDate(date);
        this.updateObject(orderSys2);
    
        //修改业务表
        if(orderSys.getType()==4  || orderSys.getType()==5) {
        	OrderRoute orderRoute=new OrderRoute();
        	orderRoute.setOrderSys1(Integer.parseInt(orderSys.getId()));
        	orderRoute.setStatus(6);
        	orderRoute.setUpdateDate(date);
        	orderRouteService.updateObj2(orderRoute);
        }else if (orderSys.getType() == 6) {//游轮
            //更新游轮订单
            OrderLiner orderLiner = new OrderLiner();
            orderLiner.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderLiner.setStatus(6);
            orderLiner.setUpdateDate(date);
            orderLinerService.updateObject2(orderLiner);
        }else if (orderSys.getType() == 7) {//景点
            //更新景点订单
            OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
            orderScenicSpot.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderScenicSpot.setStatus(6);
            orderScenicSpot.setUpdateDate(date);
            orderScenicSpotService.updateObject2(orderScenicSpot);
        }else if (orderSys.getType() == 8) {//当地玩家/导游
            //更新导游订单
            OrderGuide orderGuide = new OrderGuide();
            orderGuide.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderGuide.setStatus(6);
            orderGuide.setUpdateDate(date);
            orderGuideService.updateObject2(orderGuide);
        }else if (orderSys.getType() == 1 || orderSys.getType() == 2 || orderSys.getType() == 3) {//包车租车、短程接送、接送机
            //更新车辆订单
            OrderCar orderCar = new OrderCar();
            orderCar.setOrderSys1(Integer.valueOf(orderSys.getId()));
            orderCar.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderCar.setStatus(6);
            orderCar.setUpdateDate(date);
            orderCarService.updateObject2(orderCar);
            if(orderSys.getType() == 1){
            	//更新导游订单
                OrderGuide orderGuide = new OrderGuide();
                orderGuide.setOrderSys1(Integer.parseInt(orderSys.getId()));
                orderGuide.setStatus(6);
                orderGuide.setUpdateDate(date);
                orderGuideService.updateObject2(orderGuide);
                //更新酒店订单
                OrderHotel orderHotel = new OrderHotel();
                orderHotel.setOrderSys1(Integer.parseInt(orderSys.getId()));
                orderHotel.setStatus(6);
                orderHotel.setUpdateDate(date);
                orderHotelService.updateObject2(orderHotel);
            }
        }else if (orderSys.getType() == 11 || orderSys.getType() == 13 || orderSys.getType() == 14) {//定制
            //更新定制订单
            OrderTravelBusiness orderTravelBusiness = new OrderTravelBusiness();
            orderTravelBusiness.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderTravelBusiness.setStatus(6);
            orderTravelBusiness.setUpdateDate(date);
            orderTravelBusinessService.updateObject2(orderTravelBusiness);
            //更新车辆订单
            OrderCar orderCar = new OrderCar();
            orderCar.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderCar.setStatus(6);
            orderCar.setUpdateDate(date);
            orderCarService.updateObject2(orderCar);
        	//更新导游订单
            OrderGuide orderGuide = new OrderGuide();
            orderGuide.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderGuide.setStatus(6);
            orderGuide.setUpdateDate(date);
            orderGuideService.updateObject2(orderGuide);
            //更新酒店订单
            OrderHotel orderHotel = new OrderHotel();
            orderHotel.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderHotel.setStatus(6);
            orderHotel.setUpdateDate(date);
            orderHotelService.updateObject2(orderHotel);
            //更新景点订单
            OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
            orderScenicSpot.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderScenicSpot.setStatus(6);
            orderScenicSpot.setUpdateDate(date);
            orderScenicSpotService.updateObject2(orderScenicSpot);
        }
        //更新保险
        OrderInsurance orderInsurance = new OrderInsurance();
        orderInsurance.setOrderSys1(Integer.parseInt(orderSys.getId()));
        orderInsurance.setStatus(6);
        orderInsurance.setUpdateDate(date);
        orderInsuranceService.updateObj2(orderInsurance);
        
        
        ajaxJson.setSuccess(true);
        ajaxJson.setErrorCode("0");
        ajaxJson.setMsg("取消成功");
        return ajaxJson;
    }
	 /**
     * 订单申请退款
     * @param orderId
     * @return
     */
    @Transactional(readOnly = false)
    public AjaxJson refundOrder(Integer orderId) {
        AjaxJson ajaxJson = new AjaxJson();
        Map map = new HashMap();
        map.put("id", orderId);
       Date date= new Date();
        //修改主订单
        OrderSys orderSys = this.findObj(map);
        if (orderSys.getStatus() != 3 || orderSys.getStatus() != 4 || orderSys.getStatus() != 5) {
            ajaxJson.setSuccess(false);
            ajaxJson.setErrorCode(AppErrorUtils.error_3);
            ajaxJson.setMsg(AppErrorUtils.error_3_desc);
            return ajaxJson;
        }

        orderSys.setUpdateDate(date);
        orderSys.setStatus(7);
        this.updateObject(orderSys);
        OrderSys orderSys2 = new OrderSys();
        orderSys2.setStatus(7);
        orderSys2.setFatherid(orderId);
        orderSys2.setUpdateDate(date);
        this.updateObject(orderSys2);
    
        //修改业务表
        if(orderSys.getType()==4 || orderSys.getType()==5) {//路线、参
        	OrderRoute orderRoute=new OrderRoute();
        	orderRoute.setOrderSys1(Integer.parseInt(orderSys.getId()));
        	orderRoute.setStatus(7);
        	orderRoute.setUpdateDate(date);
        	orderRouteService.updateObj2(orderRoute);
        }else if (orderSys.getType() == 6) {//游轮
            //更新游轮订单
            OrderLiner orderLiner = new OrderLiner();
            orderLiner.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderLiner.setStatus(7);
            orderLiner.setUpdateDate(date);
            orderLinerService.updateObject2(orderLiner);
        }else if (orderSys.getType() == 7) {//景点
            //更新景点订单
            OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
            orderScenicSpot.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderScenicSpot.setStatus(7);
            orderScenicSpot.setUpdateDate(date);
            orderScenicSpotService.updateObject2(orderScenicSpot);
        }else if (orderSys.getType() == 8) {//当地玩家/导游
            //更新导游订单
            OrderGuide orderGuide = new OrderGuide();
            orderGuide.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderGuide.setStatus(7);
            orderGuide.setUpdateDate(date);
            orderGuideService.updateObject2(orderGuide);
        }else if (orderSys.getType() == 1 || orderSys.getType() == 2 || orderSys.getType() == 3) {//包车租车、短程接送、接送机
            //更新车辆订单
            OrderCar orderCar = new OrderCar();
            orderCar.setOrderSys1(Integer.valueOf(orderSys.getId()));
            orderCar.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderCar.setStatus(7);
            orderCar.setUpdateDate(date);
            orderCarService.updateObject2(orderCar);
            if(orderSys.getType() == 1){
            	//更新导游订单
                OrderGuide orderGuide = new OrderGuide();
                orderGuide.setOrderSys1(Integer.parseInt(orderSys.getId()));
                orderGuide.setStatus(7);
                orderGuide.setUpdateDate(date);
                orderGuideService.updateObject2(orderGuide);
                //更新酒店订单
                OrderHotel orderHotel = new OrderHotel();
                orderHotel.setOrderSys1(Integer.parseInt(orderSys.getId()));
                orderHotel.setStatus(7);
                orderHotel.setUpdateDate(date);
                orderHotelService.updateObject2(orderHotel);
            }
        }else if (orderSys.getType() == 11 || orderSys.getType() == 13 || orderSys.getType() == 14) {//定制
            //更新定制订单
            OrderTravelBusiness orderTravelBusiness = new OrderTravelBusiness();
            orderTravelBusiness.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderTravelBusiness.setStatus(7);
            orderTravelBusiness.setUpdateDate(date);
            orderTravelBusinessService.updateObject2(orderTravelBusiness);
            //更新车辆订单
            OrderCar orderCar = new OrderCar();
            orderCar.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderCar.setStatus(7);
            orderCar.setUpdateDate(date);
            orderCarService.updateObject2(orderCar);
        	//更新导游订单
            OrderGuide orderGuide = new OrderGuide();
            orderGuide.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderGuide.setStatus(7);
            orderGuide.setUpdateDate(date);
            orderGuideService.updateObject2(orderGuide);
            //更新酒店订单
            OrderHotel orderHotel = new OrderHotel();
            orderHotel.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderHotel.setStatus(7);
            orderHotel.setUpdateDate(date);
            orderHotelService.updateObject2(orderHotel);
            //更新景点订单
            OrderScenicSpot orderScenicSpot = new OrderScenicSpot();
            orderScenicSpot.setOrderSys1(Integer.parseInt(orderSys.getId()));
            orderScenicSpot.setStatus(7);
            orderScenicSpot.setUpdateDate(date);
            orderScenicSpotService.updateObject2(orderScenicSpot);
        }
        //更新保险
        OrderInsurance orderInsurance = new OrderInsurance();
        orderInsurance.setOrderSys1(Integer.parseInt(orderSys.getId()));
        orderInsurance.setStatus(7);
        orderInsurance.setUpdateDate(date);
        orderInsuranceService.updateObj2(orderInsurance);

        ajaxJson.setSuccess(true);
        ajaxJson.setErrorCode("0");
        ajaxJson.setMsg("申请退款成功");
        return ajaxJson;
    }
    
  //获取未提现订单
  	public Page<OrderSys> findOrderSysByExtract(Page<OrderSys> page,OrderSys orderSys){
  		dataRuleFilter(orderSys);
  		orderSys.setPage(page);
		page.setList(mapper.findOrderSysByExtract(orderSys));
		return page;
  	};
  	
  	//获取对账单
  	public List<OrderSys> findOrderSysById_Extract(String orderIds){
  		return mapper.findOrderSysById_Extract(orderIds);
  	};
  	
  	//修改提现状态
  	@Transactional(readOnly=false)
  	public void updateExtract(Integer extract,String orderIds){
  		mapper.updateExtract(extract,orderIds);
  	};
  	
  //获取提现金额
  	public OrderSys findSumPriceById(String orderIds){
  		return mapper.findSumPriceById(orderIds);
  	};
  //修改主订单状态
  	public void changeOrdersysStatus2(Integer fatherid){
  		List<OrderSys> orderSys=mapper.findDetailById(fatherid);
  		int afirm=0;
  		for (int i = 0; i < orderSys.size(); i++) {
  			int type=orderSys.get(i).getType();
  			if (type==10 || type==11 || type==13 || type==14) {
				continue;
			}
			if (orderSys.get(i).getStatus()==4) {
				afirm++;
				break;
			}
		}
  		if (afirm==0) {
  			OrderSys orderSys2=new OrderSys();
  			orderSys2.setStatus(5);
  			orderSys2.setId(fatherid.toString());
			mapper.updateObject(orderSys2);
		}
  	}
  	//修改主订单状态
  	public void changeOrdersysStatus(Integer fatherid){
  		List<OrderSys> orderSys=mapper.findDetailById(fatherid);
  		int afirm=0;
  		int ready=0;
  		for (int i = 0; i < orderSys.size(); i++) {
			if (orderSys.get(i).getStatus()==2) {
				afirm++;
				break;
			}else if (orderSys.get(i).getStatus()==3) {
				ready++;
			}
		}
  		if (afirm==0 && ready>0) {
  			OrderSys orderSys2=new OrderSys();
  			orderSys2.setStatus(3);
  			orderSys2.setId(fatherid.toString());
			mapper.updateObject(orderSys2);
		}else if(afirm==0 && ready==0) {
			OrderSys orderSys2=new OrderSys();
  			orderSys2.setStatus(6);
  			orderSys2.setId(fatherid.toString());
			mapper.updateObject(orderSys2);
		}
  	}
  //修改主订单状态
  	public void changeOrdersysStatus(String id,Integer status){
  			OrderSys orderSys2=new OrderSys();
  			orderSys2.setStatus(status);
  			orderSys2.setId(id);
			mapper.updateObject(orderSys2);
  	}
}
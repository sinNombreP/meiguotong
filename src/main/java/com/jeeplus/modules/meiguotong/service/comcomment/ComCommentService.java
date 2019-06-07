/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comcomment;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.comcomment.ComComment;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.liner_line.LinerLine;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.scenicspot.ScenicSpot;
import com.jeeplus.modules.meiguotong.mapper.comcomment.ComCommentMapper;
import com.jeeplus.modules.meiguotong.mapper.hotel.OrderHotelMapper;
import com.jeeplus.modules.meiguotong.mapper.order.OrderRouteMapper;
import com.jeeplus.modules.meiguotong.mapper.order.OrderSysMapper;
import com.jeeplus.modules.meiguotong.mapper.ordercar.OrderCarMapper;
import com.jeeplus.modules.meiguotong.mapper.orderguide.OrderGuideMapper;
import com.jeeplus.modules.meiguotong.mapper.orderliner.OrderLinerMapper;
import com.jeeplus.modules.meiguotong.mapper.orderscenicspot.OrderScenicSpotMapper;
import com.jeeplus.modules.meiguotong.service.car.CarInfoService;
import com.jeeplus.modules.meiguotong.service.citystrategy.CityStrategyService;
import com.jeeplus.modules.meiguotong.service.comcity.ComCityService;
import com.jeeplus.modules.meiguotong.service.guide.GuideService;
import com.jeeplus.modules.meiguotong.service.hotel.HotelService;
import com.jeeplus.modules.meiguotong.service.liner_line.LinerLineService;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.product.RouteService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;

/**
 * 评论表Service
 * @author cdq
 * @version 2018-08-01
 */
@Service
@Transactional(readOnly = true)
public class ComCommentService extends CrudService<ComCommentMapper, ComComment> {

	@Autowired
	private CarInfoService carInfoService;
	@Autowired
	private GuideService guideService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private LinerLineService linerLineService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private CityStrategyService cityStrategyService;
	@Autowired
	private ComCityService comCityService;
	@Autowired
	private OrderSysMapper orderSysMapper;
	@Autowired
	private OrderRouteMapper orderRouteMapper;
	@Autowired
	private OrderCarMapper orderCarMapper;
	@Autowired
	private OrderScenicSpotMapper orderScenicSpotMapper;
	@Autowired
	private OrderGuideMapper orderGuideMapper;
	@Autowired
	private OrderHotelMapper orderHotelMapper;
	@Autowired
	private OrderLinerMapper orderLinerMapper;
	@Autowired
	private OrderSysService orderSysService;
	
	public ComComment get(String id) {
		return super.get(id);
	}
	
	public List<ComComment> findList(ComComment comComment) {
		return super.findList(comComment);
	}
	/**
	 * 获取各类型产品的评论
	 * @param page
	 * @param comComment
	 * @return
	 */
	public Page<ComComment> selectRouteComment(Page<ComComment> page, ComComment comComment) {
		dataRuleFilter(comComment);
		comComment.setPage(page);
		List<ComComment> list = mapper.selectRouteComment(comComment);
		page.setList(list);
		return page;
	}
	/**
	 * 获取评论的子评论
	 * @param page
	 * @param comComment
	 * @return
	 */
	public Page<ComComment> selectChildComment(Page<ComComment> page, ComComment comComment) {
		dataRuleFilter(comComment);
		comComment.setPage(page);
		page.setList(mapper.selectChildComment(comComment));
		return page;
	}
	/**
	 * 获取消息中心评论消息接口
	 * @param page
	 * @param comComment
	 * @return
	 */
	public Page<ComComment> getComment(Page<ComComment> page, ComComment comComment) {
		dataRuleFilter(comComment);
		comComment.setPage(page);
		page.setList(mapper.getCommentList(comComment));
		return page;
	}
	
	public Page<ComComment> findPage(Page<ComComment> page, ComComment comComment) {
		return super.findPage(page, comComment);
	}
	@Transactional(readOnly = false)
	public void save1(ComComment comComment) {
		super.save(comComment);
	}
	@Transactional(readOnly = false)
	public void save(ComComment comComment,String typeid) {
		super.save(comComment);
		Date date = new Date();
		int type=comComment.getType();
		if(type != 13 && type != 14){
			OrderSys orderSys2 = new OrderSys();
	        orderSys2.setStatus(6);
	        orderSys2.setId(typeid);
	        orderSys2.setUpdateDate(date);
	        orderSysMapper.updateObject(orderSys2);
		}
		OrderSys orderSys=orderSysMapper.get(typeid);
		if (type==1 || type==2 || type==3 || type==15) {
			//修改产品收藏数量
			CarInfo carInfo=new CarInfo();
			carInfo.setId(comComment.getTypeId().toString());
			carInfoService.changeCommnetNum(carInfo);
			//修改订单状态
			OrderCar orderCar=new OrderCar();
			orderCar.setOrderSys2(comComment.getTypeId());
			orderCar.setStatus(5);
			orderCar.setUpdateDate(date);
        	orderCarMapper.updateObject2(orderCar);
    		orderSysService.changeOrdersysStatus2(orderSys.getFatherid());
		}else if (type==4 || type==5) {
			Route route=new Route();
			route.setId(comComment.getTypeId().toString());
			routeService.changeCommnetNum(route);
			//修改订单状态
			OrderRoute orderRoute=new OrderRoute();
        	orderRoute.setOrderSys2(comComment.getTypeId());
        	orderRoute.setStatus(5);
        	orderRoute.setUpdateDate(date);
        	orderRouteMapper.updateObject2(orderRoute);
        	orderSysService.changeOrdersysStatus2(orderSys.getFatherid());
		}else if (type==6) {
			LinerLine linerLine=new LinerLine();
			linerLine.setId(comComment.getTypeId().toString());
			linerLineService.changeCommnetNum(linerLine);
			//修改订单状态
			OrderLiner orderLiner=new OrderLiner();
			orderLiner.setOrderSys2(comComment.getTypeId());
			orderLiner.setStatus(5);
			orderLiner.setUpdateDate(date);
        	orderLinerMapper.updateObject2(orderLiner);
        	orderSysService.changeOrdersysStatus2(orderSys.getFatherid());
		}else if (type==7) {
			ScenicSpot scenicSpot =new ScenicSpot();
			scenicSpot.setId(comComment.getTypeId().toString());
			scenicSpotService.changeCommnetNum(scenicSpot);
			//修改订单状态
			OrderScenicSpot orderScenicSpot=new OrderScenicSpot();
			orderScenicSpot.setOrderSys2(comComment.getTypeId());
			orderScenicSpot.setStatus(5);
			orderScenicSpot.setUpdateDate(date);
        	orderScenicSpotMapper.updateObject2(orderScenicSpot);
        	orderSysService.changeOrdersysStatus2(orderSys.getFatherid());
		}else if (type==8) {
			Guide guide=new Guide();
			guide.setId(comComment.getTypeId().toString());
			guideService.changeCommnetNum(guide);
			//修改订单状态
			OrderGuide orderGuide=new OrderGuide();
			orderGuide.setOrderSys2(comComment.getTypeId());
			orderGuide.setStatus(5);
			orderGuide.setUpdateDate(date);
        	orderGuideMapper.updateObject2(orderGuide);
        	orderSysService.changeOrdersysStatus2(orderSys.getFatherid());
		}else if (type==9) {
			Hotel hotel=new Hotel();
			hotel.setId(comComment.getTypeId().toString());
			hotelService.changeCommnetNum(hotel);
			//修改订单状态
			OrderHotel orderHotel=new OrderHotel();
			orderHotel.setOrderSys2(comComment.getTypeId());
			orderHotel.setStatus(5);
			orderHotel.setUpdateDate(date);
        	orderHotelMapper.updateObject2(orderHotel);
        	orderSysService.changeOrdersysStatus2(orderSys.getFatherid());
		}else if (type==13) {
			CityStrategy cityStrategy =new CityStrategy(); 
			cityStrategy.setId(comComment.getTypeId().toString());
			cityStrategyService.changeCommnetNum(cityStrategy);
		}else if (type==14) {
			ComCity comCity=new ComCity();
			comCity.setId(comComment.getTypeId().toString());
			comCityService.changeCommnetNum(comCity);
		}
	}
	@Transactional(readOnly = false)
	public void delete(ComComment comComment) {
		super.delete(comComment);
	}
/**
 * 修改状态
 * @param comComment
 */
	@Transactional(readOnly = false)
	public void status(ComComment comComment) {
      mapper.status(comComment);
		
	}
/**
 * 攻略评论表数据
 * @param page
 * @param comComment
 * @return
 */
    public Page<ComComment> strategyComment(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.strategyComment(comComment));
	return page;
}
/**
 * 城市评论表
 * @param page
 * @param comComment
 * @return
 */
public Page<ComComment> cityComment(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.cityComment(comComment));
	return page;
}

/**
 * 邮轮评论表
 * @param page
 * @param comComment
 * @return
 */
public Page<ComComment> CruiseComment(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.CruiseComment(comComment));
	return page;
}
/**
 * 邮轮子类评论表
 * @param page
 * @param comComment
 * @return
 */
public Page<ComComment> CruiseChildComment(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.CruiseChildComment(comComment));
	return page;
}

/** 
* @Title: getCommentDate 
* @Description: 评论列表数据
* @author 彭善智
* @date 2018年8月16日上午10:00:36
*/ 
public Page<ComComment> getCommentDate(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.getCommentDate(comComment));
	return page;
}

/** 
* @Title: updateStatus 
* @Description: 启用禁用
* @author 彭善智
* @date 2018年8月16日下午2:49:49
*/ 
@Transactional(readOnly = false)
public void updateStatus(ComComment comComment) {
	mapper.updateStatus(comComment);
}

/**
 * 景点评论列表数据
 * @param page
 * @param comComment
 * @return
 */
public Page<ComComment> SceniceComment(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.SceniceComment(comComment));
	return page;
}
/**
 * 当地玩家表评论列表
 * @param page
 * @param comComment
 * @return
 */
public Page<ComComment> playerComment(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.playerComment(comComment));
	return page;
}

 /**
  * 获取邮轮用户评论列表接口
  * @param page
  * @param comComment
  * @return
  */
public Page<ComComment> findCruiseCommentList(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.findCruiseCommentList(comComment));
	return page;
}
/**
 * 评论导游接口
 * @param page
 * @param comComment
 * @return
 */
public Page<ComComment> findCommentList(Page<ComComment> page, ComComment comComment) {
	dataRuleFilter(comComment);
	comComment.setPage(page);
	page.setList(mapper.findCommentList(comComment));
	return page;
}

	
}
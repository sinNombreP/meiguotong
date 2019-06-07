/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.travel;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.alibaba.druid.stat.TableStat.Mode;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;
import com.jeeplus.modules.meiguotong.mapper.hotel.OrderHotelMapper;
import com.jeeplus.modules.meiguotong.mapper.order.OrderSysMapper;
import com.jeeplus.modules.meiguotong.mapper.travel.OrderTravelDetailsMapper;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.meiguotong.service.orderhotelroom.OrderHotelRoomService;
import com.jeeplus.modules.meiguotong.service.scenicspot.ScenicSpotService;

/**
 * 定制订单详情Service
 * @author psz
 * @version 2018-08-29
 */
@Service
@Transactional(readOnly = true)
public class OrderTravelDetailsService extends CrudService<OrderTravelDetailsMapper, OrderTravelDetails> {

	@Autowired
	private OrderSysService orderSysService;
	@Autowired
	private ScenicSpotService scenicSpotService;
	@Autowired
	private OrderHotelRoomService orderHotelRoomService;
	
	public OrderTravelDetails get(String id) {
		return super.get(id);
	}
	
	public List<OrderTravelDetails> findList(OrderTravelDetails orderTravelDetails) {
		return super.findList(orderTravelDetails);
	}
	
	public Page<OrderTravelDetails> findPage(Page<OrderTravelDetails> page, OrderTravelDetails orderTravelDetails) {
		return super.findPage(page, orderTravelDetails);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderTravelDetails orderTravelDetails) {
		super.save(orderTravelDetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderTravelDetails orderTravelDetails) {
		super.delete(orderTravelDetails);
	}

	/** 
	* @Title: getInfo 
	* @Description: 根据定制ID查询定制信息
	* @author 彭善智
	* @date 2018年8月30日上午10:37:05
	*/ 
	public List<OrderTravelDetails> getInfo(OrderTravelBusiness orderTravelBusiness) {
		List<OrderTravelDetails> list = mapper.getInfor(orderTravelBusiness);
			for(OrderTravelDetails t : list) {
				t.setOrderTrvelId(Integer.valueOf(orderTravelBusiness.getId()));
				List<ComCity> city=mapper.getDateByDay(t);
				t.setCitys(city);
				t.setHotels(orderHotelRoomService.findHotelByOrderSys(orderTravelBusiness.getOrderSys1(), t.getUseDate()));
					for (ComCity c :city) {
						t.setOrderTrvelId(Integer.valueOf(orderTravelBusiness.getId()));
						t.setCity(Integer.valueOf(c.getId()));
						c.setScenics(scenicSpotService.findScenicByCityId(t));
						c.setOrderTravelDetails(mapper.findBusinessByOrderTravelId(t));
					}
				}
			
		
		return list;
	}
	
	public List<OrderTravelDetails> getInfor(OrderTravelBusiness orderTravelBusiness){
		return mapper.getInfor(orderTravelBusiness);
	}
	
	//查询行程城市
	public List<ComCity> findTravelDetailByDate(OrderTravelDetails orderTravelDetails){
			return mapper.findTravelDetailByDate(orderTravelDetails);
		};
		
		//查询 定制商务行程
	public List<OrderTravelDetails> findBusinessByDate(OrderTravelDetails orderTravelDetails){
			return mapper.findBusinessByDate(orderTravelDetails);
		};
	
}
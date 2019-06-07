/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.hotel;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;

/**
 * 酒店订单MAPPER接口
 * @author psz
 * @version 2018-08-23
 */
@MyBatisMapper
public interface OrderHotelMapper extends BaseMapper<OrderHotel> {

	/** 
	* @Title: getInforByDate 
	* @Description: 根据订单Id和时间查询酒店
	* @author 彭善智
	* @date 2018年8月30日上午11:21:48
	*/ 
	OrderHotel getInforByDate(OrderTravelBusiness orderTravelBusiness);
	
	//根据ordersys2查询酒店信息
	public OrderHotel findhotelDetailByOrderSys2(@Param("orderSys2")Integer id);
}
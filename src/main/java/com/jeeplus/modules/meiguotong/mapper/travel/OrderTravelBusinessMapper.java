/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.travel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;

/**
 * 定制订单MAPPER接口
 * @author psz
 * @version 2018-08-29
 */
@MyBatisMapper
public interface OrderTravelBusinessMapper extends BaseMapper<OrderTravelBusiness> {

	void status(OrderTravelBusiness orderTravelBusiness);
	
	/*批量修改子订单状态*/
	void batchUpdateStatus(@Param("orderSys1")Integer id);
	
	/*查询子订单确认状态*/
	List<OrderTravelBusiness> findAffirm(@Param("orderSys1")Integer id);
	
	//根据ordersys2查询定制信息
	public OrderTravelBusiness findTravelDetailByOrderSys2(@Param("orderSys2")Integer id);
}
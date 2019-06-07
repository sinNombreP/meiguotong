/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;

/**
 * 参团订单MAPPER接口
 * @author PSZ
 * @version 2018-08-17
 */
@MyBatisMapper
public interface OrderRouteMapper extends BaseMapper<OrderRoute> {
    //常规路线状态
	void status(OrderRoute orderRoute);
	
	Long updateObject2(OrderRoute orderRoute);
	
	//获取常规路线详情
	OrderRoute findRouteDetailByOrderSys2(@Param("orderSys2")Integer id);
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.orderscenicspot;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;

/**
 * 景点评论MAPPER接口
 * @author cdq
 * @version 2018-08-17
 */
@MyBatisMapper
public interface OrderScenicSpotMapper extends BaseMapper<OrderScenicSpot> {
/**
 * 更改状态
 * @param orderScenicSpot
 */
	void status(OrderScenicSpot orderScenicSpot);
/**
 * 获取保险方案信息
 * @return
 */
OrderScenicSpot getOrderInsurance();
/**
 * 售后景点列表数据
 * @param orderScenicSpot
 * @return
 */
List<OrderScenicSpot> AfterSale(OrderScenicSpot orderScenicSpot);
	
	//前端查询景点详情
	public OrderScenicSpot findScenicSpotDetailByOrderid(@Param("orderSys2")Integer id);
	
}
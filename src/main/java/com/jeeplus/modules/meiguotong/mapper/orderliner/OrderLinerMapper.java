/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.orderliner;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;

/**
 * 邮轮订单表MAPPER接口
 * @author cdq
 * @version 2018-08-15
 */
@MyBatisMapper
public interface OrderLinerMapper extends BaseMapper<OrderLiner> {
   /**
    * 修改状态
    * @param orderLiner
    */
	void status(OrderLiner orderLiner);
	/**
	 * 售后订单列表数据
	 * @param orderLiner
	 * @return
	 */
List<OrderLiner> AfterSale(OrderLiner orderLiner);

	//查询邮轮详情
	public OrderLiner findLinerDetailByOrderSys2(@Param("orderSys2")Integer id);
	
}
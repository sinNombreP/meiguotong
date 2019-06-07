/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.insurance;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;

/**
 * 保险订单MAPPER接口
 * @author PSZ
 * @version 2018-08-20
 */
@MyBatisMapper
public interface OrderInsuranceMapper extends BaseMapper<OrderInsurance> {
   //修改保险状态
	void status(OrderInsurance orderInsurance);

	Long updateObject2(OrderInsurance orderInsurance);
	
	/*根据ordersys1查询订单保险信息*/
	OrderInsurance findOrderInsuranceByOrderSys(@Param("orderSys1")Integer id);
	
	/*根据ordersys2查询订单保险信息*/
	public OrderInsurance findInsuranceByOrderSys2(@Param("orderSys2")Integer id);
}
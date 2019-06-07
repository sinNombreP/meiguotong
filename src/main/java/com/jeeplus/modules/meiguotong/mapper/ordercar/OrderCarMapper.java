/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.ordercar;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;

/**
 * 车辆订单表MAPPER接口
 * @author cdq
 * @version 2018-08-23
 */
@MyBatisMapper
public interface OrderCarMapper extends BaseMapper<OrderCar> {
/**
 * 订单类型1.包车租车2.短程接送3.接送机
 * @param getorderCar
 * @return
 */
	OrderCar getList(OrderCar getorderCar);
/**
 * 更改包车租车的状态
 * @param getOrderCar
 */
void status(OrderCar getOrderCar);

	//获取包车租车列表
	public List<OrderCar> findOrderCarList(OrderCar orderCar);
	
	//获取供应商列表
	public List<SysUser> findagentlist();
	
	//查找定制信息
	public List<OrderCar> findOrderCarByOrderSys(@Param("orderSys1")Integer id);
	
	// 前端查找租车详情
	OrderCar findCarInfoByOrderSys2(Integer type,Integer id);
	
	//获取车辆日程
	List<OrderCar> findJourneyDateByCarid(OrderCar orderCar);
	
	//获取车辆行程
	List<OrderCar> findJourneyByCarid(OrderCar orderCar);
}
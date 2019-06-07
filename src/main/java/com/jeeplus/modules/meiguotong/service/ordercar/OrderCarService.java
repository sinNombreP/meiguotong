/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.ordercar;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drew.lang.StringUtil;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCar;
import com.jeeplus.modules.meiguotong.mapper.ordercar.OrderCarMapper;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.sys.entity.sysUser.SysUser;



/**
 * 车辆订单表Service
 * @author cdq
 * @version 2018-08-23
 */
@Service
@Transactional(readOnly = true)
public class OrderCarService extends CrudService<OrderCarMapper, OrderCar> {

	@Autowired
	private OrderCarMapper orderCarMapper;
	@Autowired
	private OrderSysService orderSysService;
	
	public OrderCar get(String id) {
		return super.get(id);
	}
	
	public List<OrderCar> findList(OrderCar orderCar) {
		return super.findList(orderCar);
	}
	
	public Page<OrderCar> findPage(Page<OrderCar> page, OrderCar orderCar) {
		return super.findPage(page, orderCar);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderCar orderCar) {
		if (StringUtils.isNotBlank(orderCar.getId())) {
			orderSysService.changeOrdersysStatus(orderCar.getOrderSys1().toString(), orderCar.getStatus());
			if (orderCar.getStatus()==3) {
				orderSysService.changeOrdersysStatus(orderCar.getOrderSys1());
			}
		}
		super.save(orderCar);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderCar orderCar) {
		super.delete(orderCar);
	}
  /**
   * 订单类型1.包车租车2.短程接送3.接送机
   * @param getorderCar
   * @return
   */
	public OrderCar getList(OrderCar getorderCar) {
		return mapper.getList(getorderCar);
	}
/**
 * 更改包车租车的状态
 * @param getOrderCar
 */
	@Transactional(readOnly = false)
public void status(OrderCar getOrderCar) {
	mapper.status(getOrderCar);
}
	//获取包车租车列表
	public Page<OrderCar> findOrderCarList(Page<OrderCar> page, OrderCar orderCar) {
		dataRuleFilter(orderCar);
		orderCar.setPage(page);
		page.setList(orderCarMapper.findOrderCarList(orderCar));
		return page;
	}
	
	public List<SysUser> findagentlist(){
		return orderCarMapper.findagentlist();
	}
	
	//查找定制信息
	public List<OrderCar> findOrderCarByOrderSys(Integer id){
		return orderCarMapper.findOrderCarByOrderSys(id);
	};

	// 前端查找租车详情
	public OrderCar findCarInfoByOrderSys2(Integer type,Integer id){
		return orderCarMapper.findCarInfoByOrderSys2(type,id);
	};
	
	//获取车辆行程
	public List<OrderCar> findJourneyDateByCarid(OrderCar orderCar){
		return mapper.findJourneyDateByCarid(orderCar);
	};
		
	//获取车辆行程
	public List<OrderCar> findJourneyByCarid(OrderCar orderCar){
		return mapper.findJourneyByCarid(orderCar);
	};
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.order;

import java.util.List;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.order.OrderRoute;
import com.jeeplus.modules.meiguotong.mapper.order.OrderRouteMapper;

/**
 * 参团订单Service
 * @author PSZ
 * @version 2018-08-17
 */
@Service
@Transactional(readOnly = true)
public class OrderRouteService extends CrudService<OrderRouteMapper, OrderRoute> {

	@Autowired
	private OrderRouteMapper orderRouteMapper;
	@Autowired
	private OrderSysService orderSysService;
	public OrderRoute get(String id) {
		return super.get(id);
	}
	
	public List<OrderRoute> findList(OrderRoute orderRoute) {
		return super.findList(orderRoute);
	}
	
	public Page<OrderRoute> findPage(Page<OrderRoute> page, OrderRoute orderRoute) {
		return super.findPage(page, orderRoute);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderRoute orderRoute) {
		if (StringUtils.isNotBlank(orderRoute.getId())) {
			orderSysService.changeOrdersysStatus(orderRoute.getOrderSys1().toString(), orderRoute.getStatus());
			if (orderRoute.getStatus()==3) {
				orderSysService.changeOrdersysStatus(orderRoute.getOrderSys1());
			}
		}
		super.save(orderRoute);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderRoute orderRoute) {
		super.delete(orderRoute);
	}
	/**
	 * 常规路线状态
	 * @param orderRoute
	 */
	@Transactional(readOnly = false)
	public void status(OrderRoute orderRoute) {
		
		mapper.status(orderRoute);
	}
	
	@Transactional(readOnly = false)
	public Long updateObj2(OrderRoute orderRoute){
		return mapper.updateObject2(orderRoute);
	}
  
	//获取常规路线详情
	public OrderRoute findRouteDetailByOrderSys2(Integer id){
		return mapper.findRouteDetailByOrderSys2(id);
	};
}
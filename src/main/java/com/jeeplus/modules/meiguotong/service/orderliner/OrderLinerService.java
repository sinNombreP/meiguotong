/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.orderliner;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.orderliner.OrderLiner;
import com.jeeplus.modules.meiguotong.mapper.orderliner.OrderLinerMapper;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;

/**
 * 邮轮订单表Service
 * @author cdq
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
public class OrderLinerService extends CrudService<OrderLinerMapper, OrderLiner> {
	
	@Autowired
	private OrderSysService orderSysService;

	public OrderLiner get(String id) {
		return super.get(id);
	}
	
	public List<OrderLiner> findList(OrderLiner orderLiner) {
		return super.findList(orderLiner);
	}
	
	public Page<OrderLiner> findPage(Page<OrderLiner> page, OrderLiner orderLiner) {
		return super.findPage(page, orderLiner);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderLiner orderLiner) {
		super.save(orderLiner);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderLiner orderLiner) {
		super.delete(orderLiner);
	}
   /**
    * 修改状态
    * @param orderLiner
    */
	@Transactional(readOnly = false)
	public void status(OrderLiner orderLiner) {
		orderLiner.preUpdate();
		if (StringUtils.isNotBlank(orderLiner.getId())) {
			orderSysService.changeOrdersysStatus(orderLiner.getOrderSys1().toString(), orderLiner.getStatus());
			if (orderLiner.getStatus()==3) {
				orderSysService.changeOrdersysStatus(orderLiner.getOrderSys1());
			}
		}
		mapper.status(orderLiner);
	}
/**
 * 售后订单列表数据
 * @param page
 * @param orderLiner
 * @return
 */
	public Page<OrderLiner> AfterSale(Page<OrderLiner> page, OrderLiner orderLiner) {
		dataRuleFilter(orderLiner);
		orderLiner.setPage(page);
		page.setList(mapper.AfterSale(orderLiner));   //再controller层中还要写date那段代码。
		return page;
	}
	//查询邮轮详情
	public OrderLiner findLinerDetailByOrderSys2(Integer id){
		return mapper.findLinerDetailByOrderSys2(id);
	};

}
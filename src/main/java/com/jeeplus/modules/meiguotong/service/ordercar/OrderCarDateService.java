/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.ordercar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarDate;
import com.jeeplus.modules.meiguotong.mapper.ordercar.OrderCarDateMapper;

/**
 * 包车订单日期详情Service
 * @author dong
 * @version 2019-05-08
 */
@Service
@Transactional(readOnly = true)
public class OrderCarDateService extends CrudService<OrderCarDateMapper, OrderCarDate> {

	@Autowired
	private OrderCarDateMapper orderCarDateMapper;
	
	public OrderCarDate get(String id) {
		return super.get(id);
	}
	
	public List<OrderCarDate> findList(OrderCarDate orderCarDate) {
		return super.findList(orderCarDate);
	}
	
	public Page<OrderCarDate> findPage(Page<OrderCarDate> page, OrderCarDate orderCarDate) {
		return super.findPage(page, orderCarDate);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderCarDate orderCarDate) {
		super.save(orderCarDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderCarDate orderCarDate) {
		super.delete(orderCarDate);
	}
	
	//查询包车租车日期行程
	public List<OrderCarDate> findOrderCarDataByOrderId(Integer id){
		return orderCarDateMapper.findOrderCarDataByOrderId(id);
	};
	
	//查询包车租车日期行程详情
	public List<OrderCarDate> findOrderCarDataByDay(OrderCarDate orderCarDate){
		return orderCarDateMapper.findOrderCarDataByDay(orderCarDate);
	};
}
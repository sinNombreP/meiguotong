/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.order.OrderDate;
import com.jeeplus.modules.sys.mapper.order.OrderDateMapper;

/**
 * 订单状态时间Service
 * @author dong
 * @version 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class OrderDateService extends CrudService<OrderDateMapper, OrderDate> {

	public OrderDate get(String id) {
		return super.get(id);
	}
	
	public List<OrderDate> findList(OrderDate orderDate) {
		return super.findList(orderDate);
	}
	
	public Page<OrderDate> findPage(Page<OrderDate> page, OrderDate orderDate) {
		return super.findPage(page, orderDate);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderDate orderDate) {
		super.save(orderDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderDate orderDate) {
		super.delete(orderDate);
	}
	
}
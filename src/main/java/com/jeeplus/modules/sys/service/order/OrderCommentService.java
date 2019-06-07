/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.order.OrderComment;
import com.jeeplus.modules.sys.mapper.order.OrderCommentMapper;

/**
 * 订单评价Service
 * @author psz
 * @version 2018-03-05
 */
@Service
@Transactional(readOnly = true)
public class OrderCommentService extends CrudService<OrderCommentMapper, OrderComment> {

	public OrderComment get(String id) {
		return super.get(id);
	}
	
	public List<OrderComment> findList(OrderComment orderComment) {
		return super.findList(orderComment);
	}
	
	public Page<OrderComment> findPage(Page<OrderComment> page, OrderComment orderComment) {
		return super.findPage(page, orderComment);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderComment orderComment) {
		super.save(orderComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderComment orderComment) {
		super.delete(orderComment);
	}
	public Page<OrderComment> findOrderCommentPage(Page<OrderComment> page, OrderComment orderComment) {
		orderComment.setPage(page);
		page.setList(mapper.findOrderCommentList(orderComment));
		return page;
	}
}
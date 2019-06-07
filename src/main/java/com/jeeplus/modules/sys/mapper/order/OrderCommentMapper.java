/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.order;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.order.OrderComment;

/**
 * 订单评价MAPPER接口
 * @author psz
 * @version 2018-03-05
 */
@MyBatisMapper
public interface OrderCommentMapper extends BaseMapper<OrderComment> {
	public List<OrderComment> findOrderCommentList(OrderComment orderComment);
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.orderlinerroom;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;
import com.jeeplus.modules.meiguotong.mapper.orderlinerroom.OrderLinerRoomMapper;

/**
 * 评价房间表Service
 * @author cdq
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
public class OrderLinerRoomService extends CrudService<OrderLinerRoomMapper, OrderLinerRoom> {

	@Autowired
	private OrderLinerRoomMapper orderLinerRoomMapper;
	public OrderLinerRoom get(String id) {
		return super.get(id);
	}
	
	public List<OrderLinerRoom> findList(OrderLinerRoom orderLinerRoom) {
		return super.findList(orderLinerRoom);
	}
	
	public Page<OrderLinerRoom> findPage(Page<OrderLinerRoom> page, OrderLinerRoom orderLinerRoom) {
		return super.findPage(page, orderLinerRoom);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderLinerRoom orderLinerRoom) {
		super.save(orderLinerRoom);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderLinerRoom orderLinerRoom) {
		super.delete(orderLinerRoom);
	}
    /**
     * 查找评论房间的数据
     * @param getorderLinerRoom
     * @return
     */
	public List<OrderLinerRoom> findListById(OrderLinerRoom getorderLinerRoom) {
		return mapper.findListById(getorderLinerRoom);
	}
	
	// 根据订单id获取房间信息
		public List<OrderLinerRoom> findRoomInfoByOrderId(Integer orderId){
			return orderLinerRoomMapper.findRoomInfoByOrderId(orderId);
		};
		
		//获取房间信息
	public List<OrderLinerRoom> findLinerRoomDetailByOrderId(Integer id){
		return mapper.findLinerRoomDetailByOrderId(id);
	};
}
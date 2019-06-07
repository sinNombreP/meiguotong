/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.orderhotelroom;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;
import com.jeeplus.modules.meiguotong.mapper.orderhotelroom.OrderHotelRoomMapper;

/**
 * 酒店订单房间Service
 * @author dong
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class OrderHotelRoomService extends CrudService<OrderHotelRoomMapper, OrderHotelRoom> {

	@Autowired
	private OrderHotelRoomMapper orderHotelRoomMapper;
	
	public OrderHotelRoom get(String id) {
		return super.get(id);
	}
	
	public List<OrderHotelRoom> findList(OrderHotelRoom orderHotelRoom) {
		return super.findList(orderHotelRoom);
	}
	
	public Page<OrderHotelRoom> findPage(Page<OrderHotelRoom> page, OrderHotelRoom orderHotelRoom) {
		return super.findPage(page, orderHotelRoom);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderHotelRoom orderHotelRoom) {
		super.save(orderHotelRoom);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderHotelRoom orderHotelRoom) {
		super.delete(orderHotelRoom);
	}
	
	public List<OrderHotelRoom> findByOrderhotelID(Integer orderHotelId){
		return orderHotelRoomMapper.findByOrderhotelID(orderHotelId);
	};
	
	/*旅游定制查找酒店房间信息*/
	public List<OrderHotelRoom> findHotelByOrderSys(Integer id,Date date){
		return orderHotelRoomMapper.findHotelByOrderSys(id, date);
	};
	
	//获取酒店日程
		public List<OrderHotelRoom> findHotelRoomByOrderHotelId(Integer id){
			return orderHotelRoomMapper.findHotelRoomByOrderHotelId(id);
		};
		
		//获取酒店日程详情
		public List<OrderHotelRoom> findHotelRoomByRoomDate(OrderHotel orderHotel){
			return orderHotelRoomMapper.findHotelRoomByRoomDate(orderHotel);
		};
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.orderhotelroom;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.entity.orderhotelroom.OrderHotelRoom;

/**
 * 酒店订单房间MAPPER接口
 * @author dong
 * @version 2018-10-16
 */
@MyBatisMapper
public interface OrderHotelRoomMapper extends BaseMapper<OrderHotelRoom> {
	
	/*根据orderhotelID获取信息*/
	List<OrderHotelRoom> findByOrderhotelID(@Param("orderHotelId")Integer orderHotelId);
	
	 /*旅游定制查找酒店房间信息*/
	List<OrderHotelRoom> findHotelByOrderSys(Integer id,Date date);
	
	//获取酒店日程
	List<OrderHotelRoom> findHotelRoomByOrderHotelId(@Param("orderHotelId")Integer id);
	
	//获取酒店日程详情
	List<OrderHotelRoom> findHotelRoomByRoomDate(OrderHotel orderHotel);
}
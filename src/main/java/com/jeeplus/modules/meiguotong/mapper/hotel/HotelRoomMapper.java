/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.hotel;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoom;

/**
 * 酒店管理MAPPER接口
 * @author PSZ
 * @version 2018-08-21
 */
@MyBatisMapper
public interface HotelRoomMapper extends BaseMapper<HotelRoom> {

	/** 
	* @Title: findListByAgentidh 
	* @Description: 根据agentid获取城市
	* @author 彭善智
	* @date 2018年8月21日下午4:12:49
	*/ 
	List<HotelRoom> findListByAgentid(HotelRoom hotelRoom);
	/**
	 * 获取酒店房间列表
	 * @param hotelRoom
	 * @return
	 */
	public List<HotelRoom> getHotelRoom(HotelRoom hotelRoom);
	/**
	 * 接口获取酒店房间详情
	 * @param hotelRoom
	 * @return
	 */
	public HotelRoom getRoomDetails(HotelRoom hotelRoom);
	

	/**
	 * 修改酒店房间编号
	 * @param hotel
	 */
	public void updateHotelNo(HotelRoom hotelRoom);
	
}
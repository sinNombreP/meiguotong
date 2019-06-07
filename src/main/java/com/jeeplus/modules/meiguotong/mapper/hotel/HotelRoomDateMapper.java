/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.hotel;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomDate;

/**
 * 酒店时间设置MAPPER接口
 * @author psz
 * @version 2018-08-21
 */
@MyBatisMapper
public interface HotelRoomDateMapper extends BaseMapper<HotelRoomDate> {

	/** 
	* @Title: deleteByHotelRoomId 
	* @Description: 根据酒店ID删除数据
	* @author 彭善智
	* @date 2018年8月22日下午8:28:52
	*/ 
	void deleteByHotelRoomId(HotelRoomDate hotelRoomDate);
	/**
	 * 根据日期房间id获取价格库存
	 * @param hotelRoomDate
	 * @return
	 */
	public HotelRoomDate getHotelRoomDate(HotelRoomDate hotelRoomDate);
	
}
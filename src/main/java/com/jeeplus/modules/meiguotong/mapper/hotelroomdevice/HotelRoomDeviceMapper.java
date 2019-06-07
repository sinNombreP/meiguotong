/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.hotelroomdevice;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.hotelroomdevice.HotelRoomDevice;

/**
 * 酒店管理MAPPER接口
 * @author cdq
 * @version 2018-08-27
 */
@MyBatisMapper
public interface HotelRoomDeviceMapper extends BaseMapper<HotelRoomDevice> {
   //修改状态
	void status(HotelRoomDevice hotelRoomDevice);
	
}
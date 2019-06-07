/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.hotelroomdevice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.hotelroomdevice.HotelRoomDevice;
import com.jeeplus.modules.meiguotong.mapper.hotelroomdevice.HotelRoomDeviceMapper;

/**
 * 酒店管理Service
 * @author cdq
 * @version 2018-08-27
 */
@Service
@Transactional(readOnly = true)
public class HotelRoomDeviceService extends CrudService<HotelRoomDeviceMapper, HotelRoomDevice> {

	public HotelRoomDevice get(String id) {
		return super.get(id);
	}
	
	public List<HotelRoomDevice> findList(HotelRoomDevice hotelRoomDevice) {
		return super.findList(hotelRoomDevice);
	}
	
	public Page<HotelRoomDevice> findPage(Page<HotelRoomDevice> page, HotelRoomDevice hotelRoomDevice) {
		return super.findPage(page, hotelRoomDevice);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelRoomDevice hotelRoomDevice) {
		super.save(hotelRoomDevice);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelRoomDevice hotelRoomDevice) {
		super.delete(hotelRoomDevice);
	}
   //修改状态
	@Transactional(readOnly = false)
	public void status(HotelRoomDevice hotelRoomDevice) {
		mapper.status(hotelRoomDevice);
	}
	
}
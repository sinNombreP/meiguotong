/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.hotel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomTime;
import com.jeeplus.modules.meiguotong.mapper.hotel.HotelRoomTimeMapper;

/**
 * 酒店类型设置Service
 * @author psz
 * @version 2018-08-21
 */
@Service
@Transactional(readOnly = true)
public class HotelRoomTimeService extends CrudService<HotelRoomTimeMapper, HotelRoomTime> {

	public HotelRoomTime get(String id) {
		return super.get(id);
	}
	
	public List<HotelRoomTime> findList(HotelRoomTime hotelRoomTime) {
		return super.findList(hotelRoomTime);
	}
	
	public Page<HotelRoomTime> findPage(Page<HotelRoomTime> page, HotelRoomTime hotelRoomTime) {
		return super.findPage(page, hotelRoomTime);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelRoomTime hotelRoomTime) {
		super.save(hotelRoomTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelRoomTime hotelRoomTime) {
		super.delete(hotelRoomTime);
	}
	
}
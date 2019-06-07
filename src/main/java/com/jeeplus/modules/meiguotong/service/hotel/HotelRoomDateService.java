/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.hotel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.hotel.HotelRoomDate;
import com.jeeplus.modules.meiguotong.mapper.hotel.HotelRoomDateMapper;

/**
 * 酒店时间设置Service
 * @author psz
 * @version 2018-08-21
 */
@Service
@Transactional(readOnly = true)
public class HotelRoomDateService extends CrudService<HotelRoomDateMapper, HotelRoomDate> {

	public HotelRoomDate get(String id) {
		return super.get(id);
	}
	/**
	 * 根据日期房间id获取价格库存
	 * @param hotelRoomDate
	 * @return
	 */
	public HotelRoomDate getHotelRoomDate(HotelRoomDate hotelRoomDate) {
		return mapper.getHotelRoomDate(hotelRoomDate);
	}
	public List<HotelRoomDate> findList(HotelRoomDate hotelRoomDate) {
		return super.findList(hotelRoomDate);
	}
	
	public Page<HotelRoomDate> findPage(Page<HotelRoomDate> page, HotelRoomDate hotelRoomDate) {
		return super.findPage(page, hotelRoomDate);
	}
	
	@Transactional(readOnly = false)
	public void save(HotelRoomDate hotelRoomDate) {
		super.save(hotelRoomDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(HotelRoomDate hotelRoomDate) {
		super.delete(hotelRoomDate);
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.hotel;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.hotel.Hotel;

/**
 * 酒店管理MAPPER接口
 * @author PSZ
 * @version 2018-08-20
 */
@MyBatisMapper
public interface HotelMapper extends BaseMapper<Hotel> {
	/**
	 * 根据城市及搜索条件获取酒店
	 * @param hotel
	 * @return
	 */
	public List<Hotel> getHotelByCity(Hotel hotel);
	
	/**
	 * 根据语言件获取酒店
	 * @param hotel
	 * @return
	 */
	public List<Hotel> getHotelByLanguage(Hotel hotel);
	
	/**
	 * 修改酒店编号
	 * @param hotel
	 */
	public void updateHotelNo(Hotel hotel);
}
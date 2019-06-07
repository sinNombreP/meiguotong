/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.travel;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;

/**
 * 旅游定制MAPPER接口
 * @author psz
 * @version 2018-08-27
 */
@MyBatisMapper
public interface TravelCustomizationMapper extends BaseMapper<TravelCustomization> {
	/**
	 *  获取后台配置的旅游定制的天数列表
	 * @param travelCustomization
	 * @return
	 */
	public List<Integer> getTravelDay(TravelCustomization travelCustomization);
	/**
	 * 根据旅游天数获取后台旅游定制列表
	 * @param travelCustomization
	 * @return
	 */
	public List<TravelCustomization> getTravelByDay(TravelCustomization travelCustomization);
	
	/**
	 * 根据城市id获取后台旅游定制列表
	 * @param travelCustomization
	 * @return
	 */
	public List<TravelCustomization> findTravelByCityId(TravelCustomization travelCustomization) ;
	
	/**
	 * 根据城市旅游定制获取后台旅游定制列表
	 * @param travelCustomization
	 * @return
	 */
	public List<TravelCustomization> findTravelByComCityTravels(String travelid);
}
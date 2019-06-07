/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.travel;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.travel.TravelCustomization;
import com.jeeplus.modules.meiguotong.entity.travel.TravelDetails;

/**
 * 旅游定制详情MAPPER接口
 * @author psz
 * @version 2018-08-27
 */
@MyBatisMapper
public interface TravelDetailsMapper extends BaseMapper<TravelDetails> {

	/** 
	* @Title: getDataBYTravelId 
	* @Description: 根据定制ID查询详情
	* @author 彭善智
	* @date 2018年8月28日上午11:29:25
	*/ 
	List<TravelDetails> getDataByTravelid(String travelid);

	/** 
	* @Title: getDateByDay 
	* @Description: 根据天数查询详情
	* @author 彭善智
	* @date 2018年8月28日下午4:09:33
	*/ 
	List<TravelDetails> getDateByDay(TravelDetails travelDetails);

	/** 
	* @Title: deleteByTravelCustomizationId 
	* @Description: 根据定制ID删除数据
	* @author 彭善智
	* @date 2018年8月29日下午3:24:08
	*/ 
	void deleteByTravelCustomizationId(TravelCustomization travelCustomization);
	/**
	 * 获取旅游定制的详细信息
	 * @param travelid
	 * @return
	 */
	public List<TravelDetails> getTravelDetails(String travelid);
	
}
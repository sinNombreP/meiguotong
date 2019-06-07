/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comcitytravel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comcitytravel.ComCityTravel;

/**
 * 目的地关联旅游定制MAPPER接口
 * @author dong
 * @version 2019-03-22
 */
@MyBatisMapper
public interface ComCityTravelMapper extends BaseMapper<ComCityTravel> {
	
	/*根据城市ID删除定制*/
	void deleteComCityTravelByCityId(Integer cityid);
	
	/*根据城市ID查找定制*/
	List<ComCityTravel> findComCityTravelByCityId(@Param("cityid")Integer cityid);
	
	/*根据城市ID查找定制*/
	List<ComCityTravel> findComCityTravelByCityId2(@Param("cityid")Integer cityid);
	
	/*根据城市ID查找定制*/
	List<ComCityTravel> findComCityTravelByCityId3(@Param("cityid")Integer cityid);
	
}
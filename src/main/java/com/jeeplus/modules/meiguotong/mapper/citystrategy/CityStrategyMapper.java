/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.citystrategy;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;

/**
 * 城市攻略表MAPPER接口
 * @author cdq
 * @version 2018-08-01
 */
@MyBatisMapper
public interface CityStrategyMapper extends BaseMapper<CityStrategy> {
    /**
     * 更改状态
     */
	void status(CityStrategy cityStrategy);
	/**
	 * 设置精华状态
	 * @param cityStrategy
	 */
	void isEssence(CityStrategy cityStrategy);
	/**
	* @Title: getCityStrategyList
	* @Description: 获取攻略列表
	* @author  彭善智
	* @Data 2018年12月5日下午2:00:06
	*/
	List<CityStrategy> getCityStrategyList(CityStrategy cityStrategy);
	
	/**
	 * 获取城市攻略列表
	 * @param cityStrategy
	 * @return
	 */
	public List<CityStrategy> findStrategyList(CityStrategy cityStrategy);
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.travel;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comcity.ComCity;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelDetails;

/**
 * 定制订单详情MAPPER接口
 * @author psz
 * @version 2018-08-29
 */
@MyBatisMapper
public interface OrderTravelDetailsMapper extends BaseMapper<OrderTravelDetails> {

	/** 
	* @Title: getInfor 
	* @Description: 根据定制ID查询定制信息
	* @author 彭善智
	* @date 2018年8月30日上午10:38:26
	*/ 
	List<OrderTravelDetails> getInfor(OrderTravelBusiness orderTravelBusiness);

	/** 
	* @Title: getDateByDay 
	* @Description: 查询每一天的定制信息
	* @author 彭善智
	* @date 2018年8月30日上午11:01:45
	*/ 
	List<ComCity> getDateByDay(OrderTravelDetails t);
	
	/*查询商务信息*/
	List<OrderTravelDetails> findBusinessByOrderTravelId(OrderTravelDetails orderTravelDetails);
	
	//查询行程城市
	List<ComCity> findTravelDetailByDate(OrderTravelDetails orderTravelDetails);
	
	//查询 定制商务行程
	List<OrderTravelDetails> findBusinessByDate(OrderTravelDetails orderTravelDetails);
}
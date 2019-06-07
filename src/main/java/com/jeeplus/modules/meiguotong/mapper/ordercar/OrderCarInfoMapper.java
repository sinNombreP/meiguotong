/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.ordercar;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarInfo;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;

/**
 * 车辆订单车辆详情MAPPER接口
 * @author psz
 * @version 2018-08-30
 */
@MyBatisMapper
public interface OrderCarInfoMapper extends BaseMapper<OrderCarInfo> {

	/** 
	* @Title: getInfo 
	* @Description: 车辆信息
	* @author 彭善智
	* @date 2018年8月30日下午3:03:59
	*/ 
	List<OrderCarInfo> getInfo(OrderTravelBusiness orderTravelBusiness);
	
}
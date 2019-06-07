/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.product;

import java.util.List;
import java.util.Map;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;

/**
 * 参团MAPPER接口
 * @author psz
 * @version 2018-08-14
 */
@MyBatisMapper
public interface RouteDateMapper extends BaseMapper<RouteDate> {

	/** 
	* @Title: deleteByRouteId 
	* @Description: 根据参团ID删除数据
	* @author 彭善智
	* @date 2018年8月26日下午11:18:03
	*/ 
	void deleteByRouteId(RouteDate routeDate);
	/**
	 * 获取路线产品日期价格（4个月）
	 * @param routeDate
	 * @return
	 */
	public List<RouteDate> getDateList(RouteDate routeDate);
	/**
	 * 获取路线产品日期价格（所有）
	 * @param routeDate
	 * @return
	 */
	public List<RouteDate> getAllDateList(RouteDate routeDate);
	
	/**
	 * 获取路线产品某个日期价格
	 * @param routeDate
	 * @return
	 */
	public RouteDate getRouteDate(RouteDate routeDate);
	
	public List<RouteDate> findListByPage(Map paramMap);
	
	/*更新已售数量*/
	void changeNumByDate(RouteDate routeDate);
	
	//获取库存
	public RouteDate findNumByDate(RouteDate routeDate);
	
	public RouteDate getByFathid(RouteDate routeDate);
	
}
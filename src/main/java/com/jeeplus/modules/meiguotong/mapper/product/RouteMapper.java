/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.product;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.product.Route;

/**
 * 当地参团MAPPER接口
 * @author psz
 * @version 2018-08-13
 */
@MyBatisMapper
public interface RouteMapper extends BaseMapper<Route> {

	/** 
	* @Title: updateStatus 
	* @Description: 修改审核状态
	* @author 彭善智
	* @date 2018年8月13日下午3:21:01
	*/ 
	void updateStatus(Route route);
	/**
	 * 常规路线搜索接口
	 * @param route
	 * @return
	 */
	public List<Route> findRouteList(Route route);
	/**
	 * 当地参团搜索接口
	 * @param route
	 * @return
	 */
	public List<Route> findCityRouteList(Route route);
	/**
	 * 获取路线详情接口
	 * @param route
	 * @return
	 */
	public Route getRoute(Route route);
	/**
	 * 获取参团详情接口
	 * @param route
	 * @return
	 */
	public Route getCityRoute(Route route);
	
	/**
	 * 新增城市根据title获取参团列表
	 * @param route
	 * @return
	 */
	public List<Route> findRrouteByTitle(Route route);
	
	/**
	 * 新增当地参团ID获取参团列表
	 * @param route
	 * @return
	 */
	public List<Route> findRrouteByOfferenTop(@Param("offerenTop")String routes);
	
	/**
	 * 目的地获取当地参团列表
	 * @param param
	 * @return
	 */
	public List<Route> findOfferedList(Map<String, Object> param);
	/**
	 * 目的地获取当地参团数量
	 * @param param
	 * @return
	 */
	public Integer countOffered(Map<String, Object> param);
	/**
	 * 添加路线或参团编号
	 * @param route
	 */
	public void updateNo(Route route);
	
	//购物车查库存
	public Route findRouteStockByDate(Integer id,Date date);
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.product;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.product.RouteContent;

/**
 * 参团内容MAPPER接口
 * @author psz
 * @version 2018-08-14
 */
@MyBatisMapper
public interface RouteContentMapper extends BaseMapper<RouteContent> {

	/** 
	* @Title: findListByRouteid 
	* @Description: route
	* @author 彭善智
	* @date 2018年8月14日下午8:24:49
	*/ 
	List<RouteContent> findListByRouteid(Route route);

	/** 
	* @Title: deleteByRouteId 
	* @Description: 根据参团ID删除数据
	* @author 彭善智
	* @date 2018年8月27日上午12:27:39
	*/ 
	void deleteByRouteId(String id);
	
	/**
	 * 查询路线行程接口
	 * @param routeContent
	 * @return
	 */
	public List<RouteContent> getContentList(RouteContent routeContent);
	
}
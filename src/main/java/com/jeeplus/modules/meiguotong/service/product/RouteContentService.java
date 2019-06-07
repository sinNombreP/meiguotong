/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.product.Route;
import com.jeeplus.modules.meiguotong.entity.product.RouteContent;
import com.jeeplus.modules.meiguotong.mapper.product.RouteContentMapper;

/**
 * 参团内容Service
 * @author psz
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
public class RouteContentService extends CrudService<RouteContentMapper, RouteContent> {

	public RouteContent get(String id) {
		return super.get(id);
	}
	/**
	 * 查询路线行程接口
	 * @param routeContent
	 * @return
	 */
	public List<RouteContent> getContentList(RouteContent routeContent) {
		return mapper.getContentList(routeContent);
	}
	public List<RouteContent> findList(RouteContent routeContent) {
		return super.findList(routeContent);
	}
	
	public Page<RouteContent> findPage(Page<RouteContent> page, RouteContent routeContent) {
		return super.findPage(page, routeContent);
	}
	
	@Transactional(readOnly = false)
	public void save(RouteContent routeContent) {
		super.save(routeContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(RouteContent routeContent) {
		super.delete(routeContent);
	}

	/** 
	* @Title: findListByRouteid 
	* @Description: 获取行程
	* @author 彭善智
	* @date 2018年8月14日下午8:24:03
	*/ 
	public List<RouteContent>  findListByRouteid(Route route) {
		return mapper.findListByRouteid(route);
	}
	
}
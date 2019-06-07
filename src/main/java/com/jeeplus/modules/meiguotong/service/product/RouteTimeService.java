/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.product.RouteTime;
import com.jeeplus.modules.meiguotong.mapper.product.RouteTimeMapper;

/**
 * 参团Service
 * @author psz
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
public class RouteTimeService extends CrudService<RouteTimeMapper, RouteTime> {

	public RouteTime get(String id) {
		return super.get(id);
	}
	
	public List<RouteTime> findList(RouteTime routeTime) {
		return super.findList(routeTime);
	}
	
	public Page<RouteTime> findPage(Page<RouteTime> page, RouteTime routeTime) {
		return super.findPage(page, routeTime);
	}
	
	@Transactional(readOnly = false)
	public void save(RouteTime routeTime) {
		super.save(routeTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(RouteTime routeTime) {
		super.delete(routeTime);
	}
	
}
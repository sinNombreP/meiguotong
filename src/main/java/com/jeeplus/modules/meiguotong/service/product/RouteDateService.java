/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.order.OrderSys;
import com.jeeplus.modules.meiguotong.entity.product.RouteDate;
import com.jeeplus.modules.meiguotong.mapper.product.RouteDateMapper;

/**
 * 参团Service
 * @author psz
 * @version 2018-08-14
 */
@Service
@Transactional(readOnly = true)
public class RouteDateService extends CrudService<RouteDateMapper, RouteDate> {
	
	@Autowired
	private RouteDateMapper oRouteDateMapper;

	public RouteDate get(String id) {
		return super.get(id);
	}
	/**
	 * 获取路线产品日期价格（4个月）
	 * @param routeDate
	 * @return
	 */
	public List<RouteDate> getDateList(RouteDate routeDate) {
		return mapper.getDateList(routeDate);
	}
	/**
	 * 获取路线产品某个日期价格
	 * @param routeDate
	 * @return
	 */
	public RouteDate getRouteDate(RouteDate routeDate) {
		return mapper.getRouteDate(routeDate);
	}
	
	public List<RouteDate> findList(RouteDate routeDate) {
		return super.findList(routeDate);
	}
	
	public Page<RouteDate> findPage(Page<RouteDate> page, RouteDate routeDate) {
		return super.findPage(page, routeDate);
	}
	
	@Transactional(readOnly = false)
	public void save(RouteDate routeDate) {
		super.save(routeDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(RouteDate routeDate) {
		super.delete(routeDate);
	}
	
	public List<RouteDate> findListByPage(Map paramMap){
		return mapper.findListByPage(paramMap);
	}
	
	/*更新已售数量*/
	public void changeNumByDate(RouteDate routeDate){
		oRouteDateMapper.changeNumByDate(routeDate);
	};
	
	//获取库存
	public RouteDate findNumByDate(RouteDate routeDate){
		return oRouteDateMapper.findNumByDate(routeDate);
	};
	
}
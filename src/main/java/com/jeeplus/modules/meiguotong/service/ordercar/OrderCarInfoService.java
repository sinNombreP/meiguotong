/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.ordercar;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarInfo;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.mapper.ordercar.OrderCarInfoMapper;

/**
 * 车辆订单车辆详情Service
 * @author psz
 * @version 2018-08-30
 */
@Service
@Transactional(readOnly = true)
public class OrderCarInfoService extends CrudService<OrderCarInfoMapper, OrderCarInfo> {

	public OrderCarInfo get(String id) {
		return super.get(id);
	}
	
	public List<OrderCarInfo> findList(OrderCarInfo orderCarInfo) {
		return super.findList(orderCarInfo);
	}
	
	public Page<OrderCarInfo> findPage(Page<OrderCarInfo> page, OrderCarInfo orderCarInfo) {
		return super.findPage(page, orderCarInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderCarInfo orderCarInfo) {
		super.save(orderCarInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderCarInfo orderCarInfo) {
		super.delete(orderCarInfo);
	}

	/** 
	* @Title: getInfo 
	* @Description: 车辆信息
	* @author 彭善智
	* @date 2018年8月30日下午3:02:40
	*/ 
	public List<OrderCarInfo> getInfo(OrderTravelBusiness orderTravelBusiness) {
		return mapper.getInfo(orderTravelBusiness);
	}
	
}
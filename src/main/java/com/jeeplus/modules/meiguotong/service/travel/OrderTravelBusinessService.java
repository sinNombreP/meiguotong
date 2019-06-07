/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.travel;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.travel.OrderTravelBusiness;
import com.jeeplus.modules.meiguotong.mapper.travel.OrderTravelBusinessMapper;

/**
 * 定制订单Service
 * @author psz
 * @version 2018-08-29
 */
@Service
@Transactional(readOnly = true)
public class OrderTravelBusinessService extends CrudService<OrderTravelBusinessMapper, OrderTravelBusiness> {

	public OrderTravelBusiness get(String id) {
		return super.get(id);
	}
	
	public List<OrderTravelBusiness> findList(OrderTravelBusiness orderTravelBusiness) {
		return super.findList(orderTravelBusiness);
	}
	
	public Page<OrderTravelBusiness> findPage(Page<OrderTravelBusiness> page, OrderTravelBusiness orderTravelBusiness) {
		return super.findPage(page, orderTravelBusiness);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderTravelBusiness orderTravelBusiness) {
		super.save(orderTravelBusiness);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderTravelBusiness orderTravelBusiness) {
		super.delete(orderTravelBusiness);
	}
	
   //旅游定制状态
	@Transactional(readOnly = false)
	public void status(OrderTravelBusiness orderTravelBusiness) {
		orderTravelBusiness.preUpdate();
		mapper.status(orderTravelBusiness);
	}
	
	/*批量修改子订单状态*/
	@Transactional(readOnly = false)
	public void batchUpdateStatus(Integer id){
		mapper.batchUpdateStatus(id);
	};
	
	/*查询子订单确认状态*/
	public List<OrderTravelBusiness> findAffirm(Integer id){
		return mapper.findAffirm(id);
	};
	
	//根据ordersys2查询定制信息
		public OrderTravelBusiness findTravelDetailByOrderSys2(Integer id){
			return mapper.findTravelDetailByOrderSys2(id);
		};
}
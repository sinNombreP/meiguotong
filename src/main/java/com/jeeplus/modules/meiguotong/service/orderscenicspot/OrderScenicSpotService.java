/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.orderscenicspot;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.orderscenicspot.OrderScenicSpot;
import com.jeeplus.modules.meiguotong.mapper.orderscenicspot.OrderScenicSpotMapper;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;

/**
 * 景点评论Service
 * @author cdq
 * @version 2018-08-17
 */
@Service
@Transactional(readOnly = true)
public class OrderScenicSpotService extends CrudService<OrderScenicSpotMapper, OrderScenicSpot> {
	
	@Autowired
	private OrderSysService orderSysService;

	public OrderScenicSpot get(String id) {
		return super.get(id);
	}
	
	public List<OrderScenicSpot> findList(OrderScenicSpot orderScenicSpot) {
		return super.findList(orderScenicSpot);
	}
	
	public Page<OrderScenicSpot> findPage(Page<OrderScenicSpot> page, OrderScenicSpot orderScenicSpot) {
		return super.findPage(page, orderScenicSpot);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderScenicSpot orderScenicSpot) {
		super.save(orderScenicSpot);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderScenicSpot orderScenicSpot) {
		super.delete(orderScenicSpot);
	}
/**
 * 更改状态
 * @param orderScenicSpot
 */
	@Transactional(readOnly = false)
	public void status(OrderScenicSpot orderScenicSpot) {
		if (StringUtils.isNotBlank(orderScenicSpot.getId())) {
			orderSysService.changeOrdersysStatus(orderScenicSpot.getOrderSys1().toString(), orderScenicSpot.getStatus());
			if (orderScenicSpot.getStatus()==3) {
				orderSysService.changeOrdersysStatus(orderScenicSpot.getOrderSys1());
			}
		}
	mapper.status(orderScenicSpot);	
	}
  /**
   * 获取保险方案信息
   * @return
   */
public OrderScenicSpot getOrderInsurance() {
	return mapper.getOrderInsurance();
}
/**
 * 售后景点列表数据
 * @param page
 * @param orderScenicSpot
 * @return
 */
public Page<OrderScenicSpot> AfterSale(Page<OrderScenicSpot> page, OrderScenicSpot orderScenicSpot) {
	dataRuleFilter(orderScenicSpot);
	orderScenicSpot.setPage(page);
	page.setList(mapper.AfterSale(orderScenicSpot)); 
	return page;
}
	
	//前端查询景点详情
	public OrderScenicSpot findScenicSpotDetailByOrderid(Integer id){
		return mapper.findScenicSpotDetailByOrderid(id);
	};
	
}
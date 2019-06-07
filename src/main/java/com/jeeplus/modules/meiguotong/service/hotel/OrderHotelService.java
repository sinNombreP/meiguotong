/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.hotel;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.hotel.OrderHotel;
import com.jeeplus.modules.meiguotong.mapper.hotel.OrderHotelMapper;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;

/**
 * 酒店订单Service
 * @author psz
 * @version 2018-08-23
 */
@Service
@Transactional(readOnly = true)
public class OrderHotelService extends CrudService<OrderHotelMapper, OrderHotel> {
	
	@Autowired
	private OrderSysService orderSysService;

	public OrderHotel get(String id) {
		return super.get(id);
	}
	
	public List<OrderHotel> findList(OrderHotel orderHotel) {
		return super.findList(orderHotel);
	}
	
	public Page<OrderHotel> findPage(Page<OrderHotel> page, OrderHotel orderHotel) {
		return super.findPage(page, orderHotel);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderHotel orderHotel) {
		if (StringUtils.isNotBlank(orderHotel.getId())) {
			orderSysService.changeOrdersysStatus(orderHotel.getOrderSys1().toString(), orderHotel.getStatus());
			if (orderHotel.getStatus()==3) {
				orderSysService.changeOrdersysStatus(orderHotel.getOrderSys1());
			}
		}
		super.save(orderHotel);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderHotel orderHotel) {
		super.delete(orderHotel);
	}
	
	//根据ordersys2查询酒店信息
		public OrderHotel findhotelDetailByOrderSys2(Integer id){
			return mapper.findhotelDetailByOrderSys2(id);
		};
}
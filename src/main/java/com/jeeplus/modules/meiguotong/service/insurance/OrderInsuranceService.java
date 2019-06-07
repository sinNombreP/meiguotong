/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.insurance;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.insurance.OrderInsurance;
import com.jeeplus.modules.meiguotong.mapper.insurance.OrderInsuranceMapper;

/**
 * 保险订单Service
 * @author PSZ
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class OrderInsuranceService extends CrudService<OrderInsuranceMapper, OrderInsurance> {

	@Autowired
	private OrderInsuranceMapper orderInsuranceMapper;
	public OrderInsurance get(String id) {
		return super.get(id);
	}
	
	public List<OrderInsurance> findList(OrderInsurance orderInsurance) {
		return super.findList(orderInsurance);
	}
	
	public Page<OrderInsurance> findPage(Page<OrderInsurance> page, OrderInsurance orderInsurance) {
		return super.findPage(page, orderInsurance);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderInsurance orderInsurance) {
		super.save(orderInsurance);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderInsurance orderInsurance) {
		super.delete(orderInsurance);
	}
 //修改保险状态
	@Transactional(readOnly = false)
	public void status(OrderInsurance orderInsurance) {
		mapper.status(orderInsurance);
	}

	@Transactional(readOnly = false)
	public Long updateObj2(OrderInsurance orderInsurance) {
		return mapper.updateObject2(orderInsurance);
	}

	/*根据ordersys1查询订单保险信息*/
	public OrderInsurance findOrderInsuranceByOrderSys(Integer id){
		return orderInsuranceMapper.findOrderInsuranceByOrderSys(id);
	};
	
	/*根据ordersys2查询订单保险信息*/
	public OrderInsurance findInsuranceByOrderSys2(Integer id){
		return orderInsuranceMapper.findInsuranceByOrderSys2(id);
	};
}
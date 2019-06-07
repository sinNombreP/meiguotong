/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.orderguide;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.meiguotong.mapper.orderguide.OrderGuideMapper;
import com.jeeplus.modules.meiguotong.service.order.OrderSysService;
import com.jeeplus.modules.sys.entity.member.MemberInformation;


/**
 * 导游订单Service
 * @author cdq
 * @version 2018-08-21
 */
@Service
@Transactional(readOnly = true)
public class OrderGuideService extends CrudService<OrderGuideMapper, OrderGuide> {
	
	@Autowired
	private OrderSysService orderSysService;

	public OrderGuide get(String id) {
		return super.get(id);
	}
	
	public List<OrderGuide> findList(OrderGuide orderGuide) {
		return super.findList(orderGuide);
	}
	
	public Page<OrderGuide> findPage(Page<OrderGuide> page, OrderGuide orderGuide) {
		return super.findPage(page, orderGuide);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderGuide orderGuide) {
		super.save(orderGuide);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderGuide orderGuide) {
		super.delete(orderGuide);
	}
	/**
	 * 更改状态
	 * @param orderGuide
	 */
	@Transactional(readOnly = false)
	public void status(OrderGuide orderGuide) {	
		if (StringUtils.isNotBlank(orderGuide.getId())) {
			orderSysService.changeOrdersysStatus(orderGuide.getOrderSys1().toString(), orderGuide.getStatus());
			if (orderGuide.getStatus()==3) {
				orderSysService.changeOrdersysStatus(orderGuide.getOrderSys1());
			}
		}
		mapper.status(orderGuide);
	}
  /**
   * 获取导游数据
   * @param getorderGuide
   * @return
   */
	public OrderGuide getGuide(OrderGuide getorderGuide) {
		return mapper.getGuide(getorderGuide);
	}
/**
 * 当地玩家售后订单列表
 * @param page
 * @param orderGuide
 * @return
 */
public Page<OrderGuide> AfterSale(Page<OrderGuide> page, OrderGuide orderGuide) {
	dataRuleFilter(orderGuide);
	orderGuide.setPage(page);
	page.setList(mapper.AfterSale(orderGuide)); 
	return page;
}
/**
 * 当地玩家详情
 * @param id
 * @return
 */
public OrderGuide findOrderGuide(String id) {
	return mapper.findOrderGuide(id);
}
	//查找定制导游信息
	public MemberInformation findGuideByOrderSys(Integer id){
		return mapper.findGuideByOrderSys(id);
		
	}
	
	/*前端查询导游信息*/
	public OrderGuide findGuideDetailByOrderSys2(Integer id){
		return mapper.findGuideDetailByOrderSys2(id);
	};
}
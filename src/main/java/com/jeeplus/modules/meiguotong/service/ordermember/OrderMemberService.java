/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.ordermember;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;
import com.jeeplus.modules.meiguotong.mapper.ordermember.OrderMemberMapper;

/**
 * 各出行人信息表Service
 * @author cdq
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
public class OrderMemberService extends CrudService<OrderMemberMapper, OrderMember> {

	public OrderMember get(String id) {
		return super.get(id);
	}
	
	public List<OrderMember> findList(OrderMember orderMember) {
		return super.findList(orderMember);
	}
	
	public Page<OrderMember> findPage(Page<OrderMember> page, OrderMember orderMember) {
		return super.findPage(page, orderMember);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderMember orderMember) {
		super.save(orderMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderMember orderMember) {
		super.delete(orderMember);
	}
  /**
   * 查找邮轮出行人的信息
   * @param orderMember
   * @return
   */
	public List<OrderMember> findListByTyid(OrderMember orderMember) {
		return mapper.findListByTyid(orderMember);
	}
	/**
	 * 保存出游人信息
	 * @param orderMemberList
	 */
	@Transactional(readOnly = false)
	public void saveCruiseVisitors(List<OrderMember> orderMemberList) {
		  if(orderMemberList!=null&&orderMemberList.size()>0){
			  for(OrderMember o:orderMemberList){
				 mapper.insert(o);
			  }
		  }
	}

	public List<OrderMember> findListByTyid2(OrderMember orderMember) {
		return mapper.findListByTyid2(orderMember);
	}
	
}
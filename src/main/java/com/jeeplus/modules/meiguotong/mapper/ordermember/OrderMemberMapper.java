/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.ordermember;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.ordermember.OrderMember;

/**
 * 各出行人信息表MAPPER接口
 * @author cdq
 * @version 2018-08-15
 */
@MyBatisMapper
public interface OrderMemberMapper extends BaseMapper<OrderMember> {
  /**
   * 查找出行人的信息
   * @param orderMember
   * @return
   */
	List<OrderMember> findListByTyid(OrderMember orderMember);
	List<OrderMember> findListByTyid2(OrderMember orderMember);
}
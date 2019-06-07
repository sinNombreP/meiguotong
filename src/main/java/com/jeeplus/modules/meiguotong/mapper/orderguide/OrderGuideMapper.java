/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.orderguide;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.orderguide.OrderGuide;
import com.jeeplus.modules.sys.entity.member.MemberInformation;

/**
 * 导游订单MAPPER接口
 * @author cdq
 * @version 2018-08-21
 */
@MyBatisMapper
public interface OrderGuideMapper extends BaseMapper<OrderGuide> {
   //更改状态
	void status(OrderGuide orderGuide);
   /**
    * 获取导游数据
    * @param getorderGuide
    * @return
    */
	OrderGuide getGuide(OrderGuide getorderGuide);
	/**
	 * 当地玩家售后订单列表
	 * @param orderGuide
	 * @return
	 */
List<OrderGuide> AfterSale(OrderGuide orderGuide);
/**
 * 当地晚间详情
 * @param id
 * @return
 */
	OrderGuide findOrderGuide(String id);
	
	//查找定制导游信息
	MemberInformation findGuideByOrderSys(@Param("orderSys1")Integer id);
	
	/*前端查询导游信息*/
	public OrderGuide findGuideDetailByOrderSys2(@Param("orderSys2")Integer id);
}
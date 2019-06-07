/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.ordercar;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.ordercar.OrderCarDate;

/**
 * 包车订单日期详情MAPPER接口
 * @author dong
 * @version 2019-05-08
 */
@MyBatisMapper
public interface OrderCarDateMapper extends BaseMapper<OrderCarDate> {
	
	//查询包车租车日期行程
	public List<OrderCarDate> findOrderCarDataByOrderId(@Param("orderid")Integer id);
	
	//查询包车租车日期行程详情
	public List<OrderCarDate> findOrderCarDataByDay(OrderCarDate orderCarDate);
}
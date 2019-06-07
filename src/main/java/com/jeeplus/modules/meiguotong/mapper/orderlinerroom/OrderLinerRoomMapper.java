/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.orderlinerroom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.orderlinerroom.OrderLinerRoom;

/**
 * 评价房间表MAPPER接口
 * @author cdq
 * @version 2018-08-15
 */
@MyBatisMapper
public interface OrderLinerRoomMapper extends BaseMapper<OrderLinerRoom> {
  /**
   * 查找评论房间的数据
   * @param getorderLinerRoom
   * @return
   */
	List<OrderLinerRoom> findListById(OrderLinerRoom getorderLinerRoom);
	
	// 根据订单id获取房间信息
	List<OrderLinerRoom> findRoomInfoByOrderId(Integer orderId);
	
	//获取房间信息
	List<OrderLinerRoom> findLinerRoomDetailByOrderId(@Param("orderId")Integer id);
}
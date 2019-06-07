/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.scenicspotticket;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.scenicspotticket.ScenicSpotTicket;

/**
 * 景点门票表MAPPER接口
 * @author cdq
 * @version 2018-08-16
 */
@MyBatisMapper
public interface ScenicSpotTicketMapper extends BaseMapper<ScenicSpotTicket> {
   /**
    * 修改状态
    * @param scenicSpotTicket
    */
	void status(ScenicSpotTicket scenicSpotTicket);
	/**
	 * 查询景点的门票
	 * @param scenicSpotTicket
	 * @return
	 */
	public List<ScenicSpotTicket> getList(ScenicSpotTicket scenicSpotTicket);
	/**
	* @Title: getScenicSpotTicketList
	* @Description: 获取景点门票列表
	* @author  彭善智
	* @Data 2018年12月5日下午4:26:26
	*/
	List<ScenicSpotTicket> getScenicSpotTicketList(ScenicSpotTicket scenicSpotTicket);
	
	/**
	 * 查询景点的定制门票
	 * @param scenicSpotTicket
	 * @return
	 */
	public ScenicSpotTicket getTicket(ScenicSpotTicket scenicSpotTicket);
	
	//查询是否超期
	public ScenicSpotTicket checkDateByScenicid(@Param("id")Integer id);
	
	//购物车获取景点门票信息
	public ScenicSpotTicket productCar_findScenice(@Param("id")Integer id);
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.guideroute;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;

/**
 * 导游路线表MAPPER接口
 * @author cdq
 * @version 2018-09-04
 */
@MyBatisMapper
public interface GuideRouteMapper extends BaseMapper<GuideRoute> {
 /**
  * 导游推荐路线接口
  * @param guideRoute
  * @return
  */
	List<GuideRoute> findGuideRouteList(GuideRoute guideRoute);
	/**
	 * 获取导游添加的路线规划接口
	 * @param guideRoute
	 * @return
	 */
	public List<GuideRoute> getGuideRoute(GuideRoute guideRoute);
	/**
	 * 获取导游的路线规划详情接口
	 * @param id
	 * @return
	 */
	public GuideRoute getGuideRouteDetail(String id);
	
	//购物车获取导游线路信息
	public GuideRoute productCar_findGuideRoute(@Param("id")Integer id);
	
}
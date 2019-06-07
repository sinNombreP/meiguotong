/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.guideroute;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteDate;

/**
 * 导游路线日期价格设置MAPPER接口
 * @author dong
 * @version 2018-09-13
 */
@MyBatisMapper
public interface GuideRouteDateMapper extends BaseMapper<GuideRouteDate> {
	/**
	 * 删除旧的日期价格
	 * @param guideRoute
	 */
	public void deleteOld(GuideRoute guideRoute);
	/**
	 * 获取导游路线可预订日期
	 * @param guideRouteDate
	 * @return
	 */
	public List<GuideRouteDate> getDateList(GuideRouteDate guideRouteDate);
}
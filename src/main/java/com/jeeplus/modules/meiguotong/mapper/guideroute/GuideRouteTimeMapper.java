/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.guideroute;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteTime;

/**
 * 导游路线日期设置MAPPER接口
 * @author dong
 * @version 2018-09-13
 */
@MyBatisMapper
public interface GuideRouteTimeMapper extends BaseMapper<GuideRouteTime> {
	public void insertRoute(GuideRoute guideRoute);
	
	public void updateRoute(GuideRoute guideRoute);
}
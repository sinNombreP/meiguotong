/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.guide;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.guide.GuideDate;
import com.jeeplus.modules.meiguotong.entity.guide.GuideTime;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRoute;

/**
 * 导游日期价格设置MAPPER接口
 * @author dong
 * @version 2018-09-12
 */
@MyBatisMapper
public interface GuideDateMapper extends BaseMapper<GuideDate> {
	/**
	 * 删除旧的日期价格
	 * @param guideRoute
	 */
	public void deleteOld(GuideTime guideTime);
	/**
	 * 获取导游某一天的价格
	 * @param guideDate
	 * @return
	 */
	public GuideDate getGuideDate(GuideDate guideDate);
	/**
	 * 获取导游可选择时间
	 * @param guideDate
	 * @return
	 */
	public List<GuideDate> getDateList(GuideDate guideDate);
	/**
	* @method deleteByGuideid
	* @Description 删除旧的日期设置
	* @Author 彭善智
	* @Date 2019/3/14 10:29
	*/
	void deleteByGuideid(String guideid);
}
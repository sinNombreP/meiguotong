/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.guide;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.guide.GuideTime;

/**
 * 导游日期设置MAPPER接口
 * @author dong
 * @version 2018-09-12
 */
@MyBatisMapper
public interface GuideTimeMapper extends BaseMapper<GuideTime> {
	/**
	 * 获取导游的设置日期价格（接口）
	 * @param guideTime
	 * @return
	 */
	public List<GuideTime> getGuideTime(GuideTime guideTime);
	/**
	* @method getGuideType
	* @Description 获取导游类型
	* @Author 彭善智
	* @Date 2019/3/13 11:48
	*/
    String getGuideType(String uid);
	/**
	* @method deleteByGuideid
	* @Description
	* @Author 彭善智
	* @Date 2019/3/14 10:28
	*/
    void deleteByGuideid(String guideid);
}
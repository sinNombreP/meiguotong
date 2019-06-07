/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.liner;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.liner.LinerCourse;

/**
 * 邮轮航区设置MAPPER接口
 * @author dong
 * @version 2018-10-29
 */
@MyBatisMapper
public interface LinerCourseMapper extends BaseMapper<LinerCourse> {
	/**
	 * 根据语言获取游轮航区
	 * @param linerCourse
	 * @return
	 */
	public List<LinerCourse> findCourseList(LinerCourse linerCourse);
}
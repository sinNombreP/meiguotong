/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.liner;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.liner.LinerDate;
import com.jeeplus.modules.meiguotong.entity.liner.LinerTime;

/**
 * 游轮路线日期价格MAPPER接口
 * @author dong
 * @version 2018-10-26
 */
@MyBatisMapper
public interface LinerDateMapper extends BaseMapper<LinerDate> {
	/**
	 * 获取一段时间内游轮路线的出发时间
	 * @param linerDate
	 * @return
	 */
	public List<LinerDate> getDateList(LinerDate linerDate);
	/**
	 * 删除游轮路线日期
	 * @param linerDate
	 */
	public void deleteByLinerLineId(LinerDate linerDate);
}
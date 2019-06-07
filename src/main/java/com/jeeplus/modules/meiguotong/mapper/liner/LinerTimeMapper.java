/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.liner;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.liner.LinerTime;

/**
 * 游轮路线日期设置MAPPER接口
 * @author dong
 * @version 2018-10-26
 */
@MyBatisMapper
public interface LinerTimeMapper extends BaseMapper<LinerTime> {
	/**
	 * 根据游轮路线id获取设置时间
	 * @param lineid
	 * @return
	 */
	public LinerTime getLinerTime(Integer lineid);
	/**
	 * 删除游轮路线时间
	 * @param linerTime
	 */
	public void deleteByLinerLineId(LinerTime linerTime);
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.liner;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.liner.LinerPort;

/**
 * 邮轮港口设置MAPPER接口
 * @author dong
 * @version 2018-10-29
 */
@MyBatisMapper
public interface LinerPortMapper extends BaseMapper<LinerPort> {
	/**
	 * 根据语言获取港口
	 * @param linerPort
	 * @return
	 */
	public List<LinerPort> getPortList(LinerPort linerPort);
	/**
	 * 根据语言城市获取港口
	 * @param linerPort
	 * @return
	 */
	public List<LinerPort> getPortByCity(LinerPort linerPort);
}
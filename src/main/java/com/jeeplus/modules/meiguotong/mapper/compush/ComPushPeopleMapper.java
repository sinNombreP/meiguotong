/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.compush;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.compush.ComPushPeople;

/**
 * 推送人员列表MAPPER接口
 * @author dong
 * @version 2018-09-17
 */
@MyBatisMapper
public interface ComPushPeopleMapper extends BaseMapper<ComPushPeople> {
	/**
	 * 获取消息中心系统消息接口
	 * @param comPushPeople
	 * @return
	 */
	public List<ComPushPeople> getCompush(ComPushPeople comPushPeople);
}
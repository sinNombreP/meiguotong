/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.MemberDigg;

/**
 * 点赞MAPPER接口
 * @author dong
 * @version 2018-03-07
 */
@MyBatisMapper
public interface MemberDiggMapper extends BaseMapper<MemberDigg> {
	/**
	 * 判断用户是否点过赞
	 * @param memberDigg
	 * @return
	 */
	public Integer getCount(MemberDigg memberDigg);
}
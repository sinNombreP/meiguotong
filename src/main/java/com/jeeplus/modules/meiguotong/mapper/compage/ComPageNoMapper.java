/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.compage;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.compage.ComPageNo;

/**
 * 分页展示数量设置MAPPER接口
 * @author dong
 * @version 2018-10-16
 */
@MyBatisMapper
public interface ComPageNoMapper extends BaseMapper<ComPageNo> {
	/**
	 * 获取列表分页数
	 * @return
	 */
	public Integer getPageSize();
}
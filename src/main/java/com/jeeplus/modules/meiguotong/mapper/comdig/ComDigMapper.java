/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comdig;

import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comdig.ComDig;

/**
 * 点赞MAPPER接口
 * @author dong
 * @version 2018-09-27
 */
@MyBatisMapper
public interface ComDigMapper extends BaseMapper<ComDig> {
	/**
	 * 查询是否点赞过
	 * @param id
	 * @return
	 */
	public Integer getCount(ComDig comDig);
	
	public void deleteDig(ComDig comDig);
}
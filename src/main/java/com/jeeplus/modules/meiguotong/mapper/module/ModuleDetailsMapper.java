/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.module;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.module.ModuleDetails;

/**
 * 模块详情MAPPER接口
 * @author psz
 * @version 2018-12-03
 */
@MyBatisMapper
public interface ModuleDetailsMapper extends BaseMapper<ModuleDetails> {

	/**
	* @Title: deleteByModeleContentId
	* @Description: 按内容ID删除数据
	* @author  彭善智
	* @Data 2018年12月10日下午5:09:34
	*/
	void deleteByModeleContentId(String deleteByModeleContentId);
	
}
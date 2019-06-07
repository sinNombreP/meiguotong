/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.module;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtmlName;

/**
 * 模块关联表MAPPER接口
 * @author psz
 * @version 2018-12-03
 */
@MyBatisMapper
public interface ModuleHtmlNameMapper extends BaseMapper<ModuleHtmlName> {

	/**
	* @Title: getModuleHtmlNameList
	* @Description: 获取关联模块
	* @author  彭善智
	* @Data 2018年12月4日下午4:54:28
	*/
	List<ModuleHtmlName> getModuleHtmlNameList(ModuleHtmlName moduleHtmlName);
	
}
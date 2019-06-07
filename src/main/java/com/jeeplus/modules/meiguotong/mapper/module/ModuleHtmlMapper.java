/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.module;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtml;

/**
 * 网站页面MAPPER接口
 * @author psz
 * @version 2018-12-03
 */
@MyBatisMapper
public interface ModuleHtmlMapper extends BaseMapper<ModuleHtml> {

	/**
	* @Title: getModuleHtmlList
	* @Description: 获取所有网站页面
	* @author  彭善智
	* @Data 2018年12月4日下午2:18:59
	*/
	List<ModuleHtml> getModuleHtmlList();

	/**
	* @Title: getModuleDataInfo
	* @Description: 获取页面ID
	* @author  彭善智
	* @Data 2018年12月12日下午3:02:43
	*/
	ModuleHtml getModuleDataInfo(ModuleHtml moduleHtml);
	
}
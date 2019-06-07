/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.module;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.module.ModuleContent;
import org.apache.ibatis.annotations.Param;

/**
 * 模块内容MAPPER接口
 * @author psz
 * @version 2018-12-03
 */
@MyBatisMapper
public interface ModuleContentMapper extends BaseMapper<ModuleContent> {

	/**
	* @Title: getModuleHtmlNameList
	* @Description: 获取关联模块
	* @author  彭善智
	* @Data 2018年12月6日下午7:53:18
	*/
	List<ModuleContent> getModuleHtmlNameList(ModuleContent moduleContent);
	
	public Integer getCount(String languageId);
	/**
	* @Title: getMaxSort
	* @Description: 获取最大排序
	* @author  彭善智
	* @Data 2018年12月11日下午3:05:45
	*/
	Integer getMaxSort(ModuleContent moduleContent);

	/**
	* @Title: getModuleContentById
	* @Description: 获取模块详情
	* @author  彭善智
	* @Data 2018年12月11日下午4:26:40
	*/
	ModuleContent getModuleContentById(String id);

	/**
	* @Title: getModuleDataInfo
	* @Description: 根据页面获取数据
	* @author  彭善智
	* @Data 2018年12月12日下午3:13:51
	*/
	List<ModuleContent> getModuleDataInfo(ModuleContent moduleContent);
	 /**
	   * @method: deleteModule
	   * @Description:  删除模块
	   * @Author:   彭善智
	   * @Date:     2018/12/18 20:53
	   */
    void deleteModule(@Param("id") String id);
}
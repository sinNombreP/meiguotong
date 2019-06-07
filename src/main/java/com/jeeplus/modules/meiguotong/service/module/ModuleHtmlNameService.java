/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.module;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtmlName;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleHtmlNameMapper;

/**
 * 模块关联表Service
 * @author psz
 * @version 2018-12-03
 */
@Service
@Transactional(readOnly = true)
public class ModuleHtmlNameService extends CrudService<ModuleHtmlNameMapper, ModuleHtmlName> {

	public ModuleHtmlName get(String id) {
		return super.get(id);
	}
	
	public List<ModuleHtmlName> findList(ModuleHtmlName moduleHtmlName) {
		return super.findList(moduleHtmlName);
	}
	
	public Page<ModuleHtmlName> findPage(Page<ModuleHtmlName> page, ModuleHtmlName moduleHtmlName) {
		return super.findPage(page, moduleHtmlName);
	}
	
	@Transactional(readOnly = false)
	public void save(ModuleHtmlName moduleHtmlName) {
		super.save(moduleHtmlName);
	}
	
	@Transactional(readOnly = false)
	public void delete(ModuleHtmlName moduleHtmlName) {
		super.delete(moduleHtmlName);
	}

	/**
	* @Title: getModuleHtmlNameList
	* @Description: 获取关联模块
	* @author  彭善智
	* @Data 2018年12月4日下午4:53:54
	*/
	public List<ModuleHtmlName> getModuleHtmlNameList(ModuleHtmlName moduleHtmlName) {
		return mapper.getModuleHtmlNameList(moduleHtmlName);
	}
	
}
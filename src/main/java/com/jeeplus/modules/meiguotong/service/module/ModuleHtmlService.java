/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.module;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.module.ModuleHtml;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleHtmlMapper;

/**
 * 网站页面Service
 * @author psz
 * @version 2018-12-03
 */
@Service
@Transactional(readOnly = true)
public class ModuleHtmlService extends CrudService<ModuleHtmlMapper, ModuleHtml> {

	public ModuleHtml get(String id) {
		return super.get(id);
	}
	
	public List<ModuleHtml> findList(ModuleHtml moduleHtml) {
		return super.findList(moduleHtml);
	}
	
	public Page<ModuleHtml> findPage(Page<ModuleHtml> page, ModuleHtml moduleHtml) {
		return super.findPage(page, moduleHtml);
	}
	
	@Transactional(readOnly = false)
	public void save(ModuleHtml moduleHtml) {
		super.save(moduleHtml);
	}
	
	@Transactional(readOnly = false)
	public void delete(ModuleHtml moduleHtml) {
		super.delete(moduleHtml);
	}

	/**
	* @Title: getModuleHtmlList
	* @Description: 获取所有网站页面
	* @author  彭善智
	* @Data 2018年12月4日下午2:18:04
	*/
	public List<ModuleHtml> getModuleHtmlList() {
		return mapper.getModuleHtmlList();
	}

	/**
	* @Title: getModuleDataInfo
	* @Description: 获取页面ID
	* @author  彭善智
	* @Data 2018年12月12日下午3:01:37
	*/
	public ModuleHtml getModuleDataInfo(ModuleHtml moduleHtml) {
		return mapper.getModuleDataInfo(moduleHtml);
	}
	
}
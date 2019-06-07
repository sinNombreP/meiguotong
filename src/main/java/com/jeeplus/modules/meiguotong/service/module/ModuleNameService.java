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
import com.jeeplus.modules.meiguotong.entity.module.ModuleName;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleNameMapper;

/**
 * 模块名称Service
 * @author psz
 * @version 2018-12-03
 */
@Service
@Transactional(readOnly = true)
public class ModuleNameService extends CrudService<ModuleNameMapper, ModuleName> {

	public ModuleName get(String id) {
		return super.get(id);
	}
	
	public List<ModuleName> findList(ModuleName moduleName) {
		return super.findList(moduleName);
	}
	
	public Page<ModuleName> findPage(Page<ModuleName> page, ModuleName moduleName) {
		return super.findPage(page, moduleName);
	}
	
	@Transactional(readOnly = false)
	public void save(ModuleName moduleName) {
		super.save(moduleName);
	}
	
	@Transactional(readOnly = false)
	public void delete(ModuleName moduleName) {
		super.delete(moduleName);
	}

}
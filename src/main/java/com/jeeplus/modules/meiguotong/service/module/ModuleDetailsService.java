/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.module;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.module.ModuleDetails;
import com.jeeplus.modules.meiguotong.mapper.module.ModuleDetailsMapper;

/**
 * 模块详情Service
 * @author psz
 * @version 2018-12-03
 */
@Service
@Transactional(readOnly = true)
public class ModuleDetailsService extends CrudService<ModuleDetailsMapper, ModuleDetails> {

	public ModuleDetails get(String id) {
		return super.get(id);
	}
	
	public List<ModuleDetails> findList(ModuleDetails moduleDetails) {
		return super.findList(moduleDetails);
	}
	
	public Page<ModuleDetails> findPage(Page<ModuleDetails> page, ModuleDetails moduleDetails) {
		return super.findPage(page, moduleDetails);
	}
	
	@Transactional(readOnly = false)
	public void save(ModuleDetails moduleDetails) {
		super.save(moduleDetails);
	}
	
	@Transactional(readOnly = false)
	public void delete(ModuleDetails moduleDetails) {
		super.delete(moduleDetails);
	}
	
}
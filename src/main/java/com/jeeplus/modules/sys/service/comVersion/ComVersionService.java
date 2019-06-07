/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.comVersion;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.comVersion.ComVersion;
import com.jeeplus.modules.sys.mapper.comVersion.ComVersionMapper;

/**
 * 版本管理Service
 * @author laiyanxin
 * @version 2018-03-05
 */
@Service
@Transactional(readOnly = true)
public class ComVersionService extends CrudService<ComVersionMapper, ComVersion> {

	public ComVersion get(String id) {
		return super.get(id);
	}
	
	public List<ComVersion> findList(ComVersion comVersion) {
		return super.findList(comVersion);
	}
	
	public Page<ComVersion> findPage(Page<ComVersion> page, ComVersion comVersion) {
		return super.findPage(page, comVersion);
	}
	
	@Transactional(readOnly = false)
	public void save(ComVersion comVersion) {
		super.save(comVersion);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComVersion comVersion) {
		super.delete(comVersion);
	}
	
}
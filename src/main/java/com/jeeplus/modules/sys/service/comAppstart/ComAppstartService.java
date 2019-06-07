/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.comAppstart;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.comAppstart.ComAppstart;
import com.jeeplus.modules.sys.entity.comVersion.ComVersion;
import com.jeeplus.modules.sys.mapper.comAppstart.ComAppstartMapper;

/**
 * APP启动界面Service
 * @author laiyanxin
 * @version 2018-03-05
 */
@Service
@Transactional(readOnly = true)
public class ComAppstartService extends CrudService<ComAppstartMapper, ComAppstart> {

	public ComAppstart get(String id) {
		return super.get(id);
	}
	
	public List<ComAppstart> findList(ComAppstart comAppstart) {
		return super.findList(comAppstart);
	}
	
	public Page<ComAppstart> findPage(Page<ComAppstart> page, ComAppstart comAppstart) {
		return super.findPage(page, comAppstart);
	}
	
	@Transactional(readOnly = false)
	public void save(ComAppstart comAppstart) {
		super.save(comAppstart);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComAppstart comAppstart) {
		super.delete(comAppstart);
	}

	/**查找版本号
	 * @param version 版本号
	 * @param source 来源
	 * @return
	 */
	public ComVersion findVersion(String version, Integer source) {
		return mapper.findVersion(version,source);
	}

	public ComVersion findNewVersion(Integer source) {
		return mapper.findNewVersion(source);
	}
	
}
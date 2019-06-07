/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.common;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.common.ComLoginLog;
import com.jeeplus.modules.sys.mapper.common.ComLoginLogMapper;

/**
 * 会员登录管理Service
 * @author xudemo
 * @version 2018-03-07
 */
@Service
@Transactional(readOnly = true)
public class ComLoginLogService extends CrudService<ComLoginLogMapper, ComLoginLog> {

	public ComLoginLog get(String id) {
		return super.get(id);
	}
	
	public List<ComLoginLog> findList(ComLoginLog comLoginLog) {
		return super.findList(comLoginLog);
	}
	
	public Page<ComLoginLog> findPage(Page<ComLoginLog> page, ComLoginLog comLoginLog) {
		return super.findPage(page, comLoginLog);
	}
	
	@Transactional(readOnly = false)
	public void save(ComLoginLog comLoginLog) {
		super.save(comLoginLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComLoginLog comLoginLog) {
		super.delete(comLoginLog);
	}
	
}
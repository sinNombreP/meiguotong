/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.common;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.common.ComTripartiteLogin;
import com.jeeplus.modules.sys.mapper.common.ComTripartiteLoginMapper;

/**
 * 三方登录Service
 * @author xudemo
 * @version 2018-01-16
 */
@Service
@Transactional(readOnly = true)
public class ComTripartiteLoginService extends CrudService<ComTripartiteLoginMapper, ComTripartiteLogin> {

	public ComTripartiteLogin get(String id) {
		return super.get(id);
	}
	
	public ComTripartiteLogin findAPI(ComTripartiteLogin comTripartiteLogin) {
		return mapper.findAPI(comTripartiteLogin);
	}
	
	public List<ComTripartiteLogin> findList(ComTripartiteLogin comTripartiteLogin) {
		return super.findList(comTripartiteLogin);
	}
	
	public List<ComTripartiteLogin> findByMember(ComTripartiteLogin comTripartiteLogin) {
		return mapper.findByMember(comTripartiteLogin);
	}
	
	public Page<ComTripartiteLogin> findPage(Page<ComTripartiteLogin> page, ComTripartiteLogin comTripartiteLogin) {
		return super.findPage(page, comTripartiteLogin);
	}
	
	@Transactional(readOnly = false)
	public void save(ComTripartiteLogin comTripartiteLogin) {
		super.save(comTripartiteLogin);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComTripartiteLogin comTripartiteLogin) {
		super.delete(comTripartiteLogin);
	}
	
	@Transactional(readOnly = false)
	public void updateBang(ComTripartiteLogin comTripartiteLogin) {
		mapper.updateBang(comTripartiteLogin);
	}
}
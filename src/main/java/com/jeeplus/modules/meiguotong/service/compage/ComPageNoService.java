/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.compage;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.compage.ComPageNo;
import com.jeeplus.modules.meiguotong.mapper.compage.ComPageNoMapper;

/**
 * 分页展示数量设置Service
 * @author dong
 * @version 2018-10-16
 */
@Service
@Transactional(readOnly = true)
public class ComPageNoService extends CrudService<ComPageNoMapper, ComPageNo> {

	public ComPageNo get(String id) {
		return super.get(id);
	}
	/**
	 * 获取列表分页数
	 * @return
	 */
	public Integer getPageSize(){
		return mapper.getPageSize();
	}
	public List<ComPageNo> findList(ComPageNo comPageNo) {
		return super.findList(comPageNo);
	}
	
	public Page<ComPageNo> findPage(Page<ComPageNo> page, ComPageNo comPageNo) {
		return super.findPage(page, comPageNo);
	}
	
	@Transactional(readOnly = false)
	public void save(ComPageNo comPageNo) {
		super.save(comPageNo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComPageNo comPageNo) {
		super.delete(comPageNo);
	}
	
}
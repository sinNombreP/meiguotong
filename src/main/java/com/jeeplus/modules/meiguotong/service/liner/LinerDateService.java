/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.liner;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.liner.LinerDate;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerDateMapper;

/**
 * 游轮路线日期价格Service
 * @author dong
 * @version 2018-10-26
 */
@Service
@Transactional(readOnly = true)
public class LinerDateService extends CrudService<LinerDateMapper, LinerDate> {

	public LinerDate get(String id) {
		return super.get(id);
	}
	/**
	 * 获取一段时间内游轮路线的出发时间
	 * @param linerDate
	 * @return
	 */
	public List<LinerDate> getDateList(LinerDate linerDate) {
		return mapper.getDateList(linerDate);
	}
	
	public List<LinerDate> findList(LinerDate linerDate) {
		return super.findList(linerDate);
	}
	
	public Page<LinerDate> findPage(Page<LinerDate> page, LinerDate linerDate) {
		return super.findPage(page, linerDate);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerDate linerDate) {
		super.save(linerDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerDate linerDate) {
		super.delete(linerDate);
	}
	
}
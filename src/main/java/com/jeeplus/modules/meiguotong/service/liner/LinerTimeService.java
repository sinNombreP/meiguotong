/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.liner;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.liner.LinerTime;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerTimeMapper;

/**
 * 游轮路线日期设置Service
 * @author dong
 * @version 2018-10-26
 */
@Service
@Transactional(readOnly = true)
public class LinerTimeService extends CrudService<LinerTimeMapper, LinerTime> {

	public LinerTime get(String id) {
		return super.get(id);
	}
	/**
	 * 根据游轮路线id获取设置时间
	 * @param lineid
	 * @return
	 */
	public LinerTime getLinerTime(Integer lineid) {
		return mapper.getLinerTime(lineid);
	}
	
	public List<LinerTime> findList(LinerTime linerTime) {
		return super.findList(linerTime);
	}
	
	public Page<LinerTime> findPage(Page<LinerTime> page, LinerTime linerTime) {
		return super.findPage(page, linerTime);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerTime linerTime) {
		super.save(linerTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerTime linerTime) {
		super.delete(linerTime);
	}
	
}
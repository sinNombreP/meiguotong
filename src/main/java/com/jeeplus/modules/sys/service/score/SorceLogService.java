/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.score;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.score.SorceLog;
import com.jeeplus.modules.sys.mapper.score.SorceLogMapper;

/**
 * 积分管理Service
 * @author psz
 * @version 2018-03-07
 */
@Service
@Transactional(readOnly = true)
public class SorceLogService extends CrudService<SorceLogMapper, SorceLog> {

	public SorceLog get(String id) {
		return super.get(id);
	}
	
	public List<SorceLog> findList(SorceLog sorceLog) {
		return super.findList(sorceLog);
	}
	
	public Page<SorceLog> findPage(Page<SorceLog> page, SorceLog sorceLog) {
		return super.findPage(page, sorceLog);
	}
	
	public Integer getMyUseScore(String memberid){
		return mapper.getMyUseScore(memberid);
	}
	
	/**
	 * 获取积分
	 * @param sorceLog
	 * @return
	 */
	public Integer getMyScore(SorceLog sorceLog){
		return mapper.getMyScore(sorceLog);
	}
	
	@Transactional(readOnly = false)
	public void save(SorceLog sorceLog) {
		super.save(sorceLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(SorceLog sorceLog) {
		super.delete(sorceLog);
	}
	
	public Page<SorceLog> findSorceLogPage(Page<SorceLog> page, SorceLog sorceLog) {
		sorceLog.setPage(page);
		page.setList(mapper.findSorceLogList(sorceLog));
		return page;
	}
	
}
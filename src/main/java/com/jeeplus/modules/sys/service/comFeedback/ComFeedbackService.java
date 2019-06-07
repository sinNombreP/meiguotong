/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.comFeedback;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.comFeedback.ComFeedback;
import com.jeeplus.modules.sys.mapper.comFeedback.ComFeedbackMapper;

/**
 * 意见反馈Service
 * @author laiyanxin
 * @version 2018-03-05
 */
@Service
@Transactional(readOnly = true)
public class ComFeedbackService extends CrudService<ComFeedbackMapper, ComFeedback> {

	public ComFeedback get(String id) {
		return super.get(id);
	}
	
	public List<ComFeedback> findList(ComFeedback comFeedback) {
		return super.findList(comFeedback);
	}
	
	public Page<ComFeedback> findPage(Page<ComFeedback> page, ComFeedback comFeedback) {
		return super.findPage(page, comFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(ComFeedback comFeedback) {
		super.save(comFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComFeedback comFeedback) {
		super.delete(comFeedback);
	}
	
}
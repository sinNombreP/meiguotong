/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comconsult;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comconsult.ComConsult;
import com.jeeplus.modules.meiguotong.mapper.comconsult.ComConsultMapper;

/**
 * 用户咨询Service
 * @author psz
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class ComConsultService extends CrudService<ComConsultMapper, ComConsult> {

	public ComConsult get(String id) {
		return super.get(id);
	}
	
	public List<ComConsult> findList(ComConsult comConsult) {
		return super.findList(comConsult);
	}
	/**
	 * 根据类型获取产品咨询（接口）
	 * @param page
	 * @param comConsult
	 * @return
	 */
	public Page<ComConsult> getRouteConsult(Page<ComConsult> page, ComConsult comConsult) {
		dataRuleFilter(comConsult);
		comConsult.setPage(page);
		page.setList(mapper.getRouteConsult(comConsult));
		return page;
	}
	public Page<ComConsult> findPage(Page<ComConsult> page, ComConsult comConsult) {
		return super.findPage(page, comConsult);
	}
	
	@Transactional(readOnly = false)
	public void save(ComConsult comConsult) {
		super.save(comConsult);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComConsult comConsult) {
		super.delete(comConsult);
	}

	/** 
	* @Title: updateStatus 
	* @Description: 启用禁用
	* @author 彭善智
	* @date 2018年8月16日下午5:47:38
	*/ 
	@Transactional(readOnly = false)
	public void updateStatus(ComConsult comConsult) {
		mapper.updateStatus(comConsult);
	}

	/** 
	* @Title: updateContent 
	* @Description: 回复
	* @author 彭善智
	* @date 2018年8月16日下午5:53:23
	*/ 
	@Transactional(readOnly = false)
	public void updateContent(ComConsult comConsult) {
		comConsult.preUpdate();
		mapper.update(comConsult);
	}
    /**
     * 用户资讯列表接口
     * @param page
     * @param comConsult
     * @return
     */
	public Page<ComConsult> findComConsultList(Page<ComConsult> page, ComConsult comConsult) {
		dataRuleFilter(comConsult);
		comConsult.setPage(page);
		page.setList(mapper.findComConsultList(comConsult));
		return page;
	}
	
}
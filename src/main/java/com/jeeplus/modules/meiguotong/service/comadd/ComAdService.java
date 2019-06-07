/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comadd;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comadd.ComAd;
import com.jeeplus.modules.meiguotong.mapper.comadd.ComAdMapper;

/**
 * 广告设置Service
 * @author cdq
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly = true)
public class ComAdService extends CrudService<ComAdMapper, ComAd> {

	public ComAd get(String id) {
		return super.get(id);
	}
	
	public List<ComAd> findList(ComAd comAd) {
		return super.findList(comAd);
	}
	/**
	 * 根据语言获取广告
	 * @param comAd
	 * @return
	 */
	public List<ComAd> getAdvertList(ComAd comAd) {
		return mapper.getAdvertList(comAd);
	}
	
	public Page<ComAd> findPage(Page<ComAd> page, ComAd comAd) {
		return super.findPage(page, comAd);
	}
	
	@Transactional(readOnly = false)
	public void save(ComAd comAd) {
		super.save(comAd);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComAd comAd) {
		super.delete(comAd);
	}
 
	/**
	 * 广告列表接口
	 * @param page
	 * @param comAd
	 * @return
	 */
public Page<ComAd> findComAdList(Page<ComAd> page, ComAd comAd) {
	dataRuleFilter(comAd);
	comAd.setPage(page);
	page.setList(mapper.findComAdList(comAd));
	return page;
}
    /**
     * 广告详情接口
     * @param comAd
     * @return
     */
	public ComAd getComAd(ComAd comAd) {
		return mapper.getComAd(comAd);
	}
	/**
	 * 根据id获取广告
	 * @param comAd
	 * @return
	 */
	public List<ComAd> getAdvertListById(String ids) {
		return mapper.getAdvertListById(ids);
	}
	
}
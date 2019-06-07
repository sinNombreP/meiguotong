/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comnavigation;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comnavigation.ComNavigation;
import com.jeeplus.modules.meiguotong.mapper.comnavigation.ComNavigationMapper;

/**
 * 主导航Service
 * @author cdq
 * @version 2018-07-27
 */
@Service
@Transactional(readOnly = true)
public class ComNavigationService extends CrudService<ComNavigationMapper, ComNavigation> {

	public ComNavigation get(String id) {
		return super.get(id);
	}
	/**
	 * 根据语言获取热门目的地
	 * @param comNavigation
	 * @return
	 */
	public List<ComNavigation> getHotCity(ComNavigation comNavigation) {
		return mapper.getHotCity(comNavigation);
	}
	public List<ComNavigation> findList(ComNavigation comNavigation) {
		return super.findList(comNavigation);
	}
	
	public Page<ComNavigation> findPage(Page<ComNavigation> page, ComNavigation comNavigation) {
		return super.findPage(page, comNavigation);
	}
	
	@Transactional(readOnly = false)
	public void save(ComNavigation comNavigation) {
		super.save(comNavigation);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComNavigation comNavigation) {
		comNavigation.preDel();
		super.delete(comNavigation);
	}
	public ComNavigation findNumber(ComNavigation comNavigation) {
		return mapper.findNumber(comNavigation);
	}
  /**
   * 热门城市的列表
   * @param page
   * @param comNavigation
   * @return
   */
	public Page<ComNavigation> HotCity(Page<ComNavigation> page, ComNavigation comNavigation) {
		dataRuleFilter(comNavigation);
		comNavigation.setPage(page);
		page.setList(mapper.findHotCityList(comNavigation));  
		return page;
	}
/**
 * 获取主导航列表接口
 * @param page
 * @param comAd
 * @return
 */
public Page<ComNavigation> findNavigationList(Page<ComNavigation> page, ComNavigation comNavigation) {
	dataRuleFilter(comNavigation);
	comNavigation.setPage(page);
	page.setList(mapper.findNavigationList(comNavigation));  
	return page;
}
	 /**
	   * @method: getComNavigation
	   * @Description:  获取首页导航栏
	   * @Author:   彭善智
	   * @Date:     2019/1/4 14:39
	   */
	public List<ComNavigation> getComNavigation(ComNavigation comNavigation) {
	    return mapper.getComNavigation(comNavigation);
	}
}
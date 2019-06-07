/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.guide;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.guide.GuideDate;
import com.jeeplus.modules.meiguotong.mapper.guide.GuideDateMapper;

/**
 * 导游日期价格设置Service
 * @author dong
 * @version 2018-09-12
 */
@Service
@Transactional(readOnly = true)
public class GuideDateService extends CrudService<GuideDateMapper, GuideDate> {
	
	public GuideDate get(String id) {
		return super.get(id);
	}
	/**
	 * 获取导游某一天的价格
	 * @param guideDate
	 * @return
	 */
	public GuideDate getGuideDate(GuideDate guideDate) {
		return mapper.getGuideDate(guideDate);
	}
	/**
	 * 获取导游可选择时间
	 * @param guideDate
	 * @return
	 */
	public List<GuideDate> getDateList(GuideDate guideDate) {
		return mapper.getDateList(guideDate);
	}
	public List<GuideDate> findList(GuideDate guideDate) {
		return super.findList(guideDate);
	}
	
	public Page<GuideDate> findPage(Page<GuideDate> page, GuideDate guideDate) {
		return super.findPage(page, guideDate);
	}
	
	@Transactional(readOnly = false)
	public void save(GuideDate guideDate) {
		super.save(guideDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuideDate guideDate) {
		super.delete(guideDate);
	}
	
}
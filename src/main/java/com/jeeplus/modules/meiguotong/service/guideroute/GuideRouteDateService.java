/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.guideroute;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteDate;
import com.jeeplus.modules.meiguotong.mapper.guideroute.GuideRouteDateMapper;

/**
 * 导游路线日期价格设置Service
 * @author dong
 * @version 2018-09-13
 */
@Service
@Transactional(readOnly = true)
public class GuideRouteDateService extends CrudService<GuideRouteDateMapper, GuideRouteDate> {

	public GuideRouteDate get(String id) {
		return super.get(id);
	}
	/**
	 * 获取导游路线可预订日期
	 * @param guideRouteDate
	 * @return
	 */
	public List<GuideRouteDate> getDateList(GuideRouteDate guideRouteDate) {
		return mapper.getDateList(guideRouteDate);
	}
	public List<GuideRouteDate> findList(GuideRouteDate guideRouteDate) {
		return super.findList(guideRouteDate);
	}
	
	public Page<GuideRouteDate> findPage(Page<GuideRouteDate> page, GuideRouteDate guideRouteDate) {
		return super.findPage(page, guideRouteDate);
	}
	
	@Transactional(readOnly = false)
	public void save(GuideRouteDate guideRouteDate) {
		super.save(guideRouteDate);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuideRouteDate guideRouteDate) {
		super.delete(guideRouteDate);
	}
	
}
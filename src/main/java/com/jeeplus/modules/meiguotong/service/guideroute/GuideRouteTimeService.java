/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.guideroute;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.guideroute.GuideRouteTime;
import com.jeeplus.modules.meiguotong.mapper.guideroute.GuideRouteTimeMapper;

/**
 * 导游路线日期设置Service
 * @author dong
 * @version 2018-09-13
 */
@Service
@Transactional(readOnly = true)
public class GuideRouteTimeService extends CrudService<GuideRouteTimeMapper, GuideRouteTime> {

	public GuideRouteTime get(String id) {
		return super.get(id);
	}
	
	public List<GuideRouteTime> findList(GuideRouteTime guideRouteTime) {
		return super.findList(guideRouteTime);
	}
	
	public Page<GuideRouteTime> findPage(Page<GuideRouteTime> page, GuideRouteTime guideRouteTime) {
		return super.findPage(page, guideRouteTime);
	}
	
	@Transactional(readOnly = false)
	public void save(GuideRouteTime guideRouteTime) {
		super.save(guideRouteTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuideRouteTime guideRouteTime) {
		super.delete(guideRouteTime);
	}
	
}
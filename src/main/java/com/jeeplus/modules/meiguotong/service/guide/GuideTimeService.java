/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.guide;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.guide.GuideTime;
import com.jeeplus.modules.meiguotong.mapper.guide.GuideTimeMapper;

/**
 * 导游日期设置Service
 * @author dong
 * @version 2018-09-12
 */
@Service
@Transactional(readOnly = true)
public class GuideTimeService extends CrudService<GuideTimeMapper, GuideTime> {

	public GuideTime get(String id) {
		return super.get(id);
	}
	
	/**
	 * 获取导游的设置日期价格（接口）
	 * @param guideTime
	 * @return
	 */
	public List<GuideTime> getGuideTime(GuideTime guideTime) {
		return mapper.getGuideTime(guideTime);
	}
	
	public List<GuideTime> findList(GuideTime guideTime) {
		return super.findList(guideTime);
	}
	
	public Page<GuideTime> findPage(Page<GuideTime> page, GuideTime guideTime) {
		return super.findPage(page, guideTime);
	}
	
	@Transactional(readOnly = false)
	public void save(GuideTime guideTime) {
		super.save(guideTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(GuideTime guideTime) {
		super.delete(guideTime);
	}
	/**
	* @method getGuideType
	* @Description 获取导游类型
	* @Author 彭善智
	* @Date 2019/3/13 11:47
	*/
    public String getGuideType(String uid) {
    	return mapper.getGuideType(uid);
    }
}
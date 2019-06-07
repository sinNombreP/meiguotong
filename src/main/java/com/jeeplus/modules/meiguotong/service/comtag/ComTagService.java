/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comtag;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.entity.comtag.ComTag;
import com.jeeplus.modules.meiguotong.entity.guide.Guide;
import com.jeeplus.modules.meiguotong.mapper.comtag.ComTagMapper;

/**
 * 各项管理Service
 * 
 * @author cdq
 * @version 2018-07-31
 */
@Service
@Transactional(readOnly = true)
public class ComTagService extends CrudService<ComTagMapper, ComTag> {

	public ComTag get(String id) {
		return super.get(id);
	}

	/**
	 * 获取玩家擅长属性
	 * 
	 * @return
	 */
	public List<ComTag> getGuideLabel(Integer languageId) {
		return mapper.getGuideLabel(languageId);
	}

	/**
	 * 获取各类型标签属性(类型1234)
	 * 
	 * @return
	 */
	public List<ComTag> getRouteLabel(ComTag comTag) {
		return mapper.getRouteLabel(comTag);
	}

	/**
	 * 获取各类型标签属性(类型56)
	 * 
	 * @return
	 */
	public List<ComTag> getLabel(ComTag comTag) {
		return mapper.getLabel(comTag);
	}

	/**
	 * 根据语言获取标签
	 * 
	 * @return
	 */
	public List<ComTag> getLabelByLanguage(ComTag comTag) {
		return mapper.getLabelByLanguage(comTag);
	}

	public List<ComTag> findList(ComTag comTag) {
		return super.findList(comTag);
	}

	public Page<ComTag> findPage(Page<ComTag> page, ComTag comTag) {
		return super.findPage(page, comTag);
	}

	@Transactional(readOnly = false)
	public void save(ComTag comTag) {
		super.save(comTag);
	}

	@Transactional(readOnly = false)
	public void delete(ComTag comTag) {
		super.delete(comTag);
	}

	/**
	 * 修改状态
	 * 
	 * @param comTag
	 */
	@Transactional(readOnly = false)
	public void status(ComTag comTag) {
		mapper.status(comTag);
	}

	/**
	 * 当地玩家列表数据
	 * 
	 * @param page
	 * @param comTag
	 * @return
	 */
	public Page<ComTag> PlayerList(Page<ComTag> page, ComTag comTag) {
		dataRuleFilter(comTag);
		comTag.setPage(page);
		page.setList(mapper.PlayerList(comTag));
		return page;
	}

	/**
	 * @Title: getNameByType
	 * @Description: 根据type查询属性
	 * @author 彭善智
	 * @date 2018年8月13日下午4:05:21
	 */
	public List<ComTag> getNameByType(ComTag comTag) {
		return mapper.getNameByType(comTag);
	}
	/**
	 * 获取某个类型还未添加的属性
	 * @param comTag
	 * @return
	 */
	public List<ComTag> getTagByType(ComTag comTag) {
		return mapper.getTagByType(comTag);
	}
	
	/**
	 * @Title: getDate
	 * @Description: 获取列表数据
	 * @author 彭善智
	 * @date 2018年8月15日上午10:23:14
	 */
	public Page<ComTag> getDate(Page<ComTag> page, ComTag comTag) {
		dataRuleFilter(comTag);
		comTag.setPage(page);
		page.setList(mapper.getNameByType(comTag));
		return page;
	}

	/**
	 * 查找当地玩家所有属性名称
	 * 
	 * @return
	 */
	public List<ComTag> getList() {
		return mapper.getList();
	}
	/**
	 * 查找当地玩家已有属性
	 * @return
	 */
	public List<ComTag> getGuideTagList(Guide guide) {
		return mapper.getGuideTagList(guide);
	}

}
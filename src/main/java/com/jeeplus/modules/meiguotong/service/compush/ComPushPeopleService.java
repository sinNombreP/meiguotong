/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.compush;

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.compush.ComPushPeople;
import com.jeeplus.modules.meiguotong.mapper.compush.ComPushPeopleMapper;

/**
 * 推送人员列表Service
 * @author dong
 * @version 2018-09-17
 */
@Service
@Transactional(readOnly = true)
public class ComPushPeopleService extends CrudService<ComPushPeopleMapper, ComPushPeople> {

	public ComPushPeople get(String id) {
		return super.get(id);
	}
	
	public List<ComPushPeople> findList(ComPushPeople comPushPeople) {
		return super.findList(comPushPeople);
	}
	/**
	 * 获取消息中心系统消息接口
	 * @param page
	 * @param comPushPeople
	 * @return
	 */
	public Page<ComPushPeople> getCompush(Page<ComPushPeople> page, ComPushPeople comPushPeople) {
		dataRuleFilter(comPushPeople);
		comPushPeople.setPage(page);
		List<ComPushPeople> list = mapper.getCompush(comPushPeople);
		if(list!=null&&list.size()>0){
			for(ComPushPeople a:list){
				a.setContent(StringEscapeUtils.unescapeHtml(a.getContent()));
			}
		}
		page.setList(list);
		return page;
	}
	public Page<ComPushPeople> findPage(Page<ComPushPeople> page, ComPushPeople comPushPeople) {
		return super.findPage(page, comPushPeople);
	}
	
	@Transactional(readOnly = false)
	public void save(ComPushPeople comPushPeople) {
		super.save(comPushPeople);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComPushPeople comPushPeople) {
		super.delete(comPushPeople);
	}
	
}
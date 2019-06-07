/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.compush;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.compush.ComPushContent;
import com.jeeplus.modules.meiguotong.mapper.compush.ComPushContentMapper;

/**
 * 自动推送模板Service
 * @author dong
 * @version 2019-03-13
 */
@Service
@Transactional(readOnly = true)
public class ComPushContentService extends CrudService<ComPushContentMapper, ComPushContent> {

	@Autowired
	private ComPushContentMapper comPushContentMapper;
	
	public ComPushContent get(String id) {
		return super.get(id);
	}
	
	public List<ComPushContent> findList(ComPushContent comPushContent) {
		return super.findList(comPushContent);
	}
	
	public Page<ComPushContent> findPage(Page<ComPushContent> page, ComPushContent comPushContent) {
		return super.findPage(page, comPushContent);
	}
	
	@Transactional(readOnly = false)
	public void save(ComPushContent comPushContent) {
		List<ComPushContent> list=comPushContent.getList();
		if (list!=null&&list.size()>0) {
			for(ComPushContent a:list){
				if(a.getStatus()==null){
					a.setStatus(2);
				}
				a.setLanguageid(comPushContent.getLanguageid());
				super.save(a);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ComPushContent comPushContent) {
		super.delete(comPushContent);
	}
	
	public List<ComPushContent> findComPushContentByLanguageid(Integer id){
		return comPushContentMapper.findComPushContentByLanguageid(id);
	}
}
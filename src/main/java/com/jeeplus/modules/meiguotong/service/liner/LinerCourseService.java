/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.liner;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.liner.LinerCourse;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerCourseMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 邮轮航区设置Service
 * @author dong
 * @version 2018-10-29
 */
@Service
@Transactional(readOnly = true)
public class LinerCourseService extends CrudService<LinerCourseMapper, LinerCourse> {

	public LinerCourse get(String id) {
		return super.get(id);
	}
	
	public List<LinerCourse> findList(LinerCourse linerCourse) {
		return super.findList(linerCourse);
	}
	/**
	 * 根据语言获取游轮航区
	 * @param linerCourse
	 * @return
	 */
	public List<LinerCourse> findCourseList(LinerCourse linerCourse) {
		return mapper.findCourseList(linerCourse);
	}
	public Page<LinerCourse> findPage(Page<LinerCourse> page, LinerCourse linerCourse) {
		return super.findPage(page, linerCourse);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerCourse linerCourse) {
		super.save(linerCourse);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerCourse linerCourse) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			linerCourse.setDelBy(user);
			linerCourse.setDelDate(new Date());
		}
		super.delete(linerCourse);
	}
	
}
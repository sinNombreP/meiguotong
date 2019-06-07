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
import com.jeeplus.modules.meiguotong.entity.liner.LinerCompany;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerCompanyMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 游轮公司表Service
 * @author dong
 * @version 2018-10-29
 */
@Service
@Transactional(readOnly = true)
public class LinerCompanyService extends CrudService<LinerCompanyMapper, LinerCompany> {

	public LinerCompany get(String id) {
		return super.get(id);
	}
	/**
	 * 根据语言获取邮轮公司
	 * @param linerCompany
	 * @return
	 */
	public List<LinerCompany> findCompanyList(LinerCompany linerCompany) {
		return mapper.findCompanyList(linerCompany);
	}
	public List<LinerCompany> findList(LinerCompany linerCompany) {
		return super.findList(linerCompany);
	}
	
	public Page<LinerCompany> findPage(Page<LinerCompany> page, LinerCompany linerCompany) {
		return super.findPage(page, linerCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(LinerCompany linerCompany) {
		super.save(linerCompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(LinerCompany linerCompany) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			linerCompany.setDelBy(user);
			linerCompany.setDelDate(new Date());
		}
		super.delete(linerCompany);
	}
	
	/**
	 * 判断公司下是否有游轮
	 * @param linerCompany
	 * @return
	 */
	public Integer getCount(LinerCompany linerCompany) {
		return mapper.getCount(linerCompany);
	}
	
}
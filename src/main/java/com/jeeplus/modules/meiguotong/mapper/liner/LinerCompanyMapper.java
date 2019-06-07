/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.liner;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.liner.LinerCompany;

/**
 * 游轮公司表MAPPER接口
 * @author dong
 * @version 2018-10-29
 */
@MyBatisMapper
public interface LinerCompanyMapper extends BaseMapper<LinerCompany> {
	/**
	 * 根据语言获取邮轮公司
	 * @param linerCompany
	 * @return
	 */
	public List<LinerCompany> findCompanyList(LinerCompany linerCompany);
	
	/**
	 * 判断公司下是否有游轮
	 * @param linerCompany
	 * @return
	 */
	public Integer getCount(LinerCompany linerCompany);
}
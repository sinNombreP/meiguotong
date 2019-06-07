/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.liner;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.liner.Liner;
import com.jeeplus.modules.meiguotong.entity.liner.LinerCompany;

/**
 * 邮轮管理表MAPPER接口
 * @author cdq
 * @version 2018-08-13
 */
@MyBatisMapper
public interface LinerMapper extends BaseMapper<Liner> {
   /**
    * 修改状态
    * @param liner
    */
	void status(Liner liner);
	/**
	 *查找邮轮
	 * @return
	 */
	List<Liner> findCruisename();

	/**
	 * 接口获取游轮信息
	 * @param liner
	 * @return
	 */
	public Liner getLiner(Liner liner);
	/**
	 * 根据语言获取可用邮轮公司（未删除，未禁用）
	 * @param linerCompany
	 * @return
	 */
	public List<LinerCompany> getCompany(LinerCompany linerCompany);
	/**
	 * 根据语言获取未删除邮轮公司
	 * @param linerCompany
	 * @return
	 */
	public List<LinerCompany> getCompanyNoDel(LinerCompany linerCompany);
	/**
	 * 根据语言获取可用邮轮（未删除，未禁用）
	 * @param liner
	 * @return
	 */
	public List<Liner> getLinerBylanguage(Liner liner);
	
}
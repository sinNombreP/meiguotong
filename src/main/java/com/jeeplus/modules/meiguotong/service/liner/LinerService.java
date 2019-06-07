/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.liner;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.liner.Liner;
import com.jeeplus.modules.meiguotong.entity.liner.LinerCompany;
import com.jeeplus.modules.meiguotong.mapper.liner.LinerMapper;

/**
 * 邮轮管理表Service
 * @author cdq
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class LinerService extends CrudService<LinerMapper, Liner> {

	public Liner get(String id) {
		return super.get(id);
	}
	/**
	 * 根据语言获取可用邮轮（未删除，未禁用）
	 * @param liner
	 * @return
	 */
	public List<Liner> getLinerBylanguage(Liner liner) {
		return mapper.getLinerBylanguage(liner);
	}
	/**
	 * 接口获取游轮信息
	 * @param liner
	 * @return
	 */
	public Liner getLiner(Liner liner) {
		return mapper.getLiner(liner);
	}
	/**
	 * 根据语言获取可用邮轮公司（未删除，未禁用）
	 * @param linerCompany
	 * @return
	 */
	public List<LinerCompany> getCompany(LinerCompany linerCompany) {
		return mapper.getCompany(linerCompany);
	}
	/**
	 * 根据语言获取未删除邮轮公司
	 * @param linerCompany
	 * @return
	 */
	public List<LinerCompany> getCompanyNoDel(LinerCompany linerCompany) {
		return mapper.getCompanyNoDel(linerCompany);
	}
	
	public List<Liner> findList(Liner liner) {
		return super.findList(liner);
	}
	
	public Page<Liner> findPage(Page<Liner> page, Liner liner) {
		liner.preFind();
		return super.findPage(page, liner);
	}
	
	@Transactional(readOnly = false)
	public void save(Liner liner) {
		//获取登录供应商id
		liner.preFind();
		//修改方法未修改agentid字段，不用判断修改角色
		super.save(liner);
	}
	
	@Transactional(readOnly = false)
	public void delete(Liner liner) {
		super.delete(liner);
	}
 /**
  * 修改状态
  * @param liner
  */
	@Transactional(readOnly = false)
	public void status(Liner liner) {
		mapper.status(liner);
	}
/**
 * 查找邮轮
 */
public List<Liner> findCruiseName() {
	return mapper.findCruisename();
}
	
}
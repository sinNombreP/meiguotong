/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.insurance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;
import com.jeeplus.modules.meiguotong.mapper.insurance.InsuranceRelationModMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 保险项目关联Service
 * @author PSZ
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class InsuranceRelationModService extends CrudService<InsuranceRelationModMapper, InsuranceRelationMod> {

	public InsuranceRelationMod get(String id) {
		return super.get(id);
	}
	/**
	 * 获取不同类型保险方案
	 * @param insuranceRelationMod
	 * @return
	 */
	public List<Insurance> getInsurance(InsuranceRelationMod insuranceRelationMod) {
		List<Insurance> list = mapper.getInsurance(insuranceRelationMod);
		if(list!=null&&list.size()>0){
			for(Insurance a:list){
				a.setContent(StringEscapeUtils.unescapeHtml(a.getContent()));
			}
		}
		return list;
	}
	public List<InsuranceRelationMod> findList(InsuranceRelationMod insuranceRelationMod) {
		return super.findList(insuranceRelationMod);
	}
	
	public Page<InsuranceRelationMod> findPage(Page<InsuranceRelationMod> page, InsuranceRelationMod insuranceRelationMod) {
		List<InsuranceRelationMod> lists = new ArrayList<InsuranceRelationMod>();
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			insuranceRelationMod.setAgentid(user.getAgentid());
		}
		for(int a = 1; a <= 9 ; a++) {
			insuranceRelationMod.setType(a);
			InsuranceRelationMod b = new InsuranceRelationMod();
			b.setType(a);
			InsuranceRelationMod m = mapper.getInfor(insuranceRelationMod);
			if(m != null) {
				b.setInsuranceId(m.getInsuranceId());
				b.setId(m.getId());
				b.setInsuranceName(m.getInsuranceName());
			}
        	switch (a) {
			case 1:b.setName("包车租车"); break;
			case 2:b.setName("短程接送"); break;
			case 3:b.setName("接送机"); break;
			case 4:b.setName("常规路线"); break;
			case 5:b.setName("当地参团"); break;
			case 6:b.setName("游轮"); break;
			case 7:b.setName("景点门票"); break;
			case 8:b.setName("当地玩家"); break;
			case 9:b.setName("旅游定制"); break;
			}
			lists.add(b);
		}
		page.setList(lists);
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(InsuranceRelationMod insuranceRelationMod) {
		super.save(insuranceRelationMod);
	}
	
	@Transactional(readOnly = false)
	public void delete(InsuranceRelationMod insuranceRelationMod) {
		super.delete(insuranceRelationMod);
	}
/**
 * 查找邮轮可买保险
 * @param page
 * @param insuranceRelationMod
 * @return
 */
	public Page<InsuranceRelationMod> findInsruanceRelationModList(Page<InsuranceRelationMod> page,
			InsuranceRelationMod insuranceRelationMod) {
		dataRuleFilter(insuranceRelationMod);
		insuranceRelationMod.setPage(page);
		page.setList(mapper.findInsruanceRelationModList(insuranceRelationMod));
		return page;
	}
/**
 * 查找保险的InsruanceId
 * @param insuranceRelationMod
 * @return
 */
public String findInsruanceId() {
	return mapper.findInsruanceId();
}
/**
 * 查找保险数据
 * @param a
 * @return
 */
public List<Insurance> findInsuranceById(String[] a) {
	return mapper.findInsuranceById(a);
}


}
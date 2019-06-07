/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.memberincome;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.memberincome.MemberIncome;
import com.jeeplus.modules.sys.mapper.memberincome.MemberIncomeMapper;

/**
 * 直客流水记录Service
 * @author dong
 * @version 2019-04-09
 */
@Service
@Transactional(readOnly = true)
public class MemberIncomeService extends CrudService<MemberIncomeMapper, MemberIncome> {

	@Autowired
	private MemberIncomeMapper memberIncomeMapper;
	
	public MemberIncome get(String id) {
		return super.get(id);
	}
	
	public List<MemberIncome> findList(MemberIncome memberIncome) {
		return super.findList(memberIncome);
	}
	/**
	 * 帐户流水接口数据
	 * @param page
	 * @param memberIncome
	 * @return
	 */
	public Page<MemberIncome> findMemberIncomePage(Page<MemberIncome> page, MemberIncome memberIncome) {
		dataRuleFilter(memberIncome);
		memberIncome.setPage(page);
		page.setList(mapper.findMemberIncomePage(memberIncome));
		return page;
	}
	public Page<MemberIncome> findPage(Page<MemberIncome> page, MemberIncome memberIncome) {
		return super.findPage(page, memberIncome);
	}
	
	@Transactional(readOnly = false)
	public void save(MemberIncome memberIncome) {
		super.save(memberIncome);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemberIncome memberIncome) {
		super.delete(memberIncome);
	}
	
	/*获取会员提现流水*/
	public Page<MemberIncome> findTiXianList(Page<MemberIncome> page, MemberIncome memberIncome) {
		dataRuleFilter(memberIncome);
		memberIncome.setPage(page);
		page.setList(memberIncomeMapper.findTiXianList(memberIncome));
		return page;
	}
	
	/*直客流水*/
	public Page<MemberIncome> findZhiKePage(Page<MemberIncome> page, MemberIncome memberIncome) {
		dataRuleFilter(memberIncome);
		memberIncome.setPage(page);
		page.setList(memberIncomeMapper.findMemberIncomeZhiKeList(memberIncome));
		return page;
	}
	
	/*获取会员提现详情*/
	public MemberIncome findMemberIncomeTiXianById(Integer id){
		return mapper.findMemberIncomeTiXianById(id);
	};
}
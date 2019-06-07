/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.income;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.income.Income;
import com.jeeplus.modules.sys.mapper.income.IncomeMapper;

/**
 * 平台流水管理Service
 * @author dongM
 * @version 2019-04-09
 */
@Service
@Transactional(readOnly = true)
public class IncomeService extends CrudService<IncomeMapper, Income> {

	public Income get(String id) {
		return super.get(id);
	}
	
	public List<Income> findList(Income income) {
		return super.findList(income);
	}
	
	public Page<Income> findPage(Page<Income> page, Income income) {
		return super.findPage(page, income);
	}
	
	@Transactional(readOnly = false)
	public void save(Income income) {
		super.save(income);
	}
	
	@Transactional(readOnly = false)
	public void delete(Income income) {
		super.delete(income);
	}
	
	public Page<Income> findIncomeListByMoreParameter(Page<Income> page, Income income) {
		dataRuleFilter(income);
		income.setPage(page);
		page.setList(mapper.findIncomeListByMoreParameter(income));
		return page;
	}
	
	//销售金额
		public Income findIncomeSale(){
			return mapper.findIncomeSale();
		};
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.income;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.income.Income;

/**
 * 平台流水管理MAPPER接口
 * @author dong
 * @version 2019-04-09
 */
@MyBatisMapper
public interface IncomeMapper extends BaseMapper<Income> {
	
	//列表
	public List<Income> findIncomeListByMoreParameter(Income income);
	
	//销售金额
	public Income findIncomeSale();
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.comcurrency;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.comcurrency.ComCurrency;

/**
 * 汇率表MAPPER接口
 * @author cdq
 * @version 2018-08-01
 */
@MyBatisMapper
public interface ComCurrencyMapper extends BaseMapper<ComCurrency> {
    /**
     * 修改状态
     * @param comCurrency
     */
	void status(ComCurrency comCurrency);
	/**
	 * 获取货币接口
	 * @return
	 */
	public List<ComCurrency> getCurrency();
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.comcurrency;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.comcurrency.ComCurrency;
import com.jeeplus.modules.meiguotong.entity.comlanguage.ComLanguage;
import com.jeeplus.modules.meiguotong.mapper.comcurrency.ComCurrencyMapper;

/**
 * 汇率表Service
 * @author cdq
 * @version 2018-08-01
 */
@Service
@Transactional(readOnly = true)
public class ComCurrencyService extends CrudService<ComCurrencyMapper, ComCurrency> {

	public ComCurrency get(String id) {
		return super.get(id);
	}
	/**
	 * 获取货币接口
	 * @return
	 */
	public List<ComCurrency> getCurrency() {
		return mapper.getCurrency();
	}
	public List<ComCurrency> findList(ComCurrency comCurrency) {
		return super.findList(comCurrency);
	}
	
	public Page<ComCurrency> findPage(Page<ComCurrency> page, ComCurrency comCurrency) {
		return super.findPage(page, comCurrency);
	}
	
	@Transactional(readOnly = false)
	public void save(ComCurrency comCurrency) {
		super.save(comCurrency);
	}
	
	@Transactional(readOnly = false)
	public void delete(ComCurrency comCurrency) {
		super.delete(comCurrency);
	}
  /**
   * 修改状态
   * @param comCurrency
   */
	@Transactional(readOnly = false)
	public void status(ComCurrency comCurrency) {
    mapper.status(comCurrency);
		
	}
	
}
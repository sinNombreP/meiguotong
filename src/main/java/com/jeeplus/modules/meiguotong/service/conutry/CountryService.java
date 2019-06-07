/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.conutry;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.conutry.Country;
import com.jeeplus.modules.meiguotong.mapper.conutry.CountryMapper;

/**
 * 国家表Service
 * @author cdq
 * @version 2018-08-09
 */
@Service
@Transactional(readOnly = true)
public class CountryService extends CrudService<CountryMapper, Country> {

	public Country get(String id) {
		return super.get(id);
	}
	/**
	 * 根据语言查询国家城市集合接口
	 * @param country
	 * @return
	 */
	public List<Country> getCountry(Country country) {
		return mapper.getCountry(country);
	}
	/**
	 * 根据语言获取国家列表
	 * @param country
	 * @return
	 */
	public List<Country> getListByLanguage(Country country) {
		return mapper.getListByLanguage(country);
	}
	public List<Country> findList(Country country) {
		return super.findList(country);
	}
	
	public Page<Country> findPage(Page<Country> page, Country country) {
		return super.findPage(page, country);
	}
	
	@Transactional(readOnly = false)
	public void save(Country country) {
		super.save(country);
	}
	
	@Transactional(readOnly = false)
	public void delete(Country country) {
		super.delete(country);
	}
  /**
   * 修改状态
   * @param country
   */
	@Transactional(readOnly = false)
	public void status(Country country) {
		mapper.status(country);
	}
	
}
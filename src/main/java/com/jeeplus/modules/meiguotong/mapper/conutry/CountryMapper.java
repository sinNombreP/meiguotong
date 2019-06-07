/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.conutry;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.conutry.Country;

/**
 * 国家表MAPPER接口
 * @author cdq
 * @version 2018-08-09
 */
@MyBatisMapper
public interface CountryMapper extends BaseMapper<Country> {
   /**
    * 修改状态
    * @param country
    */
	void status(Country country);
	
	/**
	 * 根据语言查询国家城市集合接口
	 * @param country
	 * @return
	 */
	public List<Country> getCountry(Country country);
	/**
	 * 根据语言获取国家列表
	 * @param country
	 * @return
	 */
	public List<Country> getListByLanguage(Country country);
	
}
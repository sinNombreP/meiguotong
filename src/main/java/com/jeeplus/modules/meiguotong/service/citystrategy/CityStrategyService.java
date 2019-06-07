/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.citystrategy;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.citystrategy.CityStrategy;
import com.jeeplus.modules.meiguotong.mapper.citystrategy.CityStrategyMapper;

/**
 * 城市攻略表Service
 * @author cdq
 * @version 2018-08-01
 */
@Service
@Transactional(readOnly = true)
public class CityStrategyService extends CrudService<CityStrategyMapper, CityStrategy> {

	public CityStrategy get(String id) {
		return super.get(id);
	}
	
	public List<CityStrategy> findList(CityStrategy cityStrategy) {
		return super.findList(cityStrategy);
	}
	
	public Page<CityStrategy> findPage(Page<CityStrategy> page, CityStrategy cityStrategy) {
		return super.findPage(page, cityStrategy);
	}
	/**
	 * 获取城市攻略列表
	 * @param page
	 * @param cityStrategy
	 * @return
	 */
	public Page<CityStrategy> findStrategyList(Page<CityStrategy> page, CityStrategy cityStrategy) {
		dataRuleFilter(cityStrategy);
		cityStrategy.setPage(page);
		page.setList(mapper.findStrategyList(cityStrategy));
		return page;
	}
	@Transactional(readOnly = false)
	public void save(CityStrategy cityStrategy) {
		super.save(cityStrategy);
	}
	
	@Transactional(readOnly = false)
	public void delete(CityStrategy cityStrategy) {
		super.delete(cityStrategy);
	}
   /**
    * 更改状态
    * @param cityStrategy
    */
	@Transactional(readOnly = false)
	public void status(CityStrategy cityStrategy) {	
		mapper.status(cityStrategy);
	}
  /**
   * 设置精华状态
   * @param cityStrategy
   */
	@Transactional(readOnly = false)
public void isEssence(CityStrategy cityStrategy) {
        mapper.isEssence(cityStrategy);
}

/**
* @Title: getCityStrategyList
* @Description: 获取攻略列表
* @author  彭善智
* @Data 2018年12月5日下午1:59:30
*/
public List<CityStrategy> getCityStrategyList(CityStrategy cityStrategy) {
	return mapper.getCityStrategyList(cityStrategy);
}
	
}
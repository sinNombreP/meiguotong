/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.citystrategyson;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.citystrategyson.CityStrategySon;
import com.jeeplus.modules.meiguotong.mapper.citystrategyson.CityStrategySonMapper;

/**
 * 攻略子类表Service
 * @author cdq
 * @version 2018-08-01
 */
@Service
@Transactional(readOnly = true)
public class CityStrategySonService extends CrudService<CityStrategySonMapper, CityStrategySon> {
	
	@Autowired
	private CityStrategySonMapper cityStrategySonMapper;
	
	
	public CityStrategySon get(String id) {
		return super.get(id);
	}
	
	public List<CityStrategySon> findList(CityStrategySon cityStrategySon) {
		return super.findList(cityStrategySon);
	}
	
	public Page<CityStrategySon> findPage(Page<CityStrategySon> page, CityStrategySon cityStrategySon) {
		return super.findPage(page, cityStrategySon);
	}
	
	@Transactional(readOnly = false)
	public void save(CityStrategySon cityStrategySon) {
		super.save(cityStrategySon);
	}
	
	@Transactional(readOnly = false)
	public void delete(CityStrategySon cityStrategySon) {
		super.delete(cityStrategySon);
	}
	
	public List<CityStrategySon> findListByStrategyId(CityStrategySon cityStrategySon) {
		return cityStrategySonMapper.findListByStrategyId(cityStrategySon);
	}
	
	
}
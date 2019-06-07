/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.car;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.car.CarTimePrice;
import com.jeeplus.modules.meiguotong.mapper.car.CarTimePriceMapper;

/**
 * 汽车时间表Service
 * @author psz
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class CarTimePriceService extends CrudService<CarTimePriceMapper, CarTimePrice> {

	public CarTimePrice get(String id) {
		return super.get(id);
	}
	/**
	 * 获取车辆某一天的价格
	 * @param carTimePrice
	 * @return
	 */
	public CarTimePrice getPrice(CarTimePrice carTimePrice) {
		return mapper.getPrice(carTimePrice);
	}
	public List<CarTimePrice> findList(CarTimePrice carTimePrice) {
		return super.findList(carTimePrice);
	}
	
	public Page<CarTimePrice> findPage(Page<CarTimePrice> page, CarTimePrice carTimePrice) {
		return super.findPage(page, carTimePrice);
	}
	
	@Transactional(readOnly = false)
	public void save(CarTimePrice carTimePrice) {
		super.save(carTimePrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarTimePrice carTimePrice) {
		super.delete(carTimePrice);
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.car;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.car.CarService;
import com.jeeplus.modules.meiguotong.mapper.car.CarServiceMapper;

/**
 * 汽车标题表Service
 * @author psz
 * @version 2018-08-31
 */
@Service
@Transactional(readOnly = true)
public class CarServiceService extends CrudService<CarServiceMapper, CarService> {

	public CarService get(String id) {
		return super.get(id);
	}
	/**
	 * 获取车辆业务类型
	 * @param carService
	 * @return
	 */
	public List<CarService> selectCarService(CarService carService) {
		return mapper.selectCarService(carService);
	}
	public List<CarService> findList(CarService carService) {
		return super.findList(carService);
	}
	
	public Page<CarService> findPage(Page<CarService> page, CarService carService) {
		return super.findPage(page, carService);
	}
	
	@Transactional(readOnly = false)
	public void save(CarService carService) {
		super.save(carService);
	}
	
	@Transactional(readOnly = false)
	public void delete(CarService carService) {
		super.delete(carService);
	}

	/** 
	* @Title: getTitleData 
	* @Description: 模态框获取标题数据
	* @author 彭善智
	* @date 2018年9月4日上午11:56:03
	*/ 
	public List<CarService> getTitleData(CarService carService) {
		return mapper.getTitleData(carService);
	}
	
}
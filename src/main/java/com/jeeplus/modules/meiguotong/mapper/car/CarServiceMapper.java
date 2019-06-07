/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.car;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.car.CarService;

/**
 * 汽车标题表MAPPER接口
 * @author psz
 * @version 2018-08-31
 */
@MyBatisMapper
public interface CarServiceMapper extends BaseMapper<CarService> {

	/** 
	* @Title: getTitleData 
	* @Description: 模态框获取标题数据
	* @author 彭善智
	* @date 2018年9月4日上午11:56:29
	*/ 
	List<CarService> getTitleData(CarService carService);
	/**
	 * 获取车辆业务类型
	 * @param carService
	 * @return
	 */
	public List<CarService> selectCarService(CarService carService);
	
}
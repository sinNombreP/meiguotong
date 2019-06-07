/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.car;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.car.CarBusiness;

/**
 * 汽车业务表MAPPER接口
 * @author psz
 * @version 2018-08-31
 */
@MyBatisMapper
public interface CarBusinessMapper extends BaseMapper<CarBusiness> {

	/** 
	* @Title: getDataByCarid 
	* @Description: 获取汽车业务
	* @author 彭善智
	* @date 2018年9月3日下午5:04:32
	*/ 
	CarBusiness getDataByCarid(CarBusiness carBusiness);
	
}
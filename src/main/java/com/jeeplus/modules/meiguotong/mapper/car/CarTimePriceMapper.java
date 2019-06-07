/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.car;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.car.CarBusiness;
import com.jeeplus.modules.meiguotong.entity.car.CarTimePrice;

/**
 * 汽车时间表MAPPER接口
 * @author psz
 * @version 2018-08-31
 */
@MyBatisMapper
public interface CarTimePriceMapper extends BaseMapper<CarTimePrice> {

	/** 
	* @Title: getDataByCarBusinessid 
	* @Description: 查询汽车时间
	* @author 彭善智
	* @date 2018年9月3日下午7:15:14
	*/ 
	List<CarTimePrice> getDataByCarBusinessid(CarBusiness carBusiness);

	/** 
	* @Title: deleteByCarid 
	* @Description: 删除包车租车汽车时间表
	* @author 彭善智
	* @date 2018年9月4日下午5:52:54
	*/ 
	void deleteByCarid(String id);
	/**
	 * 获取车辆某一天的价格
	 * @param carTimePrice
	 * @return
	 */
	public CarTimePrice getPrice(CarTimePrice carTimePrice);
	
}
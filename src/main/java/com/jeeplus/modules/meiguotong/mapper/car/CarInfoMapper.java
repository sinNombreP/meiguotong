/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.car;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.car.CarInfo;

/**
 * 租车管理MAPPER接口
 * @author psz
 * @version 2018-08-31
 */
@MyBatisMapper
public interface CarInfoMapper extends BaseMapper<CarInfo> {
	/**
	 * 根据城市及搜索条件获取定制汽车列表
	 * @param car
	 * @return
	 */
	public List<CarInfo> getCarByCity(CarInfo car);
	/**
	 * 根据城市及搜索条件获取短程接送汽车列表
	 * @param car
	 * @return
	 */
	public List<CarInfo> getShortCarByCity( CarInfo car);
	/**
	* @method getCarByRent
	* @Description 包车租车查询车辆列表
	* @Author 彭善智
	* @Date 2019/3/7 15:11
	*/
	List<CarInfo> getCarByRent(CarInfo car);
	/**
	 * 添加汽车编号
	 * @param car
	 */
	public void updateNo(CarInfo car);
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.citystrategyson;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.citystrategyson.CityStrategySon;

/**
 * 攻略子类表MAPPER接口
 * @author cdq
 * @version 2018-08-01
 */
@MyBatisMapper
public interface CityStrategySonMapper extends BaseMapper<CityStrategySon> {
	//获取城市攻略
	List<CityStrategySon> findListByStrategyId(CityStrategySon cityStrategySon);
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.buycar;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.buycar.BuyCar;

/**
 * 购物车MAPPER接口
 * @author dong
 * @version 2018-03-08
 */
@MyBatisMapper
public interface BuyCarMapper extends BaseMapper<BuyCar> {
	public void updateCar(BuyCar buyCar); 

	public void delCar(BuyCar buyCar); 
	
	public void addCar(BuyCar buyCar); 
	
	public Integer getCount(Integer memberId,Integer productId); 
}
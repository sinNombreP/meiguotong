/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.productcar;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.productcar.ProductCar;

/**
 * 购物车MAPPER接口
 * @author dong
 * @version 2018-09-17
 */
@MyBatisMapper
public interface ProductCarMapper extends BaseMapper<ProductCar> {
	/**
	 * 购物车删除（多个id）
	 * @param ids
	 */
	public void deleteCar(String[] ids);
	
	//根据用户ID查询购物列表
		public List<ProductCar> findproductCarListByMemberId(ProductCar productCar);
		
	//根据用户ID查询购物列表
	public List<ProductCar> findproductCarByMemberId(@Param("memberid")Integer id,@Param("languageid")String languageid);
	
	//查询购物车各类型订单数量
	public List<ProductCar> findNumGroupByType(@Param("memberid")Integer id,@Param("languageid")String languageid);
	
	//根据ID查询购物车信息
	public List<ProductCar> findProductCarById(@Param("id")String id);
}
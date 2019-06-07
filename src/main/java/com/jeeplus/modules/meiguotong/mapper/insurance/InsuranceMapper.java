/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.insurance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;

/**
 * 保险表MAPPER接口
 * @author cdq
 * @version 2018-08-15
 */
@MyBatisMapper
public interface InsuranceMapper extends BaseMapper<Insurance> {
   /**
    * 查找保险的数据
    * @param getInsurance
    * @return
    */
	Insurance findByMemberid(Insurance getInsurance);

	/** 
	* @Title: getInfo 
	* @Description: 根据订单ID和type获取保险信息
	* @author 彭善智
	* @date 2018年8月20日上午午10:20:16
	*/ 
	List<Insurance> getInfo(@Param("type") Integer type,@Param("typeId") Integer typeId, @Param("toType")Integer toType);
	
	//购物车订单确认获取保险信息
	public List<Insurance> productCar_findInsurance(@Param("type")Integer type);
	
	/**
	 * 根据语言供应商获取项目关联保险还未选择的保险列表
	 * @param insuranceRelationMod
	 * @return
	 */
	public List<Insurance> getInsuranceRelationMod(InsuranceRelationMod insuranceRelationMod);
	
}
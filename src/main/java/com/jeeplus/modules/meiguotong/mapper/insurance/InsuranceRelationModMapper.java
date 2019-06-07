/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.mapper.insurance;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.entity.insurance.InsuranceRelationMod;

/**
 * 保险项目关联MAPPER接口
 * @author PSZ
 * @version 2018-08-20
 */
@MyBatisMapper
public interface InsuranceRelationModMapper extends BaseMapper<InsuranceRelationMod> {

	/** 
	* @Title: getInfor 
	* @Description: 查询保险信息
	* @author 彭善智
	* @date 2018年8月20日下午3:37:54
	*/ 
	InsuranceRelationMod getInfor(InsuranceRelationMod insuranceRelationMod);
/**
 * 查找邮轮可买保险
 * @param insuranceRelationMod
 * @return
 */
List<InsuranceRelationMod> findInsruanceRelationModList(InsuranceRelationMod insuranceRelationMod);
/**
 * 查找保险的insuranceId
 * @return
 */
String findInsruanceId();
/**
 * 查找保险数据
 * @param a
 * @return
 */
List<Insurance> findInsuranceById(String[] a);

	/**
	 * 获取不同类型保险方案
	 * @param insuranceRelationMod
	 * @return
	 */
	public List<Insurance> getInsurance(InsuranceRelationMod insuranceRelationMod);

}
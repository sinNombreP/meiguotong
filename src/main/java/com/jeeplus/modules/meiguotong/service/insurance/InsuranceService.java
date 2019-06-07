/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.service.insurance;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meiguotong.entity.insurance.Insurance;
import com.jeeplus.modules.meiguotong.mapper.insurance.InsuranceMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 保险表Service
 * @author cdq
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
public class InsuranceService extends CrudService<InsuranceMapper, Insurance> {

	public Insurance get(String id) {
		return super.get(id);
	}
	
	public List<Insurance> findList(Insurance insurance) {
		return super.findList(insurance);
	}
	
	public Page<Insurance> findPage(Page<Insurance> page, Insurance insurance) {
		return super.findPage(page, insurance);
	}
	
	@Transactional(readOnly = false)
	public void save(Insurance insurance) {
		User user = UserUtils.getUser();
		if(user.getUserType()==2) {
			insurance.setStatus(1);
		}
		super.save(insurance);
	}
	
	@Transactional(readOnly = false)
	public void delete(Insurance insurance) {
		super.delete(insurance);
	}
   /**
    * 查找保险的数据
    * @param getInsurance
    * @return
    */
	public Insurance findByMemberid(Insurance getInsurance) {
		return mapper.findByMemberid(getInsurance);
	}

	/** 
	* @Title: getInfo 
	* @Description: 根据订单ID和type获取保险信息
	* @author 彭善智
	* @param type:传入的type类型
	* @param typeId 传入类型的订单ID
	* @param toType:搜索的type类型
	* @date 2018年8月20日上午午10:20:16
	*/ 
	public List<Insurance> getInfo(Integer type,Integer typeId,Integer toType) {
		return mapper.getInfo(type,typeId,toType);
	}

	//购物车订单确认获取保险信息
		public List<Insurance> productCar_findInsurance(Integer type){
			return mapper.productCar_findInsurance(type);
		};
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.coupon;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.coupon.CouponPeople;
import com.jeeplus.modules.sys.mapper.coupon.CouponPeopleMapper;

/**
 * 个人优惠卷Service
 * @author xudemo
 * @version 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class CouponPeopleService extends CrudService<CouponPeopleMapper, CouponPeople> {

	public CouponPeople get(String id) {
		return super.get(id);
	}
	
	public CouponPeople findMemberByCounponId(CouponPeople couponPeople) {
		return mapper.findMemberByCounponId(couponPeople);
	}
	
	public List<CouponPeople> findList(CouponPeople couponPeople) {
		return super.findList(couponPeople);
	}
	
	public Page<CouponPeople> findPage(Page<CouponPeople> page, CouponPeople couponPeople) {
		return super.findPage(page, couponPeople);
	}
	
	@Transactional(readOnly = false)
	public void save(CouponPeople couponPeople) {
		super.save(couponPeople);
	}
	
	@Transactional(readOnly = false)
	public void delete(CouponPeople couponPeople) {
		super.delete(couponPeople);
	}
	
}
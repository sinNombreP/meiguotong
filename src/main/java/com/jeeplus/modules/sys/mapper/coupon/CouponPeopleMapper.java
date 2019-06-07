/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.coupon;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.coupon.CouponPeople;

/**
 * 个人优惠卷MAPPER接口
 * @author xudemo
 * @version 2018-03-08
 */
@MyBatisMapper
public interface CouponPeopleMapper extends BaseMapper<CouponPeople> {
	CouponPeople findMemberByCounponId(CouponPeople couponPeople);
}
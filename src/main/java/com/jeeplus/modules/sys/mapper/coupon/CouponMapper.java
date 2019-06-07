/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper.coupon;

import java.util.List;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.coupon.Coupon;

/**
 * 优惠卷管理MAPPER接口
 * @author xudemo
 * @version 2018-03-08
 */
@MyBatisMapper
public interface CouponMapper extends BaseMapper<Coupon> {
	/**
	 * 更改状态
	 */
	public void status(Coupon coupon);
	/**
	 * 查询二维码是否唯一
	 * @param coupon
	 * @return
	 */
	public Coupon findQRcode(String QRcode);
	
	/**
	 * 获取未失效列表
	 * @param counpon
	 * @return
	 */
	public List<Coupon> findCouponList(Coupon coupon);
	
	/**
	 * 获取系统列表
	 * @param counpon
	 * @return
	 */
	public List<Coupon> findSysCouponLists(Coupon counpon);
	/**
	 * 个人获取优惠卷
	 */
	public void getCoupon(Coupon coupon);
	
	/**
	 * 通过二维码获取到优惠卷信息
	 */
	public Coupon getCouponByQRcode(Coupon coupon);
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.service.coupon;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.entity.coupon.Coupon;
import com.jeeplus.modules.sys.entity.coupon.CouponPeople;
import com.jeeplus.modules.sys.mapper.coupon.CouponMapper;
import com.jeeplus.modules.sys.mapper.coupon.CouponPeopleMapper;

/**
 * 优惠卷管理Service
 * @author xudemo
 * @version 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class CouponService extends CrudService<CouponMapper, Coupon> {

	@Autowired
	private CouponPeopleMapper couponPeopleMapper;
	
	public Coupon get(String id) {
		return super.get(id);
	}
	
	public List<Coupon> findList(Coupon coupon) {
		return super.findList(coupon);
	}
	
	public Page<Coupon> findPage(Page<Coupon> page, Coupon coupon) {
		return super.findPage(page, coupon);
	}
	
	@Transactional(readOnly = false)
	public void save(Coupon coupon) {
		if(coupon.getUseType()==1){
			coupon.setCategory(null);
		}
		if(coupon.getType()==1){//后台推送
			Integer typeWay=coupon.getTypeWay();
			if(typeWay==1){//所有人
				//获取用户
				//给每位用户添加优惠券
				Integer count=1;
				coupon.setAllNum(count);
				coupon.setHaveNum(count);
			}else if(typeWay==2){ //一个月内有交易的会员
				//获取用户
				//给每位用户添加优惠券
				Integer count=1;
				coupon.setAllNum(count);
				coupon.setHaveNum(count);
			}else if(typeWay==3){//本月注册的会员
				//获取用户
				//给每位用户添加优惠券
				Integer count=1;
				coupon.setAllNum(count);
				coupon.setHaveNum(count);
			}
		}else if(coupon.getType()==2){
			coupon.setHaveNum(coupon.getAllNum());
		}
		String QRcode = getQRcode();
		while(mapper.findQRcode(QRcode)!=null){
			QRcode = getQRcode();
		}
		coupon.setQRcode(QRcode);
		super.save(coupon);
	}
	
	/**
	 * 获取我的优惠卷
	 * @param coupon
	 * @return
	 */
	public List<Coupon> findCouponLists(Coupon coupon){
		return mapper.findCouponList(coupon);
	}
	public Page<Coupon> findCouponPage(Page<Coupon> page , Coupon coupon){
		coupon.setPage(page);
		page.setList(this.findCouponLists(coupon));
		return page;
	}
	
	/**
	 * 获取系统优惠卷列表
	 * @param page
	 * @param counpon
	 * @return
	 */
	public Page<Coupon> findSysCouponPage(Page<Coupon> page , Coupon coupon){
		coupon.setPage(page);
		page.setList(mapper.findSysCouponLists(coupon));
		return page;
	}
	
	
	@Transactional(readOnly = false)
	public void delete(Coupon coupon) {
		super.delete(coupon);
	}
	
	/**
	 * 更改状态
	 */
	@Transactional(readOnly = false)
	public void status(Coupon coupon) {
		mapper.status(coupon);
	}
	
	public static String getQRcode(){
		StringBuilder str=new StringBuilder();//定义变长字符串
		Random random=new Random();
		//随机生成数字，并添加到字符串
		for(int i=0;i<8;i++){
		    str.append(random.nextInt(10));
		}
		return str.toString();
	}
	

	@Transactional(readOnly = false)
	public void getCoupon(Coupon coupon,CouponPeople couponPeople) {
		couponPeopleMapper.insert(couponPeople);
		mapper.getCoupon(coupon);
	}
	
	/**
	 * 通过二维码获取到优惠卷信息
	 * @param coupon
	 * @param couponPeople
	 */
	@Transactional(readOnly = false)
	public Coupon getCouponByQRcode(Coupon coupon) {
		return mapper.getCouponByQRcode(coupon);
	}
	
}
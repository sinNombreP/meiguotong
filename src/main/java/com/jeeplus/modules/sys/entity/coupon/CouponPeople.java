/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.coupon;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 个人优惠卷Entity
 * @author xudemo
 * @version 2018-03-08
 */
public class CouponPeople extends DataEntity<CouponPeople> {
	
	private static final long serialVersionUID = 1L;
	private Integer couponid;		// 优惠卷ID
	private Integer status;		// 1.未使用 3.已使用
	private Integer memberid;		// 会员ID
	private Integer getWay;		// 获取方式 1.按指定方式  2.二维码扫码 3.按条件获取，更多条件增加枚举值
	private Date useDate;		// use_date
	private String useOrder;		// 使用订单号
	
	public CouponPeople() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public CouponPeople(String id){
		super(id);
	}

	@ExcelField(title="优惠卷ID", align=2, sort=1)
	public Integer getCouponid() {
		return couponid;
	}

	public void setCouponid(Integer couponid) {
		this.couponid = couponid;
	}
	
	@ExcelField(title="1.未使用 3.已使用", align=2, sort=2)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="会员ID", align=2, sort=3)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="获取方式 1.按指定方式  2.二维码扫码 3.按条件获取，更多条件增加枚举值", align=2, sort=4)
	public Integer getGetWay() {
		return getWay;
	}

	public void setGetWay(Integer getWay) {
		this.getWay = getWay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="use_date", align=2, sort=5)
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	
	@ExcelField(title="使用订单号", align=2, sort=7)
	public String getUseOrder() {
		return useOrder;
	}

	public void setUseOrder(String useOrder) {
		this.useOrder = useOrder;
	}
	
}
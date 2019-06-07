/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;

import java.util.Date;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会员地址Entity
 * @author xudemo
 * @version 2018-02-24
 */
public class MemberAddress extends DataEntity<MemberAddress> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberId;		// 会员ID
	private String address;		// 详细地址
	private String people;		// 收货人姓名
	private String getMobile;		// 联系电话
	private Integer isTop;		// 是否默认，第一个为默认 0.正常 1.默认
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private String country;			// 省
	private String city;			//市
	private String area;			//区

	public String getGetMobile() {
		return getMobile;
	}

	public void setGetMobile(String getMobile) {
		this.getMobile = getMobile;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public MemberAddress() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public MemberAddress(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	@ExcelField(title="地址", align=2, sort=2)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="收货人姓名", align=2, sort=3)
	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}
	
	@ExcelField(title="是否默认，第一个为默认 0.正常 1.默认", align=2, sort=6)
	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}
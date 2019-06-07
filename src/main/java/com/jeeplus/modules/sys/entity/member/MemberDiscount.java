/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.member;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 会员优惠Entity
 * @author psz
 * @version 2018-08-08
 */
public class MemberDiscount extends DataEntity<MemberDiscount> {
	
	private static final long serialVersionUID = 1L;
	private Integer memberid;		// 会员ID
	private Integer type;		// 类型：1包车租车2.短程接送3.接送机
	private Double stock;		// 消费量
	private Double price;		// 价格
	
	public MemberDiscount() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public MemberDiscount(String id){
		super(id);
	}

	@ExcelField(title="会员ID", align=2, sort=1)
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}
	

	@ExcelField(title="类型：1包车租车2.短程接送3.接送机", align=2, sort=2)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="消费量", align=2, sort=3)
	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}
	
	@ExcelField(title="价格", align=2, sort=4)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}
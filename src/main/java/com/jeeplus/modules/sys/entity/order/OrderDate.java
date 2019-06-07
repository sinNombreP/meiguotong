/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.order;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 订单状态时间Entity
 * @author dong
 * @version 2018-03-08
 */
public class OrderDate extends DataEntity<OrderDate> {
	
	private static final long serialVersionUID = 1L;
	private Integer status;		// 订单状态，对应的
	private String typeid;		// ID ,会员ID和的其他ID
	private String memberid;		// 会员ID
	private String orderid;		// 订单ID
	private String name;		// 操作人姓名
	private Integer type;		// 操作人 1.会员 2.后台管理员 3.其他
	private String adminBy;		// 当是管理员的时候，这里记录管理员的ID
	
	public OrderDate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderDate(String id){
		super(id);
	}

	@ExcelField(title="订单状态，对应的", align=2, sort=1)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@ExcelField(title="ID ,会员ID和的其他ID", align=2, sort=2)
	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	
	@ExcelField(title="会员ID", align=2, sort=3)
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	@ExcelField(title="订单ID", align=2, sort=4)
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	@ExcelField(title="操作人姓名", align=2, sort=5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="操作人 1.会员 2.后台管理员 3.其他", align=2, sort=7)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="当是管理员的时候，这里记录管理员的ID", align=2, sort=8)
	public String getAdminBy() {
		return adminBy;
	}

	public void setAdminBy(String adminBy) {
		this.adminBy = adminBy;
	}
	
}
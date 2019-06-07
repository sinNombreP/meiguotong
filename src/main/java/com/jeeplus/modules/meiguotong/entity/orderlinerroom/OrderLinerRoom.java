/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.orderlinerroom;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 评价房间表Entity
 * @author cdq
 * @version 2018-08-15
 */
public class OrderLinerRoom extends DataEntity<OrderLinerRoom> {
	
	private static final long serialVersionUID = 1L;
	private Integer roomId;		// 邮轮房间id
	private Integer adultNum;		// 成人入住数量
	private Integer childrenNum;		// 儿童入住数量
	private Integer roomNum;		// 房间数
	private Integer orderId;		// 邮轮订单id
	private Date delDate;		// del_date
	private String delBy;		// del_by
	private Integer languageId;		// 语言id
	private String name;		// 房间名称
	private String spec;		// 房间规格
	private String floor;		// 房间楼层
	
	/** 主订单id */
	private Integer orderSys1;
	/** 对应业务的订单id */
	private Integer orderSys2;

	/** 房间单价 */
	private java.math.BigDecimal price;
	
	public Integer getOrderSys1() {
		return orderSys1;
	}

	public void setOrderSys1(Integer orderSys1) {
		this.orderSys1 = orderSys1;
	}

	public Integer getOrderSys2() {
		return orderSys2;
	}

	public void setOrderSys2(Integer orderSys2) {
		this.orderSys2 = orderSys2;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	
	public OrderLinerRoom() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public OrderLinerRoom(String id){
		super(id);
	}

	@ExcelField(title="邮轮房间id", align=2, sort=1)
	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	
	@ExcelField(title="成人入住数量", align=2, sort=2)
	public Integer getAdultNum() {
		return adultNum;
	}

	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}
	
	@ExcelField(title="儿童入住数量", align=2, sort=3)
	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}
	
	@ExcelField(title="房间数", align=2, sort=4)
	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}
	
	@ExcelField(title="邮轮订单id", align=2, sort=5)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="del_date", align=2, sort=10)
	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}
	
	@ExcelField(title="del_by", align=2, sort=11)
	public String getDelBy() {
		return delBy;
	}

	public void setDelBy(String delBy) {
		this.delBy = delBy;
	}
	
	@ExcelField(title="语言id", align=2, sort=13)
	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public String getSpec() {
		return spec;
	}

	public String getFloor() {
		return floor;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}
	
}
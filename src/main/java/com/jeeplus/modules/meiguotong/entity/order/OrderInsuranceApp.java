package com.jeeplus.modules.meiguotong.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInsuranceApp {
	private Integer insuranceid;		// 保险ID
	private String name;		// 保险名称
	private String companyName;		// 供应商名称
	private Integer num;		// 预定数量
	private String id;		// id
	private BigDecimal price2;		// 商品单价

	public BigDecimal getPrice2() {
		return price2;
	}

	public void setPrice2(BigDecimal price2) {
		this.price2 = price2;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getInsuranceid() {
		return insuranceid;
	}
	public void setInsuranceid(Integer insuranceid) {
		this.insuranceid = insuranceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}

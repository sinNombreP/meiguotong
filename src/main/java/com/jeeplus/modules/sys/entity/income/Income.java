/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity.income;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.math.BigDecimal;

/**
 * 平台流水管理Entity
 * @author dong
 * @version 2019-04-09
 */
public class Income extends DataEntity<Income> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 流水号 时间搓+4位随机
	private Integer income;		// 1.收入 2.支出
	private Integer type;		// 订单类型：1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家/导游9.酒店10.保险11.旅游定制13.商务定制14.商务旅游
	private Integer orderId;		// 订单Id(关联订单表)
	private Integer payType;		// 支付方式 1支付宝 2微信支付 3银联支付 4Paypal
	private BigDecimal incomePrice;		// 金额
	private Integer status;		// 状态 1待确认 2已完成 3已取消
	
	private Integer memberId;//会员ID
	private String nickName;//昵称
	private String phone;//联系方式
	
	private BigDecimal toDay;//今日订单金额
	private BigDecimal torrow;//昨日订单金额
	private BigDecimal toWeek;//本周订单金额
	private BigDecimal toMonth;//本月订单金额
	private BigDecimal all;//总销售额

	public Income() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Income(String id){
		super(id);
	}

	@ExcelField(title="流水号 时间搓+4位随机", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@ExcelField(title="1.收入 2.支出", align=2, sort=2)
	public Integer getIncome() {
		return income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}
	
	@ExcelField(title="订单类型：1.包车租车2.短程接送3.接送机4常规路线5.当地参团6.游轮7.景点门票8.当地玩家/导游9.酒店10.保险11.旅游定制13.商务定制14.商务旅游", align=2, sort=3)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="订单Id(关联订单表)", align=2, sort=4)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
	
	@ExcelField(title="支付方式 1支付宝 2微信支付 3银联支付 4Paypal", align=2, sort=6)
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	@ExcelField(title="金额", align=2, sort=7)
	public BigDecimal getIncomePrice() {
		return incomePrice;
	}

	public void setIncomePrice(BigDecimal incomePrice) {
		this.incomePrice = incomePrice;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getToDay() {
		return toDay;
	}

	public void setToDay(BigDecimal toDay) {
		this.toDay = toDay;
	}

	public BigDecimal getTorrow() {
		return torrow;
	}

	public void setTorrow(BigDecimal torrow) {
		this.torrow = torrow;
	}

	public BigDecimal getToWeek() {
		return toWeek;
	}

	public void setToWeek(BigDecimal toWeek) {
		this.toWeek = toWeek;
	}

	public BigDecimal getToMonth() {
		return toMonth;
	}

	public void setToMonth(BigDecimal toMonth) {
		this.toMonth = toMonth;
	}

	public BigDecimal getAll() {
		return all;
	}

	public void setAll(BigDecimal all) {
		this.all = all;
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.product;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 参团Entity
 * @author psz
 * @version 2018-08-14
 */
public class RouteDate extends DataEntity<RouteDate> {
	
	private static final long serialVersionUID = 1L;
	private Integer fathid;		// 为0是后台展示 1是其他具体天数的',
	private Integer routeid;		// 路线/参团id
	private Date priceDate;		// 设置价格日期
	private BigDecimal oneProfit;		// 单房利润
	private BigDecimal twoProfit;		// 双人房利润
	private BigDecimal threeProfit;		// 三人房利润
	private BigDecimal fourProfit;		// 四人房利润
	private BigDecimal arrangeProfit;		// 配房利润
	private String priceInfo;		// 价格描述
	private BigDecimal houseOne;		// 单房差
	private Integer stock;		// 库存（-1为无限）
	private BigDecimal oneCost;		// 单房成本
	private BigDecimal twoCost;		// 双人房成本
	private BigDecimal threeCost;		// 三人房成本
	private BigDecimal fourCost;		// 四人房成本
	private BigDecimal arrangeCost;		// 配房成本
	
	private Date beginDate;		// 搜索开始日期
	private Date endDate;		// 搜索结束日期
	private Date startDate;		// 最近可预订日期
	
	private String state;  
	private Date date;  
	private BigDecimal price;
	private Integer num;//已售数量
	
	public RouteDate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getFathid() {
		return fathid;
	}

	public void setFathid(Integer fathid) {
		this.fathid = fathid;
	}

	public RouteDate(String id){
		super(id);
	}

	@ExcelField(title="路线/参团id", align=2, sort=1)
	public Integer getRouteid() {
		return routeid;
	}

	public void setRouteid(Integer routeid) {
		this.routeid = routeid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="设置价格日期", align=2, sort=2)
	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	
	@ExcelField(title="单房利润", align=2, sort=3)
	public BigDecimal getOneProfit() {
		return oneProfit;
	}

	public void setOneProfit(BigDecimal oneProfit) {
		this.oneProfit = oneProfit;
	}
	
	@ExcelField(title="双人房利润", align=2, sort=4)
	public BigDecimal getTwoProfit() {
		return twoProfit;
	}

	public void setTwoProfit(BigDecimal twoProfit) {
		this.twoProfit = twoProfit;
	}
	
	@ExcelField(title="三人房利润", align=2, sort=5)
	public BigDecimal getThreeProfit() {
		return threeProfit;
	}

	public void setThreeProfit(BigDecimal threeProfit) {
		this.threeProfit = threeProfit;
	}
	
	@ExcelField(title="四人房利润", align=2, sort=6)
	public BigDecimal getFourProfit() {
		return fourProfit;
	}

	public void setFourProfit(BigDecimal fourProfit) {
		this.fourProfit = fourProfit;
	}
	
	@ExcelField(title="配房利润", align=2, sort=7)
	public BigDecimal getArrangeProfit() {
		return arrangeProfit;
	}

	public void setArrangeProfit(BigDecimal arrangeProfit) {
		this.arrangeProfit = arrangeProfit;
	}
	
	public String getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(String priceInfo) {
		this.priceInfo = priceInfo;
	}

	@ExcelField(title="单房差", align=2, sort=9)
	public BigDecimal getHouseOne() {
		return houseOne;
	}

	public void setHouseOne(BigDecimal houseOne) {
		this.houseOne = houseOne;
	}
	
	@ExcelField(title="库存（-1为无限）", align=2, sort=10)
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	@ExcelField(title="单房成本", align=2, sort=11)
	public BigDecimal getOneCost() {
		return oneCost;
	}

	public void setOneCost(BigDecimal oneCost) {
		this.oneCost = oneCost;
	}
	
	@ExcelField(title="双人房成本", align=2, sort=12)
	public BigDecimal getTwoCost() {
		return twoCost;
	}

	public void setTwoCost(BigDecimal twoCost) {
		this.twoCost = twoCost;
	}
	
	@ExcelField(title="三人房成本", align=2, sort=13)
	public BigDecimal getThreeCost() {
		return threeCost;
	}

	public void setThreeCost(BigDecimal threeCost) {
		this.threeCost = threeCost;
	}
	
	@ExcelField(title="四人房成本", align=2, sort=14)
	public BigDecimal getFourCost() {
		return fourCost;
	}

	public void setFourCost(BigDecimal fourCost) {
		this.fourCost = fourCost;
	}
	
	@ExcelField(title="配房成本", align=2, sort=15)
	public BigDecimal getArrangeCost() {
		return arrangeCost;
	}

	public void setArrangeCost(BigDecimal arrangeCost) {
		this.arrangeCost = arrangeCost;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
}
/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.guideroute;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 导游路线日期价格设置Entity
 * @author dong
 * @version 2018-09-13
 */
public class GuideRouteDate extends DataEntity<GuideRouteDate> {
	
	private static final long serialVersionUID = 1L;
	private Integer routeid;		// 导游路线id
	private Date priceDate;		// 设置价格日期
	private BigDecimal price;		// 价格
	private Date beginDate;		// 搜索开始日期
	private Date endDate;		// 搜索结束日期
	private Date startDate;		// 最近可预订日期
	
	private Date date;		// 前端插件日期
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public GuideRouteDate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public GuideRouteDate(String id){
		super(id);
	}

	@ExcelField(title="导游路线id", align=2, sort=1)
	public Integer getRouteid() {
		return routeid;
	}

	public void setRouteid(Integer routeid) {
		this.routeid = routeid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="设置价格日期", align=2, sort=2)
	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	
	@ExcelField(title="价格", align=2, sort=3)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
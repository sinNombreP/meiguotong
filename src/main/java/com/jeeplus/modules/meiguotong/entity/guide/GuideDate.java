/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.guide;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 导游日期价格设置Entity
 * @author dong
 * @version 2018-09-12
 */
public class GuideDate extends DataEntity<GuideDate> {
	
	private static final long serialVersionUID = 1L;
	private Integer guideid;		// 导游id
	private Date priceDate;		// 设置价格日期
	private BigDecimal price;		// 价格
	private Integer type;		// 1.当地玩家2.定制旅游-导游3.定制旅游-司兼导4.包车/租车
	private Date beginDate;		// 搜索开始日期
	private Date endDate;		// 搜索结束日期
	private Date startDate;		// 最近可预订日期
	private Date date;		// 前端插件日期
	
	public GuideDate() {
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

	public GuideDate(String id){
		super(id);
	}

	@ExcelField(title="导游id", align=2, sort=1)
	public Integer getGuideid() {
		return guideid;
	}

	public void setGuideid(Integer guideid) {
		this.guideid = guideid;
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
	
	@ExcelField(title="1.当地玩家2.定制旅游-导游3.定制旅游-司兼导4.包车/租车", align=2, sort=4)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
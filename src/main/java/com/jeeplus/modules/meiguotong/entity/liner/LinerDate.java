/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.liner;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 游轮路线日期价格Entity
 * @author dong
 * @version 2018-10-26
 */
public class LinerDate extends DataEntity<LinerDate> {
	
	private static final long serialVersionUID = 1L;
	private Integer lineid;		// 路线id
	private Date startDate;		// 设置价格日期
	private Date date;		// 设置价格日期
	private BigDecimal price;		// 价格
	private Integer state;  //库存
	
	private Date beginDate;
	private Date endDate;
	
	public LinerDate() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public LinerDate(String id){
		super(id);
	}

	@ExcelField(title="路线id", align=2, sort=1)
	public Integer getLineid() {
		return lineid;
	}

	public void setLineid(Integer lineid) {
		this.lineid = lineid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="设置价格日期", align=2, sort=2)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	

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

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
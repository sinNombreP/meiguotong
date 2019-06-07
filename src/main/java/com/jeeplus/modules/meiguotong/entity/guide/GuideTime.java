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
 * 导游日期设置Entity
 * @author dong
 * @version 2018-09-12
 */
public class GuideTime extends DataEntity<GuideTime> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 1.当地玩家2.定制旅游-导游3.定制旅游-司兼导4.包车/租车
	private Integer guideid;		// 导游id
	private Integer dateType;		// 日期类型1.所有日期2.按星期3.按号数
	private Date beginDate;		// 选择所有日期的开始时间
	private Date endDate;		// 选择所有日期的结束时间
	private String weekDate;		// 选择的星期
	private String dayDate;		// 选择的号数
	private BigDecimal price;		// 价格


	public GuideTime() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public GuideTime(String id){
		super(id);
	}

	@ExcelField(title="1.当地玩家2.定制旅游-导游3.定制旅游-司兼导4.包车/租车", align=2, sort=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="导游id", align=2, sort=2)
	public Integer getGuideid() {
		return guideid;
	}

	public void setGuideid(Integer guideid) {
		this.guideid = guideid;
	}
	
	@ExcelField(title="日期类型1.所有日期2.按星期3.按号数", align=2, sort=3)
	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="选择所有日期的开始时间", align=2, sort=4)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="选择所有日期的结束时间", align=2, sort=5)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ExcelField(title="选择的星期", align=2, sort=6)
	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}
	
	@ExcelField(title="选择的号数", align=2, sort=7)
	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}
	
	@ExcelField(title="价格", align=2, sort=8)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
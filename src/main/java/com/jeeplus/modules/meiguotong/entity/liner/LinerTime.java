/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meiguotong.entity.liner;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 游轮路线日期设置Entity
 * @author dong
 * @version 2018-10-26
 */
public class LinerTime extends DataEntity<LinerTime> {
	
	private static final long serialVersionUID = 1L;
	private Integer lineid;		// 路线id
	private Integer dateType;		// 日期类型2.按星期3.按号数
	private Date beginDate;		// 选择所有日期的开始时间
	private Date endDate;		// 选择所有日期的结束时间
	private String weekDate;		// 选择的星期
	private String dayDate;		// 选择的号数
	
	public LinerTime() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public LinerTime(String id){
		super(id);
	}

	@ExcelField(title="路线id", align=2, sort=1)
	public Integer getLineid() {
		return lineid;
	}

	public void setLineid(Integer lineid) {
		this.lineid = lineid;
	}
	
	@ExcelField(title="日期类型2.按星期3.按号数", align=2, sort=2)
	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="选择所有日期的开始时间", align=2, sort=3)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="选择所有日期的结束时间", align=2, sort=4)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@ExcelField(title="选择的星期", align=2, sort=5)
	public String getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(String weekDate) {
		this.weekDate = weekDate;
	}
	
	@ExcelField(title="选择的号数", align=2, sort=6)
	public String getDayDate() {
		return dayDate;
	}

	public void setDayDate(String dayDate) {
		this.dayDate = dayDate;
	}
	
}